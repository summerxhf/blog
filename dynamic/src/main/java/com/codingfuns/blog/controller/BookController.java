package com.codingfuns.blog.controller;

import com.codingfuns.blog.controller.bean.BookVo;
import com.codingfuns.blog.controller.bean.WxRequest;
import com.codingfuns.blog.controller.bean.WxResponse;
import com.codingfuns.blog.entity.Book;
import com.codingfuns.blog.service.BookService;
import com.codingfuns.blog.controller.bean.Article;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class BookController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping("/many-books-little-time")
    public String randomBook(ModelMap modelMap, @ModelAttribute("book") BookVo bookVo) {
        List<BookVo> books;
        int bookCount;
        int tagCount = bookService.getTagCount();
        if (StringUtils.isEmpty(bookVo.getTags())) {
            bookCount = bookService.getBookCount();
            modelMap.put("bookCount", "total books:" + bookCount);
            books = bookService.getRandomBooks();
        } else {
            logger.info("tag:" + bookVo.getTags());
            books = bookService.searchByTag(bookVo.getTags());
            bookCount = books.size();
            if (books.size() < 25) {
                BookVo noticeVo = new BookVo();
                noticeVo.setTitle("-------------------No enough book, recommend these.------------------");
                books.add(noticeVo);
                List<BookVo> randomBooks = bookService.getRandomBooks();
                books.addAll(randomBooks);
            }
        }
        modelMap.put("bookCount", "total books:" + bookCount);
        modelMap.put("tagCount", "total tags:" + tagCount);
        modelMap.put("bookList", books);
        modelMap.put("title", "many books little time");
        return "bookList";
    }

    @RequestMapping("/wxbook/{bookId}")
    public String wxbook(ModelMap modelMap, @PathVariable("bookId") int bookId) {
        try {
            Book book = bookService.find(bookId);
            modelMap.put("book", BookVo.create(book));
            return "wxbook";
        } catch (Exception e) {
            logger.error("wx book error", e);
            return "500";
        }

    }

    @RequestMapping("/wx")
    public void dispatch(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter writer = response.getWriter()) {
            String responseStr;
            response.setCharacterEncoding("utf-8");
            String postData = IOUtils.toString(request.getReader());
            logger.info("postData:" + postData);
            if (StringUtils.isNotEmpty(postData)) {
                responseStr = handle(postData);
                response.setContentType("text/xml;charset=utf-8");
            } else {
                responseStr = verifyUrl(request);
                response.setContentType("text/plain;charset=utf-8");
            }
            writer.println(responseStr);
        } catch (Exception e) {
            logger.error("handle wx request error", e);
        }
    }

    private String verifyUrl(HttpServletRequest request) throws Exception {
        return request.getParameter("echostr");
    }

    private String handle(String postData) throws UnsupportedEncodingException, JAXBException {
        InputStream stream = new ByteArrayInputStream(postData.getBytes(StandardCharsets.UTF_8.name()));
        JAXBContext ctx = JAXBContext.newInstance(WxRequest.class);
        Unmarshaller um = ctx.createUnmarshaller();
        WxRequest request = (WxRequest) um.unmarshal(stream);
        logger.info("request data:" + request);
        WxResponse response = new WxResponse(request);
        logger.info("query by keyword:" + request.getContent());
        List<Article> resultByKeyword = bookService.queryByKeyWord(request.getContent());
        response.addAllArticle(resultByKeyword);
        logger.info("response data:" + response.toString());
        return response.toString();
    }
}

package com.codingfuns.blog.controller;

import com.codingfuns.blog.service.BlogService;
import com.codingfuns.blog.controller.bean.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService service;

    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletResponse response) {
        return getIndexByPageNum(modelMap, "1", response);
    }

    @RequestMapping("/page/{num}")
    public String getIndexByPageNum(ModelMap modelMap, @PathVariable("num") String pageNum, HttpServletResponse response) {
        List<BlogVo> blogVoList = service.getBlogByPageNum(Integer.valueOf(pageNum));
        if (blogVoList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "404";
        }
        int totalPage = service.getTotalPage();
        modelMap.put("blogList", blogVoList);
        modelMap.put("currentPage", pageNum);
        modelMap.put("totalPage", totalPage);
        modelMap.put("title", "www.codingfuns.com");
        return "index";
    }

    @RequestMapping(value="/{title}" ,produces = {"application/json;charset=UTF-8"})
    public String showContent(ModelMap modelMap, @PathVariable("title") String title4url, HttpServletResponse response) throws UnsupportedEncodingException {
//        String title = new String(title4url.getBytes("iso8859-1"),"UTF-8");//解决url中文乱码问题.
        BlogVo blogVo = service.findBlogByTitle(title4url);
        if (blogVo == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "404";
        }
        modelMap.put("blog", blogVo);
        modelMap.put("title", blogVo.getTitle());
        return "content";
    }
}

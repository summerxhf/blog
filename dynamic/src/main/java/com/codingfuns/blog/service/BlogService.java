package com.codingfuns.blog.service;

import com.codingfuns.blog.controller.bean.RecommendBlogVo;
import com.codingfuns.blog.controller.bean.BlogVo;
import com.codingfuns.blog.dao.BlogDao;
import com.codingfuns.blog.entity.Blog;
import com.codingfuns.blog.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class BlogService {
//    private static final String DEFAULT_AD = "<script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>\n" +
//            "<!-- blog-default -->\n" +
//            "<ins class=\"adsbygoogle\"\n" +
//            "     style=\"display:block\"\n" +
//            "     data-ad-client=\"ca-pub-4950190930837228\"\n" +
//            "     data-ad-slot=\"6455464155\"\n" +
//            "     data-ad-format=\"auto\"></ins>\n" +
//            "<script>\n" +
//            "(adsbygoogle = window.adsbygoogle || []).push({});\n" +
//            "</script>";

    @Value("${page.size}")
    private int pageSize;
    @Value("${summary.words}")
    private int summaryWords;

    @Autowired
    private BlogDao blogDao;

    public List<BlogVo> getBlogByPageNum(final int pageNum) {
        List<BlogVo> blogVoList = new LinkedList<>();
        List<Blog> blogList = blogDao.pageQueryBlog(pageSize, pageNum);
        if (blogList != null && blogList.size() > 0) {
            for (Blog blog : blogList) {
                BlogVo vo = new BlogVo();
                vo.setSummary(blog.getSummary());
                vo.setTitle4url(blog.getTitle4url());
                vo.setCreate_date(blog.getCreateDate());
                vo.setTitle(blog.getTitle());
                blogVoList.add(vo);
            }
        }
        return blogVoList;
    }

    public int getTotalPage() {
        return (blogDao.countBlog() + pageSize - 1) / pageSize;
    }

    public BlogVo findBlogByTitle(String title4url) {
        BlogVo blogVo = new BlogVo();
        Blog blog = blogDao.findBlogByUrl(title4url);
        if (blog == null) {
            return null;
        }
        blogVo.setTitle(blog.getTitle());
        blogVo.setCreate_date(blog.getCreateDate());
        blogVo.setContent(blog.getContent());
        blogVo.setAd(blog.getAd());
        blogVo.setTitle4url(blog.getTitle4url());
        int currentId = blog.getId();
        List<Blog> prevNextBlog = blogDao.findBlogByIds(Arrays.asList(currentId - 1, currentId + 1));
        for (Blog pnBlog : prevNextBlog) {
            if (pnBlog.getId() > currentId) {
                blogVo.setNextTitle(pnBlog.getTitle());
                blogVo.setNextTitle4url(pnBlog.getTitle4url());
            } else {
                blogVo.setPrevTitle(pnBlog.getTitle());
                blogVo.setPrevTitle4url(pnBlog.getTitle4url());
            }
        }
        return blogVo;
    }

    public List<String> allBlogGroupByMonth() {
        List<Blog> allBlog = blogDao.getAllBlog();
        Map<String, List<Blog>> monthCountMap = new TreeMap<>();
        for (Blog blog : allBlog) {
            String month = blog.getCreateDate().substring(0, 7);
            if (monthCountMap.get(month) == null) {
                List<Blog> blogList = new LinkedList<>();
                blogList.add(blog);
                monthCountMap.put(month, blogList);
            } else {
                monthCountMap.get(month).add(blog);
            }
        }
        List<String> blogGroupByMonth = new LinkedList<>();
        for (String month : monthCountMap.keySet()) {
            blogGroupByMonth.add(month + " (" + monthCountMap.get(month).size() + ")");
        }
        Collections.reverse(blogGroupByMonth);
        blogGroupByMonth.add("Number of blogs:" + allBlog.size());
        return blogGroupByMonth;
    }

    public String saveBlog(String gitUrl, String hashCode, String ad) {
        if (!gitUrl.startsWith("https://github.com/summerxhf/")) {
            return "500";
        }
        try {
            Document doc = Jsoup.connect(gitUrl).timeout(60 * 1000).get();
            if (!doc.select(".commit-tease-sha").text().trim().equals(hashCode)) {
                return "500";
            }
            String title = doc.select("article h1").first().text();
            String title4url = doc.select("article h1 a").attr("href").substring(1);
            doc.select("article h1").first().remove();
            doc.select(".anchor svg").remove();
            for (Element anchor : doc.select(".anchor")) {
                anchor.attr("id", anchor.attr("href").substring(1));
            }
            String content = doc.select("article").html();
            String summary = doc.select("article p").first().text();
            if (summary.length() > 500) {
                int lastWord = summary.substring(0, 500).lastIndexOf(" ");
                summary = summary.substring(0, lastWord);
            }
            String createDate = DateUtil.getCreateDateString();
            String updateDate = DateUtil.getUpdateDateString();
            Blog blogInDB = blogDao.findBlogByUrl(title4url);
            if (blogInDB != null) {
                blogInDB.setTitle(title);
                blogInDB.setContent(content);
                blogInDB.setSummary(summary);
                blogInDB.setUpdateDate(updateDate);
                blogInDB.setGitUrl(gitUrl);
                blogInDB.setTitle4url(title4url);
                blogDao.update(blogInDB);
            } else {
                Blog tobeSaved = new Blog();
//                int count = blogDao.countBlog();//自增不需要设置id
//                tobeSaved.setId(count + 1);
                tobeSaved.setContent(content);
                tobeSaved.setTitle(title);
                tobeSaved.setTitle4url(title4url);
                tobeSaved.setSummary(summary);
                tobeSaved.setCreateDate(createDate);
                tobeSaved.setUpdateDate(updateDate);
//              tobeSaved.setAd(StringUtils.defaultIfEmpty(ad, DEFAULT_AD));
                tobeSaved.setGitUrl(gitUrl);
                blogDao.save(tobeSaved);
            }
            return title4url;
        } catch (IOException e) {
            e.printStackTrace();
            return "500";
        }
    }

    public List<BlogVo> getAllBlogForSiteMap() {
        List<Blog> blogList = blogDao.getAllBlog();
        List<BlogVo> blogVoList = new LinkedList<>();
        for (Blog blog : blogList) {
            BlogVo vo = new BlogVo();
            vo.setTitle(blog.getTitle());
            vo.setTitle4url(blog.getTitle4url());
            blogVoList.add(vo);
        }
        return blogVoList;
    }

    public List<RecommendBlogVo> getRandomBlog(int number) {
        List<Blog> blogList = blogDao.getRandomBlog(number);
        List<RecommendBlogVo> blogVoList = new LinkedList<>();
        for (Blog blog : blogList) {
            RecommendBlogVo vo = new RecommendBlogVo(blog);
            blogVoList.add(vo);
        }
        return blogVoList;
    }
}

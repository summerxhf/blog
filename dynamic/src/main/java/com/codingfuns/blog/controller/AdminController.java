package com.codingfuns.blog.controller;

import com.codingfuns.blog.service.BlogService;
import com.codingfuns.blog.controller.bean.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BlogService service;

    @RequestMapping("/write")
    public String write(ModelMap modelMap) {
        modelMap.put("blog", new BlogVo());
        modelMap.put("title", "Admin Console");
        modelMap.put("blogCounter", service.allBlogGroupByMonth());
        return "write";
    }

    @RequestMapping("/save/blog")
    public String saveBlog(BlogVo blogVo, HttpServletResponse response) {
        String gitUrl = blogVo.getTitle4url();
        String hashCode = blogVo.getId();
        String target = service.saveBlog(gitUrl, hashCode, blogVo.getAd());
        if(target.equals("500")){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return target;
        }
        return "redirect:/" + target;
    }
}

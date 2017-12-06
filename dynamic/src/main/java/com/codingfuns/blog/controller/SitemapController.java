package com.codingfuns.blog.controller;


import com.codingfuns.blog.service.BlogService;
import com.codingfuns.blog.util.DateUtil;
import com.codingfuns.blog.controller.bean.BlogVo;
import com.codingfuns.blog.xml.XmlUrl;
import com.codingfuns.blog.xml.XmlUrlSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SitemapController {
    private static final String HOST = "http://www.codingfuns.com/";
    private static final String TUTORIAL = "tutorial";
    @Autowired
    private BlogService blogService;

    @RequestMapping("/sitemap.xml")
    @ResponseBody
    public XmlUrlSet main() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        List<BlogVo> blogVoList = blogService.getAllBlogForSiteMap();
        XmlUrl rootXmlUrl = new XmlUrl(HOST, DateUtil.getUpdateDateString());
        rootXmlUrl.setChangefreq(XmlUrl.ChangeFreq.DAILY.getValue());
        rootXmlUrl.setPriority(XmlUrl.Priority.HIGH.getValue());
        xmlUrlSet.addUrl(rootXmlUrl);
        for (BlogVo blogVo : blogVoList) {
            XmlUrl xmlUrl = new XmlUrl(HOST + blogVo.getTitle4url(), blogVo.getUpdate_date());
            if (blogVo.getTitle4url().contains(TUTORIAL)) {
                xmlUrl.setChangefreq(XmlUrl.ChangeFreq.DAILY.getValue());
            }
            xmlUrlSet.addUrl(xmlUrl);
        }
        return xmlUrlSet;
    }
}
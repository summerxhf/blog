package com.codingfuns.blog.xml;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

@XmlRootElement(name = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrlSet implements Serializable{

    @XmlElements({@XmlElement(name = "url", type = XmlUrl.class)})
    private Collection<XmlUrl> xmlUrls = new LinkedList<>();

    public void addUrl(XmlUrl xmlUrl) {
        xmlUrls.add(xmlUrl);
    }

    public Collection<XmlUrl> getXmlUrls() {
        return xmlUrls;
    }
}
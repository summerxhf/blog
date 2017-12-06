package com.codingfuns.blog.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(value = XmlAccessType.NONE)
@XmlRootElement(name = "url")
public class XmlUrl {
    public enum Priority {
        HIGH("1.0"), MEDIUM("0.5");

        private String value;

        Priority(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ChangeFreq {
        WEEKLY("weekly"), DAILY("daily");
        private String value;

        ChangeFreq(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @XmlElement
    private String loc;

    @XmlElement
    private String lastmod;

    @XmlElement
    private String changefreq = ChangeFreq.WEEKLY.getValue();

    @XmlElement
    private String priority = Priority.MEDIUM.getValue();

    public XmlUrl() {
    }

    public XmlUrl(String loc , String lastmod) {
        this.loc = loc;
        this.lastmod = lastmod;
    }

    public String getLoc() {
        return loc;
    }

    public String getPriority() {
        return priority;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public String getLastmod() {
        return lastmod;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

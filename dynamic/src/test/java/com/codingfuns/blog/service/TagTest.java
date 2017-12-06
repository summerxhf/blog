package com.codingfuns.blog.service;

import com.codingfuns.blog.controller.bean.Tags;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TagTest {
    public static void main(String[] args) {
        List<Tags> tags = new LinkedList<>();
        Tags tag1 = new Tags();
        tag1.setCount(1);
        tag1.setName("1");
        tag1.setTitle("title1");
        Tags tag2 = new Tags();
        tag2.setCount(22);
        tag2.setName("2");
        tag2.setTitle("title2");
        Tags tag3 = new Tags();
        tag3.setCount(13);
        tag3.setName("3");
        tag3.setTitle("title3");
        Tags tag4 = new Tags();
        tag4.setCount(24);
        tag4.setName("4");
        tag4.setTitle("title4");
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.sort(Comparator.comparingInt(Tags::getCount).reversed());
        List<String> tagNameList = new LinkedList<>();
        for (Tags dbTag : tags) {
            tagNameList.add(dbTag.getName() + "(" + dbTag.getCount() + ")");
        }
        String result = StringUtils.join(tagNameList, ",");
        System.out.println(result);
    }
}

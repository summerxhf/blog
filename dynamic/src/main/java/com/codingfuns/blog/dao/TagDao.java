package com.codingfuns.blog.dao;

import com.codingfuns.blog.entity.Tag;
import com.codingfuns.blog.entity.TagExample;
import com.github.pagehelper.PageHelper;
import com.codingfuns.blog.mapper.TagMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagDao {

    @Autowired
    private TagMapper tagMapper;

    public void batchSave(List<String> tags) {
        for (String tagName : tags) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tag.setUpdateTime(new Date());
            tag.setPageNum(-1);
            save(tag);
        }
    }

    private void save(Tag tag) {
        if (tag == null || StringUtils.isEmpty(tag.getTagName())) {
            return;
        }
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andTagNameEqualTo(tag.getTagName());
        PageHelper.startPage(1, 1, false);
        List<Tag> tags = tagMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(tags)) {
            tagMapper.insertSelective(tag);
        }
    }

    public Tag getOldest() {
        TagExample example = new TagExample();
        example.setOrderByClause("update_time ASC");
        example.setDistinct(true);
        PageHelper.startPage(1, 1, false);
        List<Tag> tags = tagMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(tags)) {
            return null;
        }
        return tags.get(0);
    }

    public void updateByTagName(Tag oldestTag) {
        oldestTag.setCombinedName(null);
        tagMapper.updateByPrimaryKey(oldestTag);
    }

    @Deprecated
    public void deleteDuplicate(Tag targetTag) {
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andTagNameEqualTo(targetTag.getTagName());
        criteria.andIdNotEqualTo(targetTag.getId());
        tagMapper.deleteByExample(example);
    }

    public int count() {
        TagExample example = new TagExample();
        return (int) tagMapper.countByExample(example);
    }

    public void delete(Tag tobeUpdatedTag) {
        if (tobeUpdatedTag == null || tobeUpdatedTag.getId() == null) {
            return;
        }
        tagMapper.deleteByPrimaryKey(tobeUpdatedTag.getId());
    }
}

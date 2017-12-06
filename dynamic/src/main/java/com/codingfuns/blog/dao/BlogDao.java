package com.codingfuns.blog.dao;

import com.codingfuns.blog.entity.Blog;
import com.codingfuns.blog.entity.BlogExample;
import com.codingfuns.blog.mapper.BlogMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogDao {

    @Autowired
    private BlogMapper blogMapper;

    public List<Blog> pageQueryBlog(int pageSize, int pageNum) {
        BlogExample example = new BlogExample();
        example.setOrderByClause("id DESC");
        PageHelper.startPage(pageNum, pageSize, false);
        return blogMapper.selectByExample(example);
    }

    public List<Blog> getAllBlog() {
        BlogExample example = new BlogExample();
        return blogMapper.selectByExample(example);
    }

    public int countBlog() {
        BlogExample example = new BlogExample();
        return (int) blogMapper.countByExample(example);
    }


    public Blog findBlogByUrl(String title4url) {
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andTitle4urlEqualTo(title4url);
        List<Blog> blogList = blogMapper.selectByExample(example);
        if (blogList != null && !blogList.isEmpty()) {
            return blogList.get(0);
        }
        return null;
    }

    public List<Blog> findBlogByIds(List<Integer> integers) {
        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(integers);
        return blogMapper.selectByExample(example);
    }

    public void update(Blog blogInDB) {
        blogMapper.updateByPrimaryKey(blogInDB);
    }

    public void save(Blog tobeSaved) {
        blogMapper.insert(tobeSaved);
    }

    public List<Blog> getRandomBlog(int number) {
        BlogExample example = new BlogExample();
        example.setOrderByClause("random()");
        PageHelper.startPage(1, number, false);
        return blogMapper.selectByExample(example);
    }
}

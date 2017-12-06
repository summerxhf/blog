package com.codingfuns.blog.intercepter;

import com.codingfuns.blog.controller.bean.BlogVo;
import com.codingfuns.blog.controller.bean.RecommendBlogVo;
import com.codingfuns.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommonInfoInterceptor extends HandlerInterceptorAdapter {
    private ThreadLocal<Long> timeCost = new ThreadLocal<>();
    private LinkedHashSet<RecommendBlogVo> recommend = new LinkedHashSet<>();
    private static Map<String, Long> currentOnline = new ConcurrentHashMap<>();

    static {
        Runnable runnable = new Runnable() {
            public void run() {
                List<String> pastUser = new LinkedList<>();
                for (String user : currentOnline.keySet()) {
                    if (System.currentTimeMillis() - currentOnline.get(user) > TimeUnit.MINUTES.toMillis(10)) {
                        pastUser.add(user);
                    }
                }
                for (String user : pastUser) {
                    currentOnline.remove(user);
                }
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
    }

    @Value("${popular.post.num}")
    private int popularNum = 10;

    @Autowired
    private BlogService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Long now = System.currentTimeMillis();
        currentOnline.put(request.getSession().getId(), now);
        timeCost.set(now);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        if (recommend.size() == 0) {
            recommend.addAll(service.getRandomBlog(popularNum - 1));
        }
        if (modelAndView.getModel() != null
                && modelAndView.getModel().get("blog") != null) {
            BlogVo blogVo = (BlogVo) modelAndView.getModel().get("blog");
            if (blogVo.getTitle4url() != null) {
                recommend.add(new RecommendBlogVo(blogVo));
                if (recommend.size() > popularNum) {
                    recommend.remove(recommend.iterator().next());
                }

            }
        }
        modelAndView.addObject("posts", recommend);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (currentYear == 2016) {
            request.setAttribute("year", "2016");
        } else {
            request.setAttribute("year", "2016-" + currentYear);
        }
        request.setAttribute("used", (System.currentTimeMillis() - timeCost.get()) / 1000.0);
        request.setAttribute("online", currentOnline.size());
    }
}

<%--@elvariable id="book" type="com.codingfuns.blog.controller.bean.BookVo"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>${book.title}</title>
    <link href="<c:url value="//res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css"/>" rel="stylesheet">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <script type="text/javascript" src="<c:url value="/js/jquery-1.12.2.mina.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-81504596-1', 'auto');
        ga('send', 'pageview');
    </script>
    <style>
        body {
            background-color: #efeff4;
        }

        .book-title {
            margin: 30px 0;
            font-weight: 400;
            font-size: 34px;
            color: #3cc51f;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div id="content" class="col-xs-12">
            <h1 class="book-title">
                <span>${book.title}</span>
            </h1>
        </div>
    </div>
    <img style="text-align: center" src="http://img3.doubanio.com/mpic/s${book.imageId}.jpg">
    <p>${book.readerNum}人读过，评分${book.score}</p>
    <p>页数:${book.pages}</p>
    <p>作者：${book.author}</p>
    <p>译者：${book.translator}</p>
    <p>出版社：${book.publisher}</p>
    <p>出版日期：${book.pubDate}</p>
    <p>相关标签：${book.tags}</p>
    <div class="col-xs-12 text-center">
        <p>
            <a href="https://book.douban.com/subject/${book.id}/" class="btn btn-info btn-block"
               role="button">去豆瓣看看别人怎么评价</a>
        </p>
        <p>
            <a href="https://www.baidu.com/s?wd=${book.title}+下载" class="btn btn-info btn-block"
               role="button">去网上搜索《${book.title}》的下载资源</a>
        </p>
    </div>
    <div class="col-xs-12">
        <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
        <!-- blog-wx book -->
        <ins class="adsbygoogle"
             style="display:block"
             data-ad-client="ca-pub-4950190930837228"
             data-ad-slot="5710707163"
             data-ad-format="auto"></ins>
        <script>
            (adsbygoogle = window.adsbygoogle || []).push({});
        </script>
    </div>
</div>
</body>
</html>

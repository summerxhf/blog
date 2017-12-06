<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <div class="row">
        <div id="content" class="col-lg-8">
            <h1 class="under_line">
                <span>${blog.title}</span>
                <span class="text-right badge">${blog.create_date}</span>
            </h1>
            ${blog.content}
            <!-- google ad begin-->
            ${blog.ad}
            <!-- google ad end-->
            <div class="blog-div">
                <span>
                    <c:if test="${blog.prevTitle4url!=null}">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                        <a class="paging" href="${blog.prevTitle4url}" title="${blog.prevTitle}">PREVIOUS</a>
                    </c:if>
                </span>
                <span class="next-blog">
                    <c:if test="${blog.nextTitle4url!=null}">
                        <a class="paging" href="${blog.nextTitle4url}" title="${blog.nextTitle}">NEXT</a>
                        <span class="glyphicon glyphicon-chevron-right" ></span>
                    </c:if>
                </span>
            </div>

            <!-- disqus begin -->
            <div id="disqus_thread"></div>
            <script>
                var disqus_config = function () {
                    this.page.url = "http://www.codingfuns.com/" + "${blog.title4url}";
                    this.page.identifier = '${blog.id}';
                };
                (function () { // DON'T EDIT BELOW THIS LINE
                    var d = document, s = d.createElement('script');

                    s.src = '//henryxi.disqus.com/embed.js';

                    s.setAttribute('data-timestamp', +new Date());
                    (d.head || d.body).appendChild(s);
                })();
            </script>
            <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments
                powered by Disqus.</a></noscript>
        </div>
        <!-- disqus end -->
        <div id="sidebar" class="col-lg-4">
            <jsp:include page="common/about.jsp"/>
            <!-- google ad begin-->
            ${blog.ad}
            <!-- google ad end-->
            <div class="row profile">
                <h4>Popular posts</h4>
                <c:forEach var="post" items="${posts}">
                    <div class="caption">
                        <p>
                            <a class="recommend_link" href="/${post.url}" title="${post.title}">${post.prettyTitle}</a>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <!-- google ad begin-->
            ${blog.ad}
            <!-- google ad end-->
        </div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
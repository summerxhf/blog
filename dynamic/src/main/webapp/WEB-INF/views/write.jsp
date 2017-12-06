<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-2 list-group">
            <c:forEach var="counter" items="${blogCounter}">
                <span class="list-group-item list">${counter}</span>
            </c:forEach>
        </div>
        <div id="content" class="col-lg-10">
            <form:form commandName="blog" modelAttribute="blog" action="/admin/save/blog"
                       method="post">
                <fieldset>
                    <legend>import blog</legend>
                    <p>
                        <label for="title4url">Title for url:</label>
                        <form:input id="title4url" path="title4url" cssClass="form-control"/>
                    </p>
                    <p>
                        <label for="id">Hash code:</label>
                        <form:input id="id" path="id" cssClass="form-control"/>
                    </p>
                    <p>
                        <label for="ad">Ad:</label>
                        <form:textarea id="ad" path="ad" cssClass="form-control"
                                       cssStyle="min-height: 300px"/>
                    </p>
                    <p>
                        <input id="create" type="submit" value="Import" class="btn btn-info"/>
                    </p>
                </fieldset>
            </form:form>
            <!-- disqus begin -->
            <div id="disqus_thread"></div>
            <script>
                var disqus_config = function () {
                    this.page.url = "http://www.codingfuns.com/admin/picture";
                    this.page.identifier = 'picture';
                };
                (function () { // DON'T EDIT BELOW THIS LINE
                    var d = document, s = d.createElement('script');

                    s.src = '//henryxi.disqus.com/embed.js';

                    s.setAttribute('data-timestamp', +new Date());
                    (d.head || d.body).appendChild(s);
                })();
            </script>
            <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript"
                                                              rel="nofollow">comments
                powered by Disqus.</a></noscript>
        </div>
        <div></div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>

<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <c:forEach var="blog" items="${blogList}" varStatus="pageStatus">
                <div class="caption">
                    <h3>
                        <a href="/${blog.title4url}">${blog.title}</a>
                    </h3>
                    <p class="under_line">${blog.summary}</p>
                </div>
            </c:forEach>
        </div>
        <div id="sidebar" class="col-lg-4">
            <jsp:include page="common/about.jsp"/>
            <!-- google ad begin-->

            <!-- google ad end-->
            <div class="row profile">
                <h4>Popular posts</h4>
                <c:forEach var="post" items="${posts}">
                    <div class="caption">
                        <p>
                            <a class="recommend_link" href="/${post.url}" title="${post.title}">${post.title}</a>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <!-- google ad begin-->

            <!-- google ad end-->
        </div>
    </div>
    <ul class="pagination">
        <c:forEach begin="1" end="${totalPage}" var="page">
            <c:choose>
                <c:when test="${currentPage==page}">
                    <li class="active"><a>
                </c:when>
                <c:otherwise>
                    <li><a href="/page/${page}">
                </c:otherwise>
            </c:choose>
            ${page}</a></li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
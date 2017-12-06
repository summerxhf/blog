<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <div class="row">
        <div id="content" class="col-lg-12">
            <%--@elvariable id="book" type="com.codingfuns.blog.controller.bean.BookVo"--%>
            <form:form modelAttribute="book" action="/many-books-little-time" method="post" >
                <fieldset>
                    <span class="list-group-item book-list col-lg-6">
                        <form:input placeholder="search by fuzzy tag" id="tag" path="tags" cssClass="form-control"/>
                    </span>
                    <span class="list-group-item book-list col-lg-6">
                        <input id="search" type="submit" value="search" class="btn btn-info"/>
                    </span>
                </fieldset>
            </form:form>
            <span class="list-group-item book-list col-lg-12">${bookCount},${tagCount}</span>
            <span class="list-group-item book-list-bg-color col-lg-1">readers</span>
            <span class="list-group-item book-list-bg-color col-lg-1">score</span>
            <span class="list-group-item book-list-bg-color col-lg-8">title</span>
            <span class="list-group-item book-list-bg-color col-lg-2">search url</span>
            <c:forEach var="book" items="${bookList}" varStatus="index">
                <c:choose>
                    <c:when test="${index.index%2==1}">
                        <span class="list-group-item book-list-bg-color col-lg-1">${book.readerNum}</span>
                        <span class="list-group-item book-list-bg-color col-lg-1">${book.score}</span>
                        <span class="list-group-item book-list-bg-color col-lg-8">
                            <a href="https://book.douban.com/subject/${book.id}/" title="${book.tags}" target="_blank">${book.title}</a>
                        </span>
                        <span class="list-group-item book-list-bg-color col-lg-2">
                            <a href="https://www.google.com/search?q=${book.title}+下载" target="_blank" >want to read</a>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="list-group-item book-list col-lg-1">${book.readerNum}</span>
                        <span class="list-group-item book-list col-lg-1">${book.score}</span>
                        <span class="list-group-item book-list col-lg-8">
                            <a href="https://book.douban.com/subject/${book.id}/" title="${book.tags}" target="_blank">${book.title}</a>
                        </span>
                        <span class="list-group-item book-list col-lg-2">
                            <a href="https://www.google.com/search?q=${book.title}+下载" target="_blank" >want to read</a>
                        </span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <div></div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>

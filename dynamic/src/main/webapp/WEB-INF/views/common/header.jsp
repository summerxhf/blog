<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/reset.css"/>" rel="stylesheet">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <script type="text/javascript" src="<c:url value="/js/common.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-1.12.2.mina.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
    <link href="<c:url value="/css/prettify.css"/>" type="text/css" rel="stylesheet" />
    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-81504596-1', 'auto');
        ga('send', 'pageview');
    </script>
</head>
<body>
<div id="navbar" class="navbar navbar-inverse bs-docs-nav">
    <div class="container"><a href="/" class="navbar-brand">codingfuns</a>
        <div class="collapse navbar-collapse bs-navbar-collapse">
            <%--<ul class="nav navbar-nav">--%>
                <%--<li class="dropdown">--%>
                    <%--<a href="#" data-toggle="dropdown" class="dropdown-toggle" aria-expanded="true">Database<span--%>
                            <%--class="caret"></span></a>--%>
                    <%--<ul role="menu" class="dropdown-menu">--%>
                        <%--<li>--%>
                            <%--<a role="menuitem" href="http://www.codingfuns.com/mongodb-tutorial">MongoDB</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a role="menuitem" href="http://www.codingfuns.com/relational-database-tutorial">Relational DB</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
                <%--<li class="dropdown">--%>
                    <%--<a href="#" data-toggle="dropdown" class="dropdown-toggle" aria-expanded="true">Java<span--%>
                            <%--class="caret"></span></a>--%>
                    <%--<ul role="menu" class="dropdown-menu">--%>
                        <%--<li>--%>
                            <%--<a role="menuitem" href="http://www.codingfuns.com/java-core-tutorial">Java Core</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a role="menuitem" href="http://www.codingfuns.com/spring-boot-tutorial">Spring Boot</a>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                            <%--<a role="menuitem" href="http://www.codingfuns.com/mybatis-tutorial">MyBatis</a>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
                <%--<li><a href="http://www.codingfuns.com/front-end-tutorial">Front End</a></li>--%>
                <%--<li><a href="http://www.codingfuns.com/design-pattern-tutorial">Design Pattern</a></li>--%>
                <%--<li><a href="http://www.codingfuns.com/linux-often-used-commands-tutorial">Linux</a>--%>
                <%--<li><a href="http://www.codingfuns.com/book-list">Books</a></li>--%>
            <%--</ul>--%>
            <form class="navbar-form pull-right" method="get"
                  onsubmit="site_search();return false;">
                <input placeholder="Search in codingfuns.com" type="text" class="form-control" name="q"
                       id="q" style="width: 250px">
                <button type="submit" class="btn btn-default">Search</button>
            </form>
        </div>
    </div>
</div>
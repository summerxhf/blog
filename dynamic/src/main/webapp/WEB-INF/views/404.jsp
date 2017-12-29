<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/header.jsp"/>
<div class="container">
    <div class="row">
        <script language="JavaScript">
//            var current=10;
//            var timerId=window.setInterval(function(){
//                if(current==1){
//                    clearInterval(timerId);
//                    window.location.replace('http://www.codingfuns.com');
//                }
//                current=current-1;
//                document.getElementById('second').text=current;
//            }, 1000);
        </script>
        <div class="col-lg-9">
            <h2><span>Page Not Found(404) Error</span></h2>
            <p>We couldn't find the page you requested.This page will redirect to <a href="http://www.codingfuns.com">Home</a> in <a id="second">10</a> seconds</p>
            <!-- google ad begin-->

            <!-- google ad end-->
        </div>
        <div id="sidebar" class="col-lg-3">
            <jsp:include page="common/about.jsp"/>
            <!-- google ad begin-->

            <!-- google ad end-->
        </div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
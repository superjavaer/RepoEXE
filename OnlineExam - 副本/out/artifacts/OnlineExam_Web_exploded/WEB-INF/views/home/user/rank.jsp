<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示页面</title>
    <%--    导入BootStrap在线cdn美化页面--%>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="row clearfix" align="center">

        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>测试排名</small>
                </h1>
            </div>
        </div>

        <div class="row">

            <div class="col-md-4 column">
            </div>

            <div class="col-md-8 column">
                <form action="${pageContext.request.contextPath}/home/user/rank"  method="get" style="float: right" class="form-inline">
                    <input type="text" name="querytestName" class="form-control" placeholder="输入测试名称">
                    <input type="text" name="queryDetail" class="form-control" placeholder="输入班级">
                    <input type="submit" value="查询" class="btn btn-primary">
                </form>
            </div>

        </div>
    </div>

    <%--展示书籍功能--%>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>测试名称</th>
                    <th>班级</th>
                    <th>姓名</th>
                    <th>分数</th>
                    <th>排名</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="list" items="${list}" varStatus="s">
                    <tr>
                        <td>${list.testName}</td>
                        <td>${list.subject}</td>
                        <td>${list.name}</td>
                        <td>${list.score}</td>
                        <td>${s.count}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


</div>
</body>
</html>

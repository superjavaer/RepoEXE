<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/5/24
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
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
                    <small>书籍列表</small>
                </h1>
            </div>
        </div>

        <div class="row">
             <div class="col-md-4 column" style="float: left">
                 <a  class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>
             </div>

            <div class="col-md-4 column">
            </div>

            <div class="col-md-7 column">
                <form action="${pageContext.request.contextPath}/book/queryBook" method="post" style="float: right" class="form-inline">
                    <input type="text" name="queryBookName" class="form-control" placeholder="输入书籍名称">
                    <input type="text" name="queryDetail" class="form-control" placeholder="输入书籍详情">
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
                    <th>书籍编号</th>
                    <th>书籍名称</th>
                    <th>书籍数量</th>
                    <th>书籍详情</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                   <c:forEach var="book" items="${list}">
                       <tr>
                           <td>${book.bookID}</td>
                           <td>${book.bookName}</td>
                           <td>${book.bookCounts}</td>
                           <td>${book.detail}</td>
                           <td>
                               <a href="${pageContext.request.contextPath}/book/toUpdate?id=${book.bookID}">修改</a> &nbsp;&nbsp;
                               <a href="${pageContext.request.contextPath}/book/deleteById?id=${book.bookID}">删除</a>
                           </td>
                       </tr>
                   </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


</div>
</body>
</html>

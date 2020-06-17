<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/5/24
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
    <style>
      a{
        text-decoration: none;
        color: black;
        font-size: 18px;
      }

      h3{
        width: 240px;
        height: 38px;
        margin:100px auto;
        text-align: center;
        /*字体垂直居中标签，设置line-height与高度相同*/
        line-height: 38px;
        background: deepskyblue;
        border-radius: 10px;
      }
    </style>
  </head>


  <body>
  <h3>
    <a href="${pageContext.request.contextPath}/book/allBook">免登陆进入书籍查看页面</a>
  </h3>
  </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: kimnamhyun
  Date: 2022/10/22
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Write</h1>
<form action="<c:url value="/board/write"/>?page=${page}" method="post">
    <table border="1" cellpadding="0" cellspacing="0">
        <tr>
            <td bgcolor="orange" width="70">제목</td>
            <td align="left">
                <input name="title" type="text">
            </td>
        </tr>
        <tr>
            <td bgcolor="orange">작성자</td>
            <td align="left">
                <input name="writer" type="text">
            </td>
        </tr>
        <tr>
            <td bgcolor="orange">내용</td>
            <td align="left">
                <textarea name="content" cols="40" rows="10"></textarea>
            </td>
        </tr>
    </table>
    <button type="submit">전송</button>
</form>
<a href="<c:url value="/board/list"/>?page=${page}">글리스트로</a>
</body>
</html>

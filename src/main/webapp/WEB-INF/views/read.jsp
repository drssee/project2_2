<%--
  Created by IntelliJ IDEA.
  User: kimnamhyun
  Date: 2022/10/22
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Write</h1>
    <table border="1" cellpadding="0" cellspacing="0">
        <tr>
            <td bgcolor="orange" width="70">제목</td>
            <td align="left">
                <input name="title" type="text" value="${board.title}" readonly>
            </td>
        </tr>
        <tr>
            <td bgcolor="orange">작성자</td>
            <td align="left">${board.writer}</td>
        </tr>
        <tr>
            <td bgcolor="orange">내용</td>
            <td align="left">
                <textarea name="content" cols="40" rows="10" readonly>${board.content}</textarea>
            </td>
        </tr>
        <tr>
            <td bgcolor="orange">등록일</td>
            <td align="left">
                <fmt:formatDate value="${board.regDate}" pattern="yyyy/MM/dd" />
            </td>
        </tr>
        <tr>
            <td bgcolor="orange">수정일</td>
            <td align="left">
                <fmt:formatDate value="${board.modDate}" pattern="yyyy/MM/dd" />
            </td>
        </tr>
    </table>
    <input name="seq" type="hidden" value="${board.bno}" />
<a href="<c:url value="/board/list"/>?page=${page}">글리스트로</a>
<a href="<c:url value="/board/modify"/>?bno=${board.bno}&page=${page}">수정/삭제</a>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: kimnamhyun
  Date: 2022/10/22
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
  <style>
    .sel {
      text-decoration: underline;
      color: red;
    }
  </style>
</head>
<body>
<h1>Board</h1>
<a href="<c:url value="/board/write"/>?page=${pageResponse.page}">글쓰기</a>
<table border="1" cellpadding="0" cellspacing="0" width="700">
  <tr>
    <th bgcolor="orange" width="100">bno</th>
    <th bgcolor="orange" width="200">title</th>
    <th bgcolor="orange" width="150">writer</th>
    <th bgcolor="orange" width="150">regDate</th>
    <th bgcolor="orange" width="100">modDate/th>
  </tr>
  <c:forEach items="${pageResponse.pageList}" var="board">
    <tr>
      <td>${board.bno}</td>
      <td align="left">
        <a href="<c:url value="/board/read"/>?bno=${board.bno}&page=${pageResponse.page}">
            ${board.title}
        </a>
      </td>
      <td>${board.writer}</td>
      <td>
        <fmt:formatDate value="${board.regDate}" pattern="yyyy/MM/dd" />
      </td>
      <td>
        <fmt:formatDate value="${board.modDate}" pattern="yyyy/MM/dd" />
      </td>
    </tr>
  </c:forEach>
  <div class="nav">
    <ul>
      <c:if test="${pageResponse.showPrev}">
        <a href="<c:url value="/board/list?page=${pageResponse.page-1}&size=${pageResponse.size}"/>">
          [PREV]
        </a>
      </c:if>
      <c:forEach begin="${pageResponse.start}" end="${pageResponse.end}" var="num">
        <a href="<c:url value="/board/list?page=${num}&size=${pageResponse.size}"/>">
          <span class="${num==pageResponse.page?'sel':''}">${num}</span>
        </a>
      </c:forEach>
      <c:if test="${pageResponse.showNext}">
        <a href="<c:url value="/board/list?page=${pageResponse.page+1}&size=${pageResponse.size}"/>">
          [NEXT]
        </a>
      </c:if>
    </ul>
  </div>
</table>
</body>
</html>

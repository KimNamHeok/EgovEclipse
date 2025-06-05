<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post">
	<input type="number" name="op1" />
		+
	<input type="number" name="op2" />
	<button type="submit">전송</button>
	<c:if test="${not empty result }">
	연산결과 : ${result }
	</c:if>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="cal" method="post">
	<form:input path="op1" type="number"/>
	<form:errors path="op1" cssClass="error"/>
		+
	<form:input path="op2" type="number"/>
	<form:errors path="op2" cssClass="error"/>
	<button type="submit">전송</button>
</form:form>
	<c:if test="${not empty cal }">
	연산결과 : ${cal.result }
	</c:if>
</form>
</body>
</html>
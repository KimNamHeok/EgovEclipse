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
<form:form modelAttribute="dummy"
action="${pageContext.request.contextPath }/case07/commandObject02" method="get" >
<pre>
	<form:input path="name"/>
	<form:errors path="name"/>
	<form:input path="age" type="number"/>
	<form:errors path="age"/>
	<form:checkbox path="hobbies" value="취미1" label="취미1" />
	<form:checkbox path="hobbies" value="취미2" label="취미2" />
	<form:errors path=".hobbies" />
	
	<button type="submit">전송</button>
</pre>
</form:form>
</form>
</body>
</html>
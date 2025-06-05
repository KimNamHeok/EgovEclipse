<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.uploaded{
		background-color: blue;
	}
</style>
</head>
<body>
<form method="post" enctype="multipart/form-data">
	<input type="file" name="uploadFile" />
	<button type="submit">업로드</button>
</form>
<ul>
	<c:forEach items="${fileNames }" var="file">
		<li class="${file eq uploaded ? 'uploaded':'normal'}">
		<a href="<c:url value='/case10/files/${file }' />">${file }</a>
		<button class="del" data-name="${file }">삭제</button>
		</li>
	</c:forEach>
</ul>
<script>
	document.addEventListener("DOMContentLoaded", ()=>{
		$(".del").on("click", async (e)=>{
			if(!confirm("지울래?")) return;
			
			const name = e.target.dataset.name;
			const resp = await axios.delete(`<c:url value="/case10/files/\${name }" />`);
			const {success, target} = resp.data;
			if(success){
				$(e.target).closest("li").remove();
			}
		});
	});
</script>
</body>
</html>
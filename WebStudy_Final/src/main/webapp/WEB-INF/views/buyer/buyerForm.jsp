<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src="<c:url value='/js/app/buyer/buyerForm.js'/>"></script>
</head>
<body>
  <div class="card">
    <div class="card-header">
      <h5>Form controls</h5>
    </div>
    <div class="card-body">
      <div class="row">
        <form:form modelAttribute="buyer" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label class="form-label" for="buyerImage">제조사전경</label>
				<input type="file" name="buyerImage" id="buyerImage" accept="image/*" />
				<form:errors path="buyerImage" cssClass="text-danger"/>
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerName">거래처이름(*)</label>
				<form:input path="buyerName" cssClass="form-control" placeholder="거래처이름(*)" />
				<form:errors path="buyerName" cssClass="text-danger"/>
			</div>
			<div class="form-group">
				<label class="form-label" for="lprodGu">분류코드(*)</label>
				<select name="lprodGu" id="lprodGu" class="form-select"
					data-init-val="${buyer.lprodGu }"
				>
					<option value="">분류선택</option>
				</select>	
				<form:errors path="lprodGu" cssClass="text-danger"/>
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerBank">주거래은행</label>
				<form:input path="buyerBank" cssClass="form-control" placeholder="주거래은행" />
				<form:errors path="buyerBank" cssClass="text-danger"/>	
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerBankno">계좌번호</label>
				<form:input path="buyerBankno" cssClass="form-control" placeholder="계좌번호" />
				<form:errors path="buyerBankno" cssClass="text-danger"/>	
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerBankname">계좌주 </label>
				<form:input path="buyerBankname" cssClass="form-control" placeholder="계좌주 " />
				<form:errors path="buyerBankname" cssClass="text-danger"/>		
				
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerZip">우편번호</label>
				<form:input path="buyerZip" cssClass="form-control" placeholder="우편번호" />
				<form:errors path="buyerZip" cssClass="text-danger"/>		
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerAdd1">기본주소</label>
				<form:input path="buyerAdd1" cssClass="form-control" placeholder="기본주소" />
				<form:errors path="buyerAdd1" cssClass="text-danger"/>		 
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerAdd2">상세주소</label>
				<form:input path="buyerAdd2" cssClass="form-control" placeholder="상세주소" />
				<form:errors path="buyerAdd2" cssClass="text-danger"/>		
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerComtel">회사전화번호</label>
				<form:input path="buyerComtel" cssClass="form-control" placeholder="회사전화번호" />
				<form:errors path="buyerComtel" cssClass="text-danger"/>		
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerFax">팩스번호</label>
				<form:input path="buyerFax" cssClass="form-control" placeholder="팩스번호" />
				<form:errors path="buyerFax" cssClass="text-danger"/>		
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerMail">메일주소</label>
				<form:input path="buyerMail" cssClass="form-control" placeholder="메일주소" />
				<form:errors path="buyerMail" cssClass="text-danger"/>		
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerCharger">담당자</label>
				<form:input path="buyerCharger" cssClass="form-control" placeholder="담당자" />
				<form:errors path="buyerCharger" cssClass="text-danger"/>		
			</div>
			<div class="form-group">
				<label class="form-label" for="buyerTelext">내선번호</label>
				<form:input path="buyerTelext" maxlength="2" cssClass="form-control" placeholder="내선번호" />
				<form:errors path="buyerTelext" cssClass="text-danger"/>		
			</div>
			<div>
			<button type="submit" class="btn btn-primary mb-4">Submit</button>
			<button type="reset" class="btn btn-danger mb-4">Reset</button>
			</div>
          </form:form>
      </div>
    </div>
  </div>
</body>
</html>
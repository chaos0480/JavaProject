<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////    
<%@page import="com.model2.mvc.service.domain.Purchase"%> 
<%
 	Purchase	purchVO = (Purchase)request.getAttribute("purchVO");
%>
/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
 
<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=${purchVO.tranNo}" method="post">
 
다음과 같이 구매가 되었습니다.
 
<table border=1>
	<tr>
		<td>물품번호</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getPurchaseProd().getProdNo() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td></td>
		<td>${purchVO.purchaseProd.prodNo}</td>		
	</tr>
	<tr>
		<td>구매자아이디</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getBuyer().getUserId() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td>${purchVO.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<%if(purchVO.getPaymentOption().trim().equals("1")){%>
			현금구매
		<%}else{%>
			신용구매
		<%} %>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<c:if test="${!empty purchVO}">
			${purchVO.paymentOption == 1 ? "현금 구매" : "신용 구매"}
		</c:if>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getReceiverName() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td>${purchVO.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getReceiverPhone() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td>${purchVO.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getDivyAddr() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td>${purchVO.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getDivyRequest() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td>${purchVO.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
		<td><%= purchVO.getDivyDate() %></td>
		/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
		<td>${purchVO.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>
 
</body>
</html>
   
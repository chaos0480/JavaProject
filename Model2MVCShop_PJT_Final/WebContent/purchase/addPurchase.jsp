<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////    
<%@page import="com.model2.mvc.service.domain.Purchase"%> 
<%
 	Purchase	purchVO = (Purchase)request.getAttribute("purchVO");
%>
/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
 
<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=${purchVO.tranNo}" method="post">
 
������ ���� ���Ű� �Ǿ����ϴ�.
 
<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getPurchaseProd().getProdNo() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td></td>
		<td>${purchVO.purchaseProd.prodNo}</td>		
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getBuyer().getUserId() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td>${purchVO.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<%if(purchVO.getPaymentOption().trim().equals("1")){%>
			���ݱ���
		<%}else{%>
			�ſ뱸��
		<%} %>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<c:if test="${!empty purchVO}">
			${purchVO.paymentOption == 1 ? "���� ����" : "�ſ� ����"}
		</c:if>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getReceiverName() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td>${purchVO.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getReceiverPhone() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td>${purchVO.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getDivyAddr() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td>${purchVO.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getDivyRequest() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td>${purchVO.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<%-- /////////////////////// EL / JSTL �������� �ּ� ó�� ////////////////////////
		<td><%= purchVO.getDivyDate() %></td>
		/////////////////////// EL / JSTL �������� �ּ� ó�� //////////////////////// --%>
		<td>${purchVO.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>
 
</body>
</html>
   
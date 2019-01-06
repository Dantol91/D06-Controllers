<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="application.accept" />
</p>

<security:authorize access="hasRole('CUSTOMER')">

	<div>
		<form:form action="application/edit.do" method="post" id="formCreate" name="formCreate" modelAttribute="application"></form:form>

		<!-- Attributes Hidden -->
		
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="customer" />
			<form:hidden path="offeredPrice" />
			<form:hidden path="registerMoment" />
			<form:hidden path="handyWorker" />
			<form:hidden path="fixupTask" />
			<form:hidden path="creditCard" />
		
			
		<form:label path="status"><spring:message code="application.status"></spring:message></form:label>
		<form:select id="status" path="status">
		<form:option value="${STATUS}" label="PENDING"></form:option>
		<form:options items="${status}" itemLabel="name" itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="status" />
		
		<form:label path="holderName"> <spring:message code="creditCard.holderName" /></form:label>
		<form:input path="holderName" /><form:errors cssClass="error" path="holderName" /><br />
			
		<form:label path="brandName"><spring:message code="creditCard.brandName"></spring:message></form:label>
		<form:select id="brandName" path="brandName">
		<form:option value="${STATUS}" label="MASTERCARD"></form:option>
		<form:options items="${status}" itemLabel="name" itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="status" />
		
		<form:label path="number"> <spring:message code="creditCard.number" /></form:label>
		<form:input path="number" /><form:errors cssClass="error" path="number" /><br />
			
		<form:label path="expirationMonth"> <spring:message code="creditCard.expirationMonth" /></form:label>
		<form:input path="expirationMonth" /><form:errors cssClass="error" path="expirationMonth" /><br />
		
				<form:label path="expirationYear"> <spring:message code="creditCard.expirationYear" /></form:label>
		<form:input path="expirationYear" /><form:errors cssClass="error" path="expirationYear" /><br />
			
		<form:label path="cvvCode"> <spring:message code="creditCard.cvvCode" /></form:label>
		<form:input path="cvvCode" /><form:errors cssClass="error" path="cvvCode" /><br />
		
	</div>
	
	
		<input type="submit" name="save" value="<spring:message code="application.save"></spring:message>" />
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('application/list.do')" />	

</security:authorize>


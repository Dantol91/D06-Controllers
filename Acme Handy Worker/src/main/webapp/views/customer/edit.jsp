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


<spring:message code="confirm.phone" var="confirmPhoneMessage" />

<security:authentication property="principal.username" var="username" />
	<jstl:if
		test='${customer.userAccount.username == username || customer.id == 0}'>



<div>
	<form:form action="customer/edit.do" method="post" id="formCreate"
		name="formCreate" modelAttribute="customer" >

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="suspicious" />
		<form:hidden path="fixupTasks" />
		<form:hidden path="score"/>
		<%-- <form:hidden path="userAccount.authorities" /> --%>
		
		


		<%-- <fieldset>
			<legend>
				<spring:message code="customer.useraccount" />
			</legend>
			<div>
				<form:label path="userAccount.username">
					<spring:message code="customer.useraccount.username" />
				</form:label>
				<form:input path="userAccount.username" />
				<form:errors path="userAccount.username" cssClass="error" />
			</div>

			<div>
				<form:label path="userAccount.password">
					<spring:message code="customer.useraccount.password" />
				</form:label>
				<form:password path="userAccount.password" />
				<form:errors path="userAccount.password" cssClass="error" />
			</div>
		</fieldset>  --%>



		<form:label path="name">
			<spring:message code="customer.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />

		<form:label path="middleName">
			<spring:message code="customer.middleName" />
		</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br />


		<form:label path="surname">
			<spring:message code="customer.surname" />
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br />

		<form:label path="photo">
			<spring:message code="customer.photo" />
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br />

		<form:label path="email">
			<spring:message code="customer.email" />
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br />

		<form:label path="phone">
			<spring:message code="customer.phone" />
		</form:label>
		<form:input path="phone" />
		<form:errors cssClass="error" path="phone" />
		<br />

		<form:label path="address">
			<spring:message code="customer.address" />
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br />
	
		

		<!--  Botones -->

		<input type="submit" name="save"
			value="<spring:message code="customer.save"></spring:message>"
			onclick="return patternPhone(document.getElementById('phone').value, '${confirmPhoneMessage}');" />

		<input type="button" name="cancel"
			value="<spring:message code="customer.cancel"></spring:message>"
			onclick="javascript:relativeRedir('customer/display.do')" />

	</form:form>
	


	


</div>

	</jstl:if>
	
	<jstl:if
		test='${customer.userAccount.username != username && customer.id != 0}'>
		
		<h1>
			<b><spring:message code="customer.permissions"></spring:message></b>
		</h1>
		
		</jstl:if>


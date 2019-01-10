<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="referee/referee-administrator/edit.do" modelAttribute="referee" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="suspicious" />
	<form:hidden path="userAccount.banned" />
	<form:hidden path="userAccount.authorities[0]" />
	
	<fieldset><legend><spring:message code="referee.useraccount" /></legend>
		<div>	
			<form:label path="userAccount.username">
				<spring:message code="referee.useraccount.username" />
			</form:label>	
			<form:input path="userAccount.username" />	
			<form:errors path="userAccount.username" cssClass="error" />
		</div>
		
		<div>
			<form:label path="userAccount.password">
				<spring:message code="referee.useraccount.password" />
			</form:label>	
			<form:password path="userAccount.password" />	
			<form:errors path="userAccount.password" cssClass="error" />
		</div>
	</fieldset>
	
	<div>
		<form:label path="name">
			<spring:message code="referee.name" />
		</form:label>	
		<form:input path="name" />	
		<form:errors path="name" cssClass="error" />
	</div>
	
	<div>
		<form:label path="middleName">
			<spring:message code="referee.middleName" />
		</form:label>	
		<form:input path="middleName" />	
		<form:errors path="middleName" cssClass="error" />
	</div>

	<div>
		<form:label path="surname">
			<spring:message code="referee.surname" />
		</form:label>	
		<form:input path="surname" />	
		<form:errors path="surname" cssClass="error" />
	</div>

	<div>
		<form:label path="photo">
			<spring:message code="referee.photo" />
		</form:label>	
		<form:input path="photo" />	
		<form:errors path="photo" cssClass="error" />
	</div>

	<div>
		<form:label path="email">
			<spring:message code="referee.email" />
		</form:label>	
		<form:input path="email" />	
		<form:errors path="email" cssClass="error" />
	</div>

	<div>
		<form:label path="address">
			<spring:message code="referee.address" />
		</form:label>	
		<form:input path="address" />	
		<form:errors path="address" cssClass="error" />
	</div>
	
	<div>
		<form:label path="phone">
			<spring:message code="referee.phone" />
		</form:label>	
		<form:input path="phone" />	
		<form:errors path="phone" cssClass="error" />
	</div>

<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="referee.save" />
	</button>
</jstl:if>
	
</form:form>

<jstl:choose>
	<jstl:when test="${referee.id > 0}">
		<button type="button" onclick="javascript: relativeRedir('referee/profile.do?refereeId=${referee.id}')" >
			<spring:message code="referee.cancel" />
		</button>
	</jstl:when>
	<jstl:when test="${referee.id == 0}">
		<button type="button" onclick="javascript: relativeRedir('')" >
			<spring:message code="referee.cancel" />
		</button>
	</jstl:when>
</jstl:choose>

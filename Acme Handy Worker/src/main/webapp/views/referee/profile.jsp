<%--
 * action-1.jsp
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

<fieldset><legend><spring:message code="referee.personalinformation" /></legend>

	<img src="${referee.photo}" />
	<p><spring:message code="referee.name" />${referee.name}</p>
	<p><spring:message code="referee.middlename" />${referee.middleName}</p>
	<p><spring:message code="referee.surname" />${referee.surname}</p>
	<p><spring:message code="referee.email" />${referee.email}</p>
	<p><spring:message code="referee.address" />${referee.address}</p>
	<p><spring:message code="referee.phone" />${referee.phone}</p>

</fieldset>

<jstl:if test="${isPrincipalAuthorizedEdit}">
	<spring:message code="referee.edit" var="editMessage" />
	<button onclick='javascript: relativeRedir("<jstl:out value="referee/edit.do?refereeId=${referee.id}"></jstl:out>")' >${editMessage}</button>
</jstl:if>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:choose>
		<jstl:when test="${referee.banned}">
			<spring:message code="referee.unban" var="unbanMessage" />
			<button onclick='javascript: relativeRedir("<jstl:out value="actor/unban.do?actorId=${referee.id}"></jstl:out>")' >${unbanMessage}</button>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="referee.ban" var="banMessage" />
			<button onclick='javascript: relativeRedir("<jstl:out value="actor/ban.do?actorId=${referee.id}"></jstl:out>")' >${banMessage}</button>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>
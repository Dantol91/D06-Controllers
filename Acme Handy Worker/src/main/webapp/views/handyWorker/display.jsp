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



<%-- Si esto da error creo que tambien se puede poner así: access="hasRole('ADMIN') OR hasRole('CUSTOMER')" --%>
<security:authorize access="hasAnyRole('ADMIN', 'HANDYWORKER')">

	<b><spring:message code="handyWorker.name"></spring:message>:</b>
	<jstl:out value="${handyWorker.name}"></jstl:out>
	<br />

	<b><spring:message code="handyWorker.middleName"></spring:message>:</b>
	<jstl:out value="${handyWorker.middleName}"></jstl:out>
	<br />

	<b><spring:message code="handyWorker.surname"></spring:message>:</b>
	<jstl:out value="${handyWorker.surname}"></jstl:out>
	<br />

	<b><spring:message code="handyWorker.photo"></spring:message>:</b>
	<jstl:out value="${handyWorker.photo}"></jstl:out>
	<br />

	<b><spring:message code="handyWorker.email"></spring:message>:</b>
	<jstl:out value="${handyWorker.email}"></jstl:out>
	<br />

	<b><spring:message code="handyWorker.phone"></spring:message>:</b>
	<jstl:out value="${handyWorker.phone}"></jstl:out>
	<br />

	<b><spring:message code="handyWorker.address"></spring:message>:</b>
	<jstl:out value="${handyWorker.address}"></jstl:out>
	<br />
	
	<b><spring:message code="handyWorker.make"></spring:message>:</b>
	<jstl:out value="${handyWorker.make}"></jstl:out>
	<br />

	<security:authorize access="hasRole('ADMIN')">
	<b>	<spring:message code="handyWorker.score"></spring:message>:</b>
		<jstl:out value="${handyWorker.score}"></jstl:out>
		<br />
	</security:authorize>

	

	<spring:message code="handyWorker.edit" var="edit"></spring:message>
	<input type="button" name="edit" value="${edit}"
		onclick="javascript:relativeRedir('handyWorker/edit.do')" />

	<%-- <spring:message code="handyWorker.return" var="return"></spring:message>
	<input type="button" name="return" value="${return}"
		onclick="javascript:relativeRedir('handyWorker/administrator/list.do')" /> --%>


</security:authorize>




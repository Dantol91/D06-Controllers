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



<%-- <security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')"> --%>

	<b><spring:message code="administrator.name"></spring:message>:</b>
	<jstl:out value="${administrator.name}"></jstl:out>
	<br />

	<b><spring:message code="administrator.middleName"></spring:message>:</b>
	<jstl:out value="${administrator.middleName}"></jstl:out>
	<br />

	<b><spring:message code="administrator.surname"></spring:message>:</b>
	<jstl:out value="${administrator.surname}"></jstl:out>
	<br />

	<b><spring:message code="administrator.photo"></spring:message>:</b>
	<jstl:out value="${administrator.photo}"></jstl:out>
	<br />

	<b><spring:message code="administrator.email"></spring:message>:</b>
	<jstl:out value="${administrator.email}"></jstl:out>
	<br />

	<b><spring:message code="administrator.phone"></spring:message>:</b>
	<jstl:out value="${administrator.phone}"></jstl:out>
	<br />

	<b><spring:message code="administrator.address"></spring:message>:</b>
	<jstl:out value="${administrator.address}"></jstl:out>
	<br />

<!-- Boton de edicion de administrador -->
<security:authentication property="principal.username" var="username" />
<jstl:if test='${administrator.userAccount.username == username}'>

	<spring:message code="administrator.edit" var="edit"></spring:message>
	<input type="button" name="edit" value="${edit}"
		onclick="javascript:relativeRedir('administrator/edit.do?administratorId=${administrator.id}')" />
</jstl:if>


<%-- </security:authorize> --%>




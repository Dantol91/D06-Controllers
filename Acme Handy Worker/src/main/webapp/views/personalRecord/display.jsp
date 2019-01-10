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


<b><spring:message code="personalRecord.fullName"></spring:message>:</b>
<jstl:out value="${personalRecord.fullName}"></jstl:out>
<br/>

<b><spring:message code="personalRecord.photo"></spring:message>:</b>
<jstl:out value="${personalRecord.photo}"></jstl:out>
<br/>

<b><spring:message code="personalRecord.email"></spring:message>:</b>
<jstl:out value="${personalRecord.email}"></jstl:out>
<br/>

<b><spring:message code="personalRecord.phone"></spring:message>:</b>
<jstl:out value="${personalRecord.phone}"></jstl:out>
<br/>

<b><spring:message code="personalRecord.linkedinProfile"></spring:message>:</b>
<jstl:out value="${personalRecord.linkedinProfile}"></jstl:out>
<br/>



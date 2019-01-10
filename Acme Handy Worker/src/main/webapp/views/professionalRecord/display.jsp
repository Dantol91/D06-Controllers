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

<p><spring:message code="professionalRecord.company" /></p>

<jstl:out value="${professionalRecord.company}"></jstl:out>
<br/>

<p><spring:message code="professionalRecord.role" /></p>

<jstl:out value="${professionalRecord.role}"></jstl:out>
<br/>


<p><spring:message code="professionalRecord.start" /></p>

<jstl:out value="${professionalRecord.start}"></jstl:out>
<br/>

<p><spring:message code="professionalRecord.end" /></p>

<jstl:out value="${professionalRecord.end}"></jstl:out>
<br/>

<spring:message code="professionalRecord.attachment"></spring:message>
:
<jstl:out value="${professionalRecord.attachment}"></jstl:out>
<br/>

<spring:message code="professionalRecord.comments"></spring:message>
:
<jstl:out value="${professionalRecord.comments}"></jstl:out>
<br/>

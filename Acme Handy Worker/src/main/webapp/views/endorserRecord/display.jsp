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


<b><spring:message code="endorserRecord.fullName"></spring:message>:</b>
<jstl:out value="${endorserRecord.fullName}"></jstl:out>
<br/>

<b><spring:message code="endorserRecord.photo"></spring:message>:</b>
<jstl:out value="${endorserRecord.photo}"></jstl:out>
<br/>

<b><spring:message code="endorserRecord.email"></spring:message>:</b>
<jstl:out value="${endorserRecord.email}"></jstl:out>
<br/>

<b><spring:message code="endorserRecord.phone"></spring:message>:</b>
<jstl:out value="${endorserRecord.phone}"></jstl:out>
<br/>

<b><spring:message code="endorserRecord.linkedinProfile"></spring:message>:</b>
<jstl:out value="${endorserRecord.linkedinProfile}"></jstl:out>
<br/>



<input type="button" name="edit" value="<spring:message code="endorserRecord.edit"></spring:message>" onclick="javascript:relativeRedir('endorserRecord/handyWorker/edit.do?endorserRecordId=${endorserRecord.id}')"/>	
<input type="button" name="cancel" value="<spring:message code="endorserRecord.cancel"></spring:message>" onclick="javascript:relativeRedir('endorserRecord/handyWorker/list.do')" />	











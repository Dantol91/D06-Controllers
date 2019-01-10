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


<b><spring:message code="educationRecord.diplomaTitle"></spring:message>:</b>
<jstl:out value="${educationRecord.diplomaTitle}"></jstl:out>
<br/>

<b><spring:message code="educationRecord.start"></spring:message>:</b>
<jstl:out value="${educationRecord.start}"></jstl:out>
<br/>

<b><spring:message code="educationRecord.end"></spring:message>:</b>
<jstl:out value="${educationRecord.end}"></jstl:out>
<br/>

<b><spring:message code="educationRecord.institution"></spring:message>:</b>
<jstl:out value="${educationRecord.institution}"></jstl:out>
<br/>

<b><spring:message code="educationRecord.attachment"></spring:message>:</b>
<jstl:out value="${educationRecord.attachment}"></jstl:out>
<br/>

<b><spring:message code="educationRecord.comments"></spring:message>:</b>
<jstl:out value="${educationRecord.comments}"></jstl:out>
<br/>



<input type="button" name="edit" value="<spring:message code="educationRecord.edit"></spring:message>" onclick="javascript:relativeRedir('educationRecord/handyWorker/edit.do?educationRecordId=${educationRecord.id}')"/>	
<input type="button" name="cancel" value="<spring:message code="educationRecord.cancel"></spring:message>" onclick="javascript:relativeRedir('educationRecord/handyWorker/list.do')" />	











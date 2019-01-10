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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="phases" id="phase" requestURI="phase/handyWorker/list.do" pagesize="5" class="displaytag">

	<%--  Primero compruebo que es un admin --%>
	<security:authorize access="hasRole('HANDYWORKER')">


		<display:column>
			<a href="phase/handyWorker/edit.do?phase=${phase.id}"><spring:message
						code="phase.edit"></spring:message></a>
		</display:column>


		<spring:message code="phase.title" var="phaseTitle"></spring:message>
		<display:column property="title" title="${phaseTitle}" sortable="true" />
		
		<spring:message code="phase.description" var="phaseDescription"></spring:message>
		<display:column property="description" title="${phaseDescription}" sortable="true" />

		<spring:message code="phase.start" var="phaseStart"></spring:message>
		<display:column property="start" title="${phaseStart}" sortable="true" />
		
		<spring:message code="phase.end" var="phaseEnd"></spring:message>
		<display:column property="end" title="${phaseEnd}" sortable="true" />
		



	</security:authorize>
</display:table>


<security:authorize access="hasRole('HANDYWORKER')">

<a href="phase/handyWorker/create.do"><spring:message code="phase.create"></spring:message></a>

</security:authorize>



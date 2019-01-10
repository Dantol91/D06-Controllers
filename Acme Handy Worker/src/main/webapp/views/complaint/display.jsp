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

<p>
	<spring:message code="complaint.display" />
</p>



<security:authorize access="hasAnyRole('REFEREE', 'CUSTOMER')">

	<spring:message code="complaint.moment"></spring:message>
	<jstl:out value="${category.moment}">
	</jstl:out>
	<br />
	
	<spring:message code="complaint.description"></spring:message>
	<jstl:out value="${complaint.description}">
	</jstl:out>
	<br />

	
	<spring:message code="complaint.attachments"></spring:message>
	<jstl:out value="${complaint.attachments}">
	</jstl:out>
	<br />
	
	<div>
		<a href="report/display.do?reportId=${report.id}"><spring:message code="complaint.report"></spring:message></a>
	</div>
	<br />
	
	<div>
		<a href="fixupTask/display.do?fixupTaskId=${fixupTask.name}"><spring:message code="complaint.fixupTask"></spring:message></a>
	</div>
	<br />
	
		
	<spring:message code="complaint.referee"></spring:message>
	<jstl:out value="${complaint.referee.name}">
	</jstl:out>
	<br />
	

		<button type="button" onclick="javascript: relativeRedir('complaint/list.do')" ><spring:message code="complaint.return" />
	</button>
	
	

</security:authorize>

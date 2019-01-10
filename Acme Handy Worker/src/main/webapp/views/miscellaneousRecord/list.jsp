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



<display:table name="miscellaneousRecords" id="miscellaneousRecord"
	requestURI="miscellaneousRecord/handyWorker/list.do" pagesize="5"
	class="displaytag">

	<security:authorize access="hasRole('HANDYWORKER')">

		<display:column>
			<a href="miscellaneousRecord/handyWorker/edit.do?miscellaneousRecordId=${miscellaneousRecord.id}">
				<spring:message code="miscellaneousRecord.edit"></spring:message></a>
		</display:column>

		<display:column>
			<a href="miscellaneousRecord/handyWorker/display.do?miscellaneousRecordId=${miscellaneousRecord.id}">
				<spring:message	code="miscellaneousRecord.display"></spring:message></a>
		</display:column>

		<spring:message code="miscellaneousRecord.title"
			var="title"></spring:message>
		<display:column property="title"
			title="${title}" sortable="true" />

		<spring:message code="miscellaneousRecord.attachment" var="attachment"></spring:message>
		<display:column property="attachment" title="${attachment}" sortable="true" />

		<spring:message code="miscellaneousRecord.comments" var="comments"></spring:message>
		<display:column property="comments" title="${comments}" sortable="true" />


	</security:authorize>
</display:table>


 <%--  Boton de creacion --%>
 

<%--  Boton de ATRAS --%>
<security:authorize access="hasRole('HANDYWORKER')">

	<input type="button" name="back"
		value="<spring:message code="miscellaneousRecord.back"></spring:message>"
		onclick="javascript:relativeRedir('curriculum/handyWorker/display.do')" />
</security:authorize>



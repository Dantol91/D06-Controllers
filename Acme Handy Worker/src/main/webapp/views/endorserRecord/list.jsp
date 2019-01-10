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



<display:table name="endorserRecords" id="endorserRecord"
	requestURI="endorserRecord/handyWorker/list.do" pagesize="5"
	class="displaytag">

	<%--  Primero compruebo que es un handyWorker --%>
	<security:authorize access="hasRole('HANDYWORKER')">


		<%--  La columna que va a la vista edit de las endorserRecord --%>
		<display:column>
			<a
				href="endorserRecord/handyWorker/edit.do?endorserRecordId=${endorserRecord.id}"><spring:message
					code="endorserRecord.edit"></spring:message></a>
		</display:column>


		<%--  La columna que va a la vista display de las endorserRecord --%>
		<display:column>
			<a
				href="endorserRecord/handyWorker/display.do?endorserRecordId=${endorserRecord.id}"><spring:message
					code="endorserRecord.display"></spring:message></a>
		</display:column>

		<spring:message code="endorserRecord.fullName"
			var="endorserRecordFullName"></spring:message>
		<display:column property="fullName"
			title="${endorserRecordFullName}" sortable="true" />

		<spring:message code="endorserRecord.photo" var="photo"></spring:message>
		<display:column property="photo" title="${photo}" sortable="true" />

		<spring:message code="endorserRecord.email" var="email"></spring:message>
		<display:column property="email" title="${email}" sortable="true" />


		<spring:message code="endorserRecord.phone" var="phone"></spring:message>
		<display:column property="phone" title="${phone}"
			sortable="true" />

		<spring:message code="endorserRecord.linkedinProfile" var="linkedinProfile"></spring:message>
		<display:column property="linkedinProfile" title="${linkedinProfile}"
			sortable="true" />

	






	</security:authorize>
</display:table>


<%--  Boton de creacion --%>
<security:authorize access="hasRole('HANDYWORKER')">

	<input type="button" name="create"
		value="<spring:message code="endorserRecord.create"></spring:message>"
		onclick="javascript:relativeRedir('endorserRecord/handyWorker/create.do')" />
</security:authorize>

<%--  Boton de ATRAS --%>
<security:authorize access="hasRole('HANDYWORKER')">

	<input type="button" name="back"
		value="<spring:message code="endorserRecord.back"></spring:message>"
		onclick="javascript:relativeRedir('curriculum/handyWorker/display.do')" />
</security:authorize>



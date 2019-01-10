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



<display:table name="professionalRecords" id="professionalRecord"
	requestURI="professionalRecord/handyWorker/list.do" pagesize="5"
	class="displaytag">

	<%--  Primero compruebo que es un handyWorker --%>
	<security:authorize access="hasRole('HANDYWORKER')">


		<%--  La columna que va a la vista edit de las professionalRecord --%>
		<display:column>
			<a
				href="professionalRecord/handyWorker/edit.do?professionalRecordId=${professionalRecord.id}"><spring:message
					code="professionalRecord.edit"></spring:message></a>
		</display:column>


		<%--  La columna que va a la vista display de las professionalRecord --%>
		<display:column>
			<a
				href="professionalRecord/handyWorker/display.do?professionalRecordId=${professionalRecord.id}"><spring:message
					code="professionalRecord.display"></spring:message></a>
		</display:column>

		<spring:message code="professionalRecord.company"
			var="company"></spring:message>
		<display:column property="company"
			title="${company}" sortable="true" />

		<spring:message code="professionalRecord.start" var="start"></spring:message>
		<display:column property="start" title="${start}" sortable="true" />

		<spring:message code="professionalRecord.end" var="end"></spring:message>
		<display:column property="end" title="${end}" sortable="true" />


		<spring:message code="professionalRecord.role" var="role"></spring:message>
		<display:column property="role" title="${role}"
			sortable="true" />

		<spring:message code="professionalRecord.attachment" var="attachment"></spring:message>
		<display:column property="attachment" title="${attachment}"
			sortable="true" />

		<spring:message code="professionalRecord.comments" var="comments"></spring:message>
		<display:column property="comments" title="${comments}"
			sortable="true" />







	</security:authorize>
</display:table>


<%--  Boton de creacion --%>
<security:authorize access="hasRole('HANDYWORKER')">

	<input type="button" name="create"
		value="<spring:message code="professionalRecord.create"></spring:message>"
		onclick="javascript:relativeRedir('professionalRecord/handyWorker/create.do')" />
</security:authorize>

<%--  Boton de ATRAS --%>
<security:authorize access="hasRole('HANDYWORKER')">

	<input type="button" name="back"
		value="<spring:message code="professionalRecord.back"></spring:message>"
		onclick="javascript:relativeRedir('curriculum/handyWorker/display.do')" />
</security:authorize>



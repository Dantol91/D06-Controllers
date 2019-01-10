<%--
 * create.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
	<spring:message code="note.edit" />
</p>

<security:authorize access="hasRole('REFEREE')">
	<div>
		<form:form action="note/referee/edit.do" method="post" id="formCreate"
			name="formCreate" modelAttribute="note">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment" />

			<form:label path="comments">
				<spring:message code="note.comments"></spring:message>
			</form:label>
			<form:textarea path="comments" id="comments" name="comments" />
			<form:errors cssClass="error" path="comments"></form:errors>
			
				<form:label path="report">
				<spring:message code="note.report"></spring:message>
			</form:label>
			<form:textarea path="report" id="report" name="report" />
			<form:errors cssClass="error" path="report"></form:errors>

			<input type="submit" name="save"
				value="<spring:message code="note.save"></spring:message>" />
			<spring:message code="note.cancel" var="cancel"></spring:message>
			<input type="button" name="cancel" value="${cancel}"
				onclick="javascript:relativeRedir('note/referee/list.do')" />
		</form:form>

	</div>
</security:authorize>

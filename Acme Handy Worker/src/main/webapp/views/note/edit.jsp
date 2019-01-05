<%--
 * edit.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
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
	<form:form action="note/referee/edit.do" modelAttribute="note">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="referee"/>
		
			<form:label path="report">
				<spring:message code="note.role"></spring:message>
			</form:label>
			<form:textarea path="role" id="role" name="role" />
			<form:errors cssClass="error" path="role"></form:errors>
			
			<form:label path="comments">
				<spring:message code="note.comments"></spring:message>
			</form:label>
			<form:textarea path="comments" id="comments" name="comments" />
			<form:errors cssClass="error" path="comments"></form:errors>


		<input type="submit" name="save"
			value="<spring:message code="note.save" />" />&nbsp; 
		<input type="button" name="cancel"
			value="<spring:message code="note.cancel" />"
			onclick="javascript: relativeRedir('note/referee/list.do');" />
		<br />

	</form:form>
	</div>
</security:authorize>
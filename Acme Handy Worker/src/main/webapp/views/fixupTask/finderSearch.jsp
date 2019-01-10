<%--
 * create.jsp
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

<form:form action="finder/explorer/edit.do" method="post" id="formCreate"
	name="formCreate" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="keyword">
		<spring:message code="finder.keyword"></spring:message>
	</form:label>
	<form:input path="keyword" />

	<fieldset>
		<form:label path="minimumPrice">
			<spring:message code="finder.minimumPrice"></spring:message>
		</form:label>
		<form:input path="minimumPrice" />
		<br />

		<form:label path="maximumPrice">
			<spring:message code="finder.maximumPrice"></spring:message>
		</form:label>
		<form:input path="maximumPrice" />
	</fieldset>
	<br />

	<fieldset>
	
		<form:label path="minimumDate">
			<spring:message code="finder.minimumDate"></spring:message>
		</form:label>
		<form:input path="minimumDate" />

		<form:label path="maximumDate">
			<spring:message code="finder.maximumDate"></spring:message>
		</form:label>
		<form:input path="maximumDate"/>
	</fieldset>

	<spring:message code="finder.save" var="save"></spring:message>
	<spring:message code="finder.cancel" var="cancel"></spring:message>
	<input type="submit" name="save" value="${save}" />
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript:relativeRedir('finder/explorer/display.do')" />
</form:form>

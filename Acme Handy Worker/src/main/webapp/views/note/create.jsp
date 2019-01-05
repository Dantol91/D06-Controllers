<%--
 * list.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="notes" requestURI="${requestURI}" id="row">
	

	<div>
		<form:label path="role">
			<spring:message code="note.role"></spring:message>
		</form:label>
		<form:input path="role" id="role" name="role" />
		<form:errors cssClass="error" path="role" />
		<br />
	</div>

		<div>
		<form:label path="comments">
			<spring:message code="note.comments"></spring:message>
		</form:label>
		<form:input path="comments" id="comments" name="comments" />
		<form:errors cssClass="error" path="comments" />
		<br />
	</div>
	
	<div>
	<spring:message code="note.moment" var="momentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="moment" title="${momentHeader}"
		 format="{0,date,${dateFormat}}"/>
		<br />
	</div>


</display:table>

<security:authorize access="hasRole('REFEREE')">

	<input type="submit" name="save" value="<spring:message code="note.save"></spring:message>" />	
	
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('note/display.do')" />	


</security:authorize>

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
	<spring:message code="note.create" />
</p>
<security:authorize access="hasRole('REFEREE')">
	<div>

		<form:form action="note/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="note">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment" />
			

			<fieldset>
				<!-------------------Form ------------------------------------>

				<div>
					<form:label path="comments">
						<spring:message code="note.comments"></spring:message>
					</form:label>
					<form:input path="comments" id="comments" name="comments" />
					<form:errors cssClass="error" path="comments" />
					<br />
				</div>
				
				<div>
					<form:label path="report">
						<spring:message code="note.report"></spring:message>
					</form:label>
					<form:input path="report" id="report" name="report" />
					<form:errors cssClass="error" path="report" />
					<br />
				</div>

			</fieldset>



		</form:form>

	</div>
	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="note.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('note/display.do')" />	




</security:authorize>
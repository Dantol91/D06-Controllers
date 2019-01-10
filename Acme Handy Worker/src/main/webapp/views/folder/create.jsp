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


	<div>

		<form:form action="folder/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="folder">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="system"/>
			<form:hidden path="folders"/>
			<form:hidden path="messages"/>

			

			<fieldset>
				<!-------------------Form ------------------------------------>

				<div>
					<form:label path="name">
						<spring:message code="folder.name"></spring:message>
					</form:label>
					<form:input path="name" id="name" name="name" />
					<form:errors cssClass="error" path="name" />
					<br />
				</div>
				
				

			</fieldset>



		</form:form>

	</div>
	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="folder.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('folder/display.do')" />	



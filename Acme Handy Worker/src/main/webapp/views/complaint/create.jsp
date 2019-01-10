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



<!-- ------------------------------PARA FECHAS------------------------------------------------ -->
<!-- Hay que crear uno por cada input de fecha: datepicker1,datepicker2.... -->
<!-- LUEGO HAY QUE PONER EN EL form:imput id="datepicker1"..... -->

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker1").datepicker();
	});
</script>

<!-- ------------------------------------------------------------------------------------------------ -->

<security:authorize access="hasRole('CUSTOMER')">
	<div>

		<form:form action="complaint/edit.do" method="POST" id="formCreate" name="formCreate" modelAttribute="complaint">

			<!-- Atributos hidden-->
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="referee" />
			<form:hidden path="ticker" />
			
			
			
				<!-- Form -->
				
				<form:label path="moment"><spring:message code="complaint.moment"></spring:message></form:label>
				<form:input path="moment" id="datepicker1" />
				<form:errors cssClass="error" path="moment" />
				<br>
				
					<form:label path="description"> <spring:message code="complaint.description"></spring:message></form:label>
					<form:textarea path="description" id="description" name="description" /><form:errors cssClass="error" path="description" />
				<br />
				
				
					<form:label path="attachments"> <spring:message code="complaint.attachments"></spring:message></form:label>
					<form:textarea rows="4" cols="80" path="attachments" id="attachments" name="attachments" /><form:errors cssClass="error" path="attachments" />
				
				<br>
							
				<form:label path="fixuptask"><spring:message code="complaint.fixuptask"></spring:message></form:label>
				<form:select id="fixuptask" path="fixuptask">
				<form:option value="${FIXUPTASK}" label="------"></form:option>
				<form:options items="${FIXUPTASKS}" itemLabel="ticker" itemValue="id" />
				</form:select>
				<form:errors cssClass="error" path="fixuptask" />

				
				<br>
				
				
	
	<!--  Los botones de crear y cancelar -->

		<input type="submit" name="save" value="<spring:message code="complaint.save"></spring:message>" />
			
		<button type="button" onclick="javascript: relativeRedir('category/administrator/list.do')" ><spring:message code="complaint.return" /></button>
			
		
		</form:form>

	</div>
	
</security:authorize>



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

<script type="text/javascript">

$(document).ready(function() {
		 $("#formID").submit(function(){
		var m = document.getElementById("phone").value;
		if(m != ""){
		var expreg = /^(\+\d{1,3})?\s(\(\d{3}\))?\s?\d{4,100}$/;
		
		if(!expreg.test(m)){
			
			return confirm("Are you sure you want to save this phone?");
		}
		}
	});
	});

</script>


<form:form id="formID" action="sponsor/registration/registration.do"
	modelAttribute="sponsor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="boxes" />
	<form:hidden path="userAccount.authorities[0].authority" />

	<form:label path="userAccount.username">
		<spring:message code="userAccount.username" />:
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />


	<form:label path="userAccount.password">
		<spring:message code="userAccount.password" />:
	</form:label>
	<form:password path="userAccount.password" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	
	<form:label path="name">
		<spring:message code="sponsor.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="middleName">
		<spring:message code="sponsor.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	
	<form:label path="surname">
		<spring:message code="sponsor.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="email">
		<spring:message code="sponsor.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="sponsor.phone" />:
	</form:label>
	<form:input id="phone" path="phone" />
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address">
		<spring:message code="sponsor.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />

	<form:label path="photo">
		<spring:message code="sponsor.photo" />:
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<!-- <script type="text/javascript">
		function valida() {
			var mo = document.getElementById("phone").value;

			var regex = /^(\+\d{1,3})?\s?(\(\d{3}))?\s?\d{4}$/;
			var regex1 = /^\d{4}$/;
			var regex2 = /^(\+\d{1,3})?\s\d{4}$/;

			if (!(regex).match(mo)) {
				alert("¿Estas seguro de querer guardar este telefono?");
				return false;
			}
			return true;
		}
	</script> -->

	<input type="submit" name="save"
		value="<spring:message code="sponsor.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="sponsor.cancel" />"
		onclick="avascript: relativeRedir('/');" />
	<br />

</form:form>
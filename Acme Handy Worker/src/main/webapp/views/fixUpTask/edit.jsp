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

<%
	Cookie[] cookies = request.getCookies();
	Cookie languageCookie = null;
	for (Cookie c : cookies) {
		if (c.getName().equals("language")) {
			languageCookie = c;
		}
	}

	String languageValue = languageCookie.getValue();
%>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

<!-- ------------------------------PARA FECHAS------------------------------------------------ -->
<!-- Hay que crear uno por cada input de fecha: datepicker1,datepicker2.... -->
<!-- LUEGO HAY QUE PONER EN EL form:imput id="datepicker1"..... -->


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker1").datepicker({ dateFormat: 'dd/mm/yy'});
	});
	$(function() {
		$("#datepicker2").datepicker({dateFormat: 'dd/mm/yy'});
	});
</script>

	<!-- ------------------------------------------------------------------------------------------------ -->

	<security:authentication property="principal.username" var="username" />
	<jstl:if
		test='${fixupTask.customer.userAccount.username == username || fixupTask.id == 0}'>


		<jstl:if test=""></jstl:if>
		<security:authorize access="hasRole('CUSTOMER')">

			<form:form action="fixupTask/customer/edit.do" method="post"
				id="formCreate" name="formCreate" modelAttribute="fixupTask">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="moment" />
				<form:hidden path="customer" />
				<form:hidden path="applications" />
				<form:hidden path="ticker" />
				<form:hidden path="complaints" />




				<form:label path="description">
					<b><spring:message code="fixupTask.description"></spring:message>:</b>
				</form:label>
				<form:textarea path="description" />
				<form:errors cssClass="error" path="description"></form:errors>
				<br />

				<form:label path="address">
					<b><spring:message code="fixupTask.address"></spring:message>:</b>
				</form:label>
				<form:input path="address" />
				<form:errors cssClass="error" path="address"></form:errors>
				<br />

				<form:label path="maximumPrice">
					<b><spring:message code="fixupTask.maximumPrice"></spring:message>:</b>
				</form:label>
				<form:input path="maximumPrice" />
				<form:errors cssClass="error" path="maximumPrice"></form:errors>
				<br />

				<form:label path="start">
					<b><spring:message code="fixupTask.start"></spring:message>:</b>
				</form:label>
				<form:input id="datepicker1" path="start" />
				<form:errors cssClass="error" path="start"></form:errors>
				<br />

				<form:label path="end">
					<b><spring:message code="fixupTask.end"></spring:message>:</b>
				</form:label>
				<form:input id="datepicker2" path="end" />
				<form:errors cssClass="error" path="end" />
				<br />


				<form:label path="warranty">
					<b><spring:message code="fixupTask.warranty"></spring:message>:</b>
				</form:label>
				<form:select id="warranty" path="warranty">
					<form:option value="${warranties}" label="------"></form:option>

					<form:options items="${warranties}" itemLabel="title"
						itemValue="id" />


				</form:select>
				<form:errors cssClass="error" path="warranty" />
				<br />





				<form:label path="category">
					<b><spring:message code="fixupTask.category"></spring:message>:</b>
				</form:label>
				<form:select id="category" path="category">
					<form:option value="${categories}" label="------"></form:option>

					<%
						if (languageValue.equals("en")) {
					%>
					<form:options items="${categories}" itemLabel="nameEnglish"
						itemValue="id" />
					<%
						} else if (languageValue.equals("es")) {
					%>
					<form:options items="${categories}" itemLabel="nameSpanish"
						itemValue="id" />
					<%
						}
					%>

				</form:select>
				<form:errors cssClass="error" path="category" />
				<br />




				<!--  Botones -->

				<input type="submit" name="save"
					value="<spring:message code="fixupTask.save"></spring:message>" />
				<spring:message code="fixupTask.cancel" var="cancel"></spring:message>
				<input type="button" name="cancel" value="${cancel}"
					onclick="javascript:relativeRedir('fixupTask/endorsable/list.do')" />

			</form:form>


		</security:authorize>

	</jstl:if> <jstl:if
		test='${fixupTask.customer.userAccount.username != username && fixupTask.id != 0}'>
		<h1>
			<b><spring:message code="fixupTask.permissions"></spring:message></b>
		</h1>
	</jstl:if>
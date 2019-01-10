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



<%-- Si esto da error creo que tambien se puede poner así: access="hasRole('ADMIN') OR hasRole('CUSTOMER')" --%>
<security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER','HANDYWORKER')">

	<b><spring:message code="customer.name"></spring:message>:</b>
	<jstl:out value="${customer.name}"></jstl:out>
	<br />

	<b><spring:message code="customer.middleName"></spring:message>:</b>
	<jstl:out value="${customer.middleName}"></jstl:out>
	<br />

	<b><spring:message code="customer.surname"></spring:message>:</b>
	<jstl:out value="${customer.surname}"></jstl:out>
	<br />

	<b><spring:message code="customer.photo"></spring:message>:</b>
	<jstl:out value="${customer.photo}"></jstl:out>
	<br />

	<b><spring:message code="customer.email"></spring:message>:</b>
	<jstl:out value="${customer.email}"></jstl:out>
	<br />

	<b><spring:message code="customer.phone"></spring:message>:</b>
	<jstl:out value="${customer.phone}"></jstl:out>
	<br />

	<b><spring:message code="customer.address"></spring:message>:</b>
	<jstl:out value="${customer.address}"></jstl:out>
	<br />

	<security:authorize access="hasRole('ADMIN')">
	<b>	<spring:message code="customer.score"></spring:message>:</b>
		<jstl:out value="${customer.score}"></jstl:out>
		<br />
	</security:authorize>

	
<security:authorize access="hasRole('CUSTOMER')">

	<spring:message code="customer.edit" var="edit"></spring:message>
	<input type="button" name="edit" value="${edit}"
		onclick="javascript:relativeRedir('customer/edit.do?customerId=${customer.id}')" />

	<%-- <spring:message code="customer.return" var="return"></spring:message>
	<input type="button" name="return" value="${return}"
		onclick="javascript:relativeRedir('customer/administrator/list.do')" /> --%>
</security:authorize>


<security:authorize access="hasRole('HANDYWORKER')">
<fieldset>
	<legend>
		<b><spring:message code="customer.fixupTask.list"></spring:message></b>
	</legend>
	
	
	<display:table name="fixupTasks" id="fixupTask" pagesize="5" class="displaytag">
	
		
		<display:column>
			<a href="fixupTask/endorsable/display.do?fixupTaskId=${fixupTask.id}"> <spring:message
					code="customer.display" />
			</a>
		</display:column>

		<spring:message code="fixupTask.ticker" var="ticker"></spring:message>
		<display:column property="ticker" title="${ticker}" sortable="true" />

		<spring:message code="fixupTask.description"
			var="description"></spring:message>
		<display:column property="description" title="${description}"
			sortable="true" />

		
		
		
	</display:table>
	
	
	<spring:message code="customer.fixupTask.show" var="show"></spring:message>
	<input type="button" name="show" value="${show}"
		onclick="javascript:relativeRedir('fixupTask/endorsable/list.do')" />
	
</fieldset>

	
</security:authorize>

</security:authorize>




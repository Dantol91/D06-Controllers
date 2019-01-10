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

<p>
	<spring:message code="customer.list" />
</p>

<%-- TODO (Juan) tengo que mirar como poner la sección de pasar de pagina y demas --%>


<security:authorize access="hasRole('ADMIN')">
	<display:table name="customers" id="customer" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
		<display:column>
			<a href="customer/display.do?customerId=${customer.id}">
			<spring:message code="customer.display"></spring:message></a>
		</display:column>
		
		<display:column>
			<a href="customer/edit.do?customerId=${customer.id}">
			<spring:message code="customer.edit"></spring:message></a>
		</display:column>
		
		<spring:message code="customer.name" var="customerName"></spring:message>
		<display:column property="name" title="${customerName}" sortable="true" />
		
		<spring:message code="customer.middleName" var="customerMiddleName"></spring:message>
		<display:column property="middleName" title="${customerMiddleName}" sortable="true" />
		
		<spring:message code="customer.surname" var="customerSurname"></spring:message>
		<display:column property="surname" title="${customerSurname}" sortable="true" />
		
		<spring:message code="customer.photo" var="customerPhoto"></spring:message>
		<display:column property="photo" title="${customerPhoto}" sortable="true" />
		
		<spring:message code="customer.email" var="customerEmail"></spring:message>
		<display:column property="email" title="${customerEmail}" sortable="true" />
		
		<spring:message code="customer.phone" var="customerPhone"></spring:message>
		<display:column property="phone" title="${customerPhone}" sortable="true" />
		
		<spring:message code="customer.address" var="customerAddress"></spring:message>
		<display:column property="address" title="${customerAddress}" sortable="true" />
		
		<spring:message code="customer.score" var="customerScore"></spring:message>
		<display:column property="score" title="${customerScore}" sortable="true" />
		
		<spring:message code="customer.suspicious" var="customerSuspicious"></spring:message>
		<display:column property="suspicious" title="${customerSuspicious}" sortable="true" />
		
		<spring:message code="customer.banned" var="customerBanned"></spring:message>
		<display:column property="banned" title="${customerBanned}" sortable="true" />
		
		<%--<%-- TODO duda de como poner este link
		<display:column>
			<a href="c/fixupTask/list.do?customerId=${customer.id}"> 
			<spring:message code="customer.fisxupTask.list"></spring:message></a>
		</display:column> --%>
		
		
		
		
	</display:table>
	
	
</security:authorize>

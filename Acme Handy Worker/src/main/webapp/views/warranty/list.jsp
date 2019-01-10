<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="warranties" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
		<jstl:if test='${row.draft==true}'>
			<a href="warranty/administrator/edit.do?warrantyId=${row.id}">
				<spring:message	code="warranty.edit" />
			</a>
			</jstl:if>
		</display:column>		
	</security:authorize>
	
	<!-- Attributos -->
	
	<spring:message code="warranty.title" var="title" />
	<display:column property="title" title="${title}" sortable="true" />

	<spring:message code="warranty.laws" var="laws" />
	<display:column property="laws" title="${laws}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="warranty.terms" var="terms" />
	<display:column property="terms" title="${terms}" sortable="false" />
	
	<spring:message code="warranty.draft" var="draft" />
	<display:column property="draft" title="${draft}" sortable="false" />
	

</display:table>

<!-- Botones  -->

<security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="warranty/administrator/create.do"> <spring:message
				code="warranty.create" />
		</a>
	</div>
</security:authorize>
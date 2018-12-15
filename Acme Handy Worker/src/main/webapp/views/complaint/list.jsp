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

	<!-- Guardamos en una variable el formato de la fecha  -->
	
	<spring:message code="master.page.date.format" var="dateFormat" />

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="stories" requestURI="${requestURI}" id="row">

	<spring:message code="complaint.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />
	
	<spring:message code="survivalClass.moment"
		var="momentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="moment"
		title="${momentHeader}" format="{0,date,${dateFormat}}" />
		
	<spring:message code="complaint.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<spring:message code="complaint.attachmentLink" var="attachmentLinkHeader" />
	<display:column property="attachmentLink" title="${attachmentLinkHeader}" />
	
	
	
	<security:authorize access="hasRole('REFEREE')">
		<display:column>
	
			<a href="complaint/referee/display.do?complaintId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.display" /></a><br/>
		
		</display:column>
	
	</security:authorize>
	
</display:table>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
		
			<a href="complaint/customer/create.do"><spring:message code="complaint.create" /></a>
			<br />
			
			<a href ="complaint/customer/edit.do?complaintId=<jstl:out value="${row.getId()}"/>"><spring:message
			code="complaint.edit" /></a>
			<br />
				
						
			<a href="complaint/display.do?complaintId=<jstl:out value="${row.getId()}"/>"><spring:message
					code="complaint.display" /></a>
			
		</display:column>
	</security:authorize>
	
	
		<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
	
			<a href="complaint/handyWorker/display.do?complaintId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.display" /></a><br/>
		
		</display:column>
	
	</security:authorize>
	
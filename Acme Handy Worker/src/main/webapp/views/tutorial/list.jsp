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

	<spring:message code="tutorial.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="survivalClass.moment"
		var="momentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="moment"
		title="${momentHeader}" format="{0,date,${dateFormat}}" />
		
	<spring:message code="tutorial.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" />
	
	<spring:message code="tutorial.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}" />
	
	<spring:message code="tuorial.sponsorship" var="sponsorship" />
	<display:column property="sponsorship.id" title="${sponsorship}"
		sortable="false" />
		
	<spring:message code="tuorial.section" var="section" />
	<display:column property="section.title" title="${section}"
		sortable="false" />
	
	
		<display:column>
	
		<a href="tutorial/display.do?tutorialId=<jstl:out value="${row.getId()}"/>"><spring:message
					code="tutorial.display" /></a>
		</display:column>
		
</display:table>

	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
		
			<a href="tutorial/handyWorker/create.do"><spring:message code="tutorial.create" /></a>
			<br />
			
			<a href ="tutorial/handyWorker/edit.do?tutorialId=<jstl:out value="${row.getId()}"/>"><spring:message
			code="tutorial.edit" /></a>
			<br />
			
			<a href="tutorial/handyWorker/display.do?tutorialId=<jstl:out value="${row.getId()}"/>"><spring:message 
			code="trip.display" /></a>
			<br/>
		
			
		</display:column>
	</security:authorize>
	
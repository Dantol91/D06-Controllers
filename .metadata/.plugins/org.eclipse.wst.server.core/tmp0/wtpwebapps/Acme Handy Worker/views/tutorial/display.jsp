<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- Guardamos en una variable el formato de la fecha  -->
	
	<spring:message code="master.page.locale" var="locale" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	
	
 	<fmt:setLocale value="${locale}"/>

<div id="tutorialDiv">

	<ul style="list-style-type: disc">
		
		<li><b><spring:message code="tutorial.title"></spring:message>:</b>
			<jstl:out value="${tutorial.getTitle()}" /></li> 

		<li><b><spring:message code="tutorial.moment"></spring:message>:</b>
		<fmt:formatDate value="${tutorial.getMoment()}" pattern="${dateFormat}" /></li>

		<li><b><spring:message code="tutorial.summary"></spring:message>:</b> 
			<jstl:out value="${tutorial.getSummary()}" /></li> 
			
			<li><b><spring:message code="tutorial.picture"></spring:message>:</b>
			<jstl:out value="${tutorial.getPicutre}"/></li>
		
	</ul>

</div>

	<security:authorize access="hasRole('HANDYWORKER')">
	<input type="button" name="edit"
		value="<spring:message code="tutorial.edit" />"
		onclick="javascript: relativeRedir('tutorial/handyWorker/edit.do?tutorialId=${tutorial.getId()}')" />
	
	<input type="button" name="back"
		value="<spring:message code="tutorial.back" />"
		onclick="javascript: relativeRedir('tutorial/handyWorker/list.do')" />
	</security:authorize>
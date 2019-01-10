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


<security:authorize access="hasRole('ADMIN')">

	<display:table name="actors" id="actor" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
		<spring:message code="actor.name" var="actorName"></spring:message>
		<display:column property="name" title="${actorName}" sortable="false" />
	
		
		<spring:message code="actor.surname" var="actorSurname"></spring:message>
		<display:column property="surname" title="${actorSurname}" sortable="false" />
		
		<spring:message code="userAccount.authorities" var="userAccount.authorities"></spring:message>
		<display:column property="userAccount.authorities" title="${actor.userAccount.authorities}" sortable="false" />
		
		<spring:message code="actor.email" var="actorEmail"></spring:message>
		<display:column property="email" title="${actorEmail}" sortable="false" />


		
		<display:column> 
		
		<jstl:if test="${actor.suspicious eq true}">
			<jstl:if test="${actor.userAccount.banned eq true}">
					<a href="actor/unBan.do?actorId=${actor.id}"><spring:message
					code="actor.unBan"></spring:message></a></jstl:if>
			<jstl:if test="${actor.userAccount.banned eq false && actor.suspicious eq true}">
					<a href="actor/ban.do?actorId=${actor.id}"><spring:message
					code="actor.ban"></spring:message></a></jstl:if>
		
		</jstl:if>
		
		</display:column>
	
	
	</display:table>

</security:authorize>

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

<style type="text/css">

.pending{color: white;}
.pendingLessThanAMonth{color: grey;}
.accepted{color: green;}
.rejected{color: orange;}

</style>

<!-- Listing grid -->

<security:authorize access="hasRole('CUSTOMER')">
<spring:message code="application.filterByStatus"/>
<%-- <form action="application/CUSTOMER/list.do">
<select name="statusSelection">
<jstl:forEach items="${statusSet}" var="status">
<option label="${status}" value="${status}"/>
</jstl:forEach>
</select>
<input type="submit"/>
</form> --%>
<a href="application/customer/list.do"><spring:message code="application.seeAll"/></a>
</security:authorize>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="applications"  requestURI="${requestURI}" id="row">
	
	<!-- Cambiamos el color de las applications según el estado -->

	<jsp:useBean id="now" class="java.util.Date"/>
	<fmt:parseNumber var="dateDifference"
    value="${(now.time - row.fixUpTask.startDate.time) / (1000*60*60*24) }"
    integerOnly="true" />
	
	<jstl:choose>
	
		<jstl:when test="${row.status eq 'PENDING' and dateDifference < 30}">
			<jstl:set value="pendingLessThanAMonth" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'PENDING'}">
			<jstl:set value="pending" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'ACCEPTED'}">
			<jstl:set value="accepted" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.status eq 'REJECTED'}">
			<jstl:set value="rejected" var="style"/>
		</jstl:when>
		
	</jstl:choose>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:choose>
			<jstl:when test="${registered}">
				<display:column>
				<a href="application/handyWorker/edit.do?applicationId=$row.id"><spring:message code="application.list" /></a>
				</display:column>
			</jstl:when>
		</jstl:choose>
	</security:authorize>

	<spring:message code="application.registerMoment"
		var="registerMomentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="registerMoment" title="${registerMomentHeader}"
		 format="{0,date,${dateFormat}}" />

	
	<spring:message code="application.status" var="status" />
	<display:column class="${style}" title="${status}">  
	
	<jstl:out value="${row.status}"/>
	
	<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${row.status eq 'PENDING'}">
	[<a href="application/edit.do?applicationId=${row.id}"><spring:message
						code="application.changeStatus" /></a>]<br/>
	</jstl:if>
	</security:authorize>
	
	</display:column>

	<spring:message code="application.creditCard"
		var="creditCard" />
	<display:column title="${creditCard}">
	
	 	<jstl:choose>

			<jstl:when test="${empty row.creditCard and row.status == 'ACCEPTED'}">
			<security:authorize access="hasRole('CUSTOMER')">
				<a href="application/edit.do?applicationId=${row.id}"><spring:message
						code="application.enterCard" /></a>
			</security:authorize>
			</jstl:when>
			
			<jstl:otherwise>
			${row.creditCard.brandName}
			</jstl:otherwise>
			
		</jstl:choose> 
	</display:column>

	<spring:message code="application.comment" var="comment" />
	<display:column property="comment" title="${comment}"  />
	
</display:table>

<security:authorize access="hasRole('HANDYWORKER')">
<a href="application/handyWorker/create.do"><spring:message code="application.create"/></a>
 </security:authorize>

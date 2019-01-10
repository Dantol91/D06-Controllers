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


	<spring:message code="application.price"></spring:message> <jstl:out value="${application.price}"></jstl:out>
	<br />
	
	<spring:message code="application.moment"></spring:message> <jstl:out value="${application.moment}"></jstl:out>
	<br />
	
	<spring:message code="application.status"></spring:message> <jstl:out value="${application.status}"></jstl:out>
	<br />
	
	<spring:message code="application.workerComments"></spring:message> <jstl:out value="${application.workerComments}"></jstl:out>
	<br />
	
	<spring:message code="application.customerComments"></spring:message> <jstl:out value="${application.customerComments}"></jstl:out>
	<br />
	
	
	<jstl:if test="${empty application.creditCard  }">
	<spring:message code="application.creditCard"></spring:message> <jstl:out value="${application.creditCard.brandName}" ></jstl:out>:  NOT ASSIGNED YET / NO AÑADIDA
	</jstl:if>
	
	
	<jstl:if test="${not empty application.creditCard  }">
	<spring:message code="application.creditCard"></spring:message> <jstl:out value="${application.creditCard.brandName}" ></jstl:out>
	</jstl:if>
	
	<br />
	
<%-- 	<spring:message code="application.customer"></spring:message> <jstl:out value="${application.customer.name}"></jstl:out>
	<br /> --%>
	
	<spring:message code="application.handyWorker"></spring:message> <jstl:out value="${application.handyWorker.name}"></jstl:out>
	<br />
	<br />


<!-- Boton return -->

			
	<security:authorize access="hasRole('HANDYWORKER')">	
				<button type="button"
					onclick="javascript: relativeRedir('application/handyworker/list.do')">
					<spring:message code="application.return" />
				</button>
			</security:authorize>
			<security:authorize access="hasRole('CUSTOMER')">
				<button type="button"
					onclick="javascript: relativeRedir('application/customer/list.do?fixupTaskId=${application.fixupTask.id}')">
					<spring:message code="application.return" />
				</button>
			</security:authorize>

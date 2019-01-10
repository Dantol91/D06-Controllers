<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${isPrincipalAuthorizedEdit or not report.draft}">

	<p><spring:message code="report.moment" /> : <fmt:formatDate value="${report.moment}" pattern="dd/MM/yyyy HH:mm" /></p>
	<p><spring:message code="report.description" /> : ${report.description}</p>
	
	<fieldset><legend><spring:message code="report.attachments" /></legend>
		<ul>
			<jstl:forEach items="${report.attachments}" var="attachment">
				<li>${attachment.url}</li>
			</jstl:forEach>
		</ul>
	</fieldset>
	
	<p><spring:message code="report.draft" /> : ${report.draft}</p>
	
</jstl:if>	
	
<jstl:if test="${isPrincipalAuthorizedEdit}">
	<spring:message code="report.edit" var="editMessage" />
	<a href ="report/referee/edit.do?reportId=${report.id}">${editMessage}</a>
</jstl:if>
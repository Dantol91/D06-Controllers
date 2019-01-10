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


<%

Cookie[] cookies = request.getCookies();
Cookie languageCookie = null;
for(Cookie c : cookies) {
	if(c.getName().equals("language")) {
		languageCookie = c;
	}
}

String languageValue = languageCookie.getValue();

%>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.name"></spring:message>:
	<% if(languageValue.equals("en")) { %>
		<jstl:out value="${category.nameEnglish}" />
	<% } else if (languageValue.equals("es")) { %>
		<jstl:out value="${category.nameSpanish}" />
	<% } %>
	<br />


	<spring:message code="category.parentCategory"></spring:message>:
	<a href="category/administrator/display.do?categoryId=${category.parentCategory.id}">
		<% if(languageValue.equals("en")) { %>
			<jstl:out value="${category.parentCategory.nameEnglish}" />
		<% } else if (languageValue.equals("es")) { %>
			<jstl:out value="${category.parentCategory.nameSpanish}" />
		<% } %>
	</a>
	<br />
<%-- 	<fieldset>
		<legend>
			<spring:message code="category.childCategories"></spring:message>
		</legend>
		<jstl:forEach items="${category.childCategories}" var="category">
			<a href="category/administrator/display.do?categoryId=${category.id}"><jstl:out value="${category.name}"></jstl:out></a>
		</jstl:forEach>
	</fieldset> --%>

	<br />
	
	
<%-- 	<fieldset>
	<legend>
		<spring:message code="category.fixupTasks"></spring:message>
	</legend>
	<jstl:forEach items="${category.fixupTasks}" var="fixupTask">
		<a href="fixupTask/display.do?fixupTaskId=${FixupTask.id}"><jstl:out value="${fixupTask.name}"></jstl:out></a>
	</jstl:forEach>



</fieldset> --%>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<%-- 	<spring:message code="category.return" var="return"></spring:message>
	<input type="button" name="return" value="${return}" onclick="javascript:relativeRedir('category/list.do')" /> --%>
		
	<spring:message code="category.edit" var="edit"></spring:message>
	<input type="button" name="edit" value="${edit}" onclick="javascript:relativeRedir('category/administrator/edit.do?categoryId=${category.id}')" />
	
	<button type="button" onclick="javascript: relativeRedir('category/administrator/list.do')" ><spring:message code="category.return" />
	</button>

		
</security:authorize>




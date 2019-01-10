<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%

String languageMessage = "en";

Cookie[] cookies = request.getCookies();
Cookie languageCookie = null;

try {
	for(Cookie c : cookies) {
		if(c.getName().equals("language")) {
			languageCookie = c;
		}
	}
} catch (NullPointerException e) {
	
}

request.getParameter("language");

if(request.getParameter("language") != null) {
	languageMessage = request.getParameter("language");
} else if(languageCookie != null) {
	languageMessage = languageCookie.getValue();
} else {
	languageMessage = "en";
}
	
%>

	<% if(languageMessage.equals("en")) { %>
		<p>${englishWelcome}</p>
	<% } if(languageMessage.equals("es")) { %>
		<p>${spanishWelcome}</p>
	<% } %>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 

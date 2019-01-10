<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="note.display" />
</p>

<spring:message code="note.date" var="date"></spring:message>

<spring:message code="note.moment"></spring:message>
<fmt:formatDate value="${note.moment}" pattern="${date}"
	var="moment" />
<jstl:out value="${moment}"></jstl:out>
<jstl:if test="${note.comments != null }">

	<spring:message code="note.comments"></spring:message>
	<jstl:out value="${note.comments}"></jstl:out>
</jstl:if>

<spring:message code="note.report"></spring:message>
:
<jstl:out value="${note.report}"></jstl:out>
<br/>


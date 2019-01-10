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

<jstl:if test="${finder.keyword ne null}">
	<spring:message code="finder.keyword" />
	<jstl:out value="${finder.keyword}" />
	<br />
</jstl:if>

<jstl:if test="${finder.minimumPrice ne null}">
	<spring:message code="finder.minimumPrice" />
	<jstl:out value="${finder.minimumPrice}" />
	<br />
</jstl:if>

<jstl:if test="${finder.maximumPrice ne null}">
	<spring:message code="finder.maximumPrice" />
	<jstl:out value="${finder.maximumPrice}" />
	<br />
</jstl:if>

<jstl:if test="${finder.minimumDate ne null}">
	<spring:message code="finder.minimumDate" />
	<jstl:out value="${finder.minimumDate}" />
	<br />
</jstl:if>

<jstl:if test="${finder.maximumDate ne null}">
	<spring:message code="finder.maximumDate" />
	<jstl:out value="${finder.maximumDate}" />
	<br />
</jstl:if>

<spring:message code="finder.edit" var="editHeader"></spring:message>
<a href="finder/edit.do"><button type="submit">
		<jstl:out value="${editHeader}" />
	</button></a>
<br />
<form:form method="POST" action="fixupTask/finder/search.do"
	modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="keyword" />
	<form:hidden path="minimumPrice" />
	<form:hidden path="maximumPrice" />
	<form:hidden path="minimumDate" />
	<form:hidden path="maximumDate" />
	<form:hidden path="lastUse" />
	<form:hidden path="results" />
	<spring:message code="finder.use" var="useHeader"></spring:message>
	<button type="submit">
		<jstl:out value="${useHeader}" />
	</button>
</form:form>

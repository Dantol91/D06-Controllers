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

	<!-- Guardo en una variable el formato según el idioma -->
	<spring:message code="master.page.date.format" var="dateFormat" />

<form:form action="trip/search.do" modelAttribute="finder">

	<spring:message code="finder.searchForFixUpTasks" /></br>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTasks" />
	<form:hidden path="handyWorker" />
	<form:hidden path="lastSearchDate" />

	<form:label path="keyWord">
		<spring:message code="finder.keyWord" />:
	</form:label>
	<form:input path="keyWord" />
	<form:errors cssClass="error" path="keyWord" />
	<br />

	<form:label path="minPrice">
		<spring:message code="finder.minPrice" />
	</form:label>
	<form:input path="minPrice" value="" placeholder="0.0"/></br>
	<form:errors cssClass="error" path="minPrice" />

	<form:label path="maxPrice">
		<spring:message code="finder.maxPrice" />
	</form:label>
	<form:input path="maxPrice" value="" placeholder="0.0" /></br>
	<form:errors cssClass="error" path="maxPrice" />
	<br />

	<form:label path="startDate">
		<spring:message code="finder.startDate" />:
	</form:label>
	<form:input path="startDate" placeholder="${dateFormat}"/></br>
	<form:errors cssClass="error" path="startDate" />

	<form:label path="endDate">
		<spring:message code="finder.endDate" />:
	</form:label>
	<form:input path="endDate" placeholder="${dateFormat}"/></br>
	<form:errors cssClass="error" path="endDate" />
	<br />

	<input type="submit" name="searchForFixUpTasks"
		value="<spring:message code="finder.searchForFixUpTasks" />" />

</form:form>
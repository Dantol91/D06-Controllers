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

<form action="trip/searchByKeyword.do" method="get">

	<spring:message code="finder.searchForFixUpTasks" /><br/>

	<label for="keyword">
		<spring:message code="finder.keyWord" />:
	</label>
	<input name="keyword" />
	<br />

	<input type="submit"
		value="<spring:message code="finder.searchForFixUpTasks" />" />

</form>
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

<display:table pagesize="10" class="displaytag" name="boxs"
	requestURI="box/list.do" id="row">

	<spring:message code="box.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader }" sortable="true" />

	<display:column>
		<a href="box/display.do?boxId=<jstl:out value="${row.id}"/>"><spring:message
				code="box.show" /></a>

	</display:column>
</display:table>


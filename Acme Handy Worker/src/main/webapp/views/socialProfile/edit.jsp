
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="socialProfile/edit.do" modelAttribute="socialProfile">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:label path="nick">
		<spring:message code="socialProfile.nick" />:
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick" />
	<br />

	<form:label path="link">
		<spring:message code="socialProfile.link" />:
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="socialProfile.save"/>" />
		<!-- onclick="javascript: window.location.replace('welcome/index.do'); -->
		
	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript: relativeRedir('/curriculum/handyWorker/displayMyCurriculum.do');" />
		
	<jstl:if test="${socialProfile.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="educationRecord.delete" />"
			onclick="return confirm('<spring:message code="socialProfile.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<acme:cancel url="/" code="socialProfile.cancel"/>


</form:form>

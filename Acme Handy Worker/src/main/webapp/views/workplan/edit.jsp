<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authentication property="principal.username" var="username" />
<jstl:if test='${workplan.handyWorker.userAccount.username == username || workPlan.id == 0}'>

<form:form action="workplan/handyWorker/edit.do" modelAttribute="workplan">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="phases" />
	
	
	<form:select id="fixupTask" path="fixupTask">
			<form:option value="0" label="------"></form:option>
			<form:options items="${fixUp}" itemLabel="ticker" itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="fixupTask" />
	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="workplan.save" />" />&nbsp; 
	
	<jstl:if test="${workplan.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="workplan.delete" />"
			onclick="return confirm('<spring:message code="workplan.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="workplan.cancel" />"
		onclick="javascript: relativeRedir('workplan/handyWorker/list.do');" />
	<br />

</form:form>

</jstl:if>

<jstl:if test='${workplan.handyWorker.userAccount.username != username && workPlan.id != 0}'>
	<h1>
		<b><spring:message code="workplan.permissions"></spring:message></b>
	</h1>
</jstl:if>
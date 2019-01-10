<%--
 * create.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<jstl:out value="${messageCode}"></jstl:out>
<security:authorize access="isAuthenticated()">
	<div>
		<form:form action="message/admin/messageToAll.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="mensaje">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="sentDate"/>
			<form:hidden path="sender"/>
			<form:hidden path="receiver"/>
			<form:hidden path="folder"/>

			<form:label path="subject">
				<spring:message code="message.subject"></spring:message>
			</form:label>
			:
			<form:input path="subject" id="subject" name="subject" />
			<form:errors cssClass="error" path="subject" />
			<br>

			<form:label path="body">
				<spring:message code="message.body"></spring:message>
			</form:label>
			<form:input path="body" id="body" name="body" />
			<form:errors cssClass="error" path="body" />

			<form:label path="priority">
				<spring:message code="message.priority"></spring:message>
			</form:label>
				<spring:message code="message.high" var="HighHeader"></spring:message>
				<spring:message code="message.neutral" var="NeutralHeader"></spring:message>
				<spring:message code="message.low" var="LowHeader"></spring:message>
			<form:select path="priority" name="selectPriority"
				id="selectPriority">
				<form:option label="${HighHeader}" value="HIGH"></form:option>
				<form:option label="${NeutralHeader}" value="NEUTRAL"></form:option>
				<form:option label="${LowHeader}" value="LOW"></form:option>
			</form:select>
			<form:errors cssClass="error" path="priority" />


			<input type="submit" name="save"
				value="<spring:message code="message.save"></spring:message>" />
			<spring:message code="message.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('message/actor/list.do')" />
		</form:form>
	</div>
</security:authorize>
</security:authorize>
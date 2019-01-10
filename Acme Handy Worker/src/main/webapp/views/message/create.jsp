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

<security:authorize access="isAuthenticated()">
	<div>
		<form:form action="message/actor/edit.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="privateMessage">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment"/>
			<form:hidden path="receiver"/>
			<form:hidden path="sender"/>
			<form:hidden path="folder"/>

			<form:label path="subject">
				<spring:message code="message.subject"></spring:message>
			</form:label>
			<form:input path="subject" id="subject" name="subject" />
			<form:errors cssClass="error" path="subject" />

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
			
				<form:label path="receiver">
				<spring:message code="message.receiver"></spring:message>
			</form:label>
			<form:select path="receiver" name="selectReceiver"
				id="selectReceiver">
				<form:option label="---" value="0"></form:option>
				<form:options items="${receivers}" itemLabel="name" itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="receiver" />
			
			<form:label path="tags">
				<spring:message code="message.tags"></spring:message>
			</form:label>
			<form:input path="tags" id="body" name="tags" />
			<form:errors cssClass="error" path="tags" />

			

			<input type="submit" name="save"
				value="<spring:message code="message.save"></spring:message>" />
			<spring:message code="message.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('message/actor/list.do')" />
		</form:form>
	</div>
</security:authorize>
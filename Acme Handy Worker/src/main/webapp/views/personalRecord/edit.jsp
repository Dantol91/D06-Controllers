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

<security:authorize access="hasRole('HANDYWORKER')">
	<div>

		<form:form action="personalRecord/handyWorker/edit.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="personalRecord">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="curriculum" />


			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="fullName">
						<spring:message code="personalRecord.fullName"></spring:message>
					</form:label>
					<form:input path="fullName" id="fullName"
						name="fullName" />
					<form:errors cssClass="error" path="fullName" />
					<br />
				</div>

				<div>
					<form:label path="photo">
						<spring:message code="personalRecord.photo"></spring:message>
					</form:label>
					<form:input path="photo" id="photo" name="photo" />
					<form:errors cssClass="error" path="photo" />
					<br />
				</div>
				
				<div>
					<form:label path="phone">
						<spring:message code="personalRecord.phone"></spring:message>
					</form:label>
					<form:input path="phone" id="phone" name="phone" />
					<form:errors cssClass="error" path="phone" />
					<br />
				</div>

				<div>
					<form:label path="email">
						<spring:message code="personalRecord.email"></spring:message>
					</form:label>
					<form:input path="email" id="email" name="email" />
					<form:errors cssClass="error" path="email" />
					<br />
				</div>

				<div>
					<form:label path="linkedinProfile">
						<spring:message code="personalRecord.linkedinProfile"></spring:message>
					</form:label>
					<form:input path="linkedinProfile" id="linkedinProfile" name="linkedinProfile" />
					<form:errors cssClass="error" path="linkedinProfile" />
					<br />
				</div>

			</fieldset>
			
			<input type="submit" name="save" value="<spring:message code="personalRecord.save"></spring:message>" />

			<button type="button"
				onclick="javascript: relativeRedir('curriculum/handyWorker/display.do">
				<spring:message code="personalRecord.cancel" />
			</button>

			<jstl:if test="${personalRecord.id != 0}">
				<input type="submit" name="delete"
					value="<spring:message code="personalRecord.delete" />"
					onclick="return confirm('<spring:message code="personalRecord.confirm.delete" />')" />&nbsp;
			</jstl:if>

		</form:form>

	</div>


</security:authorize>
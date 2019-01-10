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

		<form:form action="miscellaneousRecord/handyWorker/edit.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="miscellaneousRecord">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="curriculum" />


			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="title">
						<spring:message code="miscellaneousRecord.title"></spring:message>
					</form:label>
					<form:input path="title" id="title"
						name="title" />
					<form:errors cssClass="error" path="title" />
					<br />
				</div>

				<div>
					<form:label path="attachment">
						<spring:message code="miscellaneousRecord.attachment"></spring:message>
					</form:label>
					<form:input path="attachment" id="attachment" name="attachment" />
					<form:errors cssClass="error" path="attachment" />
					<br />
				</div>

				<div>
					<form:label path="comments">
						<spring:message code="miscellaneousRecord.comments"></spring:message>
					</form:label>
					<form:input path="comments" id="comments" name="comments" />
					<form:errors cssClass="error" path="comments" />
					<br />
				</div>


			</fieldset>


			<!--  Los botones de crear y cancelar -->

			<input type="submit" name="save"
				value="<spring:message code="miscellaneousRecord.save"></spring:message>" />

			<button type="button"
				onclick="javascript: relativeRedir('miscellaneousRecord/handyWorker/list.do')">
				<spring:message code="miscellaneousRecord.cancel" />
			</button>

			<jstl:if test="${miscellaneousRecord.id != 0}">
				<input type="submit" name="delete"
					value="<spring:message code="miscellaneousRecord.delete" />"
					onclick="return confirm('<spring:message code="miscellaneousRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>






		</form:form>

	</div>





</security:authorize>
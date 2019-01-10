<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<p>
	<spring:message code="finder.title" />
</p>


<security:authorize access="hasRole('HANDYWORKER')">
	<div>
		<form:form action="finder/display.do" method="post" id="formCreate"
			name="formCreate" modelAttribute="finder">

			<form:label path="keyWord">
				<spring:message code="finder.keyWord"></spring:message>
			</form:label>
			<form:input path="keyWord" id="keyWord" name="keyWord" />
			<form:errors cssClass="error" path="keyWord" />
			<br>

			<form:label path="minPrice">
				<spring:message code="finder.minPrice"></spring:message>
			</form:label>
			<form:input path="minPrice" id="minPrice" name="minPrice" />
			<form:errors cssClass="error" path="minPrice" />
			<br>

			<form:label path="maxPrice">
				<spring:message code="finder.maxPrice"></spring:message>
			</form:label>
			<form:input path="maxPrice" id="maxPrice" name="maxPrice" />
			<form:errors cssClass="error" path="maxPrice" />
			<br>

			<form:label path="start">
				<spring:message code="finder.start"></spring:message>
			</form:label>
			<form:input path="start" id="start" name="start" />
			<form:errors cssClass="error" path="start" />
			<br>

			<form:label path="end">
				<spring:message code="finder.end"></spring:message>
			</form:label>
			<form:input path="end" id="end" name="end" />
			<form:errors cssClass="error" path="end" />
			<br>

			<form:label path="category">
				<spring:message code="finder.category" />:
			</form:label>
			<form:select id="categories" path="category">
				<form:option value="0" label="----" />
				<form:options items="${categories}" itemValue="id" itemLabel="title" />
			</form:select>
			<form:errors cssClass="error" path="categories" />


			<form:label path="warranty">
				<spring:message code="finder.warranty" />:
			</form:label>
			<form:select id="warranties" path="warranty">
				<form:option value="0" label="----" />
				<form:options items="${warranties}" itemValue="id" itemLabel="title" />
			</form:select>
			<form:errors cssClass="error" path="warranties" />


			<input type="submit" name="save"
				value="<spring:message code="finder.search" />" />&nbsp; 


		</form:form>
	</div>

























</security:authorize>


<spring:message code="finder.keyWord" />
<jstl:out value="${curriculum.ticker}" />

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:out value="${curriculum.handyWorker.id}" value="handyWorkerId"></jstl:out>
	<security:authentication property="principal.username" var="username" />
	<jstl:if
		test='${curriculum.handyWorker.userAccount.username} == ${username}'>


		<fieldset>
			<legend>
				<spring:message code="curriculum.personalRecord"></spring:message>
			</legend>
			<spring:message code="curriculum.fullName"></spring:message>
			<jstl:out value="${customer.personalRecord.fullName}"></jstl:out>
			<br />
			<spring:message code="curriculum.linkedinProfile"></spring:message>
			<jstl:out value="${customer.personalRecord.linkedinProfile}"></jstl:out>
			<br />
			<jstl:if test="${curriculum.id}== 0">
				<spring:message code="curriculum.add" var="add"></spring:message>
				<input type="button" name="add" value="${add}"
					onclick="javascript:relativeRedir('personalRecord/create.do?handyWorkerId=${curriculum.handyWorker.id}')" />
			</jstl:if>
			<spring:message code="curriculum.display" var="display"></spring:message>
			<input type="button" name="display" value="${display}"
				onclick="javascript:relativeRedir('personalRecord/display.do?personalRecordId=${curriculum.personalRecord.id}')" />
			<spring:message code="curriculum.edit" var="edit"></spring:message>
			<input type="button" name="edit" value="${edit}"
				onclick="javascript:relativeRedir('personalRecord/edit.do?personalRecordId=${curriculum.personalRecord.id}')" />
		</fieldset>




		<fieldset>
			<legend>
				<spring:message code="curriculum.EducationRecords"></spring:message>
			</legend>
			<display:table name="educationRecords" id="eduactionRecord"
				pagesize="5" class="displaytag">

				<spring:message code="curriculum.diplomeTitle" var="diplomeTitle"></spring:message>
				<display:column property="name" title="${diplomeTitle}"
					sortable="true" />

			</display:table>


			<spring:message code="curriculum.fullName"></spring:message>
			<jstl:out value="${customer.personalRecord.fullName}"></jstl:out>
			<br />
			<spring:message code="curriculum.linkedinProfile"></spring:message>
			<jstl:out value="${customer.personalRecord.linkedinProfile}"></jstl:out>
			<br />
			<jstl:if test="${curriculum.id}== 0">
				<spring:message code="curriculum.add" var="add"></spring:message>
				<input type="button" name="add" value="${add}"
					onclick="javascript:relativeRedir('personalRecord/create.do?handyWorkerId=${curriculum.handyWorker.id}')" />
			</jstl:if>
			<spring:message code="curriculum.display" var="display"></spring:message>
			<input type="button" name="display" value="${display}"
				onclick="javascript:relativeRedir('personalRecord/display.do?personalRecordId=${curriculum.personalRecord.id}')" />
			<spring:message code="curriculum.edit" var="edit"></spring:message>
			<input type="button" name="edit" value="${edit}"
				onclick="javascript:relativeRedir('personalRecord/edit.do?personalRecordId=${curriculum.personalRecord.id}')" />
		</fieldset>





	</jstl:if>
	<security:authentication property="principal.username" />
</security:authorize>

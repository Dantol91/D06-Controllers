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




<div>
	<form:form action="administrator/edit.do" method="post" id="formCreate"
		name="formCreate" modelAttribute="administrator">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="suspicious" />
		<form:hidden path="userAccount.authorities" />




		<jstl:if test="${administrator.id == 0 }">
			<fieldset>
				<legend>
					<spring:message code="administrator.useraccount" />
				</legend>
				<div>
					<form:label path="userAccount.username">
						<spring:message code="administrator.useraccount.username" />
					</form:label>
					<form:input path="userAccount.username" />
					<form:errors path="userAccount.username" cssClass="error" />
				</div>

				<div>
					<form:label path="userAccount.password">
						<spring:message code="administrator.useraccount.password" />
					</form:label>
					<form:password path="userAccount.password" />
					<form:errors path="userAccount.password" cssClass="error" />
				</div>
			</fieldset>
		</jstl:if>



		<form:label path="name">
			<spring:message code="administrator.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />

		<form:label path="middleName">
			<spring:message code="administrator.middleName" />
		</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br />


		<form:label path="surname">
			<spring:message code="administrator.surname" />
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br />

		<form:label path="photo">
			<spring:message code="administrator.photo" />
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br />

		<form:label path="email">
			<spring:message code="administrator.email" />
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br />

		<form:label path="phone">
			<spring:message code="administrator.phone" />
		</form:label>
		<form:input path="phone" />
		<form:errors cssClass="error" path="phone" />
		<br />

		<form:label path="address">
			<spring:message code="administrator.address" />
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br />


		<!--  Botones -->

		<input type="submit" name="save"
			value="<spring:message code="administrator.save"></spring:message>" 
			onclick="javascript:relativeRedir('administrator/display.do')" />

		<jstl:if test="${administrator.id == 0 }">
			<input type="button" name="cancel"
				value="<spring:message code="administrator.cancel"></spring:message>"
				onclick="javascript:relativeRedir('/')" />
		</jstl:if>

		<jstl:if test="${administrator.id != 0 }">

			<input type="button" name="cancel"
				value="<spring:message code="administrator.cancel"></spring:message>"
				onclick="javascript:relativeRedir('administrator/display2.do')" />

		</jstl:if>
	</form:form>




</div>


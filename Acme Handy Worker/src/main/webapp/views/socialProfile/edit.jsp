<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${isPrincipalAuthorizedEdit}">

<form:form action="socialprofile/actor/edit.do" modelAttribute="socialProfile" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />
	
	<div>	
		<form:label path="nick">
			<spring:message code="socialprofile.nick" />
		</form:label>	
		<form:input path="nick" />	
		<form:errors path="nick" cssClass="error" />
	</div>
	
	<div>	
		<form:label path="networkName">
			<spring:message code="socialprofile.networkname" />
		</form:label>	
		<form:input path="networkName" />	
		<form:errors path="networkName" cssClass="error" />
	</div>
	
	<div>	
		<form:label path="profile">
			<spring:message code="socialprofile.profile" />
		</form:label>	
		<form:input path="profile" />	
		<form:errors path="profile" cssClass="error" />
	</div>

<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="socialprofile.save" />
	</button>
	<jstl:if test="${socialProfile.id > 0}">
		<button type="submit" name="delete" class="btn btn-primary">
			<spring:message code="socialprofile.delete" />
		</button>
	</jstl:if>
</jstl:if>	
	
</form:form>

<button type="button" onclick="javascript: relativeRedir('socialprofile/actor/list.do')" >
	<spring:message code="socialprofile.cancel" />
</button>

</jstl:if>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form id="formID" action="endorserRecord/handyWorker/edit.do" modelAttribute="endorserRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="fullName">
		<spring:message code="endorserRecord.fullName" />:
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br/>
	<br/>
	
	<form:label path="email">
		<spring:message code="endorserRecord.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br/>
	<br/>
	
	<form:label path="phoneNumber">
		<spring:message code="endorserRecord.phoneNumber" />:
	</form:label>
	<form:input path="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br/>
	<br/>
	
	<form:label path="linkedInProfileUrl">
		<spring:message code="endorserRecord.linkedInProfileUrl" />:
	</form:label>
	<form:input path="linkedInProfileUrl" />
	<form:errors cssClass="error" path="linkedInProfileUrl" />
	<br/>
	<br/>
	
	<form:label path="comment">
		<spring:message code="endorserRecord.comment" />:
	</form:label>
	<form:textarea path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br/>
	<br/>


	<input type="submit" name="save"
		value="<spring:message code="endorserRecord.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript: relativeRedir('/curriculum/handyWorker/displayMyCurriculum.do');" />
		
	<jstl:if test="${endorserRecord.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="endorserRecord.delete" />" />
	</jstl:if>

</form:form>

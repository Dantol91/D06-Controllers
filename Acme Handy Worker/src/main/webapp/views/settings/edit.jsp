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

<form:form action="settings/administrator/edit.do" modelAttribute="settings" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<div>
		<form:label path="banner">
			<spring:message code="settings.banner" />
		</form:label>	
		<form:input path="banner" />	
		<form:errors path="banner" cssClass="error" />
	</div>
	
	
<fieldset><legend><spring:message code="settings.spamwords" /></legend>

	<jstl:if test="${settings.spamWords.size() == 0}">
	
		<form:hidden path="spamWords" />
	
	</jstl:if>
	
	<jstl:if test="${settings.spamWords.size() > 0}">
	
		<jstl:forEach begin="0" end="${settings.spamWords.size() - 1}" varStatus="varStatusSpamWords" >
			<div>
				<form:label path="spamWords[${varStatusSpamWords.index}]">
					<spring:message code="settings.spamword" />
				</form:label>	
				<form:input path="spamWords[${varStatusSpamWords.index}]" />	
				<form:errors path="spamWords[${varStatusSpamWords.index}]" cssClass="error" />
			</div>
		</jstl:forEach>
		
		<spring:message code="settings.spamword.remove" var="removeSpamWordTitle" />
		<button type="submit" name="removeSpamWord" class="btn btn-primary">
			${removeSpamWordTitle}
		</button>
	
	</jstl:if>

	<spring:message code="settings.spamword.add" var="addSpamWordTitle" />
	<button type="submit" name="addSpamWord" class="btn btn-primary">
		${addSpamWordTitle}
	</button>

</fieldset>

<fieldset><legend><spring:message code="settings.positivewords" /></legend>

	<jstl:if test="${settings.positiveWords.size() == 0}">
	
		<form:hidden path="positiveWords" />
	
	</jstl:if>
	
	<jstl:if test="${settings.positiveWords.size() > 0}">
	
		<jstl:forEach begin="0" end="${settings.positiveWords.size() - 1}" varStatus="varStatusPositiveWords" >
			<div>
				<form:label path="positiveWords[${varStatusPositiveWords.index}]">
					<spring:message code="settings.positiveword" />
				</form:label>	
				<form:input path="positiveWords[${varStatusPositiveWords.index}]" />	
				<form:errors path="positiveWords[${varStatusPositiveWords.index}]" cssClass="error" />
			</div>
		</jstl:forEach>
		
		<spring:message code="settings.positiveword.remove" var="removePositiveWordTitle" />
		<button type="submit" name="removePositiveWord" class="btn btn-primary">
			${removePositiveWordTitle}
		</button>
	
	</jstl:if>

	<spring:message code="settings.positiveword.add" var="addPositiveWordTitle" />
	<button type="submit" name="addPositiveWord" class="btn btn-primary">
		${addPositiveWordTitle}
	</button>

</fieldset>

<fieldset><legend><spring:message code="settings.negativewords" /></legend>

	<jstl:if test="${settings.negativeWords.size() == 0}">
	
		<form:hidden path="negativeWords" />
	
	</jstl:if>
	
	<jstl:if test="${settings.negativeWords.size() > 0}">
	
		<jstl:forEach begin="0" end="${settings.negativeWords.size() - 1}" varStatus="varStatusNegativeWords" >
			<div>
				<form:label path="negativeWords[${varStatusNegativeWords.index}]">
					<spring:message code="settings.negativeword" />
				</form:label>	
				<form:input path="negativeWords[${varStatusNegativeWords.index}]" />	
				<form:errors path="negativeWords[${varStatusNegativeWords.index}]" cssClass="error" />
			</div>
		</jstl:forEach>
		
		<spring:message code="settings.negativeword.remove" var="removeNegativeWordTitle" />
		<button type="submit" name="removeNegativeWord" class="btn btn-primary">
			${removeNegativeWordTitle}
		</button>
	
	</jstl:if>

	<spring:message code="settings.negativeword.add" var="addNegativeWordTitle" />
	<button type="submit" name="addNegativeWord" class="btn btn-primary">
		${addNegativeWordTitle}
	</button>

</fieldset>
	
<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="settings.save" />
	</button>
</jstl:if>	
	
</form:form>

<button type="button" onclick="javascript: relativeRedir('/')" >
	<spring:message code="settings.cancel" />
</button>


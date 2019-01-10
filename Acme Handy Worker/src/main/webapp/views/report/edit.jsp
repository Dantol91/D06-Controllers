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

<jstl:if test="${isPrincipalAuthorisedEdit}">

	<form:form action="report/referee/edit.do" modelAttribute="report" method="post">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="complaint" />
		<form:hidden path="notes" />
			
		<div>
			<form:label path="description">
				<spring:message code="report.description" />
			</form:label>	
			<form:input path="description" />	
			<form:errors path="description" cssClass="error" />
		</div>
		
		<fieldset><legend><spring:message code="report.attachments" /></legend>
			
			<jstl:if test="${report.attachments.size() > 0}">
			
				<jstl:forEach items="${report.attachments}" var="attachment" varStatus="varStatusAttachments" >
					
					<div>
						<form:label path="attachments[${varStatusAttachments.index}]">
							<spring:message code="report.attachment" />
						</form:label>	
						<form:input path="attachments[${varStatusAttachments.index}]" />	
						<form:errors path="attachments[${varStatusAttachments.index}]" cssClass="error" />
					</div>
					
				</jstl:forEach>
				
			</jstl:if>
		
			<jstl:if test="${report.attachments.size() == 0}">
			
				<form:hidden path="attachments" />
			
			</jstl:if>
		
			<spring:message code="report.attachment.add" var="addAttachmentTitle" />
			<button type="submit" name="addAttachment" class="btn btn-primary">
				${addAttachmentTitle}
			</button>
			
			<jstl:if test="${report.attachments.size() > 0}">
				<spring:message code="report.attachment.remove" var="removeAttachmentTitle" />
				<button type="submit" name="removeAttachment" class="btn btn-primary">
					${removeAttachmentTitle}
				</button>
			</jstl:if>
			
		</fieldset>
		
		<div>
			<form:label path="draft">
				<spring:message code="report.draft" />
			</form:label>	
			<form:checkbox path="draft" />	
			<form:errors path="draft" cssClass="error" />
		</div>
	
	<jstl:if test="${isPrincipalAuthorizedEdit}">	
		<button type="submit" name="save" class="btn btn-primary">
			<spring:message code="report.save" />
		</button>
		<button type="submit" name="delete" class="btn btn-primary">
			<spring:message code="report.delete" />
		</button>
	</jstl:if>
		
	</form:form>
	
</jstl:if>

<jstl:choose>
	<jstl:when test="${report.id > 0}">
		<button type="button" onclick="javascript: relativeRedir('report/actor/profile.do?reportId=${report.id}')" >
			<spring:message code="report.cancel" />
		</button>
	</jstl:when>
	<jstl:when test="${report.id == 0}">
		<button type="button" onclick="javascript: relativeRedir('')" >
			<spring:message code="report.cancel" />
		</button>
	</jstl:when>
</jstl:choose>
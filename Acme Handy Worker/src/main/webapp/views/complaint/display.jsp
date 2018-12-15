<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- Guardamos en una variable el formato de la fecha  -->
	
	<spring:message code="master.page.locale" var="locale" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	
	
 	<fmt:setLocale value="${locale}"/>
	
	
<div id="complaint">

	<ul style="list-style-type: disc">

		<li><b><spring:message code="complaint.title"></spring:message>:</b>
			<jstl:out value="${complaint.getTitle()}" /></li>

		<li><b><spring:message code="complaint.description"></spring:message>:</b>
			<jstl:out value="${complaint.getDescription()}" /></li>

		<li><b><spring:message code="complaint.moment"></spring:message>:</b> 
		<fmt:formatDate value="${complaint.getMoment()}" pattern="${dateFormat}" /></li>
		
		<li><b><spring:message code="complaint.attachmentLink"></spring:message>:</b>
			<jstl:out value="${complaint.getAttachmentLink()}" /></li>
	

		<li><b><spring:message code="complaint.fixUpTask"></spring:message>:</b>
				<jstl:out value="${complaint.getFixUpTask().getId()}"/></li>

	</ul>

</div>


	<security:authorize access="hasRole('CUSTOMER')">

		<input type="button" name="edit"
			value="<spring:message code="complaint.edit" />"
			onclick="javascript: relativeRedir('complaint/customer/edit.do?complaintId=${complaint.getId()}')" />
		<input type="button" name="cancel"
			value="<spring:message code="complaint.cancel" />"
			onclick="javascript: relativeRedir('complaint/customer/list-all.do')" />
	</security:authorize>

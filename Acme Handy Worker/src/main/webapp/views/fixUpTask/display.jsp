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

<%
	Cookie[] cookies = request.getCookies();
	Cookie languageCookie = null;
	for (Cookie c : cookies) {
		if (c.getName().equals("language")) {
			languageCookie = c;
		}
	}

	String languageValue = languageCookie.getValue();
%>

<b><spring:message code="fixupTask.moment"></spring:message>:</b>
<jstl:out value="${fixupTask.moment}"></jstl:out>
<br />

<b><spring:message code="fixupTask.description"></spring:message>:</b>
<jstl:out value="${fixupTask.description}"></jstl:out>
<br />

<b><spring:message code="fixupTask.address"></spring:message>:</b>
<jstl:out value="${fixupTask.address}"></jstl:out>
<br />

<b><spring:message code="fixupTask.maximumPrice"></spring:message>:</b>
<jstl:out value="${fixupTask.maximumPrice}"></jstl:out>
<br />

<b><spring:message code="fixupTask.start"></spring:message>:</b>
<jstl:out value="${fixupTask.start}"></jstl:out>
<br />

<b><spring:message code="fixupTask.end"></spring:message>:</b>
<jstl:out value="${fixupTask.end}"></jstl:out>
<br />

<b><spring:message code="fixupTask.customer"></spring:message>:</b>
<jstl:out value="${fixupTask.customer.name}"></jstl:out>

<security:authorize access="hasRole('HANDYWORKER')">
	<input type="button" name="edit"
		value="<spring:message code="fixupTask.showCustomer"></spring:message>"
		onclick="javascript:relativeRedir('endorsable/customer/display.do?customerId=${fixupTask.customer.id}')" />

</security:authorize>

<br />

<b><spring:message code="fixupTask.category"></spring:message>:</b>
<%
	if (languageValue.equals("en")) {
%>
<jstl:out value="${fixupTask.category.nameEnglish}"></jstl:out>
<%
	} else if (languageValue.equals("es")) {
%>
<jstl:out value="${ffixupTask.category.nameSpanish}" />
<%
	}
%>
<br />

<b><spring:message code="fixupTask.warranty"></spring:message>:</b>
<jstl:out value="${fixupTask.warranty.title}"></jstl:out>
<br />


<fieldset>
	<legend>
		<b><spring:message code="fixupTask.applicationsLegend"></spring:message></b>
	</legend>
	
	
	<display:table name="apps" id="app" pagesize="5" class="displaytag">
	
	
	
		<%-- <jstl:if test="${status == 'PENDING'}">
		<display:column>
				<a href="application/endorsable/edit.do?applicationId=${app.id}"> <spring:message
						code="fixupTask.edit" />
				</a>
		</display:column>
		</jstl:if>
			<display:column property="status" sortable="true">
			<jstl:if test='${status == PENDING}'>
				
			</jstl:if>
			</display:column> --%>
			
		
		
	
		<display:column>
			<a href="application/endorsable/display.do?applicationId=${app.id}"> <spring:message
					code="fixupTask.display" />
			</a>
		</display:column>

		<spring:message code="fixupTask.application.price" var="price"></spring:message>
		<display:column property="price" title="${price}" sortable="true" />

		<spring:message code="fixupTask.application.handyWorker"
			var="handyWorkerName"></spring:message>
		<display:column property="handyWorker.name" title="${handyWorkerName}"
			sortable="true" />

		<spring:message code="fixupTask.application.status" var="status"></spring:message>
		<display:column property="status" title="${status}" sortable="true" />
		
		
		
		
	</display:table>
</fieldset>

<fieldset>
	<legend>
		<b><spring:message code="fixupTask.complaintsLegend"></spring:message></b>
	</legend>
	<display:table name="compls" id="compl" pagesize="5" class="displaytag">
	
	<display:column>
			<a href="complaint/display.do?complaintId=${compl.id}"> <spring:message
					code="fixupTask.display" />
			</a>
		</display:column>

		<spring:message code="fixupTask.complaint.description"
			var="description"></spring:message>
		<display:column property="description" title="${description}"
			sortable="true" />

		<spring:message code="fixupTask.complaint.referee" var="refereeName"></spring:message>
		<display:column property="referee.name" title="${refereeName}"
			sortable="true" />
	</display:table>
</fieldset>





<security:authorize access="hasRole('CUSTOMER')">
	<input type="button" name="edit"
		value="<spring:message code="fixupTask.edit"></spring:message>"
		onclick="javascript:relativeRedir('fixupTask/customer/edit.do?fixupTaskId=${fixupTask.id}')" />

</security:authorize>

<input type="button" name="cancel"
	value="<spring:message code="fixupTask.cancel"></spring:message>"
	onclick="javascript:relativeRedir('fixupTask/endorsable/list.do')" />











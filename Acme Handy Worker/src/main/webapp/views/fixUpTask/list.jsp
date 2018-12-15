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

<style type="text/css">

.links{font-size: 30px}
.current{color: blue;}

</style>

<!-- Listing grid -->

<jstl:if test="${fromFinder eq true}">
<jstl:set var="tarea" value="100"/>
</jstl:if>

<display:table class="displaytag" keepStatus="true" name="visiblefixUpTasks" pagesize="${tarea}"
	requestURI="${requestURI}" id="row">

	<!-- Action links -->

	<!-- Attributes -->

	<!-- SE GUARDA SI ES CUSTOMER PARA EL DISPLAY -->
	<security:authorize access="hasRole('CUSTOMER')" var="iscustomer" />	

	<spring:message code="fixUpTask.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />
	
	<spring:message code="fixUpTask.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" /> 


	<%--Se guarda el formato del precio--%>
	<spring:message code="master.page.maxPrice.format" var="maxPriceFormat" />

	<spring:message code="fixUpTask.maxPrice" var="maxPriceHeader" />
	<spring:message code="master.page.maxPrice.format" var="maxPriceFormat" />
	<display:column property="maxPrice" title="${maxPriceHeader}"
		format="${maxPriceFormat}" />
	
	<spring:message code="fixUpTask.publicationDate" var="publicationDateHeader" />
	<display:column property="publicationDate" title="${publicationDateHeader}" format="{0,date,dd/MM/yyyy HH:mm}" /> --%>

	<spring:message code="fixUpTask.startDate" var="startDateHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="startDate" title="${startDateHeader}"
		format="{0,date,${dateFormat}}" />

	<spring:message code="fixUpTask.endDate" var="endDateHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="endDate" title="${endDateHeader}"
		format="{0,date,${dateFormat}}" />

	

	<display:column>
		<%-- TODO EL MUNDO PUEDE VER LOS DETALLES DE UNA FIXUPTASK PERO CUSTOMER TIENE OTRA VISTA --%>
		<jstl:choose>
			<jstl:when test="${isCustomer}">
				<a
					href="fixUpTask/customer/display.do?fixUpTaskId=<jstl:out value="${row.getId()}"/>"><spring:message
						code="fixUpTask.display" /></a>
				<br />
			</jstl:when>
			<jstl:otherwise>
				<a href="fixUpTask/display.do?fixUpTaskId=<jstl:out value="${row.getId()}"/>"><spring:message
						code="fixUpTask.display" /></a>
				<br />
			</jstl:otherwise>
		</jstl:choose>

		<%-- LOS HANDYWORKERS PUEDEN APLICAR A UNA FIXUPTASK --%>
		<security:authorize access="hasRole('HANDYWORKER')">
		<a href="application/handyWorker/create.do?fixUpTaskId=<jstl:out value="${row.getId()}"/>"><spring:message code="fixUpTask.apply"/></a><br/>
		</security:authorize> 

	 
		<%-- LOS CUSTOMER EDITAN FIXUPTAKS --%>
		<security:authorize access="hasRole('CUSTOMER')">
		<a href ="fixUpTask/customer/edit.do?fixUpTaskId=<jstl:out value="${row.getId()}"/>"><spring:message code="fixUpTask.edit" /></a>
		</security:authorize>
	</display:column>

</display:table>


<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/create.do"><spring:message code="fixUpTask.create" /></a>
</security:authorize>




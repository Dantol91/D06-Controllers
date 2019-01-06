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

	<!-- Se guarda en una variable el formato de la fecha y el precio según el idioma -->
	
	<spring:message code="master.page.locale" var="locale" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<spring:message code="master.page.maxPrice.format.display" var="priceFormat" />
	<spring:message code="master.page.maxPrice.currencyCode" var="currencyCode" />
	<fmt:setLocale value="${locale}"/>
	
	
<div id="fixUpTaskDiv">

	<ul style="list-style-type: disc">
		
		<li><b><spring:message code="fixUpTask.ticker"></spring:message>:</b>
			<jstl:out value="${fixUpTask.getTicker()}" /></li> 

		<!-- <li><b><spring:message code="fixUpTask.description"></spring:message>:</b> 
		 <li><jstl:out value="${fixUpTask.getDescription()}" /></li> -->
		<br/> 
		
		<div id="descriptionDiv">
			<jstl:out value="${fixUpTask.getDescription()}" />
		</div>
		
		
		<li><b><spring:message code="fixUpTask.maxPrice"></spring:message>:</b>
		<fmt:setLocale value="es_ES"/> 
		<fmt:formatNumber value="${fixUpTask.getMaxPrice()}" pattern="${priceFormat}" currencySymbol="${currencyCode}"/>
		<jstl:out value="${currency}"/>
		</li>

		<li><b><spring:message code="fixUpTask.publicationDate"></spring:message>:</b>
				<fmt:formatDate value="${fixUpTask.getPublicationDate()}" pattern="${dateFormat}" /></li>

		<li><b><spring:message code="fixUpTask.startDate"></spring:message>:</b>
				<fmt:formatDate value="${fixUpTask.getStartDate()}" pattern="${dateFormat}" /></li>

		<li><b><spring:message code="fixUpTask.endDate"></spring:message>:</b>
				<fmt:formatDate value="${fixUpTask.getEndDate()}" pattern="${dateFormat}" /></li>		
		
		<div id="categoryDiv">
		<spring:message code="master.page.category"/>:<br/>
		<jstl:out value="${fixUpTask.category.name}"/>
		</div>
		
	</ul>

</div>

<input type="button" name="back"
	value="<spring:message code="fixUpTask.back" />"
	onclick="javascript: relativeRedir('fixUpTask/list.do')" />
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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="sponsorships" requestURI="sponsorship/sponsor/list.do"
	id="row">

	<!-- Action links -->
	
	<!-- Attributes -->

	<%-- <display:column>
		<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}"><spring:message
				code="sponsorship.edit" /></a>
	</display:column> --%>

	<%-- <spring:message code="sponsorship.bannerURL" var="bannerURL" />
	<display:column property="bannerURL" title="${bannerURL}"
		sortable="false" /> --%>

	<spring:message code="sponsorship.targetPage" var="targetPage" />
	<display:column property="targetPage" title="${targetPage}"
		sortable="false" />

	<spring:message code="sponsorship.creditCard" var="creditCard" />
	<display:column property="creditCard.brandName" title="${creditCard}"
		sortable="false" />
		
	<spring:message code="sponsorship.fixUpTask" var="fixUpTask" />
	<display:column property="fixUpTask.id" title="${fixUpTask}"
		sortable="false" />
		
	<display:column>
			<a href="sponsorship/sponsor/display.do?sponsorshipId=<jstl:out value="${row.getId()}"/>"><spring:message code="fixUpTask.display" /></a><br/>
	</display:column>
</display:table>

<a href="sponsorship/sponsor/create.do"><spring:message
		code="sponsorship.create" /></a>



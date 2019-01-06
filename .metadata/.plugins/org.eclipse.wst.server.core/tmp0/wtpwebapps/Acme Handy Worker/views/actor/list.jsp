<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="actors" requestURI="actor/administrator/list.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="userAccount.username" var="usernameHeader" />
	<display:column property="userAccount.username" title="${usernameHeader}"  />
	
	<spring:message code="actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}"/>
	
	<spring:message code="actor.middleName" var="middleNameHeader" />
	<display:column property="middleName" title="${middleNameHeader}"  />

	<spring:message code="actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"  />
	
	<spring:message code="actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"  />
	
	<spring:message code="actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"  />

	<spring:message code="actor.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}"/>
	
	<spring:message code="actor.photo" var="photoHeader" />
	<display:column title="${photoHeader}">
		<img src="${row.photo}"
			alt="<spring:message code="image.notfound"/>" width="75" height="75" />
	</display:column>
	
	
	<spring:message code="actor.banOrUnban" var="banHeader" />
	<display:column title="${banHeader}" sortable="false">
	
	<jstl:choose>
		<jstl:when test="${row.userAccount.enabled eq false}">
			<a href="actor/unban.do?actorId=${row.id}"><spring:message code="actor.unban"/></a>
		</jstl:when>
		<jstl:otherwise>
			<a href="actor/ban.do?actorId=${row.id}"><spring:message code="actor.ban"/></a>
		</jstl:otherwise>
	</jstl:choose>
	</display:column>

</display:table>


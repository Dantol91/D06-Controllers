<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="boxes" id="row" requestURI="box/move.do">
	<spring:message code="box.box" var="boxHeader" />
	<display:column property="name" title="${ boxrHeader}" />
	<display:column>
		<jstl:choose>
			<jstl:when test="${box.box eq row.name}">
				<spring:message code="box.samebox" />
			</jstl:when>
			<jstl:otherwise>
				<a href="box/saveMove.do?targetfolderId=${row.id}&boxId=${box.getId()}"><spring:message
						code="box.move" /></a>		
						
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
</display:table>
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

<h2>
	<jstl:out value="${box.getName()}" />
</h2>

<b><spring:message code="box.boxes" /> :</b>
<br />
<display:table pagesize="10" class="displaytag" name="boxes"
	requestURI="box/display.do" id="row">

	<spring:message code="box.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader }" sortable="true" />

	<display:column>
		<a href="box/display.do?boxId=<jstl:out value="${row.id}"/>"><spring:message
				code="box.show" /></a>
	</display:column>


</display:table>
<br />
<br />
<b><spring:message code="box.messages" /> :</b>
<br />
<display:table pagesize="10" class="displaytag" name="messages"
	requestURI="box/display.do" id="row2">

	<spring:message code="ms.subject" var="nameHeader" />
	<display:column property="subject" title="${nameHeader }"
		sortable="true" />

	<display:column>
		<a href="message/display.do?messageId=<jstl:out value="${row2.id}"/>"><spring:message
				code="ms.show" /></a>
	</display:column>


</display:table>

<br />
<br />


<a href="box/create.do?boxId=${box.id }"><spring:message
		code="box.newbox" /></a>

<input type="button" name="back"
	value="<spring:message code="box.back"/>"
	onclick=<jstl:choose>
			<jstl:when test="${empty box.getParentBox()}">
			<jstl:out value="javascript:relativeRedir('box/list.do')"/>
		</jstl:when>
		<jstl:otherwise>
		<jstl:out value="javascript:relativeRedir('box/display.do?boxId=${box.parentBox.id}')"/>
		</jstl:otherwise>
		</jstl:choose> />
<jstl:if test="${box.getSystemBox() eq false }">

	<%-- <input type="submit" name="move"
		value="<spring:message code="box.move" />" /> --%>
	 <input type="button" name="move"
		value="<spring:message code="box.move" />"
		onclick="javascript: relativeRedir('box/move.do?boxId=<jstl:out value="${box.getId()}"/>');" /> 
	<input type="button" name="delete"
		value="<spring:message code="box.delete" />"
		onclick="javascript: relativeRedir('box/delete.do?boxId=<jstl:out value="${box.getId()}"/>');" />

</jstl:if>

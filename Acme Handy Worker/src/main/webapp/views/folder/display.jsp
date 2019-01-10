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

<fieldset>
<spring:message code="folder.name"></spring:message>:
<jstl:out value="${fo.name}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="folder.system"></spring:message>:
<jstl:out value="${fo.systemFolder}"></jstl:out>
</fieldset>
	

<fieldset>
<spring:message code="folder.folders"></spring:message>:
<jstl:forEach items="${fo.folders}" var="fol">
<jstl:out value="${fol.name}"></jstl:out>
</jstl:forEach>
</fieldset>

<fieldset>
<spring:message code="folder.messages"></spring:message>:
<jstl:forEach items="${fo.messages}" var="mes">
<jstl:out value="${mes.subject}"></jstl:out>
</jstl:forEach>
</fieldset>
<br>
<spring:message code="folder.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('folder/actor/list.do')" />

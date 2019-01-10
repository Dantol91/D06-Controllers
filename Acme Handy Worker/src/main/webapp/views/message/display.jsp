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
<spring:message code="message.sentDate"></spring:message>:
<jstl:out value="${mes.sentDate}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="message.subject"></spring:message>:
<jstl:out value="${mes.subject}"></jstl:out>
</fieldset>
	
<fieldset>
<spring:message code="message.body"></spring:message>:
<jstl:out value="${mes.body}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="message.priority"></spring:message>:
<jstl:out value="${mes.priority}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="message.folder"></spring:message>:
		<a href="folder/actor/display.do?folderId=${mes.folder.id}">
			<jstl:out value="${mes.folder.name}" />
		</a>
</fieldset>
	
<fieldset>
<spring:message code="message.sender"></spring:message>:
<jstl:out value="${mes.sender}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="message.receiver"></spring:message>:
<jstl:out value="${mes.receiver}"></jstl:out>
</fieldset>
<br>
<spring:message code="message.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('message/actor/list.do')" />
				
		<spring:message code="message.delete" var="deleteHeader"></spring:message>
		<input type="button" name="delete" value="${deleteHeader}"
			onclick="javascript:relativeRedir('message/actor/list.do')" />

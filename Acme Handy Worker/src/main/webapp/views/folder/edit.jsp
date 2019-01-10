
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="folder/actor/edit.do" modelAttribute="folder">
	
	<jstl:if test="${isPrincipalAuthorizedEdit}">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="system"/>
		<form:hidden path="actor" />
		
		<div>
			<form:label path="name">
				<spring:message code="folder.name"/>
			</form:label>
			<form:input path="name" id="name" name="name"/>
			<form:errors path="name" class="error" />
		</div>
		
		<div>
			<form:label path="parentFolder">
				<spring:message code="folder.parentfolder"/>
			</form:label>
			<form:select path="parentFolder">
				<form:option value="${folder.id}">-----</form:option>
				<jstl:forEach items="${folders}" var="folderVar">
					<form:option value="${folderVar.id}">${folderVar.name}</form:option>
				</jstl:forEach>
			</form:select>
			<form:errors path="parentFolder" class="error" />
		</div>
		
		<div>
			<spring:message code="folder.save" var="saveHeader"></spring:message>
			<input type="submit" name="save" value="${saveHeader}"/>
			<jstl:if test="${folder.id > 0}">
				<spring:message code="folder.delete" var="deleteHeader"></spring:message>
				<input type="submit" name="delete" value="${deleteHeader}" />
			</jstl:if>
		</div>
	</jstl:if>
	
	
	
</form:form>

<div>
	<spring:message code="folder.cancel" var="cancelHeader"></spring:message>
	<button name="cancel" value="${cancelHeader}" onClick="javascript:relativeRedir('folder/actor/list.do');" >${cancelHeader}</button>
</div>

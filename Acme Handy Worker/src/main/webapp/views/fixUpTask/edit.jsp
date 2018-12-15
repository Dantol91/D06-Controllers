<link rel="stylesheet" href="styles/fixUpTask.css" type="text/css">

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

<script type="text/javascript">

$(document).ready(function() {
	 $("#formID").submit(function(){
	var partsPD = document.getElementById("publicationDateID").value.split('/');
	var publicationDate = new Date(partsPD[2],partsPD[1]-1,partsPD[0]); 
	var partsSD = document.getElementById("startDateID").value.split('/');
	var startDate = new Date(partsSD[2],partsSD[1]-1,partsSD[0]); 
	var partsED = document.getElementById("endDateID").value.split('/');
	var endDate = new Date(partsED[2],partsED[1]-1,partsED[0]); 
	
	if(publicationDate > startDate){
		alert("Publication date must be before the start date");
		return false;
	}else if(publicationDate > endDate){
		alert("Publication date must be before the end date");
		return false;
	}else if(startDate > endDate){
		alert("Start date must be before the end date");
		return false;
	}
	
});
});

</script>


<%-- SE OBTINE LA FECHA ACTUAL --%>
<jsp:useBean id="date" class="java.util.Date" />

<form:form id="formID" action="fixUpTask/customer/edit.do" modelAttribute="fixUpTask">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="applications" />

	<jstl:choose>

		<jstl:when test="${fixUpTask.getPublicationDate() lt date and fixUpTask.id!=0}">

			<form:hidden path="title" />
			<form:hidden path="description" />
			<form:hidden path="publicationDate" />
			<form:hidden path="startDate" />
			<form:hidden path="endDate" />
			<form:hidden path="category" />
			
		</jstl:when>
		<jstl:otherwise>

			<div id="fixUpTaskForm">

				<!-- Se guarda en una variable el formato de la fecha según el idioma -->
				<spring:message code="master.page.date.format" var="dateFormat" />

				<form:label path="description">
					<spring:message code="fixUpTask.description" />:
				</form:label>
				<form:textarea path="description" />
				<form:errors cssClass="error" path="description" />
				<br /> <br />

				<form:label path="publicationDate">
					<spring:message code="fixUpTask.publicationDate" />:
				</form:label>
				<form:input id="publicationDateID" path="publicationDate" class="fixUpTaskDate" placeholder="${dateFormat}" />
				<form:errors cssClass="error" path="publicationDate" />
				<br /> <br />


				<form:label path="startDate">
					<spring:message code="fixUpTask.startDate" />:
				</form:label>
				<form:input id="startDateID" path="startDate" class="fixUpTaskDate" placeholder="${dateFormat}" />
				<form:errors cssClass="error" path="startDate" />
				<br /> <br />


				<form:label path="endDate">
					<spring:message code="fixUpTask.endDate" />:
				</form:label>
				<form:input id="endDateID" path="endDate" class="fixUpTaskDate" placeholder="${dateFormat}" />
				<form:errors cssClass="error" path="endDate" />
				<br /> <br />


				<!-- RELACIONES -->

				<form:label path="category">
					<spring:message code="master.page.category" />:
				</form:label>
				
				<form:select path="category">
					<form:option value="0" label="----" />
					<jstl:forEach items="${categories}" var="cat">
						<form:option value="${cat.id}"
							label="${cat.name} [Parent: ${cat.parentCategory.name}]" />
					</jstl:forEach>
				</form:select>
				<form:errors cssClass="error" path="category" />
				<br /> <br />

				
				<jstl:if test="${fixUpTask.id != 0}">
					<input type="button"
						value="<spring:message code="fixUpTask.addStage" />"
						onclick="javascript: relativeRedir('stage/customer/create.do?fixUpTaskId=${fixUpTask.id}');" />
					<input type="button" value="<spring:message code="fixUpTask.addTag" />"
						onclick="javascript: relativeRedir('tag/list.do?fixUpTaskId=<jstl:out value="${fixUpTask.getId()}"/>');" />
				</jstl:if>
				<%-- SOLO LOS CUSTOMERS PUEDEN EDITAR Y ELIMINAR LAS FIXUPTASKS, SI LA PUBLICATION DATE NO HA PASADO --%>

				<%-- SE CONTROLA QUE LA PUBLICATION DATE NO HAYA PASADO PARA PODER ELIMINAR --%>
				<jstl:if test="${fixUpTask.getPublicationDate() gt date}">

					<security:authorize access="hasRole('CUSTOMER')">
						<jstl:if test="${fixUpTask.id != 0}">
							<input type="submit" name="delete"
								value="<spring:message code="fixUpTask.delete" />"
								onclick="return confirm('<spring:message code="fixUpTask.confirm.delete" />')" />
						</jstl:if>
					</security:authorize>
				</jstl:if>

			</div>
			<br />
		</jstl:otherwise>

	</jstl:choose>

	<br />

	<input type="submit" name="save"
		value="<spring:message code="fixUpTask.save" />" />&nbsp; 
	<jstl:if test="${fixUpTask.id != 0">
		<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('fixUpTask/customer/list.do');" />
	<br />

</form:form>
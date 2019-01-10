<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!--  Primero pongo la autoridad ya que solo un admin maneja las categorias -->
<security:authorize access="hasAnyRole('CUSTOMER' , 'HANDYWORKER')">

	<div>
		<form:form action="application/endorsable/edit.do" method="post" modelAttribute="application">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="handyWorker" />
			<form:hidden path="moment" />
		

		<security:authorize access="hasRole('HANDYWORKER')">
		
		
		
			<form:hidden path="status" />
			<form:hidden path="creditCard" />
			<form:hidden path="customerComments" />
			
			<form:label path="price"> <b><spring:message code="application.price" />:</b></form:label>
			<form:input path="price" /><form:errors cssClass="error" path="price" /><br />
			
			<form:label path="workerComments"> <b><spring:message code="application.workerComments" />:</b></form:label>
			<form:input path="workerComments" /><form:errors cssClass="error" path="workerComments" /><br />
			
			
				
			<form:label path="fixupTask">
				<b><spring:message code="application.fixupTask"></spring:message>:</b>
			</form:label>

			
			<form:select id="fixupTask" path="fixupTask">
			<form:option value="${application.fixupTask}" label="------"></form:option>
			<form:options items="${allFixupTasks}" itemLabel="ticker"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="fixupTask" />
			<jstl:if test="${fromFixup eq true }">
			<spring:message code="appication.fromFixupTask" />: 
			<b><jstl:out value="${fixupTask.ticker}"></jstl:out></b>
			
			 </jstl:if>
			<br />
			
			
			
			<%-- form:label path="fixupTask"> <spring:message code="application.fixupTask"></spring:message></form:label>
			<form:select id="fixupTask" path="fixupTask">
			<form:option value="${application.fixupTask}" label="-----"></form:option>
			<form:options items="${allFixupTasks}" itemLabel="ticker" itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="fixupTask" />
			<br /> --%>
		</security:authorize>
			
			
		<%-- Status--%>
		<security:authorize access="hasRole('CUSTOMER')">
		
			<form:hidden path="fixupTask" />
			<form:hidden path="workerComments" />
		
			<fieldset><legend><spring:message code="application.creditCard" /></legend>
			<jstl:choose>
				<jstl:when test="${useCreditCard}">
				
					<form:label path="creditCard.holderName"> <spring:message code="creditCard.holderName" /></form:label>
					<form:input path="creditCard.holderName" /><form:errors cssClass="error" path="creditCard.holderName" /><br />
					
					<form:label path="creditCard.brandName"> <spring:message code="creditCard.brandName" /></form:label>
					<form:input path="creditCard.brandName" /><form:errors cssClass="error" path="creditCard.brandName" /><br />
					
					<form:label path="creditCard.number"> <spring:message code="creditCard.number" /></form:label>
					<form:input path="creditCard.number" /><form:errors cssClass="error" path="creditCard.number" /><br />
					
					<form:label path="creditCard.expirationDate"> <spring:message code="creditCard.expirationDate" /></form:label>
					<form:input path="creditCard.expirationDate" /><form:errors cssClass="error" path="creditCard.expirationDate" /><br />
	
					<form:label path="creditCard.cvvCode"> <spring:message code="creditCard.cvv" /></form:label>
					<form:input path="creditCard.cvvCode" /><form:errors cssClass="error" path="creditCard.cvvCode" /><br />
				
					<jstl:if test="${not (application.status eq 'ACCEPTED')}">
						<input type="submit" name="removeCreditCard" value="<spring:message code="creditCard.notuse"></spring:message>" />
					</jstl:if>
				
				</jstl:when>
				<jstl:otherwise>
				
					<form:hidden path="creditCard" />
					
					<input type="submit" name="addCreditCard" value="<spring:message code="creditCard.use"></spring:message>" />
				
				</jstl:otherwise>
			</jstl:choose>
			</fieldset>
			
			<form:label path="status"><spring:message code="application.status"></spring:message></form:label>
			<form:select id="status" path="status">
			<form:option value="PENDING" label="PENDING"></form:option>
			<form:option value="ACCEPTED" label="ACCEPTED"></form:option>
			<form:option value="REJECTED" label="REJECTED"></form:option>
			</form:select>
			<form:errors cssClass="error" path="status" />
		
			<form:label path="customerComments"> <spring:message code="application.customerComments" /></form:label>
			<form:input path="customerComments" /><form:errors cssClass="error" path="customerComments" /><br />
		</security:authorize>  
			
			
				<!--  Los botones de crear y cancelar -->
			<br />

		<input type="submit" name="save" value="<spring:message code="application.save"></spring:message>" />	
		<security:authorize access="hasRole('HANDYWORKER')">	
				<button type="button"
					onclick="javascript: relativeRedir('application/handyworker/list.do')">
					<spring:message code="application.return" />
				</button>
			</security:authorize>
			<security:authorize access="hasRole('CUSTOMER')">
				<button type="button"
					onclick="javascript: relativeRedir('application/customer/list.do?fixupTaskId=${application.fixupTask.id}')">
					<spring:message code="application.return" />
				</button>
			</security:authorize>
			
		</form:form>
		
	</div> 

</security:authorize>


<%--
 * create.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<jstl:if test="${isPrincipalAuthorizedEdit}">

	<div>
		<form:form action="message/actor/edit.do" method="post" id="formCreate"
			name="formCreate" modelAttribute="messageObject">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment" />
			<form:hidden path="sender" />

			<jstl:if test="${messageObject.id == 0}">
			
				<form:hidden path="folder" />
				
				<div>
					<form:label path="subject">
						<spring:message code="message.subject"></spring:message>
					</form:label>
					<form:input path="subject" id="subject" name="subject" />
					<form:errors cssClass="error" path="subject"></form:errors>
				</div>
				
				<div class="form-group">
					<form:label path="body">
						<spring:message code="message.body"></spring:message>
					</form:label>
					<form:textarea path="body" id="body" name="body" />
					<form:errors cssClass="error" path="body"></form:errors>
				</div>
				
				<div>
					<form:label path="priority">
						<spring:message code="message.priority"></spring:message>
					</form:label>
					<form:select path="priority">
						<form:option value="">-----</form:option>
						<form:option value="HIGH"><spring:message code="message.HIGH" /></form:option>
						<form:option value="NEUTRAL"><spring:message code="message.NEUTRAL" /></form:option>
						<form:option value="LOW"><spring:message code="message.LOW" /></form:option>
					</form:select>
					<form:errors cssClass="error" path="priority"></form:errors>
				</div>
				
				<div>
					<form:label path="tags">
						<spring:message code="message.tags"></spring:message>
					</form:label>
					<form:input path="tags" id="tags" name="tags" />
					<form:errors cssClass="error" path="tags"></form:errors>
				</div>
				
				<div>
					<form:label path="receiver">
						<spring:message code="message.receiver"></spring:message>
					</form:label>
					<form:select path="receiver">
						<form:option value="0">-----</form:option>
						<jstl:forEach items="${actors}" var="actor">
							<form:option value="${actor.id}"><jstl:out value="${actor.name} ${actor.surname}" /></form:option>
						</jstl:forEach>
					</form:select>
					<form:errors cssClass="error" path="receiver"></form:errors>
				</div>
		
			</jstl:if>
		
			<jstl:if test="${messageObject.id > 0}">
				
				<form:hidden path="subject" />
				<form:hidden path="body" />
				<form:hidden path="tags" />
				<form:hidden path="priority" />
				<form:hidden path="receiver" />
			
				<div>
					<form:label path="folder">
						<spring:message code="message.folder" />
					</form:label>
					<form:select path="folder" >
						<form:option value="0">-----</form:option>
						<jstl:forEach items="${folders}" var="folder">
							<form:option value="${folder.id}"><jstl:out value="${folder.name}" /></form:option>
						</jstl:forEach>
					</form:select>
					<form:errors cssClass="error" path="folder" />
				</div>
				
			</jstl:if>

			<jstl:choose>
				<jstl:when test="${isBroadcast}">
					<input type="submit" name="broadcast"
						value="<spring:message code="message.broadcast"></spring:message>" />
				</jstl:when>
				<jstl:otherwise>
					<input type="submit" name="save"
						value="<spring:message code="message.save"></spring:message>" />
				</jstl:otherwise>
			</jstl:choose>
			<jstl:if test="${messageObject.id > 0}">
				<input type="submit" name="delete"
					value="<spring:message code="message.delete"></spring:message>" />
			</jstl:if>
			
		</form:form>

	</div>

</jstl:if>

<spring:message code="message.cancel" var="cancel"></spring:message>
<button name="cancel" value="${cancel}" onclick="javascript:relativeRedir('message/actor/list.do?folderId=${messageObject.folder.id}')" >${cancel}</button>


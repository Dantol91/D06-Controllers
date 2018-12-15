<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="complaint/customer/edit.do" modelAttribute="complaint">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	
	<form:label path="ticker">
		<spring:message code="complaint.ticker" />:
	</form:label>
	<form:textarea path="ticker" />
	<form:errors cssClass="error" path="ticker" />
	<br/>
	<br/>
	
	<form:label path="moment">
		<spring:message code="complaint.moment" />:
	</form:label>
	<form:textarea path="moment" />
	<form:errors cssClass="error" path="moment" />
	<br/>
	<br/>
	
	<form:label path="description">
		<spring:message code="complaint.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br/>
	<br/>
	
	<form:label path="attachmentLink">
		<spring:message code="complaint.attachmentLink" />:
	</form:label>
	<form:textarea path="attachmentLink" />
	<form:errors cssClass="error" path="attachmentLink" />
	<br/>
	<br/>
	
	<form:label path="fixUpTask">
		<spring:message code="master.page.fixUpTasks" />:
	</form:label>
	<form:select id="fixUpTasks" path="fixUpTask">
		<form:option value="0" label="----" />
		<form:options items="${fixUpTasks}" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="fixUpTask" />
	<br/>
	<br/>

	<input type="submit" name="save"
		value="<spring:message code="complaint.save" />" />&nbsp;
		
	<jstl:if test="${complaint.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="complaint.delete" />"
		onclick="return confirm('<spring:message code="complaint.confirm.delete" />')" />&nbsp; 
	</jstl:if>

	
	<input type="button" name="cancel"
		value="<spring:message code="contact.cancel" />"
		onclick="javascript: relativeRedir('complaint/customer/list.do');" />
	<br />

</form:form>

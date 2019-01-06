<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="tutorial/customer/edit.do" modelAttribute="tutorial">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	
	<form:label path="title">
		<spring:message code="tutorial.title" />:
	</form:label>
	<form:textarea path="title" />
	<form:errors cssClass="error" path="title" />
	<br/>
	<br/>
	
	<form:label path="moment">
		<spring:message code="tutorial.moment" />:
	</form:label>
	<form:textarea path="moment" />
	<form:errors cssClass="error" path="moment" />
	<br/>
	<br/>
	
	<form:label path="summary">
		<spring:message code="tutorial.summary" />:
	</form:label>
	<form:textarea path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br/>
	<br/>
	
	<form:label path="picture">
		<spring:message code="tutorial.picture" />:
	</form:label>
	<form:textarea path="picture" />
	<form:errors cssClass="error" path="picture" />
	<br/>
	<br/>
	
	<form:label path="sponsorship">
		<spring:message code="master.page.sponsorships" />:
	</form:label>
	<form:select id="sponsorships" path="sponsorship">
		<form:option value="0" label="----" />
		<form:options items="${sponsorships}" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="sponsorship" />
	<br/>
	<br/>
	
		<form:label path="section">
		<spring:message code="master.page.sections" />:
	</form:label>
	<form:select id="sections" path="section">
		<form:option value="0" label="----" />
		<form:options items="${sections}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="section" />
	<br/>
	<br/>
	
	

	<input type="submit" name="save"
		value="<spring:message code="tutorial.save" />" />&nbsp;
		
	<jstl:if test="${tutorial.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="tutorial.delete" />"
		onclick="return confirm('<spring:message code="tutorial.confirm.delete" />')" />&nbsp; 
	</jstl:if>

	
	<input type="button" name="cancel"
		value="<spring:message code="contact.cancel" />"
		onclick="javascript: relativeRedir('tutorial/customer/list.do');" />
	<br />

</form:form>

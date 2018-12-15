<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="configuration/admin/edit.do" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="bannerURL">
		<spring:message code="configuration.bannerURL" />:
	</form:label>
	<form:input path="bannerURL" />
	<form:errors cssClass="error" path="bannerURL" />
	<br/>
	<br/>
	
	<form:label path="welcomeMessageEN">
		<spring:message code="configuration.welcomeMessageEN" />:
	</form:label>
	<form:textarea path="welcomeMessageEN" />
	<form:errors cssClass="error" path="welcomeMessageEN" />
	<br/>
	<br/>
	
	<form:label path="welcomeMessageES">
		<spring:message code="configuration.welcomeMessageES" />:
	</form:label>
	<form:textarea path="welcomeMessageES" />
	<form:errors cssClass="error" path="welcomeMessageES" />
	<br/>
	<br/>
	
	<form:label path="spamWords">
		<spring:message code="configuration.spamWords" />:
	</form:label>
	<form:textarea path="spamWords" />
	<form:errors cssClass="error" path="spamWords" />
	<br/>
	<br/>
	
	<form:label path="VATTax">
		<spring:message code="configuration.VATTax" />:
	</form:label>
	<form:input path="VATTax" />
	<form:errors cssClass="error" path="VATTax" />
	<br/>
	<br/>
	
	<form:label path="countryCode">
		<spring:message code="configuration.countryCode" />:
	</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br/>
	<br/>
	
	<form:label path="finderCached">
		<spring:message code="configuration.finderCached" />:
	</form:label>
	<form:input path="finderCached" />
	<form:errors cssClass="error" path="finderCached" />
	<br/>
	<br/>
	
	<form:label path="finderReturn">
		<spring:message code="configuration.finderReturn" />:
	</form:label>
	<form:input path="finderReturn" />
	<form:errors cssClass="error" path="finderReturn" />
	<br/>
	<br/>
	

	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />&nbsp;


	<input type="button" name="cancel"
		value="<spring:message code="configuration.cancel" />"
		onclick="javascript: relativeRedir('configuration/admin/display.do');" />
	<br />

</form:form>

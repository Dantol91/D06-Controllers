<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<div id="configuration" >
	
	<ul style="list-style-type:disc">

	
	<li>
	<b><spring:message code="configuration.bannerURL"></spring:message>:</b>
	<jstl:out value="${configuration.getBannerURL()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.welcomeMessageEN"></spring:message>:</b>
	<jstl:out value="${configuration.getWelcomeMessageEN()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.welcomeMessageES"></spring:message>:</b>
	<jstl:out value="${configuration.getWelcomeMessageES()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.spamWords"></spring:message>:</b>
	<jstl:out value="${configuration.getSpamWords()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.VATTax"></spring:message>:</b>
	<jstl:out value="${configuration.getVATTax()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.countryCode"></spring:message>:</b>
	<jstl:out value="${configuration.getCountryCode()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.finderCached"></spring:message>:</b>
	<jstl:out value="${configuration.getFinderCached()}"/>
	</li>
	
	<li>
	<b><spring:message code="configuration.finderReturn"></spring:message>:</b>
	<jstl:out value="${configuration.getFinderReturn()}"/>
	</li>

	</ul>
	
</div>

	<input type="button" name="edit"
		value="<spring:message code="configuration.edit" />"
		onclick="javascript: relativeRedir('configuration/admin/edit.do?configurationId=${configuration.id}')" />
	
	
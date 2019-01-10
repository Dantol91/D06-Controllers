<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<%

String languageValue;
try{
Cookie[] cookies = request.getCookies();
Cookie languageCookie = null;
for(Cookie c : cookies) {
	if(c.getName().equals("language")) {
		languageCookie = c;
	}
}

languageValue = languageCookie.getValue();}
catch(NullPointerException e){
	languageValue = "en";	
}

%>



<security:authorize access="hasRole('HANDYWORKER')">
<fieldset><legend><spring:message code="fixuptask.search" /></legend>

	<form action="fixupTask/handyworker/search.do" method="get">
		<label for="keyword"><spring:message code="search.keyword" /></label>
		<input type="text" id="keyword" name="keyword" /><br>
		<label for="categoryId"><spring:message code="search.category" /></label>
		<select id="categoryId" name="categoryId">
			<option value="0">-----</option>
			<jstl:forEach items="${categories}" var="category">
				<% if(languageValue.equals("en")) { %>
					<option value="${category.id}"><jstl:out value="${category.nameEnglish}" /></option>
				<% } else if(languageValue.equals("es")) { %>
					<option value="${category.id}"><jstl:out value="${category.nameSpanish}" /></option>
				<% } %>
			</jstl:forEach>
		</select><br>
		<label for="warrantyId"><spring:message code="search.warranty" /></label>
		<select id="warrantyId" name="warrantyId">
			<option value="0">-----</option>
			<jstl:forEach items="${warranties}" var="warranty">
				<option value="${warranty.id}"><jstl:out value="${warranty.title}" /></option>
			</jstl:forEach>
		</select><br>
		<label for="minPrice"><spring:message code="search.minprice" /></label>
		<input type="number" id="minPrice" name="minPrice" min="0" /><br>
		<label for="maxPrice"><spring:message code="search.maxprice" /></label>
		<input type="number" id="maxPrice" name="maxPrice" min="0" /><br>
		<label for="minDate"><spring:message code="search.mindate" /></label>
		<input type="date" id="minDate" name="minDate" placeholder="YYYY-MM-DD" /><br>
		<label for="maxDate"><spring:message code="search.maxdate" /></label>
		<input type="date" id="maxDate" name="maxDate" placeholder="YYYY-MM-DD" /><br>
		
		<input type="submit" value="<spring:message code="search.search" />" />
		
	</form>
	
	
	
</fieldset>
</security:authorize>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="fixupTasks" requestURI="${requestURI}" id="row">


	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="fixupTask/customer/edit.do?fixupTaskId=${row.id}"> <spring:message
					code="fixupTask.edit" />
			</a>
			
		</display:column>
	</security:authorize>
	
		<display:column>
			<a href="fixupTask/endorsable/display.do?fixupTaskId=${row.id}"> <spring:message
					code="customer.display" />
			</a>
		</display:column>
		
		<display:column>
			<a href="application/customer/list.do?fixupTaskId=${row.id}"> <spring:message
					code="application.list" />
			</a>
		</display:column>


	<display:column property="ticker" titleKey="fixupTask.ticker"
		sortable="false" />

	<display:column property="description" titleKey="fixupTask.description"
		sortable="false" />

	<display:column property="address" titleKey="fixupTask.address"
		sortable="false" />

	<display:column property="maximumPrice"
		titleKey="fixupTask.maximumPrice" sortable="false" />

	<display:column  titleKey="fixupTask.category" sortable="false" >
		<% if(languageValue.equals("en")) { %>
			<jstl:out value="${row.category.nameEnglish}" />
		<% } else if(languageValue.equals("es")) { %>
			<jstl:out value="${row.category.nameSpanish}" />
		<% } %>
	</display:column>

	<display:column property="warranty.title" titleKey="fixupTask.warranty"
		sortable="false" />

	<display:column property="start" titleKey="fixupTask.start"
		sortable="false" format="{0,date,dd/MM/yyyy HH:mm}" />

	<display:column property="end" titleKey="fixupTask.end" sortable="false"
		format="{0,date,dd/MM/yyyy HH:mm}" />


</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<div>
	
	
		<a href="fixupTask/customer/create.do"> <spring:message
				code="fixupTask.create" />
		</a>
	</div>
</security:authorize>
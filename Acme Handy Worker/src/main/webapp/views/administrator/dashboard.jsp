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

<security:authorize access="hasRole('ADMIN')">

	<fieldset>
		<legend>
			<spring:message code="dashboard.FixStats"> </spring:message>
		</legend>
		
		<spring:message code="dashboard.FixStats.min" /> : <jstl:out value="${minFixupTaskPerUser}" /><br>
		<spring:message code="dashboard.FixStats.max" /> : <jstl:out value="${maxFixupTaskPerUser}" /><br>
		<spring:message code="dashboard.FixStats.avg" /> : <jstl:out value="${avgFixupTaskPerUser}" /><br>
		<spring:message code="dashboard.FixStats.stdev" /> : <jstl:out value="${stdFixupTaskPerUser}" />
		
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="dashboard.ratioApplications"> </spring:message>
		</legend>
		
		<spring:message code="dashboard.appsPendingRatio" /> : <jstl:out value="${ratioPendingApplications}" /><br>
		<spring:message code="dashboard.appsAcceptedRatio" /> : <jstl:out value="${ratioAcceptedApplications}" /><br>
		<spring:message code="dashboard.appsRejectedRatio" /> : <jstl:out value="${ratioRejectedApplications}" /><br>
		<spring:message code="dashboard.appsRatioElapsed" /> : <jstl:out value="${ratioLateApplications}" />
		
	</fieldset>

<!-- ----------------------------------------------------------------------------------------- -->
	<fieldset>
		<legend>
			<spring:message code="dashboard.fixuptaskMaxStats"></spring:message>
		</legend>
		
		<spring:message code="dashboard.FixStatsMax.min" /> : <jstl:out value="${minMaximumPriceOfFixupTasks}" /><br>
		<spring:message code="dashboard.FixStatsMax.max" /> : <jstl:out value="${maxMaximumPriceOfFixupTasks}" /><br>
		<spring:message code="dashboard.FixStatsMax.avg" /> : <jstl:out value="${avgMaximumPriceOfFixupTasks}" /><br>
		<spring:message code="dashboard.FixStatsMax.stdev" /> : <jstl:out value="${stdMaximumPriceOfFixupTasks}" />
	
	</fieldset>
	
	
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.appsPerfixuptask"></spring:message>
		</legend>
		
		<spring:message code="dashboard.AppsFixStats.min" /> : <jstl:out value="${minApplicationsPerFixupTask}" /><br>
		<spring:message code="dashboard.AppsFixStats.max" /> : <jstl:out value="${maxApplicationsPerFixupTask}" /><br>
		<spring:message code="dashboard.AppsFixStats.avg" /> : <jstl:out value="${avgApplicationsPerFixupTask}" /><br>
		<spring:message code="dashboard.AppsFixStats.stdev" /> : <jstl:out value="${stdApplicationsPerFixupTask}" />
	
	</fieldset>


	<fieldset>
		<legend>
			<spring:message code="dashboard.AppsPrice"></spring:message>
		</legend>
		
		<spring:message code="dashboard.AppsPrice.min" /> : <jstl:out value="${minPriceOfApplications}" /><br>
		<spring:message code="dashboard.AppsPrice.max" /> : <jstl:out value="${maxPriceOfApplications}" /><br>
		<spring:message code="dashboard.AppsPrice.avg" /> : <jstl:out value="${avgPriceOfApplications}" /><br>
		<spring:message code="dashboard.AppsPrice.stdev" /> : <jstl:out value="${stdPriceOfApplications}" />
				
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.customerMost"></spring:message>
		</legend>
		
		<spring:message code="dashboard.customerMostFix" var="customer10Title" />
		<display:table name="customersMoreFixupTasksAverage" id="customerMoreFixupTasks" requestURI="${requestURI}" pagesize="5">
			<display:column title="${customer10Title}">
				<jstl:out value="${customerMoreFixupTasks.name} ${customerMoreFixupTasks.surname}" />
			</display:column>
		</display:table>

	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.handyWorkerMost"></spring:message>
		</legend>

		<spring:message code="dashboard.handyWorkerMostFix" var="handyWorker10Title" />
		<display:table name="handyWorkersMoreFixupTasksAverage" id="handyWorkerMoreFixupTasks" requestURI="${requestURI}" pagesize="5">
			<display:column title="${handyWorker10Title}">
				<jstl:out value="${handyWorkerMoreFixupTasks.name} ${handyWorkerMoreFixupTasks.surname}" />
			</display:column>
		</display:table>
	
	</fieldset>

<!-- ------------------------------ Nivel B y A------------------------------------------------------- -->


	<fieldset>
		<legend>
			<spring:message code="dashboard.fixuptaskComplainStats"></spring:message>
		</legend>
			
		<spring:message code="dashboard.fixuptaskComplainStats.min" /> : <jstl:out value="${minComplaintsPerFixupTask}" /><br>
		<spring:message code="dashboard.fixuptaskComplainStats.max" /> : <jstl:out value="${maxComplaintsPerFixupTask}" /><br>
		<spring:message code="dashboard.fixuptaskComplainStats.avg" /> : <jstl:out value="${avgComplaintsPerFixupTask}" /><br>
		<spring:message code="dashboard.fixuptaskComplainStats.stdev" /> : <jstl:out value="${stdComplaintsPerFixupTask}" />
	
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.notePerRefereeStats"></spring:message>
		</legend>
		
		<spring:message code="dashboard.notePerRefereeStats.min" /> : <jstl:out value="${minNotesPerReport}" /><br>
		<spring:message code="dashboard.notePerRefereeStats.max" /> : <jstl:out value="${maxNotesPerReport}" /><br>
		<spring:message code="dashboard.notePerRefereeStats.avg" /> : <jstl:out value="${avgNotesPerReport}" /><br>
		<spring:message code="dashboard.notePerRefereeStats.stdev" /> : <jstl:out value="${stdNotesPerReport}" />
	
	</fieldset>
	
	<!-- ------------------------------------------------------------------ -->
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.ratioTaskComplaint"> </spring:message>
		</legend>
		
		<spring:message code="dashboard.fixRatioTaskComplaint" /> : <jstl:out value="${ratioFixupTasksWithComplaints}" />
		
	</fieldset>
 
	<fieldset>
			<display:table name="top3CustomersWithMoreComplaints" id="topCustomer" pagesize="5" requestURI="${requestURI}" class="displaytag">
				<spring:message code="dashboard.topCustomersComplaint" var="dash28" />
				<display:column title="${dash28}"> 
					<jstl:out value="${topCustomer.name} ${topCustomer.surname}"/> 
				</display:column>	
			</display:table>
	</fieldset>
	
	<fieldset>
			<display:table name="top3HandyWorkerWithMoreComplaints" id="topHandyWorker" pagesize="5" requestURI="${requestURI}" class="displaytag">
				<spring:message code="dashboard.topHandyWorkerComplaint" var="dash29" />
				<display:column title="${dash29}"> 
					<jstl:out value="${topHandyWorker.name} ${topHandyWorker.surname}"/> 
				</display:column>	
			</display:table>
	</fieldset>

</security:authorize>


<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${events}" id="event" class="displaytag" pagesize="5" requestURI="${requestURI}">
	
	<jstl:set var="available" value="${false}"/>
		<jstl:if test="${event.seats > event.chorbies.size()}">
			<jstl:set var="available" value="${true}"/>
	</jstl:if>
	
	<jstl:set var="style" value="color:grey;text-shadow: 0.1em 0.1em 0.2em darkgrey"/>
	<jstl:if test="${event.moment.time > hoy}">
		<jstl:set var="style" value="font-weight:bold;text-shadow: 0.1em 0.1em 0.2em darkgrey"/>
		<jstl:if test="${(event.moment.time > mes) or (!available)}">
			<jstl:set var="style" value="none"/>
		</jstl:if>
	</jstl:if>
	
	<acme:column code="event.title" property="title" sortable="true" style="${style}"/>
	<acme:column code="event.moment" property="moment" format="{0,date,dd-MM-yyyy HH:mm}" sortable="true" style="${style}"/>
	<acme:column code="event.description" property="description" sortable="false" style="${style}"/>
	<acme:column code="event.availableSeats" property="availableSeats" sortable="true" style="${style}"/>
	
	<spring:message code="event.display" var="displayHeader" />
	<display:column title="${displayHeader}" >
		<a href="event/display.do?eventId=${event.id}"><spring:message code="event.display"/></a>
	</display:column>
	
	<security:authorize access="isAuthenticated()">
		
		<security:authorize access="hasRole('MANAGER')">
			<spring:message code="event.edit" var="editHeader" />
			<display:column title="${editHeader}" >
				<jstl:if test="${event.manager.id == principal.id}">
					<a href="event/managerUser/edit.do?eventId=${event.id}"><spring:message code="event.edit"/></a>
				</jstl:if>
			</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('CHORBI')">
			<spring:message code="event.register" var="registerHeader" />
			<display:column title="${registerHeader}">
				
				<jstl:if test="${event.moment.time > hoy}">
					<jstl:set var="isRegisted" value="${false}"/>
					<jstl:forEach var="chorbi" items="${event.chorbies}">
						<jstl:if test="${chorbi.id == principal.id}">
							<jstl:set var="isRegisted" value="${true}"/>
						</jstl:if>
					</jstl:forEach>
					<jstl:if test="${isRegisted}">
						<a href="event/chorbi/unregister.do?eventId=${event.id}"><spring:message code="event.unregister"/></a>
					</jstl:if>
					<jstl:if test="${!isRegisted}">
						<jstl:if test="${available}">
							<a href="event/chorbi/register.do?eventId=${event.id}"><spring:message code="event.register"/></a>
						</jstl:if>
						<jstl:if test="${!available}">
							<spring:message code="event.soldOut" />
						</jstl:if>
					</jstl:if>
				</jstl:if>
				
			</display:column>
		</security:authorize>
		
	</security:authorize>
	
</display:table>

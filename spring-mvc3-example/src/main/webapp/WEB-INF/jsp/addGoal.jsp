<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring MVC - Add Goal</title>
	<link rel="stylesheet" type="text/css" href="/spring-webmvc-helloworld/statics/styles.css"></link>
</head>
<body>
	<p><spring:message code="addGoal.title"/></p>
	<form:form commandName="goal">
		<table>
			<tr>
				<td><spring:message code="addGoal.description.label" /></td>
				<td><form:input path="description"/></td>
				<td><form:errors element="span" cssClass="error" path="description"/></td>
			</tr>
			<tr>
				<td><spring:message code="addGoal.minutes.label" /></td>
				<td><form:input path="minutes"/></td>
				<td><form:errors element="span" cssClass="error" path="minutes"/></td>
			</tr>
			<tr>
				<td>
					<button type="submit">Save</button>
				</td>
			</tr>
		</table>
	</form:form>
	<hr>
	<p>
		<spring:message code="language.change" />:
		<a href="?language=ptbr">Português</a> | <a href="?language=en">English</a>
	</p>
</body>
</html>
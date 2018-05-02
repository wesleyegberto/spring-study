<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring MVC - Add Minutes</title>
	<link rel="stylesheet" type="text/css" href="/spring-mvc4-example/statics/styles.css"></link>
</head>
<body>
	<p><spring:message code="addMinutes.title"/></p>
	<form:form commandName="trackedMinute">
		<table>
			<tr>
				<td><spring:message code="addMinutes.minutes.label" /></td>
				<td><form:input path="minutes"/></td>
			</tr>
			<tr>
				<td>
					<button type="submit">Save</button>
				</td>
			</tr>
		</table>
	</form:form>
	<br>
	<p>
		<spring:message code="addMinutes.todaysGoal.label"/>: ${goal.description} - ${goal.minutes}min
	</p>
	<hr>
	<p>
		<spring:message code="language.change" />:
		<a href="?lang=pt_BR">Português</a> | <a href="?lang=en">English</a>
	</p>
</body>
</html>
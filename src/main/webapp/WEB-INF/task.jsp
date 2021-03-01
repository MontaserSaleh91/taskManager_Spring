<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Task</title>
</head>
<body>
	<div class="container">
		<div class="myheader">
			<p class="cleardisplayblock">Create a new task</p>
		</div>
		<section>
			<div class="columns">
				<div class="column">

					<form:form method="POST" action="/tasks/new" modelAttribute="task">
						<table>
							<tr>
								<td><form:label path="taskName">Task:</form:label></td>
								<td><form:input path="taskName" class="input" /></td>
								<%-- <td><form:errors path="taskName" /></td> --%>
							</tr>
								
							<tr>
								<td><form:label path="assignee">Assignee:</form:label></td>
								<td><form:select path="assignee"
										class="select is-one-third">
										<form:option value=""></form:option>

										<c:forEach items="${users}" var="user">
											<c:if test="${user.name != currentUser.name}">
												<form:option value="${user}">
													<c:out value="${user.name}" />
												</form:option>
											</c:if>
										</c:forEach>
									</form:select></td>
								<%-- <td><form:errors path="assignee" /></td> --%>
							</tr>
							<tr>
								<td><form:label path="priority">Priority:</form:label></td>
								<td><form:select path="priority"
										class="select is-one-third">
										<form:option value=""></form:option>
										<form:option value="1">Low</form:option>
										<form:option value="2">Medium</form:option>
										<form:option value="3">High</form:option>
									</form:select></td>
								<%-- <td><form:errors path="priority" /></td> --%>
							</tr>
						</table>
						<div class="buttons has-addons is-centered">
							<input type="submit" value="Create" class="button" />
						</div>
					</form:form>
					<p>
						<form:errors path="task.*"></form:errors>
					</p>
					<p><c:out value="${errors}"/></p>

				</div>
				<div class="column"></div>
			</div>

		</section>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Belt Reviewer</title>
</head>
<body>
	<div class="container">
		<div class="notification">
		</div>

		<!-- left and right -->
		<section>
			<div class="columns">

				<!-- registration -->
				<div class="column">
					<h3 class="title">Register</h3>
					<p>
						<form:errors path="user.*" />
					</p>

					<!-- left column -->


					<form:form method="POST" action="/registration"
						modelAttribute="user">
						<table class="mytable">

							<tr>
								<td class="is-one-thrid"><form:label path="name">Name:</form:label></td>
								<td class="is-two-thrids"><form:input path="name"
										class="input" /></td>
							</tr>
							<tr>
								<td class="is-one-thrid"><form:label path="email">Email:</form:label></td>
								<td class="is-two-thrids"><form:input path="email"
										class="input" /></td>
							</tr>
							<tr>
								<td class="is-one-thrid"><form:label path="password">password:</form:label></td>
								<td class="is-two-thrids"><form:input path="password"
										type="password" class="input" /></td>
							</tr>
							<tr>
								<td class="is-one-thrid"><form:label
										path="passwordConfirmation">password Confirmation:</form:label></td>
								<td class="is-two-thrids"><form:input
										path="passwordConfirmation" type="password" class="input" /></td>
							</tr>
							<!-- <tr>
								<td><input type="submit" value="Register!" class="button" /></td>
							</tr> -->

						</table>
						<div class="buttons has-addons is-right">
							<input type="submit" value="Register!" class="button" />
						</div>
					</form:form>
				</div>



				<!-- login -->
				<div class="column">
					<div class="gap"></div>
					<h3 class="title">Login</h3>
					<p>
						<c:out value="${error}" />
					</p>

					<form method="post" action="/login">
						<table class="mytable">
							<tr>
								<td class="is-one-thrid"><label for="email">Email</label></td>
								<td class="is-two-thrids"><input type="text" id="email"
									name="email" class="input" /></td>
							</tr>
							<tr>
								<td class="is-one-thrid"><label for="password">Password</label></td>
								<td class="is-two-thrids"><input type="password"
									id="password" name="password" class="input" /></td>
							</tr>

						</table>
						<div class="buttons has-addons is-right">
							<input type="submit" value="Login" class="button" />
						</div>
					</form>
				</div>
			</div>
		</section>
	</div>
</body>
</html>
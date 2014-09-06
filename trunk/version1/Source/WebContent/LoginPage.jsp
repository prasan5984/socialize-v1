<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Socialize-Login</title>
<link rel="stylesheet" type="text/css" href="SocStyles.css">
</head>
<body>

	<div class="logo" align="center">
		<img alt="" src="images/Logo.jpg" width="600" height="120"
			align="middle" class="inditem">
	</div>

	<div class="mbody">
		<%
			String errorFlag = (String)request.getAttribute( "Failure" );
			if ( errorFlag != null && errorFlag.equals( "true" ) )
			{
		%>
		<div style="text-align: center;">
			<b>User Authentication Failed</b>
		</div>
		<%
			}
		%>
		<br>
		<form action="LoginValidation" method="post">
			<div class="commonelement leftelement">
				<span style="padding-right: 10px;">User Id</span>
			</div>
			<div class="commonelement">
				<input type="text" name="uname" required>
			</div>
			<br>
			<div class="commonelement leftelement">
				<span style="padding-right: 10px;">Password</span>
			</div>
			<div class="commonelement">
				<input type="password" name="password" required>
			</div>

			<div class="rightonlyelement">
				<input type="submit" value="Login" class="button">
			</div>
		</form>

		<form action="RegistrationPage.jsp" method="post">
			<div class="rightonlyelement">
				<input type="submit" value="New User?" class="button">
			</div>
		</form>

	</div>
</body>
</html>
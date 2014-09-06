<%@page import="src.constants.SocializeConstants"%>
<%@page import="src.helpers.SocializeHelper"%>
<%@page import="src.objects.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Socialize - Registration</title>
<link rel="stylesheet" type="text/css" href="SocStyles.css">
</head>
<body>

	<div class="logo" align="center">
		<img alt="" src="images/Logo.jpg" width="600" height="120"
			align="middle" class="inditem">
	</div>
	<%
		boolean editFl = false;
		Profile userProfile = null;
		String path = "images/Default.jpg";
		String editFlag = null;

		if (request.getParameter("edit") != null)
			editFl = true;

		if (editFl) {
	%>
	<div class="pageLinks" align="center">
		<span class="pageLinkItem"><a href="LogOut">Log Out</a> </span> <span
			class="pageLinkItem"><a href="WelcomePage.jsp">Home</a> </span>
	</div>
	<%
		userProfile = SocializeHelper.getProfile(request.getCookies());
			if (userProfile != null) {
				path = userProfile.getProfilePicPath();
			}
			editFlag = "Y";
		} else {
			editFlag = "N";
	%>
	<div class="pageLinks" align="center">
		<span class="pageLinkItem"><a href="LoginPage.jsp">Log In</a> </span>
	</div>
	<%
		}
	%>
	<div class="mbody">
		<div class="regLeft">
			<img id="Img1" src="<%=path%>" width="200px" height="200px"> <br>
			<a onclick="showPicture()" href="#">Change</a>
			<%
				if (editFl) {
			%><br> <a onclick="removePicture()" href="#">Remove</a>
			<%
				}
			%>
		</div>
		<div class="regRight">
			<var hidden>UserExist value="false" hidden=true</var>
			<var hidden>PasswordMismatch value="false" hidden=true</var>
			<%
				String userExists = (String) request.getAttribute("UserExist");
				String passwordMismatch = (String) request
						.getAttribute("PasswordMismatch");
				if ((userExists != null) && userExists.equals("true")) {
			%>
			<div style="text-align: center;">
				<b>User name not available</b>
			</div>

			<%
				} else if ((passwordMismatch != null)
						&& passwordMismatch.equals("true")) {
			%>
			<div style="text-align: center;">
				<b>Passwords entered do not match!</b>
			</div>
			<%
				}
			%>
			<form method="post" enctype="multipart/form-data"
				action="UserRegistration">

				<div class="leftelement">User Id</div>
				<div class="commonelement">
					<input type="text" name="userid" <%if (editFl) {%>
						value="<%=userProfile.getUserId()%>" readonly <%} else {%>
						required <%}%>>
				</div>

				<div class="leftelement">Password</div>
				<div class="commonelement">
					<input type="password" name="password" required <%if (editFl) {%>
						value="<%=userProfile.getPassword()%>" <%}%>>
				</div>
				<div class="leftelement">Confirm Password</div>
				<div class="commonelement">
					<input type="password" name="repassword" required <%if (editFl) {%>
						value="<%=userProfile.getPassword()%>" <%}%>>
				</div>
				<div class="leftelement">Name</div>
				<div class="commonelement">
					<input type="text" name="name" required <%if (editFl) {%>
						value="<%=userProfile.getUsername()%>" <%}%>>
				</div>
				<div class="leftelement">Interests</div>
				<div class="commonelement">
					<input type="text" name="description" <%if (editFl) {%>
						value="<%=userProfile.getInterest()%>" <%}%>>
				</div>
				<input id="profilePic" type="file" name="profilePic"
					value="Profile Picture" size="40" hidden="true"
					onchange="previewPicture()">
				<div class="rightonlyelement">
					<input type="submit" value="Save" class="button">
				</div>

				<input type="text" value="<%=editFlag%>" hidden="true"
					name="editFlag"> <input type="text" hidden="true"
					id="input1" name="picFlag" value="N"> <input type="text"
					hidden="true" id="input2" name="defaultFlag" value="N">
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function removePicture() {
		var el = document.getElementById("input2");
		el.setAttribute("value", "Y");
		
		var img = document.getElementById("Img1");
		img.setAttribute("src", "<%=SocializeConstants.DEF_PIC_PATH%>");
	}

	function showPicture() {
		var el = document.getElementById("profilePic");
		el.click();
	}

	function previewPicture() {
		var input = document.getElementById("profilePic");
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.readAsDataURL(input.files[0]);
			reader.onload = function(e) {
				var imgEl = document.getElementById("Img1");
				imgEl.setAttribute("src", e.target.result);
				var input1 = document.getElementById("input1");
				input1.setAttribute("value", "Y");
			};

		}
	}
</script>
</html>
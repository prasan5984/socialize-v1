<%@page import="src.helpers.SocializeHelper"%>
<%@page import="src.objects.*"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>
<link rel="stylesheet" type="text/css" href="SocStyles.css">
</head>

<body>

	<div class="logo" align="center">
		<img alt="" src="images/Logo.jpg" width="600" height="120"
			align="middle">
	</div>

	<%
		Profile vProfile = SocializeHelper.getProfile( request.getCookies() );
		if ( vProfile == null )
			response.sendRedirect( "LogOut" );

		String userId = request.getParameter( "hostUserId" );

		Profile userProfile;
		char hostFl = 'n';
		if ( userId != "" && userId != null )
			userProfile = SocializeHelper.getProfileDetails( userId );
		else
			userProfile = vProfile;
		if ( vProfile.equals( userProfile ) )
		{
			hostFl = 'y';
		}

		if ( hostFl == 'y' )
		{
	%>
	<div class="pageLinks" align="center">
		<span class="pageLinkItem"><a href="LogOut">Log Out</a> </span>
	</div>
	<%
		}
		else
		{
	%>

	<div class="pageLinks" align="center">
		<span class="pageLinkItem"><a href="LoginPage.jsp">Log Out</a>
		</span> <span class="pageLinkItem"><a href="WelcomePage.jsp">Home</a>
		</span>
	</div>
	<%
		}
	%>
	<div class="mbody">
		<%
			if ( userProfile != null )
			{
				String path = userProfile.getProfilePicPath();
		%>
		<div class="WelcomePageLeftPane">
			<img alt="Profile Picture" src="<%=path%>" width="200px"
				height="200px" onerror="images/Default.jpg">
		</div>

		<div class="listbody">
			<div class="Welcomeheading">
				<h1>
					<%
						if ( hostFl == 'y' )
							{
					%>Welcome <%
						}
							else
							{
					%>This is
					<%
						}
					%><%=userProfile.getUsername()%></h1>
			</div>

			<div class="listitem">
				<a href="FriendsList.jsp">Friends</a>
			</div>
			<%
				if ( hostFl == 'y' )
					{
			%>
			<div class="listitem">
				<a
					href="RegistrationPage.jsp?edit=<%=hostFl%>&name=<%=userProfile.getUserId()%>">Update
					Profile</a>
			</div>
			<%
				}
			%>
			<div class="listitem">
				<a href="PostListPage.jsp?userId=<%=userProfile.getUserId()%>">Posts</a>
			</div>

			<%
				if ( hostFl == 'n' )
					{
			%>
			<div style="text-align: left; margin-left: 15%; margin-top: 4%">
				<span style="font-weight: bold;">Interested In: </span><span><%=userProfile.getInterest()%></span>
			</div>
			<%
				}
			%>
		</div>
		<%
			}
		%>
	</div>
</body>
</html>
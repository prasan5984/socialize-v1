<%@page import="src.helpers.SocializeHelper"%>
<%@page import="src.objects.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Socialize - Friends List</title>
<link rel="stylesheet" type="text/css" href="SocStyles.css">
</head>

<body>
	<div class="logo" align="center">
		<img alt="" src="images/Logo.jpg" width="600" height="120"
			align="middle" class="inditem">
	</div>
	<div class="pageLinks" align="center">
		<span class="pageLinkItem"><a href="LogOut">Log Out</a> </span> <span
			class="pageLinkItem"><a href="WelcomePage.jsp">Home</a> </span>
	</div>
	<div class="mbody">
		<div class="lpane">
			<%
				String frndName = request.getParameter( "errFriend" );

				if ( frndName != "" && frndName != null )
				{
			%>
			<div style="text-align: center;">
				<b>User <%=frndName%> not found</b>
			</div>
			<%
				}
			%>

			<form action="FriendAction/Invite" method="post">
				<input type="text" name="friend" placeholder="User Id"> <input
					type="submit" value="Invite" class="button">
			</form>
		</div>
		<div class="rpane">
			<%
				Profile p = SocializeHelper.getProfile( request.getCookies() );
				for ( Profile friend : p.getInvitationList() )
				{
			%>
			<div class="frnditem">
				<div class="imgitem">
					<div class="inditem">
						<img src="<%=friend.getProfilePicPath()%>" width="100px"
							height="100px">
					</div>
				</div>
				<div class="frnddtl">
					<div class="frndnamesec">
						<span class="inditem"> <a href="WelcomePage.jsp?hostUserId=<%=friend.getUserId()%>"> <%
 	out.print( friend.getUsername() );
 %>
						</a> </span>
					</div>
					<div class="frndbuttonsec">
						<div class="inditem">
							<form action="FriendAction/Accept" method="post">
								<input type="hidden" name="friend"
									value="<%=friend.getUserId()%>"><input type="submit"
									value="Accept" class="button">
							</form>
						</div>
					</div>
					<div class="frndbuttonsec">
						<div class="inditem">
							<form action="FriendAction/Reject" method="post">
								<input type="hidden" name="friend"
									value="<%=friend.getUserId()%>"><input type="submit"
									value="Reject" class="button">
							</form>
						</div>
					</div>
				</div>
			</div>
			<%
				}
				for ( Profile friend : p.getFriendList() )
				{
			%>
			<div class="frnditem">
				<div class="imgitem">
					<div class="inditem">

						<img src="<%=friend.getProfilePicPath()%>" width="100px"
							height="100px">
					</div>
				</div>
				<div class="frnddtl">
					<div class="frndnamesec">
						<span class="inditem"> <a href="WelcomePage.jsp?hostUserId=<%=friend.getUserId()%>"> <%
 	out.print( friend.getUsername() );
 %>
						</a> </span>
					</div>
				</div>
			</div>
			<%
				}
				for ( Profile friend : p.getResponseList() )
				{
			%>
			<div class="frnditem">
				<div class="imgitem">
					<div class="inditem">
						<img src="<%=friend.getProfilePicPath()%>" width="100px"
							height="100px">
					</div>
				</div>
				<div class="frnddtl">
					<div class="frndnamesec">
						<span class="inditem"> <%=friend.getUsername()%> </span>
					</div>
					<div class="frndbuttonsec">
						<div class="inditem">Pending</div>
					</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>
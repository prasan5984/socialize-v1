<%@page import="src.helpers.SocializeHelper"%>
<%@page import="src.objects.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Socialize Posts</title>
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
		<div class="PostPageLeftPane">
			<label class="PostPageLinkText" for="NewPost">New Post</label>
		</div>
		<div class="PostPageRightPane">
			<%
		Profile p = SocializeHelper.getProfile( request.getCookies() );
		Profile hostProfile = SocializeHelper.getProfileDetails( request.getParameter( "userId" ) );
		List< Conversation > cList = hostProfile.getConversation();
		for ( Conversation c : cList )
		{
			if ( !c.getDeleteFl() )
			{
				List< Response > rLst = c.getResponseList();
	%>
			<div class="PostPagePostItem">
				<%
			for ( Response r : rLst )
					{
						boolean ownerfl = r.getAuthor().equals( p ) ? true : false;
		%>
				<div class="PostPageResponseItem">
					<div class="PostPageImageItem">
						<div class="inditem">
							<img alt="" src="<%=r.getAuthor().getProfilePicPath()%>"
								width="75px" height="75px">
						</div>
					</div>
					<div class="PostPageResponseDetail">

						<div class="PostPageResponseHeader"><%=r.getAuthor().getUsername()%>
							<%=r.getResponseTime()%></div>
						<%
 	if ( ownerfl )
 				{
 		
 %>
						<div id="div<%=r.getId()%>" class="PostPageResponseTextArea"
							contenteditable="true">

							<%=r.getText()%>
						</div>
						<div class="PostPageResponseLeftAction">
							<form id="frm<%=r.getId()%>" action="PostAction/editResponse"
								method="post">
								<input type="hidden" name="pUserId"
									value=<%=hostProfile.getUserId()%>> <input
									type="hidden" name="cId" value=<%=c.getId()%>> <input
									type="hidden" name="rId" value=<%=r.getId()%>> <input
									type="hidden" name="txt" id="txt<%=r.getId()%>"> <input
									type="submit" value="Save"
									class="PostPageLinkText PostPageResponseButton"
									onclick="setText(<%=r.getId()%>)">
							</form>
						</div>
						<div class="PostPageResponseRightAction">
							<form action="PostAction/deleteResponse" method="post">
								<input type="hidden" name="pUserId"
									value=<%=hostProfile.getUserId()%>> <input
									type="hidden" name="cId" value=<%=c.getId()%>> <input
									type="hidden" name="rId" value=<%=r.getId()%>> <input
									type="submit" value="Delete"
									class="PostPageLinkText PostPageResponseButton">
							</form>
						</div>

						<%
 	}
 				else
 				{
 %>
						<div id="div<%=r.getId()%>" class="PostPageResponseTextArea"><%=r.getText()%></div>
						<%
 	}
 %>
					</div>
				</div>

				<%
			}
		%>
			</div>
			<div class="PostPageNewResponseDetail" style="float: right;">
				<form action="PostAction/newResponse" method="post">
					<input type="hidden" name="pUserId"
						value=<%=hostProfile.getUserId()%>> <input type="hidden"
						name="cId" value=<%=c.getId()%>>
					<textarea class="PostPageNewResponseTextArea" placeholder="Reply"
						name="txt"></textarea>
					<div class="PostPageOneAction">
						<input type="submit" value="Save"
							class="PostPageLinkText PostPageResponseButton">
					</div>
				</form>
			</div>
			<%
		}
		}
	%>
			<div class="PostPageResponseItem">
				<form action="PostAction/newConversation" method="post">
					<input type="hidden" name="pUserId"
						value=<%=hostProfile.getUserId()%>>
					<textarea id="NewPost" class="PostPageNewResponseTextArea"
						placeholder="New Post" name="txt"></textarea>
					<div class="PostPageOneAction">
						<input type="submit" value="Save"
							class="PostPageLinkText PostPageResponseButton">
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function setText(id) {
		var txtElement = document.getElementById("txt" + id);
		txtElement.value = document.getElementById("div"+id).innerHTML;
		document.getElementById("frm" + id).submit();
	}
	</script>
</body>
</html>
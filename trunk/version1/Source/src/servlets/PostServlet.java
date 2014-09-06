package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.helpers.SocializeHelper;
import src.objects.Conversation;
import src.objects.Profile;
import src.objects.Response;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends AbstractSocializeServlet
{
	private static final long	serialVersionUID	= 1L;

	protected void localPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String action = request.getPathInfo();
		Profile editor = SocializeHelper.getProfile( request.getCookies() );
		Profile dProfile = SocializeHelper.getProfileDetails( request.getParameter( "pUserId" ) );

		if ( action.contains( "newConversation" ) )
		{
			String txt = request.getParameter( "txt" );
			Conversation c = new Conversation( new Date(), editor, txt );
			synchronized (dProfile)
			{
				dProfile.includeConversation( c );
			}
			modifiedSocObs.add( dProfile );
			modifiedSocObs.add( c );

		}
		else
		{

			int cId = Integer.parseInt( request.getParameter( "cId" ) );
			Conversation c = getConversation( cId, dProfile, editor );
			modifiedSocObs.add( c );

			Response r = null;

			synchronized (c)
			{

				if ( action.contains( "newResponse" ) )
				{
					String txt = request.getParameter( "txt" );
					r = c.createResponse( editor, new Date(), txt );
				}
				else
				{
					int rId = Integer.parseInt( request.getParameter( "rId" ) );

					if ( action.contains( "deleteResponse" ) )
					{
						c.deleteResponse( rId );
					}
					else if ( action.contains( "editResponse" ) )
					{
						String txt = request.getParameter( "txt" );
						r = c.updateReponse( rId, txt );
					}

				}
			}

			if ( r != null )
				modifiedSocObs.add( r );
		}

		dProfile = dProfile == null ? editor : dProfile;
		response.sendRedirect( "../PostListPage.jsp?userId=" + dProfile.getUserId() );
	}

	private Conversation getConversation( int cId, Profile hostProfile, Profile editorProfile )
	{

		Conversation c;

		c = (Conversation)SocializeHelper.getSocializeObject( editorProfile.getConversation(), cId );

		if ( c == null )
		{
			c = (Conversation)SocializeHelper.getSocializeObject( hostProfile.getConversation(), cId );
			editorProfile.includeConversation( c );
			modifiedSocObs.add( c );
		}
		return c;
	}

}

package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.helpers.SocializeHelper;
import src.objects.Profile;

/**
 * Servlet implementation class FriendAction
 */

public class FriendAction extends AbstractSocializeServlet
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	protected void localPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		Profile p = SocializeHelper.getProfile( request.getCookies() );

		String action = request.getPathInfo();

		String friendName = request.getParameter( "friend" );
		Profile friendProfile = SocializeHelper.getProfileDetails( friendName );

		String queryString = "";

		if ( friendProfile == null )
		{
			queryString = "?errFriend=" + friendName;
		}

		else
		{
			if ( friendProfile != p )
			{
				modifiedSocObs.add( p );

				if ( action.equals( "/Invite" ) )
				{
					modifiedSocObs.add( friendProfile );

					if ( friendProfile.getResponseList().contains( p ) )
					{
						synchronized (friendProfile)
						{
							friendProfile.addFriend( p );
							friendProfile.removeResponse( p );
						}

						synchronized (p)
						{
							p.addFriend( friendProfile );
							p.removeInvite( friendProfile );
						}
					}
					else
					{
						synchronized (friendProfile)
						{
							friendProfile.addInviation( p );
						}

						synchronized (p)
						{
							p.addResponse( friendProfile );
						}
					}
				}
				else if ( action.equals( "/Accept" ) )
				{
					modifiedSocObs.add( friendProfile );

					synchronized (friendProfile)
					{
						friendProfile.addFriend( p );
						friendProfile.removeResponse( p );
					}

					synchronized (p)
					{
						p.addFriend( friendProfile );
						p.removeInvite( friendProfile );
					}
				}
				else if ( action.equals( "/Reject" ) )
				{
					synchronized (p)
					{
						p.removeInvite( friendProfile );
					}
				}
			}
		}

		response.sendRedirect( "../FriendsList.jsp" + queryString );

	}

}

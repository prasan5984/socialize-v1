package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.datastructure.MultiPartDetails;
import src.helpers.SocializeHelper;
import src.objects.Profile;

/**
 * Servlet implementation class ValidateUser
 */

public class UserRegistration extends AbstractSocializeServlet
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	protected void localPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		MultiPartDetails m = SocializeHelper.processMultiPartRequest( request );

		boolean editFl = m.stringList.get( 5 ).equals( "Y" ) ? true : false;
		boolean picFl = m.stringList.get( 6 ).equals( "Y" ) ? true : false;
		boolean defPicFl = m.stringList.get( 7 ).equals( "Y" ) ? true : false;

		boolean errorFl = false;

		if ( !m.stringList.get( 1 ).equals( m.stringList.get( 2 ) ) )
		{
			request.setAttribute( "PasswordMismatch", "true" );
			errorFl = true;
		}
		else if ( !editFl )
		{
			Profile p = new Profile( m.stringList.get( 0 ), m.stringList.get( 1 ), m.stringList.get( 4 ), m.stringList.get( 3 ) );

			if ( SocializeHelper.addNewProfile( p ) )
			{
				modifiedSocObs.add( p );
				if ( picFl )
					addProfilePicture( m, p );
				else
				{
					addDefaultPicture( request, p );
				}
			}
			else
			{
				request.setAttribute( "UserExist", "true" );
				errorFl = true;
			}

		}
		else if ( editFl )
		{
			Profile p = SocializeHelper.getProfile( request.getCookies() );

			if ( p != null )
			{
				modifiedSocObs.add( p );
				p.setInterest( m.stringList.get( 4 ) );
				p.setPassword( m.stringList.get( 1 ) );
				p.setUserName( m.stringList.get( 3 ) );

				if ( picFl )
					addProfilePicture( m, p );

				if ( defPicFl )
					addDefaultPicture( request, p );
			}

		}

		if ( errorFl )
		{
			String queryString = "";
			if ( editFl )
				queryString = "?edit=Y";

			request.getRequestDispatcher( "RegistrationPage.jsp" + queryString ).forward( request, response );
		}
		else
		{
			if ( !editFl )
				response.sendRedirect( "LoginPage.jsp" );
			else
				response.sendRedirect( "WelcomePage.jsp" );
		}

	}

	private void addProfilePicture( MultiPartDetails m, Profile p )
	{
		try
		{
			synchronized (p)
			{
				if ( m.fileItemList.size() > 0 )
					m.fileItemList.get( 0 ).write( p.createNewFile( true ) );
			}
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addDefaultPicture( HttpServletRequest r, Profile p )
	{
		synchronized (p)
		{
			p.setDefaultPic();

		}

	}

}

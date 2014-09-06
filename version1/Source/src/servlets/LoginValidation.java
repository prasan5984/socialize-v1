package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import src.helpers.SocializeHelper;
import src.objects.Profile;

/**
 * Servlet implementation class InitialServlet
 */

public class LoginValidation extends HttpServlet
{
	private static final long			serialVersionUID	= 1L;
	public static List< HttpSession >	sessionTrackerList	= new ArrayList< HttpSession >();

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String user = request.getParameter( "uname" );
		String password = request.getParameter( "password" );

		RequestDispatcher dispatcher;

		if ( isAuthorised( user, password ) )
		{
			Cookie cookie = new Cookie( "uname", user );
			response.addCookie( cookie );
			String encodedUrl = response.encodeRedirectURL( "WelcomePage.jsp" );
			response.sendRedirect( encodedUrl );
		}
		else
		{
			dispatcher = request.getRequestDispatcher( "LoginPage.jsp" );
			request.setAttribute( "Failure", "true" );
			dispatcher.forward( request, response );
		}
	}

	private boolean isAuthorised( String user, String password )
	{
		Profile p = SocializeHelper.getUserProfile( user );

		if ( p != null )
			if ( p.getPassword().equals( password ) )
				return true;

		return false;
	}

}

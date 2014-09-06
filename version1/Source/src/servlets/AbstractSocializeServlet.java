package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.datastructure.AbstractSocializeObject;
import src.helpers.SocializeHelper;

public abstract class AbstractSocializeServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 2025122000692168700L;
	List< AbstractSocializeObject >	modifiedSocObs		= new ArrayList< AbstractSocializeObject >();

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		localPost( request, response );
		serialize();
	}

	protected void localPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		localGet( request, response );
		serialize();
	}

	protected void localGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
	}

	protected void serialize()
	{
		SocializeHelper.serialize( modifiedSocObs );
	}

}

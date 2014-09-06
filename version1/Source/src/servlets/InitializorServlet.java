package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import src.helpers.SocializeHelper;
import src.storage.DeserializeManager;

@WebServlet("/InitializorServlet")
public class InitializorServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	public void init() throws ServletException
	{
		SocializeHelper.initialize();
		DeserializeManager manager = new DeserializeManager();
		try
		{
			manager.start();
		}
		catch ( Exception e )
		{
			throw new ServletException( e );
		}
	}

}

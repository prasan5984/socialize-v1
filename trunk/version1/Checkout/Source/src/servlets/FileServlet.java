package servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.helpers.SocializeHelper;

/**
 * Servlet implementation class FileServlet
 */

public class FileServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		String filePath = request.getPathInfo();
		File f = new File( SocializeHelper.rootLocation, filePath );

		
		
		BufferedInputStream ipStream = new BufferedInputStream( new FileInputStream( f ) );
		BufferedOutputStream opStream = new BufferedOutputStream( response.getOutputStream() );

		byte[] buffer = new byte[SocializeHelper.getBufferSize()];
		int i;

		while ( ( i = ipStream.read( buffer ) ) > 0 )
			opStream.write( buffer, 0, i );

		ipStream.close();
		opStream.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

}

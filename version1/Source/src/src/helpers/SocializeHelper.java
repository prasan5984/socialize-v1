package src.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import src.constants.SocializeConstants;
import src.datastructure.AbstractSocializeObject;
import src.datastructure.MultiPartDetails;
import src.objects.Conversation;
import src.objects.Profile;
import src.objects.Response;
import src.storage.Serializer;

public class SocializeHelper
{
	static Map< String, Profile >			userProfileMap	= new ConcurrentHashMap< String, Profile >();
	public static File						rootLocation;
	private static AtomicInteger			cId				= new AtomicInteger();
	private static AtomicInteger			rId				= new AtomicInteger();
	private static AtomicInteger			pId				= new AtomicInteger();
	private static ExecutorService			serialExecutor;
	private static Map< String, String >	configMap		= new HashMap< String, String >();

	public static void initialize()
	{
		try
		{
			readConfig();
		}
		catch ( IOException e )
		{
			System.out.print( "Unable to load Configuration. Default Values will be used." );
			configMap = SocializeConstants.DEF_PROP_MAP;
		}

		String thread = getConfig( SocializeConstants.PROP_SER_THREAD );
		int th = Integer.parseInt( thread );
		serialExecutor = Executors.newFixedThreadPool( th );
		rootLocation = getRootLocation();
	}

	public static void shutdownSerializor()
	{
		serialExecutor.shutdown();
	}

	private static void readConfig() throws IOException
	{
		InputStream ipStm = SocializeHelper.class.getClassLoader().getResourceAsStream( SocializeConstants.PROP_FILE_NAME );

		StringWriter writer = new StringWriter();
		IOUtils.copy( ipStm, writer );
		String op = writer.toString();

		Pattern p = Pattern.compile( "\"(.*)\"" );

		for ( String line : op.split( System.getProperty( "line.separator" ) ) )
		{
			String[] arr = line.split( "-" );

			Matcher m = p.matcher( arr[ 1 ] );
			if ( m.matches() )
				configMap.put( arr[ 0 ], m.group( 1 ) );
		}
	}

	public static String getConfig( String propKey )
	{
		if ( configMap.containsKey( propKey ) )
		{
			return configMap.get( propKey );
		}
		else
		{
			return SocializeConstants.DEF_PROP_MAP.get( propKey );
		}

	}

	public static Profile getProfileDetails( String user )
	{
		return getUserProfileMap().get( user );
	}

	public static Map< String, Profile > getUserProfileMap()
	{
		return userProfileMap;
	}

	public static String getUserName( HttpServletRequest request )
	{
		for ( Cookie c : request.getCookies() )
			if ( c.getName().equals( "uname" ) )
				return c.getValue();

		return null;
	}

	public static Profile getUserProfile( String uname )
	{
		return userProfileMap.get( uname );
	}

	public static boolean addNewProfile( Profile p )
	{
		String userId = p.getUserId();

		synchronized (userId)
		{
			if ( !userProfileMap.containsKey( userId ) )
			{
				userProfileMap.put( userId, p );
				return true;
			}
			else
				return false;
		}
	}

	public static MultiPartDetails processMultiPartRequest( HttpServletRequest request )
	{
		MultiPartDetails mPartDetail = new MultiPartDetails();
		try
		{
			List< FileItem > items = new ServletFileUpload( new DiskFileItemFactory() ).parseRequest( request );

			for ( FileItem item : items )
			{
				if ( item.isFormField() )
					mPartDetail.stringList.add( item.getString() );

				if ( !item.isFormField() )
					mPartDetail.fileItemList.add( item );
			}
		}
		catch ( FileUploadException e )
		{
			e.printStackTrace();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return mPartDetail;
	}

	public static int generateId( int type )
	{
		if ( type == SocializeConstants.CONVERSATION )
			return cId.incrementAndGet();
		else if ( type == SocializeConstants.RESPONSE )
			return rId.incrementAndGet();
		else if ( type == SocializeConstants.PROFILE )
			return pId.incrementAndGet();

		return 0;
	}

	public static Profile getProfile( Cookie[] cookies )
	{
		Cookie c;
		if ( ( c = getProfileCookie( cookies ) ) != null )
			return userProfileMap.get( c.getValue() );
		return null;
	}

	public static Cookie getProfileCookie( Cookie[] cookies )
	{
		for ( Cookie c : cookies )
			if ( c.getName().equals( "uname" ) )
				return c;
		return null;
	}

	private static File getRootLocation()
	{
		String path = getConfig( SocializeConstants.PROP_STORAGE_DIR );
		return new File( path );
	}

	public static String getPathSeperator()
	{
		// TODO Auto-generated method stub
		return "\\";
	}

	public static int getBufferSize()
	{
		// TODO Auto-generated method stub
		return 1024;
	}

	public static String getRelativePath( String path )
	{
		return path.replace( getRootLocation().getAbsolutePath(), "File/" );
	}

	public static String getLogo()
	{
		return SocializeConstants.GET_LOGO_PATH.replace( getRootLocation().getAbsolutePath(), "File/" );
	}

	public static AbstractSocializeObject getSocializeObject( Collection< ? extends AbstractSocializeObject > c, int id )
	{
		Iterator< ? extends AbstractSocializeObject > i = c.iterator();

		while ( i.hasNext() )
		{
			AbstractSocializeObject obj = i.next();
			if ( id == obj.getId() )
				return obj;
		}

		return null;
	}

	public static File getObjectDirectory( int objectType )
	{
		File rootDir = getRootLocation();
		File objDir = null;

		switch (objectType)
		{
		case SocializeConstants.PROFILE:
			objDir = new File( rootDir, "//Profile" );
			break;

		case SocializeConstants.CONVERSATION:
			objDir = new File( rootDir, "//Conversation" );
			break;

		case SocializeConstants.RESPONSE:
			objDir = new File( rootDir, "//Response" );
			break;

		default:
			objDir = rootDir;
		}

		objDir.mkdirs();
		return objDir;

	}

	public static int getDeserializorThreadCount()
	{
		String threadCount = getConfig( SocializeConstants.PROP_DESER_THREAD );
		return Integer.parseInt( threadCount );
	}

	public static synchronized void serialize( int objType, AbstractSocializeObject abstractSocializeObject )
	{
		serialExecutor.submit( new Serializer( objType, abstractSocializeObject ) );
	}

	public static void serialize( List< AbstractSocializeObject > modifiedSocObs )
	{
		for ( AbstractSocializeObject obj : modifiedSocObs )
		{
			if ( obj instanceof Profile )
				serialize( SocializeConstants.PROFILE, obj );
			else if ( obj instanceof Conversation )
				serialize( SocializeConstants.CONVERSATION, obj );
			else if ( obj instanceof Response )
				serialize( SocializeConstants.RESPONSE, obj );
		}

	}

	public static File getObjectDirectory( AbstractSocializeObject obj )
	{
		File rootDir = getRootLocation();

		if ( obj instanceof Profile )
			return getObjectDirectory( SocializeConstants.PROFILE );

		if ( obj instanceof Conversation )
			return getObjectDirectory( SocializeConstants.CONVERSATION );

		if ( obj instanceof Response )
			return getObjectDirectory( SocializeConstants.RESPONSE );

		return rootDir;

	}

}

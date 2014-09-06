package src.constants;

import java.util.LinkedHashMap;

public final class SocializeConstants
{

	public static final String							ERR01				= "User Already Exist";
	public static final String							ERR02				= "Passwords entered do not match. Please re-enter";
	public static final int								CONVERSATION		= 1;
	public static final int								RESPONSE			= 2;
	public static final int								PROFILE				= 3;

	public static String								PROP_STORAGE_DIR	= "STORAGE_DIRECTORY";
	public static String								PROP_SER_THREAD		= "SERIALIZATION_THREAD_COUNT";
	public static String								PROP_DESER_THREAD	= "DESERIALIZATION_THREAD_COUNT";

	public static String								PROP_FILE_NAME		= "SocProps.txt";

	public static int									BUFFER_SIZE			= 1024;

	public static final LinkedHashMap< String, String >	DEF_PROP_MAP		= new LinkedHashMap< String, String >()
																			{
																				private static final long	serialVersionUID	= 1L;
																				{
																					put( PROP_STORAGE_DIR, "/" );
																					put( PROP_SER_THREAD, "2" );
																					put( PROP_DESER_THREAD, "3" );
																				}
																			};
	public static final String							GET_LOGO_PATH		= null;
	public static final String							DEF_PIC_PATH		= "images/Default.jpg";

}

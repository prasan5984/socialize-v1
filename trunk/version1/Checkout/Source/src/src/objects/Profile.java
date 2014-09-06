package src.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import src.constants.SocializeConstants;
import src.datastructure.AbstractSocializeObject;
import src.helpers.SocializeHelper;

public class Profile extends AbstractSocializeObject
{
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	private String						userName;
	private String						interest;
	private File						profilePic;
	private String						password;
	private ArrayList< Conversation >	conversationList	= new ArrayList< Conversation >();
	private String						userId;
	private File						userDir;
	private List< File >				userFiles			= new ArrayList< File >();
	private List< Profile >				friendList			= new ArrayList< Profile >();
	private List< Profile >				invitationList		= new ArrayList< Profile >();
	private List< Profile >				responseList		= new ArrayList< Profile >();
	private boolean						defaultPicFl		= false;

	public Profile( String userId, String password, String interest, String username )
	{
		super();
		this.userName = username;
		this.interest = interest;
		this.password = password;
		this.setUserId( userId );
		createUserDirectory();
	}

	public void setDefaultPic()
	{
		this.defaultPicFl = true;
		if ( profilePic != null )
			profilePic.delete();
		profilePic = null;
	}

	public void setUserName( String uname )
	{
		this.userName = uname;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	private void setUserId( String userId2 )
	{
		this.userId = userId2;

	}

	private boolean createUserDirectory()
	{
		userDir = new File( SocializeHelper.rootLocation + SocializeHelper.getPathSeperator() + userId );
		return userDir.mkdir();

	}

	public void addFriend( Profile p )
	{
		if ( !friendList.contains( p ) )
			friendList.add( p );

	}

	public void addInviation( Profile p )
	{
		if ( !invitationList.contains( p ) )
			invitationList.add( p );

	}

	public void addResponse( Profile p )
	{
		if ( !responseList.contains( p ) )
			responseList.add( p );

	}

	public List< Profile > getFriendList()
	{
		return friendList;
	}

	public List< Profile > getInvitationList()
	{
		return invitationList;
	}

	public List< Profile > getResponseList()
	{
		return responseList;
	}

	public String getUsername()
	{
		return userName;
	}

	public void setInterest( String interest )
	{
		this.interest = interest;
	}

	public String getInterest()
	{
		return interest;
	}

	public String getProfilePicPath()
	{
		if ( !defaultPicFl )
			return SocializeHelper.getRelativePath( profilePic.getAbsolutePath() );
		else
			return SocializeConstants.DEF_PIC_PATH;
	}

	public void includeConversation( Conversation n )
	{
		for ( Conversation c : this.conversationList )
		{
			if ( c.getId() == n.getId() )
				return;
		}
		conversationList.add( n );
	}

	public void updateConversation( Profile responseAuthor, int id, String txt )
	{
		for ( Conversation c : this.conversationList )
		{
			if ( c.getId() == id )
			{
				c.createResponse( responseAuthor, new Date(), txt );
				return;
			}
		}
		new Conversation( new Date(), responseAuthor, txt );

	}

	public List< Conversation > getConversation()
	{
		return conversationList;
	}

	public String getPassword()
	{
		return password;
	}

	public String getUserId()
	{
		return userId;
	}

	public File createNewFile( boolean profilePicFl )
	{
		File f = new File( userDir, getFileSequence() + "" );
		userFiles.add( f );
		if ( profilePicFl )
		{
			this.setProfilePic( f );
			this.defaultPicFl = false;
		}
		return f;

	}

	public void setProfilePic( File f )
	{
		this.profilePic = f;

	}

	private int getFileSequence()
	{
		return userFiles.size();
	}

	public void removeInvite( Profile friendProfile )
	{
		invitationList.remove( friendProfile );
		serialize();
	}

	public void removeResponse( Profile friendProfile )
	{
		responseList.remove( friendProfile );
		serialize();
	}

}

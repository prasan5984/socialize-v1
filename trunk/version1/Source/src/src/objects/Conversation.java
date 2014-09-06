package src.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import src.datastructure.AbstractSocializeObject;
import src.helpers.SocializeHelper;

public class Conversation extends AbstractSocializeObject
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Date				lastUpdateTime;
	private Profile				initiatorProfile;
	private List< Response >	responseList		= new ArrayList< Response >();
	private boolean				deleteFl			= false;
	private boolean				readFl				= false;

	public Conversation( Date dt, Profile p, String msg )
	{
		super();
		this.initiatorProfile = p;
		createResponse( p, dt, msg );

	}

	public Response createResponse( Profile p, Date d, String msg )
	{
		p.includeConversation( this );
		this.lastUpdateTime = d;
		Response r = new Response( d, p, msg );
		this.responseList.add( r );
		return r;
	}

	public List< Response > getResponseList()
	{
		return this.responseList;
	}

	public Date getTime()
	{
		return lastUpdateTime;
	}

	public Profile getFromProfile()
	{
		return initiatorProfile;
	}

	public boolean getDeleteFl()
	{
		return this.deleteFl;
	}

	public boolean isReadFl()
	{
		return readFl;
	}

	public Response deleteResponse( int id )
	{
		Response r = getResponse( id );
		this.responseList.remove( r );
		if ( this.responseList.size() == 0 )
		{
			this.deleteFl = true;
		}

		return r;

	}

	public Response updateReponse( int id, String txt )
	{
		Response r = getResponse( id );
		r.setText( txt );
		return r;

	}

	private Response getResponse( int id )
	{
		return (Response)SocializeHelper.getSocializeObject( this.responseList, id );
	}

}

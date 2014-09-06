package src.objects;

import java.util.Date;

import src.datastructure.AbstractSocializeObject;

public class Response extends AbstractSocializeObject
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Profile				author;
	private String				text;
	private Date				responseTime;
	private Boolean				deleteFl			= false;

	Response( Date rTime, Profile p, String txt )
	{
		super();
		setResponseTime( rTime );
		setAuthor( p );
		setText( txt );

	}

	public void setResponseTime( Date rTime )
	{
		this.responseTime = rTime;
	}

	public void setAuthor( Profile p )
	{
		this.author = p;
	}

	public void setText( String txt )
	{
		this.text = txt;
	}

	public Profile getAuthor()
	{
		return author;
	}

	public String getText()
	{
		return text;
	}

	public Date getResponseTime()
	{
		return responseTime;
	}

	public boolean getDeleteFl()
	{
		return this.deleteFl;
	}

}

package src.datastructure;

import java.io.Serializable;

import src.constants.SocializeConstants;
import src.helpers.SocializeHelper;
import src.objects.Conversation;
import src.objects.Profile;
import src.objects.Response;

public abstract class AbstractSocializeObject implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	private static int			objType;
	protected int				id;

	public AbstractSocializeObject()
	{
		if ( this instanceof Profile )
		{
			objType = SocializeConstants.PROFILE;
		}
		else if ( this instanceof Conversation )
		{
			objType = SocializeConstants.CONVERSATION;
		}
		else if ( this instanceof Response )
		{
			objType = SocializeConstants.RESPONSE;
		}

		id = SocializeHelper.generateId( objType );

	}

	public final int getId()
	{
		return this.id;
	}

	protected void serialize()
	{
		SocializeHelper.serialize( objType, this );

	}

}

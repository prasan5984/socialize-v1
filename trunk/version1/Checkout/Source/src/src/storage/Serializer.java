package src.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

import src.datastructure.AbstractSocializeObject;
import src.helpers.SocializeHelper;

public class Serializer implements Callable< Object >
{
	private AbstractSocializeObject	obj;

	public Serializer( int objType, AbstractSocializeObject obj )
	{
		this.obj = obj;
	}

	@Override
	public Object call() throws Exception
	{
		File dir = SocializeHelper.getObjectDirectory( obj );
		File opFile = new File( dir, obj.getId() + ".obj" );

		FileOutputStream filStm = null;
		ObjectOutputStream objStm = null;

		try
		{
			filStm = new FileOutputStream( opFile );
			objStm = new ObjectOutputStream( filStm );
			objStm.writeObject( obj );
		}
		finally
		{
			if ( filStm != null )
				filStm.close();

			if ( objStm != null )
				objStm.close();
		}

		return true;
	}

}

package src.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.Callable;

import src.datastructure.AbstractSocializeObject;
import src.helpers.SocializeHelper;
import src.objects.Profile;

public class Deserializer implements Callable< AbstractSocializeObject >
{
	private File	ipFile;

	public Deserializer( File ipFile )
	{
		this.ipFile = ipFile;
	}

	@Override
	public AbstractSocializeObject call() throws Exception
	{
		FileInputStream filStm = new FileInputStream( ipFile );
		ObjectInputStream objStm = new ObjectInputStream( filStm );
		AbstractSocializeObject obj = (AbstractSocializeObject)objStm.readObject();

		if ( obj instanceof Profile )
		{
			SocializeHelper.addNewProfile( (Profile)obj );
		}

		return obj;
	}

}

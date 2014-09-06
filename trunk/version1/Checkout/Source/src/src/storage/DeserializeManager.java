package src.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import src.constants.SocializeConstants;
import src.datastructure.AbstractSocializeObject;
import src.helpers.SocializeHelper;

public class DeserializeManager
{
	private ExecutorService						threadPool;
	List< Future< AbstractSocializeObject > >	resultObjLst	= new ArrayList< Future< AbstractSocializeObject >>();

	public void start() throws InterruptedException, ExecutionException
	{
		threadPool = Executors.newFixedThreadPool( SocializeHelper.getDeserializorThreadCount() );

		int[] objType = { SocializeConstants.RESPONSE, SocializeConstants.CONVERSATION, SocializeConstants.PROFILE };

		for ( int i : objType )
		{
			// load responses
			File f = SocializeHelper.getObjectDirectory( i );
			scheduleDeserialize( f.listFiles() );
		}

		processResult();

		System.out.print( "Objects Deserialisation successfully completed" );

	}

	private void scheduleDeserialize( File[] files )
	{
		if ( files != null )
		{
			for ( File f : files )
				resultObjLst.add( threadPool.submit( new Deserializer( f ) ) );
		}
	}

	private void processResult() throws InterruptedException, ExecutionException
	{
		for ( Future< AbstractSocializeObject > result : resultObjLst )
			result.get();

	}

}

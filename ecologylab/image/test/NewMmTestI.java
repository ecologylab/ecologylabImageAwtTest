/**
 * 
 */
package ecologylab.image.test;

import java.io.OutputStream;

import ecologylab.semantics.documentparsers.ImageParserAwt;
import ecologylab.semantics.metametadata.test.NewMmTest;
import ecologylab.serialization.SIMPLTranslationException;
import ecologylab.serialization.SimplTypesScope;
import ecologylab.serialization.SimplTypesScope.GRAPH_SWITCH;

/**
 * @author andruid
 *
 */
public class NewMmTestI extends NewMmTest
{
	static
	{
		SimplTypesScope.graphSwitch	= GRAPH_SWITCH.ON;
		ImageParserAwt.init();
	}

	/**
	 * @param appName
	 * @throws SIMPLTranslationException
	 */
	public NewMmTestI(String appName) throws SIMPLTranslationException
	{
		super(appName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param appName
	 * @param outputStream
	 * @throws SIMPLTranslationException
	 */
	public NewMmTestI(String appName, OutputStream outputStream) throws SIMPLTranslationException
	{
		super(appName, outputStream);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param appName
	 * @param outputStream
	 * @param metadataSimplTypesScope
	 * @throws SIMPLTranslationException
	 */
	public NewMmTestI(String appName, OutputStream outputStream,
			SimplTypesScope metadataSimplTypesScope) throws SIMPLTranslationException
	{
		super(null, appName, outputStream, metadataSimplTypesScope);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		NewMmTest mmTest;
		try
		{
			mmTest = new NewMmTestI("NewMmTest");
			mmTest.collect(args);
		}
		catch (SIMPLTranslationException e)
		{
			e.printStackTrace();
		}
		
	}

}

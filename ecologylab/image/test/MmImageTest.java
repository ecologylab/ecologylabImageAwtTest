/**
 * 
 */
package ecologylab.image.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import ecologylab.semantics.documentparsers.ImageParserAwt;
import ecologylab.semantics.documentparsers.ImageParserAwtResult;
import ecologylab.semantics.documentparsers.ParserResult;
import ecologylab.semantics.metadata.builtins.Document;
import ecologylab.semantics.metadata.builtins.DocumentClosure;
import ecologylab.semantics.metadata.builtins.Image;
import ecologylab.semantics.metametadata.test.NewMmTest;
import ecologylab.serialization.TranslationScope;
import ecologylab.serialization.TranslationScope.GRAPH_SWITCH;

/**
 * Test program for using semantics to extract metadata for images, and then downloadParse and display them in a JFrame.
 *
 * @author andruid
 */
public class MmImageTest extends NewMmTest
{
	ArrayList<BufferedImage>	bImageCollection;
	
	public static int IMAGE_WIDTH		= 100;
	public static int IMAGE_HEIGHT	= 100;
	
	public static int SPACING				= 3;
	

	/**
	 * @param outputStream
	 */
	public MmImageTest(String[] args, boolean outputOneAtATime)
	{
		super(System.out);
		
		this.args	= args;
		this.outputOneAtATime	= outputOneAtATime;
		
		ImageParserAwt.init();
		
		jFrame(IMAGE_WIDTH, IMAGE_HEIGHT, 0);	// get the first repaint -> paint happening.
	}
	
	@Override
	protected void postParse(int size)
	{
		bImageCollection	= new ArrayList<BufferedImage>(size);
	}
	
	@Override
	protected synchronized void output(DocumentClosure incomingClosure)
	{
		Document document	= incomingClosure.getDocument();
		
		if (document instanceof Image)
		{
			ParserResult	parserResult	= document.getParserResult();
			if (parserResult instanceof ImageParserAwtResult)
			{
				BufferedImage bufferedImage	= ((ImageParserAwtResult) parserResult).getBufferedImage();
				int index	= bImageCollection.size();
				bImageCollection.add(bufferedImage);
				jFrame(bufferedImage.getWidth(), bufferedImage.getHeight(), index);
			}
		}
		super.output(incomingClosure);
	}	
	
	JFrame jFrame;
	JFrame jFrame(final int width, final int height, int index)
	{
		JFrame result	= this.jFrame;
		if (result == null)
		{
			result	= new JFrame()
			{
				boolean	init	= true;

				@Override
				public void paint(Graphics g)
				{
					super.paint(g);
					synchronized (MmImageTest.this)
					{
						if (init)
						{
							init = false;
							int currentHeight = getHeight();
							insets = getInsets();
	
							setJFrameSize(width, height);
	
							repaint();
							
							afterInitialPaint();
						}
						else
						{
							Graphics2D g2 = (Graphics2D) g;
							int x	= insets.left;
							for (BufferedImage bImage: bImageCollection)
							{
								int y	= insets.top + (getBounds().height - (bImage.getHeight() + insets.top + insets.bottom)) / 2;
								g2.drawImage(bImage, x, y, null);
								x		+= bImage.getWidth() + SPACING;
							}
						}
					}
				}
			};
			this.jFrame	= result;
			result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			result.setBackground(Color.white);
			result.setVisible(true);
		}
		else
		{
			if (index == 0)
				setJFrameSize(width, height);
			else
			{
				incrementJFrameSizeXEnsureY(width+SPACING, height);
			}
		}
		result.repaint();
		return result;
	}
	
	protected void afterInitialPaint()
	{
		collect();
	}
	
	String[] args;
	
	protected void collect()
	{
		collect(args);
	}
	Insets					insets;

	private synchronized void setJFrameSize(int width, int height)
	{
		width	+= insets.left + insets.right;
		height+= insets.top + insets.bottom;
		System.out.println("setJFrameSize(" + width+","+height);
		jFrame.setSize(width, height);
	}

	private synchronized void incrementJFrameSizeXEnsureY(final int deltaWidth, final int requiredHeight)
	{
		Rectangle bounds	= jFrame.getBounds();
		int width = bounds.width + deltaWidth;
		int borderHeight	= insets.top + insets.bottom;
		int deltaHeight	= requiredHeight + borderHeight - bounds.height;
		int height = deltaHeight > 0 ? deltaHeight + bounds.height : bounds.height;
		System.out.println("incrementJFrameSize(" + width+","+height);
		jFrame.setSize(width, height);
	}

	public static void main(String[] args)
	{
		TranslationScope.graphSwitch	= GRAPH_SWITCH.ON;
		MmImageTest mmTest			= new MmImageTest(args, true);
//		mmTest.collect(args);
	}
}

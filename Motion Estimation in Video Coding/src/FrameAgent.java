package human_action;
import java.util.*;
import java.lang.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File.*;
import java.io.*;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class FrameAgent extends Agent
{	
	long startTime;
	protected void setup()
	{	
		startTime = System.currentTimeMillis();
		addBehaviour(new FrameAgentBehaviour());
	}
	
	private class FrameAgentBehaviour extends Behaviour
	{
		public void action()
		{
			int red,green,blue,argb,alpha,row=0,col=0,grey_difference=0,grey_count=0,width,height,n_agents=0,agent_count=0;
			BufferedImage image1;
			BufferedImage image2;
			double var=0;
			int[][][] frame1;
			int[][][] frame2;
			int[][][] result;
			try
			{
				image1=ImageIO.read(new File("D:/Java/classes/human_action/man11.jpg"));
				image2=ImageIO.read(new File("D:/Java/classes/human_action/man12.jpg"));
				result=new int[image1.getHeight()][image1.getWidth()][1];
				frame1=new int[image1.getHeight()][image1.getWidth()][1];
				frame2=new int[image2.getHeight()][image2.getWidth()][1];
				height=image1.getHeight();
				width=image1.getWidth();
		
				for (row=0; row<height; row++) 
				{
				for (col=0; col<width; col++) 
				{
				result[row][col][0] = image1.getRGB(col, row);
				alpha = (result[row][col][0] >> 24) & 0x000000FF;
				red = (result[row][col][0] >> 16) & 0x000000FF;
				green = (result[row][col][0] >> 8) & 0x000000FF;
				blue = (result[row][col][0]) & 0x000000FF;
				var=(0.2126*red+0.7152*green+0.0722*blue);
				frame1[row][col][0]=(int)var;
				result[row][col][0] = image2.getRGB(col, row);
				alpha = (result[row][col][0] >> 24) & 0x000000FF;
				red = (result[row][col][0] >> 16) & 0x000000FF;
				green = (result[row][col][0] >> 8) & 0x000000FF;
				blue = (result[row][col][0]) & 0x000000FF;
				var=(0.2126*red+0.7152*green+0.0722*blue);
				frame2[row][col][0]=(int)var;
				grey_difference=frame2[row][col][0]-frame1[row][col][0];
				if(grey_difference>5)
					grey_count++;
				}
				}
				System.out.println("grey count:"+grey_count);
		
			if(grey_count<=1000)
					n_agents=4;
				else if(grey_count>1000 && grey_count<=2000 )
					n_agents=8;
				else if(grey_count>2000 )
					n_agents=16;

		//		n_agents=4;
				RandomAccessFile raf = new RandomAccessFile("D:/Java/classes/human_action/file.txt", "rw");
				String s=Integer.toString(n_agents);
				raf.writeBytes(s);
				s=Integer.toString(agent_count);
				raf.writeBytes("\n"+agent_count);
				raf.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			
	
		}//action
		
		public boolean done() 
		{
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Computational time:"+(totalTime/1000)+" sec");
			return true;
		} 
	
	}//inner class
}//outer class
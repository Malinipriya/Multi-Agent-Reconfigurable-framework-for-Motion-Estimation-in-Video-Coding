
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
public class CheckAgent10
{
	public static void main(String[] args) throws IOException
	{
		try
			{
				RandomAccessFile raf = new RandomAccessFile("D:/Java/classes/human_action/file9.txt", "rw");
				String s=raf.readLine();
				int n_agents = Integer.parseInt(s);
				//System.out.println("Pixel Agent 3 n_agents: "+n_agents);
				s=raf.readLine();
				int agent_count = Integer.parseInt(s);
				//System.out.println("Pixel Agent 3 agent_count: "+agent_count);
				raf.close();
				RandomAccessFile raf1 = new RandomAccessFile("D:/Java/classes/human_action/file10.txt", "rw");
				s=Integer.toString(n_agents);
				raf1.writeBytes(s);
				if(n_agents!=agent_count)
				{
					agent_count++;
					s=Integer.toString(agent_count);
					raf1.writeBytes("\n"+s);
					raf1.writeBytes("\n"+"1");
					System.out.println("Check Agent 10 running.........");
				}
				else
				{
					s=Integer.toString(agent_count);
					raf1.writeBytes("\n"+s);
					raf1.writeBytes("\n"+"0");
				}
				raf1.close();
			}//try
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
			}
	}
}
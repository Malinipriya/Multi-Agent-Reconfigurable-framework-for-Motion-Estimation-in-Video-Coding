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

public class PixelAgent7 extends Agent
{
		long startTime;
		protected void setup()
		{
			startTime = System.currentTimeMillis();
			addBehaviour(new PixelAgentBehaviour());
		}
		private class PixelAgentBehaviour extends Behaviour
		{	
		BufferedImage image1;
		BufferedImage image2;
		int n_agents=0,iv1=0,iv2=0,running;
		int width,height,sadcounter=0,blockcount=1,blockno=0,loop1=1,runtime=0,dummy_loop1=1;
		int i,currentwidth,currentheight;
		int widthby2,heightby2,widthby4,heightby4,width1,height1;
		double var;
		int[][][] frame;
		int[][][] result;
		int[][] blockx=new int[25][25];
		int[][] blocky=new int[25][25];
		int[][] currentblockx=new int[4][4];
		int[][] currentblocky=new int[4][4];
		int sad=0,ad=0; 
		int[] blocks_sad= new int[10000];
		int[] blocks_match= new int[16];
		int blocks_flag,final_sad;
		int p=0,q=0;
		int[] mvx= new int[16];
		int[] mvy= new int[16];
		PixelAgentBehaviour frame3;
		PixelAgentBehaviour frame1;
		PixelAgentBehaviour frame2;
		public void find_rgb(BufferedImage image)//To find the average rgb value of each pixel in the current block
		{
			int red,green,blue,argb,alpha,row=0,col=0;
			result=new int[image.getHeight()][image.getWidth()][6];
			frame=new int[image.getHeight()][image.getWidth()][7];
			for (row=currentblocky[0][0]; row<currentblocky[0][0]+16; row++) 
			{
			for (col=currentblockx[0][0]; col<currentblockx[0][0]+25; col++) 
			{
				result[row][col][0] = image.getRGB(col, row);
				alpha = (result[row][col][0] >> 24) & 0x000000FF;
				red = (result[row][col][0] >> 16) & 0x000000FF;
				green = (result[row][col][0] >> 8) & 0x000000FF;
				blue = (result[row][col][0]) & 0x000000FF;
				frame[row][col][1]=alpha;
				frame[row][col][2]=red;
				frame[row][col][3]=green;
				frame[row][col][4]=blue;
				var=(0.2126*frame[row][col][2]+0.7152*frame[row][col][3]+0.0722*frame[row][col][4]);
				frame[row][col][5]=(int)var;
			}
			}
		}//end of rgb() function
		
		public void form_blocks(BufferedImage image)//to form blocks for the given frame
		{
		int j=0,x=0,y=0;
		width=image.getWidth();
		height=image.getHeight();
		width1=width;
		height1=height;
		widthby2=width1/2;
		heightby2=height1/2;
		widthby4=width1/4;
		heightby4=height1/4;
		for(i=0;i<5;i++)
		{
			if(i==4)
			{
				blockx[i][j]=x;
				blocky[i][j++]=y+height;
				blockx[i][j]=x+widthby4;
				blocky[i][j++]=y+height;
				blockx[i][j]=x+widthby2;
				blocky[i][j++]=y+height;
				blockx[i][j]=x+widthby4+widthby2;
				blocky[i][j++]=y+height;
				blockx[i][j]=x+width;
				blocky[i][j++]=y+height;
			}
			else if(i==3)
			{
				blockx[i][j]=x;
				blocky[i][j++]=y+heightby4+heightby2;
				blockx[i][j]=x+widthby4;
				blocky[i][j++]=y+heightby4+heightby2;
				blockx[i][j]=x+widthby2;
				blocky[i][j++]=y+heightby4+heightby2;
				blockx[i][j]=x+widthby4+widthby2;
				blocky[i][j++]=y+heightby4+heightby2;
				blockx[i][j]=x+width;
				blocky[i][j++]=y+heightby4+heightby2;
			}
			else if(i==2)
			{
				blockx[i][j]=x;
				blocky[i][j++]=y+heightby2;
				blockx[i][j]=x+widthby4;
				blocky[i][j++]=y+heightby2;
				blockx[i][j]=x+widthby2;
				blocky[i][j++]=y+heightby2;
				blockx[i][j]=x+widthby4+widthby2;
				blocky[i][j++]=y+heightby2;
				blockx[i][j]=x+width;
				blocky[i][j++]=y+heightby2;
			}
			else if(i==1)
			{
				blockx[i][j]=x;
				blocky[i][j++]=y+heightby4;
				blockx[i][j]=x+widthby4;
				blocky[i][j++]=y+heightby4;
				blockx[i][j]=x+widthby2;
				blocky[i][j++]=y+heightby4;
				blockx[i][j]=x+widthby4+widthby2;
				blocky[i][j++]=y+heightby4;
				blockx[i][j]=x+width;
				blocky[i][j++]=y+heightby4;
			}
			else if(i==0)
			{
				blockx[i][j]=x;
				blocky[i][j++]=y;
				blockx[i][j]=x+widthby4;
				blocky[i][j++]=y;
				blockx[i][j]=x+widthby2;
				blocky[i][j++]=y;
				blockx[i][j]=x+widthby4+widthby2;
				blocky[i][j++]=y;
				blockx[i][j]=x+width;
				blocky[i][j++]=y;
			}
			j=0;
		}
		}//end of form_blocks funtion
		
		public void choose_currentblock_frame1(int start_block,int n_agents) 
		{
			int j=0,count=0;
			if(start_block==1)
			{
				if(n_agents==4)
				{
					iv1=0;
					iv2=0;
				}
				else if(n_agents==8)
				{
					iv1=3;
					iv2=0;
				}
				else if(n_agents==16)
				{
					iv1=1;
					iv2=2;
				}
			}
			for(i=0;i<2;i++)
			{
				for(j=0;j<2;j++)
				{
					if(count==0)
					{
						currentblockx[i][j]=blockx[iv1][iv2];
						currentblocky[i][j]=blocky[iv1][iv2++];
					}
					else if(count==1)
					{
						currentblockx[i][j]=blockx[iv1][iv2];
						currentblocky[i][j]=blocky[iv1++][iv2--];
					}
					else if(count==2)
					{
						currentblockx[i][j]=blockx[iv1][iv2];
						currentblocky[i][j]=blocky[iv1][iv2++];
					}
					else if(count==3)
					{
						currentblockx[i][j]=blockx[iv1][iv2];
						currentblocky[i][j]=blocky[iv1--][iv2];
					}
					if(count<3)
					count++;
					else 
					break;
				}
			}
		if(iv2%4==0)
		{
			iv1++;
			iv2=0;
		}
		}//end of choose_currentblock_frame1
		
		public void choose_currentblock_frame2(int p,int q)
		{
			int j=0,count=0;
			for(i=0;i<2;i++)
			{
				for(j=0;j<2;j++)
				{
					if(count==0)
					{
						currentblockx[i][j]=p;
						currentblocky[i][j]=q;
					}
					else if(count==1)
					{
						currentblockx[i][j]=p+25;
						currentblocky[i][j]=q;
					}
					else if(count==2)
					{
						currentblockx[i][j]=p;
						currentblocky[i][j]=q+16;
					}
					else if(count==3)
					{
						currentblockx[i][j]=p+25;
						currentblocky[i][j]=q+16;
					}
					if(count<3)
					count++;
					else 
					break;
				}
			}
		}//end of choose_currentblock_frame2
		
		public void full_search(PixelAgentBehaviour frame1,PixelAgentBehaviour frame2,int blockcount)
		//to find the Minimum SAD value for each pixel in the current block
		{
		int j=0,k=0,z=0,a=0,x=0,y=0;
		int count=0;
		sad=0;
		
		for (x=frame2.currentblocky[0][0], z=frame1.currentblocky[0][0]; x<frame2.currentblocky[0][0]+16 ; x++ ,z++) 
		{
			for (y=frame2.currentblockx[0][0], a=frame1.currentblockx[0][0]; y<frame2.currentblockx[0][0]+25 ; y++ ,a++) 
			{
				ad=0;
				ad=Math.abs(frame2.frame[x][y][5]-frame1.frame[z][a][5]);
			    //System.out.println("current rgb:"+frame2.frame[x][y][5]+"\tPrevious rgb:"+frame1.frame[z][a][5]+"\tad:"+ad);
				sad+=ad;
			}//inner for
		}//outer for
	 
		blocks_sad[sadcounter]=sad;
		//System.out.println("Blockno:"+(sadcounter+1)+" sad:"+blocks_sad[sadcounter]);
		
		if(blocks_flag==0)
		{
			blocks_match[blockno]=blocks_sad[sadcounter];
			blocks_flag=1;
			final_sad=1;
		}
		sadcounter++;
		}//full_search
		
		public void load_agentmodule(PixelAgentBehaviour frame1,PixelAgentBehaviour frame2,int n_agents)throws IOException//The module calling the various functions to be executed by an agent
		{	
			int i=0,j=0,count=0,loop_limit=0;
			image1=ImageIO.read(new File("D:/Java/classes/human_action/man11.jpg"));
			image2=ImageIO.read(new File("D:/Java/classes/human_action/man12.jpg"));
			frame2.height=image2.getHeight();
			frame2.width=image2.getWidth();
			frame1.form_blocks(image1);//call form_blocks for frame 1 alone
				
			if(n_agents==4)
			{
				dummy_loop1=100;
				loop_limit=0;
			}
			else if(n_agents==8)
			{
				dummy_loop1=13;
				loop_limit=dummy_loop1+2;
			}
			else if(n_agents==16)
			{
				dummy_loop1=7;
				loop_limit=dummy_loop1+1;
			}
				
			for(loop1=dummy_loop1;loop1<loop_limit;loop1++)//repeats 16/(number of agents) times for 16 block comparisons
			{	
				sadcounter=0;
				blocks_flag=0;
				for(count=0;count<=6399;count++)
					blocks_sad[count]=0;
		//		System.out.println("*******************  BLOCK "+loop1+" COMPARISON  **********************");
				frame2.blockcount=1;
				if(loop1==dummy_loop1)
				{
					frame1.blockcount=dummy_loop1;
					frame1.choose_currentblock_frame1(1,n_agents);
					frame1.currentwidth=frame1.currentblockx[1][1]-frame1.currentblockx[0][0];
					frame1.currentheight=frame1.currentblocky[1][1]-frame1.currentblocky[0][0];
					frame1.find_rgb(image1);
				}
				else
				{
					frame1.choose_currentblock_frame1(2,n_agents);
					frame1.find_rgb(image1);
				}
				while(q+16<frame2.height)
				{
					frame2.choose_currentblock_frame2(p,q);
					frame2.currentwidth=frame2.currentblockx[1][1]-frame2.currentblockx[0][0];
					frame2.currentheight=frame2.currentblocky[1][1]-frame2.currentblocky[0][0];
					frame2.find_rgb(image2);
					full_search(frame1,frame2,frame2.blockcount);
					frame2.blockcount++;
					if(blocks_sad[sadcounter-1]<blocks_match[blockno])
					{
						blocks_match[blockno]=blocks_sad[sadcounter-1];
						final_sad=sadcounter;
						mvx[blockno]=p;
						mvy[blockno]=q;
					}
					p++;
					if(p+25==frame2.width)
					{
						p=0;
						q++;
					}
				}//inner while
				frame1.currentwidth+=25;
				if(frame1.blockcount%4==0)
				{
					frame1.currentwidth=25;
					frame1.currentheight+=16;
				}
				//System.out.println("Block No "+final_sad+" has Minimum sad of "+blocks_match[blockno]);
				System.out.println("Final motion vector for block no "+frame1.blockcount+": ("+mvx[blockno]+","+mvy[blockno]+")");
				blockno++;
				frame1.blockcount++;
				p=0;
				q=0;
			}//outer for
		}//end of load_agentmodule		
			public void action()
			{
/*			try
			{
				Thread.sleep(6000); 
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}*/
			try
			{
				RandomAccessFile raf = new RandomAccessFile("D:/Java/classes/human_action/file7.txt", "rw");
				String s1=raf.readLine();
				n_agents = Integer.parseInt(s1);
				String s2=raf.readLine();
				String s3=raf.readLine();
				running = Integer.parseInt(s3);
				
				if(running==1)
				{
					System.out.println("Pixel Agent 7 running.........");
					PixelAgentBehaviour frame1;
					PixelAgentBehaviour frame2;
					frame1=new PixelAgentBehaviour();
					frame2=new PixelAgentBehaviour();
					frame3=new PixelAgentBehaviour();
					frame3.load_agentmodule(frame1,frame2,n_agents);
				}
				raf.close();
			}//try
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
			}
			
			try{
				System.out.println("Pixel Agent 7 killed");
				AgentContainer ac = getContainerController();
				AgentController a = ac.getAgent(getLocalName());
				a.kill();
				}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}//action
		
		public boolean done() 
		{
			long endTime = System.currentTimeMillis();
			long time= endTime-startTime;
			//System.out.println("Pixel Agent 7 time:"+time);

			return true;
		} 
	}//inner
}//end of outer class


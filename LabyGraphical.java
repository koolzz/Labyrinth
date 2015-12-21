/*
*Author: Mykola Yurchenko
*Version: 1.05
*Date: 16/04/2015
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LabyGraphical   {
	
	private static  int[][] map;
	private static  int[][] pathMap;
	private static  boolean[][] cellVisited;
	private static  boolean[][] path;
	private static int mapLength;
	private static Vector2 start,end;
	private static String[] directions;
	private static boolean isRunning;
	private static JFrame frame;
	private static Canvas can;
	
	private static int pathFindingX;
	private static int pathFindingY;

	public static void Restart(){
		//System.out.println(mapLength);
		map= new int[mapLength][mapLength];
		cellVisited= new boolean[mapLength][mapLength];
		path= new boolean[mapLength][mapLength];
		preGenerations();
		
		can.changeMap(map);
	
	}
	
	public static void ReBuild(int x,int y){
		
		map= new int[mapLength][mapLength];
		cellVisited= new boolean[mapLength][mapLength];
		path= new boolean[mapLength][mapLength];
		preGenerations(x,y);
		can.changeMap(map,x,y);
	
	}
	
	public static void preGenerations(){
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				if(col==0||col==map.length-1||row==0||row==map.length-1)
					map[row][col]=1;
				else if((row+1)%2==0&&(col+1)%2==0) {
					cellVisited[row][col]=false;
					path[row][col]=false;
					map[row][col]=0;}
				else
				map[row][col]=7;
			}
		}
		
		pathFindingX=1;
		pathFindingY=1;
		
		int x=map.length/2;
		int y=map.length/2;
		
		if(x%2==0)x--;
		if(y%2==0)y--;
		
					//Not sure which one is prefferable ( 1,1 alw works thO)
		
		//x=1;
		//y=1;
		
		//System.out.println(x+"   "+y);
		
		 
		/*
		cellVisited[x][y]=true;
		cellVisited[x+2][y]=true;
		cellVisited[x+2][y+2]=true;
		cellVisited[x+2][y-2]=true;
		cellVisited[x][y]=true;
		cellVisited[x][y+2]=true;
		cellVisited[x][y-2]=true;
		
		for(int i=x;i<=x+2;i++)
			for(int q=y-2;q<=y+2;q++)
				map[i][q]=0;
	
		*/
		
	//createRooms(x+2,y+2,2,2);

		//createRooms(11,11,4,2);
		//createRooms(15,51,4,8);
		//createRooms(33,30,2,2);
	
		
		for (int i=0;i<(int)(Math.random()*mapLength)+1+mapLength/10;i++){
			int xPos=(int)(mapLength*Math.random());
			int yPos=(int)(mapLength*Math.random());
			int xLength=(int)(Math.random()*(mapLength/(Math.random()*20+10)))+1;
			int yLength=(int)(Math.random()*(mapLength/(Math.random()*20+10)))+1;
			if (xPos%2==0)
				xPos++;
			if (yPos%2==0)
				yPos++;
			if (xLength%2!=0)
				xLength++;
			if (yLength%2!=0)
				yLength++;
			//createRooms(xPos,yPos,xLength,yLength);
		}
		genMaze(x,y);
	

		//Note because of the room creation, x and y coordinates could be inside of an explored room which shuts down the maze generation
	for (int col=0;col<map.length;col++ ){
		for (int row=0;row<map.length;row++ ){
			if(map[row][col]==2){
				map[row][col]=0;
			
			}
			if((row+1)%2==0&&(col+1)%2==0&&cellVisited[row][col]==false) 
				genMaze(row,col);
		}
	}
	


		if (Math.random() > 0) {
			x = mapLength - 2;
			y = mapLength - 2;
			
			end= new Vector2(x,y);
			map[x+1][y]=0;
		}
		else if(Math.random()>0.5f){
			x = mapLength - 2;
			y = 1;
			map[x+1][y]=0;
		}
		else{
			x = 1;
			y = mapLength - 2;
			map[x][y+1]=0;
		}
			
		//createPath();
		//ShowArray();
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				if(map[row][col]==2){
					map[row][col]=0;
				
				}}}
	}
	
	public static void preGenerations(int x,int y){
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				if(col==0||col==map.length-1||row==0||row==map.length-1)
					map[row][col]=1;
				else if((row+1)%2==0&&(col+1)%2==0) {
					cellVisited[row][col]=false;
					map[row][col]=0;}
				else
				map[row][col]=7;
			}
		}
					//Not sure which one is prefferable ( 1,1 alw works thO)
		
		//x=1;i
		//y=1;
		
		//System.out.println(x+"   "+y);
		
		
		genMaze(x,y);

		cellVisited[x][y]=true;


		if (Math.random() > 0.25f) {
			x = mapLength - 2;
			y = mapLength - 2;
			map[x+1][y]=0;
		}
		else if(Math.random()>0.5f){
			x = mapLength - 2;
			y = 1;
			map[x+1][y]=0;
		}
		else{
			x = 1;
			y = mapLength - 2;
			map[x][y+1]=0;
		}
			
			map[x][y]=6;
			//ShowArray();
			System.out.println();
	}
	
	public static void IncreaseMapLength(){
		mapLength+=mapLength/10;
		System.out.println("map"+mapLength);
		if(mapLength%2==0)mapLength++;
		Restart();
	}
	 
	public static void main (String[]args){
	//(new Thread(new LabyGraphical())).start();
	isRunning=true;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	int height = screenSize.height ;
	int width = screenSize.width ;
	 	
		frame = new JFrame("Labyrinth");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.pack();
		 
		mapLength=21;
		map= new int[mapLength][mapLength];
		
		cellVisited= new boolean[mapLength][mapLength];
		path= new boolean[mapLength][mapLength];

	directions=new String[5];
	directions[1]="Up";
	directions[2]="Right";
	directions[3]="Down";
	directions[4]="Left";
	
	preGenerations();
	
	can= new Canvas(map);

	frame.getContentPane().add(can, BorderLayout.CENTER);

	//System.out.println(x+" "+y);
}
	
	public static void createRooms(int x,int y,int xDelta,int yDelta){

		//System.out.println("x="+x+" ;y=" +y);
		
		for(int i=x-xDelta;i<=x+xDelta;i++){
			for(int q=y-yDelta;q<=y+yDelta;q++){
				if(i>0&&q>0&&i<mapLength-1&&q<mapLength-1){
				if(i%2!=0||q%2!=0){
				cellVisited[i][q]=true;
				//System.out.println("\tCell Visited  x="+i+" ;y=" +q);
				}

				map[i][q]=0;}
			}
		}
			
		
		int exit=direction();
		int xExit=0;
		int yExit=0;
		//TODO sometimes room generations causes maze exit pass to be blocked by a room
		while(	  (exit==1&&!inBounds(x,y-yDelta-1,1))
				||(exit==2&&!inBounds(x+xDelta+1,y,1))  
				||(exit==3&&!inBounds(x,y+yDelta+1,1))  
				||(exit==4&&!inBounds(x-xDelta-1,y,1))  	){
			exit=direction();
		}
			
		
		
		if (exit==1){
			yExit=y-yDelta-1;
			xExit=(int)(Math.random()*xDelta*2);
			xExit+=x-xDelta;
			if(xExit%2==0)
				xExit++;
			
		}
		else if (exit==2){
			xExit=x+xDelta+1;
			yExit=(int)(Math.random()*yDelta*2);
			yExit+=y-yDelta;
			if(yExit%2==0)
				yExit++;
			
		}
		else if (exit==3){
			yExit=y+yDelta+1;
			xExit=(int)(Math.random()*xDelta*2);
			xExit+=x-xDelta;
			if(xExit%2==0)
				xExit++;
			
		}
		else if (exit==4){
			xExit=x-xDelta-1;
			yExit=(int)(Math.random()*yDelta*2);
			yExit+=y-yDelta;
			if(yExit%2==0)
				yExit++;
			
		}
			if(inBounds(xExit,yExit,0))
			map[xExit][yExit]=0;
		
	}
	
	public static void randomizeExit(){
		end.x=(int)(end.x*Math.random());
		end.y=(int)(end.x*Math.random());
		if(end.x%2==0)
			end.x++;
		if(end.y%2==0)
			end.y++;
	}
	
	public static int wrongPassDir(int x, int y){
		if (inBounds(x,y,1)&&pathMap[x][y-1]==1&&map[x][y-1]==4) return 1;
		else if (inBounds(x,y,2)&&pathMap[x+1][y]==1&&map[x+1][y]==4) return 2; 
		else if (inBounds(x,y,3)&&pathMap[x][y+1]==1&&map[x][y+1]==4) return 3;
		else if (inBounds(x,y,4)&&pathMap[x-1][y]==1&&map[x-1][y]==4) return 4;
		else return 0;
	}
	public static int availableDir(int x, int y){
		//NOTE only works when labyrinth pass is 0
		
		if (inBounds(x,y,1)&&map[x][y-1]==0&&!path[x][y-1]) return 1;
		else if (inBounds(x,y,2)&&map[x+1][y]==0&&!path[x+1][y]) return 2; 
		else if (inBounds(x,y,3)&&map[x][y+1]==0&&!path[x][y+1]) return 3;
		else if (inBounds(x,y,4)&&map[x-1][y]==0&&!path[x-1][y]) return 4;
		else return 0;
	}
	
	public static int verifyDir(int x, int y){
		if (inBounds(x,y,1)&&map[x][y-1]==4&&path[x][y-1]) return 1;
		else if (inBounds(x,y,2)&&map[x+1][y]==4&&path[x+1][y]) return 2; 
		else if (inBounds(x,y,3)&&map[x][y+1]==4&&path[x][y+1]) return 3;
		else if (inBounds(x,y,4)&&map[x-1][y]==4&&path[x-1][y]) return 4;
		else return 0;
	}
	
	public static int openings(int x,int y){
		int passes=0;
		if (inBounds(x,y,1)&&map[x][y-1]==0)passes++;
		if (inBounds(x,y,2)&&map[x+1][y]==0)passes++;
		if (inBounds(x,y,3)&&map[x][y+1]==0)passes++;
		if (inBounds(x,y,4)&&map[x-1][y]==0)passes++;
		return passes;
	}
	public static void verifyPass(int startingX, int startingY){
		cleanUp();
		while ((!(startingX==end.x&&startingY==end.y))||(startingX==end.x&&startingY==end.y)){
			
			int dir=verifyDir(startingX,startingY);
			System.out.println("Dir"+dir);
			System.out.println("X="+startingX+" Y="+startingY);
			path[startingX][startingY]=false;
			map[startingX][startingY]=9;
			if (dir==1){
				startingY--;	
			}
			else if (dir==2){
				startingX++;	
			}
			else if (dir==3){
				startingY++;	
			}
			else if (dir==4){
				startingX--;	
			}
			else break;
			
		}
		System.out.println("Pass Confirmed");
	}
	public static void createPathMultibranch(){

		pathFindingX=1;
		pathFindingY=1;
		pathMap=new int[mapLength][mapLength];
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				if (map[row][col]==4||map[row][col]>=10)
					map[row][col]=0;
				pathMap[row][col]=0;
				path[pathFindingX][pathFindingY]=false;
			}
		}
		color=10;
		finalPathKey="";
		pathFindingMultibranch(1,1,"");
		
		
	}
	public static void createPathBruteForce(){
		pathFindingX=1;
		pathFindingY=1;
		pathMap=new int[mapLength][mapLength];
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				pathMap[row][col]=0;
				path[pathFindingX][pathFindingY]=false;
			}
		}
		pathFindingBruteForce();
		
		
	}
	public static void repeatPath(){
		pathFindingX=1;
		pathFindingY=1;
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				pathMap[row][col]=0;
				path[row][col]=false;
				if (map[row][col]!=1&&map[row][col]!=7)
					map[row][col]=0;
			}
		}
	}

	public static void MultibranchPassClearing(){
		/*
		if (pathMap==null)
			pathMap=new int[mapLength][mapLength];
		pathFinding();
	*/
	//	verifyPass(1,1);
		String[] pathKeyNumbers=finalPathKey.split(",");
		finalPathKey="";
		int i=0;
		for(int pathKeyNumber=0;pathKeyNumber<pathKeyNumbers.length;pathKeyNumber++){
			 i=Integer.parseInt(pathKeyNumbers[pathKeyNumber]);
			 System.out.println(i);
				for (int col=0;col<map.length;col++ ){
					for (int row=0;row<map.length;row++ ){
						if (map[row][col]==i)
							map[row][col]=4;
					}
				}
		}
		
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				if (map[row][col]>=10)
					map[row][col]=0;
			}
		}
				
	}
	public static void cleanUp(){
		boolean cleaned=false;
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				if(map[row][col]==5){
					map[row][col]=0;
					cleaned=true;
				}
			}
		}
		if(!cleaned){
			for (int col=0;col<map.length;col++ ){
				for (int row=0;row<map.length;row++ ){
					if(map[row][col]==4)
						map[row][col]=0;
						
					
				}
			}
		}
		
	}
	
	public static void pathFindingBruteForce(){

		while (!(pathFindingX==end.x&&pathFindingY==end.y)){
			
			System.out.println("\nEnding positions "+end.x+"  "+end.y);
			System.out.println("Current location "+pathFindingX+"   "+pathFindingY);
			System.out.println("Oppenings "+openings(pathFindingX,pathFindingY));
			System.out.println("1="+map[pathFindingX][pathFindingY-1]+inBounds(pathFindingX,pathFindingY,1)+" 2="+map[pathFindingX+1][pathFindingY]+inBounds(pathFindingX,pathFindingY,2)+" 3="+map[pathFindingX][pathFindingY+1]+inBounds(pathFindingX,pathFindingY,3)+" 4="+map[pathFindingX-1][pathFindingY]+inBounds(pathFindingX,pathFindingY,4));

			int availableDirection=availableDir(pathFindingX,pathFindingY);
			System.out.println("Direction "+availableDirection);
			
			map[pathFindingX][pathFindingY]=4;
			path[pathFindingX][pathFindingY]=true;
			pathMap[pathFindingX][pathFindingY]=1;
			
			if (openings(pathFindingX,pathFindingY)>0){
	
					if (availableDirection==1){
						pathFindingY--;						
					}
					else if (availableDirection==2){
						pathFindingX++;						
					}
					else if (availableDirection==3){
						pathFindingY++;							
					}
					else if (availableDirection==4){
						pathFindingX--;	
					}	
			}
			
			else {
				int backwardsDir = wrongPassDir(pathFindingX,pathFindingY);
				map[pathFindingX][pathFindingY]=5;
				path[pathFindingX][pathFindingY]=true;
				pathMap[pathFindingX][pathFindingY]=3;
				System.out.println("Backwards direction"+backwardsDir);
				if (backwardsDir==1){
					pathFindingY--;	
				}
				else if (backwardsDir==2){
					pathFindingX++;	
				}
				else if (backwardsDir==3){
					pathFindingY++;	
				}
				else if (backwardsDir==4){
					pathFindingX--;	
				}
				else {
					map[pathFindingX][pathFindingY]=6;
					break;
					}
			}
	
		}
		if(pathFindingX==end.x&&pathFindingY==end.y){
		map[pathFindingX][pathFindingY]=4;
		path[pathFindingX][pathFindingY]=true;
		pathMap[pathFindingX][pathFindingY]=1;
		
		}
}	
	private static String finalPathKey="";
	public static int test=0;
	private static int color=10;
	
	public static void pathFindingMultibranch(int x, int y,String pathKey){
		
		System.out.println("\nCurrent location "+x+"   "+y);
		//System.out.println("Ending positions "+end.x+"  "+end.y);
		System.out.println("Avaiable oppenings "+openings(x,y));
		System.out.println("Current pathkey "+pathKey);
		System.out.println("FINALPASSKEYLENGTH "+finalPathKey.length());
		if ((x!=end.x||y!=end.y)&&finalPathKey.length()==0){
			pathKey+=color+",";
			map[x][y]=color;
			//path[x][y]=true;
			//pathMap[x][y]=color;
			int availableDirection=availableDir(x,y);
			if (openings(x,y)==1){
				
					while(openings(x,y)==1&&(!(x==end.x&&y==end.y))){
						availableDirection=availableDir(x,y);
						System.out.println("Available directions "+availableDirection);
						if (availableDirection==1){
							y--;	
						}
						else if (availableDirection==2){
							x++;								
						}
						else if (availableDirection==3){		
							y++;								
						}
						else if (availableDirection==4){
							x--;		
						}
						else if (availableDirection==0){
							break;	
						}
						map[x][y]=color;
						//path[x][y]=true;
						//pathMap[x][y]=1;
					}
					
					if (!(x==end.x&&y==end.y)){
						pathFindingMultibranch(x,y,pathKey);	
					}
					else {
						finalPathKey=pathKey;
						
					}
						
					}
			else if (openings(x,y)>1){
			
				if (availableDirection==1){
					color++;
					pathFindingMultibranch(x,y-1,pathKey);
					availableDirection=availableDir(x,y);
				
				
				}
				 if (availableDirection==2){
					 color++;
					 pathFindingMultibranch(x+1,y,pathKey);
					 availableDirection=availableDir(x,y);
				
					
				}
				 if (availableDirection==3){
					 color++;
					 pathFindingMultibranch(x,y+1,pathKey);
					 availableDirection=availableDir(x,y);
						
					
				}
				 if (availableDirection==4){
					 color++;
					 pathFindingMultibranch(x-1,y,pathKey);
					 availableDirection=availableDir(x,y);
						
				}
			}
			
			else if (openings(x,y)==0){
				for (int col=0;col<map.length;col++ ){
					for (int row=0;row<map.length;row++ ){
						if (map[row][col]==color)
							map[row][col]=color;
					}
				}
			}
				
		}
		System.out.println("Final pathkey "+finalPathKey);

	
}
	public static void genMaze(int x,int y){
		int dir=direction(x,y);
		if(dir!=0){	
			if (dir==1&&cellVisited[x][y-2]==false){
				cellVisited[x][y-2]=true;
				map[x][y-1]=0;
				genMaze(x,y-2);
			}
			else if (dir==2&&cellVisited[x+2][y]==false){
				cellVisited[x+2][y]=true;
				map[x+1][y]=0;
				genMaze(x+2,y);
			}
			else if (dir==3&&cellVisited[x][y+2]==false){
				cellVisited[x][y+2]=true;
				map[x][y+1]=0;
				genMaze(x,y+2);
			}
			else if (dir==4&&cellVisited[x-2][y]==false){
				cellVisited[x-2][y]=true;
				map[x-1][y]=0;
				genMaze(x-2,y);
			}	
			
			if((inBounds(x,y,4)&&cellVisited[x-2][y]==false)||(inBounds(x,y,3)&&cellVisited[x][y+2]==false)||
			(inBounds(x,y,2)&&cellVisited[x+2][y]==false)||(inBounds(x,y,1)&&cellVisited[x][y-2]==false))
				genMaze(x,y);
			}
		
		}
	
	
	
	/*
	public static void genMaze(int x,int y){

		dir=direction();
		map[x][y]=2;

		//System.out.println("x="+x+"y="+y);
		//System.out.println(directions[dir]);
		//System.out.println(inBounds(x,y,dir));
		
		if(inBounds(x,y,dir)){
				if(dir==1&&map[x][y-2]==0){
	
					map[x][y-1]=0;
					genMaze(x,y-2);
				}
				else if(dir==2&&map[x+2][y]==0){
	
					map[x+1][y]=0;
					genMaze(x+2,y);
				}
				else if(dir==3&&map[x][y+2]==0){
	
					map[x][y+1]=0;
					genMaze(x,y+2);
				}
				else if(dir==4&&map[x-2][y]==0){
	
					map[x-1][y]=0;
					genMaze(x-2,y);
				}
				
					 if(inBounds(x,y,4)&&map[x-2][y]==0)
							genMaze(x,y,4);
					 if(inBounds(x,y,3)&&map[x][y+2]==0)
							genMaze(x,y,3);
					 if(inBounds(x,y,2)&&map[x+2][y]==0)
							genMaze(x,y,2);
					 if(inBounds(x,y,1)&&map[x][y-2]==0)
							genMaze(x,y,1);
					
		}
		else if(inBounds(x,y,4)&&map[x-2][y]==0)
				genMaze(x,y,4);
		else if(inBounds(x,y,3)&&map[x][y+2]==0)
				genMaze(x,y,3);
		else if(inBounds(x,y,2)&&map[x+2][y]==0)
				genMaze(x,y,2);
		else if(inBounds(x,y,1)&&map[x][y-2]==0)
				genMaze(x,y,1);

		

	}
	
	public static void genMaze(int x,int y,int dir){

		
		if(inBounds(x,y,dir)){
			if(dir==1&&map[x][y-2]==0){

				map[x][y-1]=0;
				genMaze(x,y-2);
			}
			else if(dir==2&&map[x+2][y]==0){

				map[x+1][y]=0;
				genMaze(x+2,y);
			}
			else if(dir==3&&map[x][y+2]==0){

				map[x][y+1]=0;
				genMaze(x,y+2);
			}
			else if(dir==4&&map[x-2][y]==0){

				map[x-1][y]=0;
				genMaze(x-2,y);
			
		}

		}
	}
	*/
	/*
	public static void genMaze(int x,int y){

			dir=direction();


		//System.out.println("x="+x+"y="+y);
		//System.out.println(directions[dir]);
		if(inBounds(x,y,dir)){

		if (dir==1&&cellVisited[x][y-2]==false){
			//System.out.println("Up");
			
			map[x][y-1]=0;
			y=y-2;
			cellVisited[x][y]=true;
			genMaze(x,y);
		}
		else if (dir==2&&cellVisited[x+2][y]==false){
			//System.out.println("Right");
			
			map[x+1][y]=0;
			x=x+2;
			cellVisited[x][y]=true;
			genMaze(x,y);
		}
		else if (dir==3&&cellVisited[x][y+2]==false){
			//System.out.println("Down");
			
			map[x][y+1]=0;
			y=y+2;
			cellVisited[x][y]=true;
			genMaze(x,y);
		}
		else if (dir==4&&cellVisited[x-2][y]==false){
			//System.out.println("Left");
			
			map[x-1][y]=0;
			x=x-2;
			cellVisited[x][y]=true;
			genMaze(x,y);
		}
		//add an else statement for magic
		 if((inBounds(x,y,4)&&cellVisited[x-2][y]==false)||(inBounds(x,y,3)&&cellVisited[x][y+2]==false)
		||(inBounds(x,y,2)&&cellVisited[x+2][y]==false)||(inBounds(x,y,1)&&cellVisited[x][y-2]==false))
		genMaze(x,y);
		}
		else if((inBounds(x,y,4)&&cellVisited[x-2][y]==false)||(inBounds(x,y,3)&&cellVisited[x][y+2]==false)
				||(inBounds(x,y,2)&&cellVisited[x+2][y]==false)||(inBounds(x,y,1)&&cellVisited[x][y-2]==false))
			genMaze(x,y);

		}
	*/
	
	/*
	public static void genMaze(int x,int y){
		
	if (specificDir==0)
		dir=direction();
	else
		dir=specificDir;
	specificDir=0;
	
	//dir=direction();
	//System.out.println("x="+x+"y="+y);
	//System.out.println(directions[dir]);
	if(inBounds(x,y,dir)){
		
			if (dir==1&&cellVisited[x][y-2]==false){
				//System.out.println("Up");
				cellVisited[x][y-2]=true;
				map[x][y-1]=0;
				y=y-2;
				genMaze(x,y);
			}
			else if (dir==2&&cellVisited[x+2][y]==false){
				//System.out.println("Right");
				cellVisited[x+2][y]=true;
				map[x+1][y]=0;
				x=x+2;
				genMaze(x,y);
			}
			else if (dir==3&&cellVisited[x][y+2]==false){
				//System.out.println("Down");
				cellVisited[x][y+2]=true;
				map[x][y+1]=0;
				y=y+2;
				genMaze(x,y);
			}
			else if (dir==4&&cellVisited[x-2][y]==false){
				//System.out.println("Left");
				cellVisited[x-2][y]=true;
				map[x-1][y]=0;
				x=x-2;
				genMaze(x,y);
				
			}	
	//add an else statement for magic
	 if((inBounds(x,y,4)&&cellVisited[x-2][y]==false)||(inBounds(x,y,3)&&cellVisited[x][y+2]==false)
	||(inBounds(x,y,2)&&cellVisited[x+2][y]==false)||(inBounds(x,y,1)&&cellVisited[x][y-2]==false)){
	if (inBounds(x,y,4)&&cellVisited[x-2][y]==false)
		specificDir=4;
	else if (inBounds(x,y,3)&&cellVisited[x][y+2]==false)
		specificDir=3;
	else if (inBounds(x,y,2)&&cellVisited[x+2][y]==false)	
		specificDir=2;
	else if (inBounds(x,y,1)&&cellVisited[x][y-2]==false)
		specificDir=1;

	genMaze(x,y);
	 	}
	}
}*/

	public static boolean inBounds(int x,int y,int dir){
	
	if(dir==0)
		return (y>0&&x>0&&x<mapLength-1&&y<mapLength-1);
	else if(dir==1)
		return (y-2>=0);
	else if (dir==2)
		return (x+2<=mapLength-1);
	else if (dir==3)
		return (y+2<=mapLength-1);
	else if (dir==4)
		return (x-2>=0);	
	return false;
}

	public static int direction(){
		
		return (int)(Math.random()*4+1);
	}
	public static int direction(int x,int y){
		int temp=0;
		if(!inBounds(x,y,temp))
			return temp;
		do{
		 temp=(int)(Math.random()*4+1);
			}while (!inBounds(x,y,temp));
		
		
		return temp;
	}
	

	public static void ShowArray(){
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
					System.out.print(map[row][col]+" ");
					}
			System.out.println();
			}
		
		
	}

	public static Vector2 randomSidePoint(){

		int x,y;
		x=0;
		y=0;

		//2 main branches, either through x or y coordinates
		if(Math.random()>0.5){
		x=(int)( Math.round(Math.random())* (mapLength-1));
		y=(int)(Math.random()*mapLength);
		}
		else{
		y=(int)( Math.round(Math.random())* (mapLength-1));
		x=(int)(Math.random()*mapLength);
		}
		return new Vector2(x,y);
		
	}
	
}
class Canvas extends JPanel  implements KeyListener{
	private LabyGraphical labyrinth; 
	private Vector2 player;
	private int[][] map;
	private boolean hard=false;
	private boolean view=false;
	private int block;
	private int width,height;
	private Color a[]= new Color[500];
	public Canvas(int[][]map){
		this.map=map;
		setBlock();
		player=new Vector2(block,block);
		addKeyListener(this);
		
				for(int i=0;i<a.length;i++)
		a[i]=new Color((float)Math.random(),(float)Math.random(),(float)Math.random());
		a[7]=Color.DARK_GRAY;
		a[1]=Color.black;
		a[0]=Color.white;
	}
	
	public void setBlock(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		 height = screenSize.height ;
		 width = screenSize.width ;
		if(height<width)
		block=height/map.length;
		else
		block=width/map.length;
	}
	
	public void changeMap(int[][]map){
		this.map=map;
		setBlock();
		player=new Vector2(block,block);
		//ShowArray();
		
		
		for(int i=0;i<a.length;i++)
		a[i]=new Color((float)Math.random(),(float)Math.random(),(float)Math.random());
		a[7]=Color.DARK_GRAY;
		a[1]=Color.black;
		a[0]=Color.white;
	}
	public void changeMap(int[][]map,int x,int y){
		this.map=map;
		setBlock();
		player=new Vector2(x*block,y*block);
		//ShowArray();
		
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	 
	public void paint(Graphics g){
		repaint();
		g.clearRect(0, 0,width,height);

		
if(!hard){
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
				
				drawCube(row,col,g);
				}
			}
}
else{

	
	for(int col=player.y/block-1;col<=player.y/block+1;col++){
		for(int row=player.x/block-1;row<=player.x/block+1;row++){
			

			drawCube(row,col,g);
		
		}
		
	}	
	int y=player.y/block;
	int x=player.x/block;
	int dir=1;
	int temp=1;
	
	while(map[x][y]==0){
		drawCube(x,y+1,g);
		drawCube(x,y-1,g);
		drawCube(x+dir,y,g);
		drawCube(x+dir,y+1,g);
		drawCube(x+dir,y-1,g);
		x++;
		}
	
	x=player.x/block;
	y=player.y/block;	
	while(map[x][y]==0){
		drawCube(x-1,y,g);
		drawCube(x,y,g);
		
		drawCube(x,y+dir,g);
		drawCube(x+1,y+dir,g);
		drawCube(x-1,y+dir,g);

		y++;
		}
	
	dir=-1;
	
	x=player.x/block;
	y=player.y/block;
	while(map[x][y]==0){
		drawCube(x,y+1,g);
		drawCube(x,y-1,g);
		drawCube(x+dir,y,g);
		drawCube(x+dir,y+1,g);
		drawCube(x+dir,y-1,g);
		x--;
		}
	
	
	x=player.x/block;
	y=player.y/block;	
	while(map[x][y]==0){
		drawCube(x-1,y,g);
		drawCube(x,y,g);
		
		drawCube(x,y+dir,g);
		drawCube(x+1,y+dir,g);
		drawCube(x-1,y+dir,g);

		y--;
		}
	

	
}		g.setColor(Color.green );

g.drawRect(player.x,player.y , block, block);
g.fillRect(player.x,player.y , block, block);
	
		}
	
	public void drawCube(int row,int col,Graphics g){
		if(view){
		
	if(map[row][col]==7&&row>0&&row<map.length-1&&map[row+1][col]==0&&map[row-1][col]==0){
		g.setColor(Color.DARK_GRAY );
		g.drawRect((int)(row*block+block*0.375),col*block ,block/4, block);
		g.fillRect((int)(row*block+block*0.375),col*block ,block/4, block);
	}
	else 	if(map[row][col]==7&&col>0&&col<map.length-1&&map[row][col+1]==0&&map[row][col-1]==0){
		g.setColor(Color.DARK_GRAY );
		g.drawRect(row*block,(int)(col*block+block*0.375) ,block, block/4);
		g.fillRect(row*block,(int)(col*block+block*0.375) ,block, block/4);
	}
	else if(map[row][col]==7&&col>0&&col<map.length-1&&row>0&&row<map.length-1&&map[row+1][col]==0&&map[row][col+1]==0){
		//left and down
		g.setColor(Color.blue );
		g.drawRect((int)(row*block+block*0.375),col*block ,block/4, block/2+block/9);
		g.fillRect((int)(row*block+block*0.375),col*block ,block/4, block/2+block/9);
		
		g.drawRect(row*block,(int)(col*block+block*0.375) ,block/2+block/9, block/4);
		g.fillRect(row*block,(int)(col*block+block*0.375) ,block/2+block/9, block/4);
	}
	else if(map[row][col]==7&&col>0&&col<map.length-1&&row>0&&row<map.length-1&&map[row-1][col]==0&&map[row][col+1]==0){
		//right and down
		g.setColor(Color.red );
		g.drawRect((int)(row*block+block*0.375),col*block ,block/4, block/2+block/9);
		g.fillRect((int)(row*block+block*0.375),col*block ,block/4, block/2+block/9);
		
		g.drawRect((int)(row*block+block*0.375),(int)(col*block+block*0.375) ,block/2+block/7, block/4);
		g.fillRect((int)(row*block+block*0.375),(int)(col*block+block*0.375) ,block/2+block/7, block/4);
	}
	else if(map[row][col]==7&&col>0&&col<map.length-1&&row>0&&row<map.length-1&&map[row+1][col]==0&&map[row][col-1]==0){
		//up and left
		g.setColor(Color.yellow );
		g.drawRect((int)(row*block+block*0.375),col*block+block/2 ,block/4, block/2+block/9);
		g.fillRect((int)(row*block+block*0.375),col*block+block/2,block/4, block/2+block/9);
		
		g.drawRect(row*block,(int)(col*block+block*0.375) ,block/2+block/9, block/4);
		g.fillRect(row*block,(int)(col*block+block*0.375) ,block/2+block/9, block/4);
	}
	else if(map[row][col]==7&&col>0&&col<map.length-1&&row>0&&row<map.length-1&&map[row-1][col]==0&&map[row][col-1]==0){
		//up and left
		g.setColor(Color.cyan );
		g.drawRect((int)(row*block+block*0.375),col*block+block/2 ,block/4, block/2+block/9);
		g.fillRect((int)(row*block+block*0.375),col*block +block/2,block/4, block/2+block/9);
		
		g.drawRect((int)(row*block+block*0.375),(int)(col*block+block*0.375) ,block/2+block/7, block/4);
		g.fillRect((int)(row*block+block*0.375),(int)(col*block+block*0.375) ,block/2+block/7, block/4);
	}
	else{	
			if(map[row][col]==1){
				g.setColor(Color.black );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==7){
				g.setColor(Color.DARK_GRAY );
				g.drawOval(row*block,col*block , block, block);
				g.fillOval(row*block,col*block , block, block);
				
			}
			
		}
		}
		else{
			/*
			if(map[row][col]==1){
				g.setColor(Color.black );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==7){
				g.setColor(Color.DARK_GRAY );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==4){
				g.setColor(Color.red );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==5){
				g.setColor(Color.blue );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==6){
				g.setColor(Color.orange );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==9){
				g.setColor(Color.cyan );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==10){
				g.setColor(Color.green );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==11){
				g.setColor(Color.pink );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			else if(map[row][col]==12){
				g.setColor(Color.yellow );
				g.drawRect(row*block,col*block , block, block);
				g.fillRect(row*block,col*block , block, block);
				
			}
			*/

			if(map[row][col]<a.length)
				g.setColor(a[map[row][col]] );
				
			else
				g.setColor(	new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			
			if (map[row][col]==4)
				//g.setColor(	new Color((float)Math.random()/2,(float)Math.random(),(float)Math.random()/2));
				g.setColor(Color.cyan);
				
			g.drawRect(row*block,col*block , block, block);
			g.fillRect(row*block,col*block , block, block);
			
		}
		g.setColor(Color.black );
		g.drawRect(row*block,col*block , block+1, block+1);
	}
	
	public  void ShowArray(){
		for (int col=0;col<map.length;col++ ){
			for (int row=0;row<map.length;row++ ){
					System.out.print(map[row][col]+" ");
					}
			System.out.println();
			}
		System.out.println();
		System.out.println();
		
	}
	public void keyTyped(KeyEvent e) {
		 //System.out.println( "KEY TYPED: "+e.getKeyChar());
  }

  /** Handle the key-pressed event from the text field. */
  public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()=='w'&&map[player.x/block][player.y/block-1]==0)
			player.y-=block;
		if(e.getKeyChar()=='s'&&map[player.x/block][player.y/block+1]==0)
			player.y+=block;
		if(e.getKeyChar()=='a'&&map[player.x/block-1][player.y/block]==0)
			player.x-=block;
		if(e.getKeyChar()=='d'&&map[player.x/block+1][player.y/block]==0)
			player.x+=block;
		
		
		  if(e.getKeyChar()=='r')
		      labyrinth.Restart();

		  if(e.getKeyChar()=='t'&&(player.x/block+1)%2==0&&(player.y/block+1)%2==0)
		      labyrinth.ReBuild(player.x/block, player.y/block);
		 
		  if(e.getKeyChar()=='i')
		      labyrinth.IncreaseMapLength();
		  
		  if(e.getKeyChar()=='h')
		     hard=!hard;
		  if(e.getKeyChar()=='v')
			  view=!view;
		  
		  if(e.getKeyChar()=='1')
			  labyrinth.createPathMultibranch();
		  if(e.getKeyChar()=='2')
			  labyrinth.MultibranchPassClearing();
		  
		  if(e.getKeyChar()=='0')
			  labyrinth.createPathBruteForce();  
		  if(e.getKeyChar()=='9')	  
			  labyrinth.cleanUp();
		  
		  if(e.getKeyChar()=='q')
			  labyrinth.randomizeExit();
		  if(e.getKeyChar()=='c')
			  labyrinth.repeatPath();
		 

  	 //System.out.println( "KEY PRESSED: "+e.getKeyChar());
  }

  /** Handle the key-released event from the text field. */
  public void keyReleased(KeyEvent e) {
      //System.out.println("KEY RELEASED: "+e.getKeyChar() );

  }

}


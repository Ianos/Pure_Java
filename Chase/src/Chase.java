import java.io.*;

public class Chase {
	/**
	 * @param args
	 */	
	public static void main(String[] args) {
		int W, L, x1, x2, y1, y2;
				
		if(args.length>0){
			try{
				BufferedReader input = new BufferedReader (new FileReader (args[0]));
				//parsing the first line of the text that contains room's width and length
				String lineA = input.readLine();
				String[] a = lineA.split(" ");
				W=Integer.parseInt(a[0]); L=Integer.parseInt(a[1]);
				//parsing the first line of the text that contains first robot's coordinates
				String lineB = input.readLine();
				String[] b = lineB.split(" ");
				x1=Integer.parseInt(b[0]); y1=Integer.parseInt(b[1]);
				//parsing the first line of the text that contains second robot's coordinates 
				String lineC = input.readLine();
				String[] c = lineC.split(" ");
				x2=Integer.parseInt(c[0]); y2=Integer.parseInt(c[1]);
				//parsing rest of the text that contains room's layout
				char array[][] = new char[L][W];
				for(int i=0; i<L; i++){
					String line = input.readLine();
					char[] l = line.toCharArray();
					for(int j=0; j<W; j++){
						array[i][j]=l[j];
					}
				}
				Room room = new Room(L,W,array);
				Position pos1 = new Position(x1,y1);
				AStarRobot robot1 = new AStarRobot(pos1,1,room);
				Position pos2 = new Position(x2,y2);
				RandomRobot robot2 = new RandomRobot(pos2,1,room);
				int count = 0;
				while(!robot1.isCloseEnough(robot2)){
					count++;
					robot2.updatePosition();
					//System.out.println("robot 2 is at: "+robot2.getPos().getX()+" "+robot2.getPos().getY());
					//System.out.println("Calculating next step ... ");
					robot1.updatePosition(robot2);
					//System.out.println("robot 1 is at: "+robot1.getPos().getX()+" "+robot1.getPos().getY());
					//System.out.println();
				}
				System.out.println("Robot 1 has successfully reached robot 2!!!");
				System.out.println("-------------------------------");
				System.out.println("Statistics...");
				System.out.println("Total moves made : "+count);
				System.out.println("Total nodes explored : "+robot1.getStatesNo());
				
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}

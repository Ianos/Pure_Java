public class Room {

	private int length;
	private int width;
	private char[][] layout;
	
	public Room(int n, int m, char[][] a){
		length = n;
		width = m;
		layout = a;		
	}
	
	public boolean isPositionInRoom(Position pos){
		if ((pos.getX()>length) || (pos.getX()<=0) || (pos.getY()<=0) || (pos.getY()>width))
			return false;
		else
			return true;
		}
	
	public boolean isPotitionEmpty(Position pos){
		if (layout[pos.getX()-1][pos.getY()-1]=='o' || layout[pos.getX()-1][pos.getY()-1]=='O')
			return true;
		else
			return false;
	}
}


public class Robot {
	
    protected Position p;
	protected int d;
	Room r;
	
	public Robot(Position pos, int direction, Room room){
		p=pos;
		d=direction;
		r=room;
	}
	
	public Position getPos(){
		return p;
	}
	
	public int getDir(){
		return d;
	}
	
	public void setDir(int dir){
		d=dir;
	}
	
	public void setPos(Position pos){
		p=pos;
	}
	
	public Room getRoom(){
		return r;
	}

}

import java.util.Random;

public class RandomRobot extends Robot {
	private int speed = 1;
	
	public RandomRobot(Position pos, int direction, Room room){
		super(pos, direction, room);
		Random generator = new Random(); 
		d = generator.nextInt(4) + 1;
	}

	public int getSpeed(){
		return speed;
	}
	
	public void updatePosition(){
		while(true){
			Position pos = new Position(p.getX(), p.getY());
			pos.next(this.getDir(), this.getSpeed());
			if (r.isPositionInRoom(pos) && r.isPotitionEmpty(pos)){
				p=pos;
				break;
			}
			else{
				Random generator = new Random(); 
				d = generator.nextInt(4) + 1;
			}
		}
	}
}

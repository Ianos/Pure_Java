public class AStarRobot extends Robot {
	private int speed = 3;
	private int statesNo;
	
	public AStarRobot(Position pos, int direction, Room room){
		super(pos, direction, room);
	}

	public int getSpeed(){
		return speed;
	}
	
	public int getStatesNo(){
		return statesNo;
	}
		
	public void updatePosition(Robot goal){
		AStarSolver solver = new AStarSolver();
		State initialState = new State(this.getPos(), goal.getPos(),this.getRoom());
		Position next = solver.solve(initialState);
		this.setPos(next);
		statesNo += solver.getStatesNo();
	}
	
	public boolean isCloseEnough(Robot robot2) {
		if(this.getPos().euDist(robot2.getPos())<3.0){
			if (this.getPos().getX()==robot2.getPos().getX())
				if (Math.abs(this.getPos().getY()-robot2.getPos().getY())>1.0){
					Position temp = new Position(this.getPos().getX(), Math.min(robot2.getPos().getY(),this.getPos().getY())+1 );
					if(this.getRoom().isPotitionEmpty(temp))
						return true;
					else
						return false;
				}
				else
					return true;
			else if (this.getPos().getY()==robot2.getPos().getY()){
				if (Math.abs(this.getPos().getX()-robot2.getPos().getX())>1.0){
					Position temp = new Position(Math.min(robot2.getPos().getX(),this.getPos().getX())+1, this.getPos().getY());
					if(this.getRoom().isPotitionEmpty(temp))
						return true;
					else
						return false;
				}
				else
					return true;
			}
			else
				return false;
		}
		else
			return false;
	}
}

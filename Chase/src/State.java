import java.util.Set;
import java.util.HashSet;


public class State{
	
	protected Position current, goal ;
	private State parent;
	private Room room;
	private boolean start;	
	protected double localCost; // cost of getting from this state to goal
	private double parentCost; // cost of getting from parent state to this state
	private double passThroughCost;// cost of getting from the start to the goal
	// through this state
	
	private Set<State> nextStates = new HashSet<State>();
	
	public State(Position pos1, Position pos2, Room room){
		current=pos1;
		goal=pos2;
		this.room = room;
	}

	public Position getCur(){
		return current;
	}
	
	public Position getGoal(){
		return goal;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public Set<State> getNextStates() {
		return nextStates;
	}
	
	public void setNextStates(Set<State> nextStates) {

		this.nextStates = nextStates;
	}

	public State getParent() {

		return parent;
	}

	public void setParent(State parent) {

		this.parent = parent;
	}
	
	public boolean isStart() {

		return start;
	}

	public void setStart(boolean start) {

		this.start = start;
	}
	
	public void calculateNextStates() {
		for (int i=1;i<=3;i++){
		Position top = new Position(current.getX(),current.getY());
		top.next(1,i); 
		Position bottom = new Position(current.getX(),current.getY());
		bottom.next(2,i);
		Position left = new Position(current.getX(),current.getY());
		left.next(3,i);
		Position right = new Position(current.getX(),current.getY());
		right.next(4,i);
		
		State topState = new State(top, goal, room);
		State bottomState = new State(bottom, goal, room);
		State leftState = new State(left, goal, room);
		State rightState = new State(right, goal, room);
		
		if(room.isPositionInRoom(top) && room.isPotitionEmpty(top)){
			addNextState(topState);
			topState.setParent(this);
		}
		if(room.isPositionInRoom(bottom) && room.isPotitionEmpty(bottom)){
			addNextState(bottomState);
			bottomState.setParent(this);
		}
		if(room.isPositionInRoom(left) && room.isPotitionEmpty(left)){
			addNextState(leftState);
			leftState.setParent(this);
		}
		if(room.isPositionInRoom(right) && room.isPotitionEmpty(right)){
			addNextState(rightState);
			rightState.setParent(this);
		}
		}
	}
	
	public void addNextState(State state) {
		nextStates.add(state);
	}
	
	public void removeNextState(State state) {
		nextStates.remove(state);
	}

	public double getPassThrough() {
		
		if (isStart()) {
			return 0.0;
		}

		passThroughCost = getLocalCost() + getParentCost();
		return passThroughCost;
	}

	public double getLocalCost() {

		if (isStart()) {
			return 0.0;
		}

		//localCost = current.euDist(goal);		//underestimates
		localCost = current.circDist(goal);		//overestimates
		return localCost;
	}

	public double getParentCost() {

		if (isStart()) 
			return 0.0;
		parentCost = 3.0 + parent.getParentCost();
		
		return parentCost;
	}
		
	public boolean isWinning() {
		if(current.euDist(goal)<3.0){
			if (current.getX()==goal.getX())
				if (Math.abs(current.getY()-goal.getY())>1.0){
					Position temp = new Position(current.getX(), Math.min(goal.getY(),current.getY())+1 );
					if(room.isPotitionEmpty(temp))
						return true;
					else
						return false;
				}
				else
					return true;
			else if (current.getY()==goal.getY()){
				if (Math.abs(current.getX()-goal.getX())>1.0){
					Position temp = new Position(Math.min(goal.getX(),current.getX())+1, current.getY());
					if(room.isPotitionEmpty(temp))
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((current == null) ? 0 : current.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (current == null) {
			if (other.current != null)
				return false;
		} else if (!current.equals(other.current))
			return false;
		return true;
	}
	
	
}

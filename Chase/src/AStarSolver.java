import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStarSolver{
	
	public AStarSolver(){};
	
	private Set<State> opened = new HashSet<State>();
	private Set<State> closed = new HashSet<State>();
	private List<Position> bestList = new ArrayList<Position>();
	private int statesNo = 0;	
	
	public Position solve(State initialState){
		findBestPath(initialState);
		return (bestList.get(bestList.size()-1));
	}
	
	public int getStatesNo(){
		return statesNo;
	}
	
	private void findBestPath(State initialState){
		initialState.setStart(true);
		initialState.calculateNextStates();
		Set <State> nextStates = initialState.getNextStates();
		for (State nextState : nextStates) {
			nextState.setParent(initialState);
			if (!nextState.isStart()) {
				opened.add(nextState);
				statesNo++;
			}
		}
		while (opened.size() > 0) {
			State best = findBestPassThrough(); //h katastash me to mikritero f
			opened.remove(best);
			//System.out.println("exploring "+best.getCur().getX()+","+best.getCur().getY()+","+best.getPassThrough());
			closed.add(best);
			if (best.isWinning()) {
				//System.out.println("Goal Found");
				populateBestList(best);
				return;
			} 
			else {
				best.calculateNextStates();   //βρίσκω με ποιες γειτονεύει
				Set<State> neighbors = best.getNextStates();
				for (State neighbor : neighbors) {	
					if (opened.contains(neighbor)) { //κλάδεμα
						State tmpState = new State(neighbor.getCur(), neighbor.getGoal(), neighbor.getRoom());
						tmpState.setParent(best);
						if (tmpState.getPassThrough() >= neighbor
								.getPassThrough()) {
							//System.out.println("branched1");
							continue;
							
						}
					}

					if (closed.contains(neighbor)) {  //κλάδεμα
						State tmpState = new State(neighbor.getCur(), neighbor.getGoal(), neighbor.getRoom());
						tmpState.setParent(best);
						if (tmpState.getPassThrough() >= neighbor
								.getPassThrough()) {
							//System.out.println("branched2");
							continue;
						}
					}
					
					
					neighbor.setParent(best);
					opened.remove(neighbor);
					closed.remove(neighbor);
					opened.add(neighbor);
					statesNo++;
				}
			}
		}
		return;
	}	
	
	private void populateBestList(State state) {

		bestList.add(state.getCur());
		if (!state.getParent().isStart()) {
			populateBestList(state.getParent());
		}

		return;
	}
	
	private State findBestPassThrough() {

		State best = null;
		for (State state : opened) {
			if (best == null
					|| state.getPassThrough() < best.getPassThrough()) {
				best = state;
			}
		}

		return best;
	}
	
}

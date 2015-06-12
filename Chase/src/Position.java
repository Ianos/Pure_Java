
public class Position {
	private int x;
	private int y;
	
	public Position(int xcoor, int ycoor){
		x=xcoor;
		y=ycoor;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int newX){
		x=newX;
	}
	
	public void setY(int newY){
		y=newY;
	}
	
	public void next(int d, int s){
		if (d==1) //goes up
			y -= s;
		if (d==2) //goes down
			y += s;  
		if (d==3) //goes left
			x -= s;
		if (d==4) //goes right
			x += s;
	}

	public double euDist(Position pos){
		return Math.sqrt(Math.pow((this.getX()-pos.getX()),2) + Math.pow((this.getY()-pos.getY()),2));
	}
	
	public double circDist(Position pos){
		return Math.pow((this.getX()-pos.getX()),2) + Math.pow((this.getY()-pos.getY()),2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}

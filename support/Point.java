public class Point {
	private double x,y;
	
	/*
	 * A point represented through normalized points (0-1)
	 */
	public Point(double X,double Y) {
		this.x      = X;
		this.y      = Y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public String toString(){
		return x+", " + y;
	}
	public double distance(Point that){
		//TODO
		return Math.sqrt(Math.pow(this.getX() - that.getX(),2) + 
				Math.pow(this.getY() - that.getY(),2));
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Point)) {
			return false;
		}
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}

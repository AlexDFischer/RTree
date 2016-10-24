

public class Rect {

	private double x,y,width,height;
	
	/*
	 * A rectangle represented through normalized points (0-1)
	 */
	public Rect(double X,double Y, double Width,double Height) {
		this.x      = X;
		this.y      = Y;
		this.height = Height;
		this.width  = Width;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	
	public double getArea(){
		return height*width;
	}
	
	public String toString(){
		return x+", " + y + ", " + width + ", " + height;
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Rect)) {
			return false;
		}
		return this.hashCode() == o.hashCode();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}

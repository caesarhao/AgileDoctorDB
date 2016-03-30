package beziercurve;

public class Point {
	public double x;
	public double y;
	public double t = -1;
	public static int Dimention = 2;
	public Point() {
		x = 0;
		y = 0;
	}
	public Point(double px, double py){
		x = px;
		y = py;
	}
	public Point add(Point p){
		return new Point(x+p.x, y+p.y);
	}
	public Point multiple(double t){
		return new Point(x*t, y*t);
	}
	@Override
	public String toString(){
		return "x: " + x + "\t y: " + y;
	}
}

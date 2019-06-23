import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Stroke {

	private Color color;
	private int strokeWidth;
	private int strokeHeight;
	private ArrayList<Point> refPoints;
	private ArrayList<Point> realPoints;

	public Stroke(ArrayList<Point>points) {
		this.refPoints = points;
		realPoints = new ArrayList<Point>();
		this.strokeWidth = 10;
		this.strokeHeight = 10;
	}
	public Stroke(ArrayList<Point>points, Color color, int strokeWidth, int strokeHeight) {
		this.color = color;
		this.strokeWidth = strokeWidth;
		this.strokeHeight = strokeHeight;
		this.refPoints = points;
		realPoints = new ArrayList<Point>();
	}

	public Stroke(ArrayList<Point>points, Color color, int strokeSize) {
		this.color = color;
		this.strokeWidth = strokeSize;
		this.strokeHeight = strokeSize;
		this.refPoints = points;
		realPoints = new ArrayList<Point>();
	}
	
	public Stroke() {
		// TODO Auto-generated constructor stub
	}
	public abstract void findRealPoints();
	
	public void erasePoints(ArrayList<Point>points) {
		for(Point p: points) {
			for(int i = 0; i<realPoints.size();i++) {
				if(p == realPoints.get(i)) {
					System.out.print(realPoints.toString());
					realPoints.remove(i);
					i--;
				}
			}
		}
	
	}
	
	protected Point rotatePoint(Point center, Point p, double refAngle) {
		refAngle = refAngle/180*Math.PI;
		Point p2 = new Point((int)(p.getX()-center.getX()),(int)(p.getY()-center.getY()));
		Point c = new Point((int)(p2.getX()*Math.cos(refAngle)-p2.getY()*Math.sin(refAngle)),(int)(p2.getY()*Math.cos(refAngle)+p2.getX()*Math.sin(refAngle)));
		c.setLocation(new Point((int)(c.getX()+center.getX()),(int)(c.getY()+center.getY())));
		return c;
	}
	protected double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.getX()-b.getX(), 2)+Math.pow(a.getY()-b.getY(), 2));
	}
	

	public Color getColor() {
		return this.color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public int getStrokeHeight() {
		return strokeHeight;
	}
	public void setStrokeHeight(int strokeHeight) {
		this.strokeHeight = strokeHeight;
	}
	public void addRealPoint(Point p) {
		this.realPoints.add(p);
	}
	public void clearRealPoints() {
		this.realPoints.clear();
	}
	public void setRefPoints(ArrayList<Point>points) {
		this.refPoints = points;
	}
	public ArrayList<Point> getRefPoints(){
		return this.refPoints;
	}
	public ArrayList<Point> getRealPoints() {
	return realPoints;
}

}

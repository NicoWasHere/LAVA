import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Line extends Stroke{

	public Line(ArrayList<Point> points, Color color, int strokeWidth, int strokeHeight) {
		super(points, color, strokeWidth, strokeHeight);
		// TODO Auto-generated constructor stub
	}
	public Line (Point startPoint, Point endPoint, Color color, int size) {
		super(null, color, size, size);
		ArrayList<Point>refPoints = new ArrayList<Point>();
		if(startPoint.getX()>endPoint.getX()) {
			Point temp = startPoint;
			startPoint = endPoint;
			endPoint = temp;
		}
		refPoints.add(startPoint);
		refPoints.add(endPoint);
		this.setRefPoints(refPoints);
		findRealPoints();
	}

	@Override
	public void findRealPoints() {
		// TODO Auto-generated method stub
		int pointNumber = (int) Math.ceil(distance(this.getRefPoints().get(0),this.getRefPoints().get(1)));
		int xDist = (int) (this.getRefPoints().get(1).getX()-this.getRefPoints().get(0).getX());
		int yDist = (int) (this.getRefPoints().get(1).getY()-this.getRefPoints().get(0).getY());
		for(int i = 0; i<=pointNumber; i++) {
			if(pointNumber != 0) {
			addRealPoint(new Point((int)(this.getRefPoints().get(0).getX()+(xDist*i/pointNumber)),(int)(this.getRefPoints().get(0).getY()+(yDist*i/pointNumber))));
		}
		}
		
	}

	
}

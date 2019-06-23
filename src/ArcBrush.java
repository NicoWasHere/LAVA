import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class ArcBrush extends Brush{
	int smoothing;
	private final int defaultSmoothing = 5; 
	public ArcBrush(Color c, int s) {
		super(c, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Stroke newStroke(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		return new Arc(points,color,size);
	}

	@Override
	public boolean splitPoints(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		if(new Arc(points).getDistance()>smoothing) {
			return true;
		}
		 return false;
	}
}

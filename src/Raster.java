import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Raster extends Stroke{
	public Raster() {
		super();
	}
	public Raster(ArrayList<Point> points, Color color, int strokeSize) {
		super(points, color, strokeSize, strokeSize);
		findRealPoints();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void findRealPoints() {
		// TODO Auto-generated method stub
		for(Point p: getRefPoints()) {
			addRealPoint(p);
		}
	}

}

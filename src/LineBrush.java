import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class LineBrush extends Brush{

	public LineBrush(Color c, int s) {
		super(c, s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Stroke newStroke(ArrayList<Point> points) {
		// TODO Auto-generated method stud
		return new Line(points.get(0),points.get(points.size()-1),color,size);

	}

	@Override
	public boolean splitPoints(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		return false;
	}

}

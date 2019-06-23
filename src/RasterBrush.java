import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

public class RasterBrush extends Brush {
	
	

	public RasterBrush(Color c, int s) {
		super(c,s);
	}

	@Override
	public Stroke newStroke(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		return new Raster(points,color, size);
	}

	@Override
	public boolean splitPoints(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		return false;
	}
}

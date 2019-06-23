import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public abstract class Brush {

	public int smoothing = 5;

	protected int size = 10;
	protected Color color;
	public Brush() {}
	public Brush(Color c, int s) {
		this.size = s;
		this.color = c;
	}
	public void setSmoothing(int s) {
		this.smoothing = s;
	}
	public abstract Stroke newStroke(ArrayList<Point> points);
	public abstract boolean splitPoints(ArrayList<Point>points);

	public void setSize(int i) {
		// TODO Auto-generated method stub
		this.size = i;
	}
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		this.color = c;
	}
	public Color getColor() {
		return color;
	}
	public int getSize() {
		return size;
	}

}

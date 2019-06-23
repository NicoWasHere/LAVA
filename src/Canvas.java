import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas {
	private ArrayList<Stroke>lineObjects;
	private ArrayList<Stroke>fillObjects;
	private ArrayList<Point>points;
	private JPanel p;
	Brush currentBrush;
	Brush arcDraw = new ArcBrush(Color.BLACK,10);
	Brush lineTool = new LineBrush(Color.BLACK,10);
	Brush raster = new RasterBrush(Color.WHITE,10);
	int brushNum = 0;
	public Canvas () {
		points = new ArrayList<Point>();
		lineObjects = new ArrayList<Stroke>();
		fillObjects = new ArrayList<Stroke>();
		p = new JPanel(){
			{
				
				currentBrush = arcDraw;
				this.setBackground(Color.WHITE);
				addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
					}
					public void mouseReleased(MouseEvent e) {
						if(points.size()>2) {
							Stroke theStroke = currentBrush.newStroke(points);
							addStroke(theStroke);
							points.clear();
							repaint();
						}
					}
				});
				addMouseMotionListener(new MouseMotionAdapter() {
					public void mouseMoved(MouseEvent e) {
					}
					public void mouseDragged(MouseEvent e) {
						points.add(e.getPoint());
						repaint();
					}
				});

			}
		
			public void paint(Graphics g) {
				super.paint(g);
				Stroke currentStroke = null;
				
				for(Stroke stk:fillObjects) {
					g.setColor(stk.getColor());
					for(Point p: stk.getRealPoints()) {
						if(stk.getClass()==new Raster().getClass()) {
							g.fillRect((int)p.getX(), (int)p.getY(), stk.getStrokeWidth(), stk.getStrokeHeight());
						}
						else {
						g.fillOval((int)p.getX(), (int)p.getY(), stk.getStrokeWidth(), stk.getStrokeHeight());
						}
					}
				}
				if(brushNum == 3) {
					if(points.size()>2) {
						currentStroke = currentBrush.newStroke(points);
					currentStroke.findRealPoints();
					if (currentBrush.splitPoints(points)) {
						doSplitPoints();
					}
				
				
					g.setColor(currentStroke.getColor());
					for(Point p: currentStroke.getRealPoints()) {
						g.fillRect((int)p.getX(), (int)p.getY(), currentStroke.getStrokeWidth(), currentStroke.getStrokeHeight());
					}
					}
				}
				for(Stroke stk:lineObjects) {
					g.setColor(stk.getColor());
					for(Point p: stk.getRealPoints()) {
						if(stk.getClass()==new Raster().getClass()) {
							g.fillRect((int)p.getX(), (int)p.getY(), stk.getStrokeWidth(), stk.getStrokeHeight());
						}
						else {
						g.fillOval((int)p.getX(), (int)p.getY(), stk.getStrokeWidth(), stk.getStrokeHeight());
						}
					}
				
				}
				if(brushNum != 3) {
				if(points.size()>2) {
					currentStroke = currentBrush.newStroke(points);
				currentStroke.findRealPoints();
				if (currentBrush.splitPoints(points)) {
					doSplitPoints();
				}
			
			
				g.setColor(currentStroke.getColor());
				for(Point p: currentStroke.getRealPoints()) {
					g.fillRect((int)p.getX(), (int)p.getY(), currentStroke.getStrokeWidth(), currentStroke.getStrokeHeight());
				}
				}
			}
			}
			};
	}
	public void setBrush(int brush) {
		brushNum = brush;
		switch(brushNum) {
		case 0: currentBrush = arcDraw;
			break;
		case 1: currentBrush = lineTool;
		break;
		case 2:	raster.setColor(arcDraw.getColor());
			currentBrush = raster;
		break;
		case 3: raster.setColor(Color.WHITE);
			currentBrush = raster;
			break;
		case 4: raster.setColor(Color.RED);
			currentBrush = raster;
			break;
	}
	}
	
	public void setBrushColor(Color c) {
		arcDraw.setColor(c);
		lineTool.setColor(c);
		if(brushNum!=3) {
			raster.setColor(c);
		}
	}
	
	public void setSize(int s) {
		arcDraw.setSize(s);
		lineTool.setSize(s);
		raster.setSize(s);
	}
	
	public void setPanelSize(int a, int b) {
		p.setSize(a,b);
	}
	public void setPanelLocation(int a, int b) {
		p.setLocation(a, b);
	}
	public JPanel getPanel() {
		return p;
	}
	public void addStroke(Stroke stk) {
		switch(brushNum) {
		case 0: addLine(stk);
		break;
		case 1: addLine(stk);
		break;
	case 2:	addFill(stk);
	break;
	case 3: addFill(stk);
		break;
	case 4: deleteLines(stk);
	break; 
		}
	}
	public void addLine(Stroke stk) {
		this.lineObjects.add(stk);
	}
	public void addFill(Stroke stk) {
		this.fillObjects.add(stk);
	}
	public void deleteLines(Stroke stk) {
		boolean stopLoop = false;
		for(int j = 0; j<lineObjects.size();j++) {
			stopLoop = false;
		for(Point p: stk.getRealPoints()) {
			for(int i = 0; !stopLoop&&i<lineObjects.get(j).getRealPoints().size();i++) {
				if ((Math.abs(p.getX() - lineObjects.get(j).getRealPoints().get(i).getX())<=(stk.getStrokeWidth()))&&(Math.abs(p.getY() - lineObjects.get(j).getRealPoints().get(i).getY())<=(stk.getStrokeHeight()))) {
					lineObjects.remove(j);
					j--;
					stopLoop = true;
					
				}
			}
		}
		}
	}

	private void doSplitPoints() {
		ArrayList<Point>goodPoints = new ArrayList<Point>();
		for(int i = 0; i < points.size()-2;i++) {
			goodPoints.add(points.get(i));
		}
		ArrayList<Point>keepPoints = new ArrayList<Point>();
		for(int i = points.size()-4;i<points.size();i++) {
			if(i>=0) {
			keepPoints.add(points.get(i));
			}
		}
		points.clear();
		for(Point p: keepPoints){
			points.add(p);
		}
		Line l = new Line(goodPoints.get(goodPoints.size()-1),keepPoints.get(0),currentBrush.getColor(),currentBrush.getSize());
		l.findRealPoints();
		addStroke(currentBrush.newStroke(goodPoints));
		addStroke(l);
	}
	public void undo() {
		switch(brushNum) {
		case 0: if(lineObjects.size()!=0) {lineObjects.remove(lineObjects.size()-1);}
		break;
		case 1: if(lineObjects.size()!=0) {lineObjects.remove(lineObjects.size()-1);}
		break;
	case 2:	if(fillObjects.size()!=0) {fillObjects.remove(fillObjects.size()-1);}
	break;
	case 3: if(fillObjects.size()!=0) {fillObjects.remove(fillObjects.size()-1);}
		break;
	case 4:
		break;
	}
		p.repaint();
	}
}

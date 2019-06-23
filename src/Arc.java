import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Arc extends Stroke{
	double[] formula = new double[3];


	public Arc(ArrayList<Point>points) {
		super(points);
		quadtraticRegression(points);
		if(getRefPoints().size()>2) {
			findRealPoints();
		}
	}
	public Arc(ArrayList<Point> points,Color color, int size) {
		super(points, color, size);
		quadtraticRegression(points);
		if(getRefPoints().size()>2) {
			findRealPoints();
		}
	}

	private int getFormulaOutput(int input) {
		return getFormulaOutput(input, formula[0],formula[1],formula[2]);
	}
	private int getFormulaOutput(int input, double a, double b, double c) {
		return  (int) ((a*Math.pow((input),2)+b*Math.abs((input))+c));

	}

	private Point getPointWithShift(int i) {
		Point p = new Point(i,getFormulaOutput(i));
		p.setLocation((int)p.getX(),p.getY()-((getFormulaOutput((int) getRefPoints().get(0).getX()))-getRefPoints().get(0).getY()));
		return p;
	}

	private void quadtraticRegression(ArrayList<Point> points) {
		int n = points.size(); //count the number of vars
		double SumX = 0, SumY = 0, SumX2 = 0,SumX3 = 0,SumX4 = 0,SumXY = 0,SumX2Y = 0; //the sum vars
		for(Point p:points) {SumX+=p.getLocation().getX();}
		for(Point p:points) {SumY+=p.getLocation().getY();}
		for(Point p:points) {SumX2+=Math.pow(p.getLocation().getX(),2);}
		for(Point p:points) {SumX3+=Math.pow(p.getLocation().getX(),3);}
		for(Point p:points) {SumX4+=Math.pow(p.getLocation().getX(),4);}
		for(Point p:points) {SumXY+=p.getLocation().getX()*p.getLocation().getY();}
		for(Point p:points) {SumX2Y+=Math.pow(p.getLocation().getX(),2)*p.getLocation().getY();}

		double SumXX = (SumX2 - (Math.pow(SumX, 2)/n));
		SumXY = SumXY-(SumX*SumY)/n;
		double SumXX2 = SumX3-(SumX2*SumX)/n;
		SumX2Y = SumX2Y-(SumX2*SumY)/n;
		double SumX2X2 = SumX4 - (Math.pow(SumX2,2)/n);

		double a = ((SumX2Y*SumXX)-(SumXY*SumXX2))/((SumXX*SumX2X2)-Math.pow(SumXX2, 2));
		double b = ((SumXY*SumX2X2)-(SumX2Y*SumXX2))/((SumXX*SumX2X2)-Math.pow(SumXX2, 2));
		double c = (SumY/n)-(b*(SumX/n))-(a*(SumX2/n));
		this.formula[0] = a;
		this.formula[1] = b;
		this.formula[2] = c;
	}
	@Override
	public void findRealPoints() {
		// TODO Auto-generated method stub

		ArrayList<Point> tempReal = getRefPoints();
		if(tempReal.size()>1) {
		for(int i = (int) tempReal.get(getLeastPoint(tempReal)).getX();i<(int) tempReal.get(getGreatestPoint(tempReal)).getX();i++) {
			addRealPoint(getPointWithShift(i));
		}
		}
	}

	private int getGreatestPoint(ArrayList<Point>points) {
		int greatestPoint = 0;
		for(int i = 1; i<points.size();i++) {
			if (points.get(i).getX()>points.get(greatestPoint).getX()) {
				greatestPoint = i;
			}
		}
		return greatestPoint;
	}
	private int getLeastPoint(ArrayList<Point>points) {
		int leastPoint = 0;
		for(int i = 1; i<points.size();i++) {
			if (points.get(i).getX()<points.get(leastPoint).getX()) {
				leastPoint = i;
			}
		}
		return leastPoint;
	}
	
	public double getDistance(ArrayList<Point>points) {
		int distance = 0;
		for(Point p: points) {
			distance+=distance(p,new Point((int)p.getX(),getFormulaOutput((int)p.getX())));
		}
		return distance/points.size();
	}
	public double getDistance() {
		int distance = 0;
		for(Point p: getRefPoints()) {
			distance+=distance(p,new Point((int)p.getX(),getFormulaOutput((int)p.getX())));
		}
		return distance/getRefPoints().size();
	}



}

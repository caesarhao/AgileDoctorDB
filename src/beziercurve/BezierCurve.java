package beziercurve;

import java.util.ArrayList;
import java.util.List;
// http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/
public class BezierCurve {
	private List<Point> ctrlPoints = null;
	private int numOfControlPoints = 0;
	public static double Limitation = 1E-3;
	public BezierCurve(List<Point> ctrlPoints) throws BezierCurveException {
		if (null == ctrlPoints){
			throw new BezierCurveException("ctrlPoints can't be null.");
		}
		if (ctrlPoints.size() > 4 || ctrlPoints.size() < 2){
			throw new BezierCurveException("There must be 2, 3 or 4 control points.");
		}
		this.ctrlPoints = ctrlPoints;
		numOfControlPoints = this.ctrlPoints.size();
	}
	public BezierCurve(double[] ctrlXs, double[] ctrlYs){
		List<Point> ps = new ArrayList<Point>();
		for(int i = 0; i < ctrlXs.length; i++){
			ps.add(new Point(ctrlXs[i], ctrlYs[i]));
		}
		this.ctrlPoints = ps;
		numOfControlPoints = this.ctrlPoints.size();
	}
	public Point calcPointWithT(double t) throws BezierCurveException{
		Point p = null;
		if (t < 0 || t > 1){
			throw new BezierCurveException("t must be between 0 and 1.");
		}
		switch(numOfControlPoints){
		case 2:
			p = ctrlPoints.get(0).multiple(1-t);
			p = p.add(ctrlPoints.get(1).multiple(t));
			break;
		case 3:
			p = ctrlPoints.get(0).multiple(1-t).multiple(1-t);
			p = p.add(ctrlPoints.get(1).multiple(2).multiple(1-t).multiple(t));
			p = p.add(ctrlPoints.get(2).multiple(t).multiple(t));
			break;
		case 4:
			p = ctrlPoints.get(0).multiple(1-t).multiple(1-t).multiple(1-t);
			p = p.add(ctrlPoints.get(1).multiple(3).multiple(1-t).multiple(1-t).multiple(t));
			p = p.add(ctrlPoints.get(2).multiple(3).multiple(1-t).multiple(t).multiple(t));
			p = p.add(ctrlPoints.get(3).multiple(t).multiple(t).multiple(t));
			break;
		default:
			break;
		}
		return p;
	}
	public Point calcWithX(double destX){
		double left = 0;
		double right = 1;
		double middle = 0.5;
		Point p = null;
		while(Math.abs(right-left) > Limitation){
			middle = (left + right)/2;
			try {
				p = calcPointWithT(middle);
			} catch (BezierCurveException e) {
				e.printStackTrace();
			}
			if (Math.abs(p.x - destX) < Limitation){
				p.t = middle;
				break;
			}
			else if(destX < p.x){
				right = middle; //(middle + right)/2;
			}
			else{
				left = middle; //(middle + left)/2;
			}
		}
		if (Math.abs(right-left) <= Limitation){
			p.t = (left + right)/2;
		}
		return p;
	}
}

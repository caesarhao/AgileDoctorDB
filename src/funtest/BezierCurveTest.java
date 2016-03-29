package funtest;

import beziercurve.BezierCurve;
import beziercurve.BezierCurveException;
import beziercurve.Point;

public class BezierCurveTest {
	BezierCurve bCurve;
	public BezierCurveTest() {
		double[] ctlPXs = {0, 2, 2};
		double[] ctlPYs = {2, 2, 0};
		bCurve = new BezierCurve(ctlPXs, ctlPYs);
	}
	public static void main(String[] args) {
		BezierCurveTest test = new BezierCurveTest();
		try {
			System.out.println(test.bCurve.calcPointWithT(0.5));
			Point p = test.bCurve.calcWithX(1);
			System.out.println(p + "t: " + p.t);
		} catch (BezierCurveException e) {
			e.printStackTrace();
		}
	}
}

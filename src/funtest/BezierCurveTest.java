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
		bCurve.setBDebugMode(true);
	}
	public static void main(String[] args) {
		BezierCurveTest test = new BezierCurveTest();
		try {
			System.out.println(test.bCurve.calcPointWithT(0.5));
			Point p = null;
			for (double i = 0; i < 2.1; i += 0.1){
				p = test.bCurve.calcWithX(i);
				System.out.println(p);
			}
		} catch (BezierCurveException e) {
			e.printStackTrace();
		}
	}
}

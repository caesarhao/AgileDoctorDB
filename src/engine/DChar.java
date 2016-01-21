package engine;

import model.DoctorPhrase;

public class DChar {
	private DoctorPhrase dp = null;
	
	public double valEffTrust;
	public double valEffDist;
	public double valClarity;
	
	public DChar(DoctorPhrase pdp) {
		dp = pdp;
		calcFromDoctorPhrase();
	}
	private void calcFromDoctorPhrase(){
		//TODO: add code to generate DChar.
		valEffTrust = 5 - dp.getAggressiveLevel().ordinal();
		valEffDist = dp.getAggressiveLevel().ordinal();
		valClarity = dp.getClearLevel().ordinal();
	}
	
	public DoctorPhrase getDoctorPhrase() {
		return dp;
	}
	public boolean calcDropFlag(){
		//TODO: how to calc drop flag from doctor action.
		return true;
	}
}

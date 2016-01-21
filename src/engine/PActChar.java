package engine;

import model.PatientPhrase;

public class PActChar {
	public double valAggr;
	public double valLong;
	public PActChar() {
		valAggr = 0;
		valLong = 0;
	}
	public static PActChar generateFromPatientPhrase(PatientPhrase pp){
		PActChar pac = new PActChar();
		//TODO: add code to generate PActChar.
		pac.valAggr = pp.getAggressiveLevel().ordinal();
		pac.valLong = pp.getLongLevel().ordinal();
		return pac;
	}
}

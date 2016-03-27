package engine;

import jpa.JpaManager;
import model.*;

public class ScenarioParameter {
	// a three-dimension vector that represents patient's initial profile(0-1):
	// talkative, aggressive, chaotics.
	//coeff & initiative value
	public class PatientInitialProfile {
		public double coef_talktive;
		public double coef_aggressive;
		public double coef_chaotic;
		// initial value of talkative(0-100)
		public double init_talk;
		// initial value of aggressive(0-100)
		public double init_aggr;
		// initial value of disturbance(0-100)
		public double init_dist;
	}

	public DoctorActor doctor;
	public PatientActor patient;
	public PatientInitialProfile pTypeV;

	// initial value of trust(0-100)
	public double init_tr;

	// probability of not understanding the dialogue of the patient(0-1)
	public double probR;
	// probability of questioning by the patient(0-1)
	public double  probDU;

	
	// threshold of disturbance
	public double Threshold_Disturb;
	// threshold of aggressive
	public double Threshold_Aggr;
	public void setPatientParameter(double t, double a, double c, double initTalk,double initAggr, double initChao, double trustInit,double probDU, double probR){
		this.pTypeV.coef_talktive = t;
		this.pTypeV.coef_aggressive = a;
		this.pTypeV.coef_chaotic = c;
		this.pTypeV.init_talk = initTalk;
		this.pTypeV.init_aggr = initAggr;
		this.pTypeV.init_dist = initChao;
		

		this.init_tr = trustInit;
		this.probDU = probDU;
		this.probR = probR;
	
		
	}

	public ScenarioParameter() {
		pTypeV = new PatientInitialProfile();
		setPatientParameter(0.5,0.5,0.5,50,0,0,50,0.1,0.1);
		
		doctor = JpaManager.<DoctorActor>findAll("DoctorActor").get(0);
		patient = JpaManager.<PatientActor>findAll("PatientActor").get(0);
	/*	dist_init = 10;
		tr_init = 80;
		beta = 0.1;
		gamma = 0.5;

		Threshold_Disturb = 50;
		Threshold_Aggr = 50;*/

	}

	@Override
	public String toString() {
		String rt = "";
		rt += "--------------------------------------------------\n";
		rt += "Scenario Parameter:\n";
		rt += "* Patient talktive:\t" + pTypeV.init_talk + "\n";
		rt += "* Patient aggressive:\t" + pTypeV.coef_aggressive + "\n";
		rt += "* Patient chaotic:\t" + pTypeV.coef_chaotic + "\n";
		rt += "* Initial disturbance:\t" + pTypeV.init_aggr + "\n";
		rt += "* probability DU:\t" + probDU + "\n";
		rt += "* probability Refuse:\t" + probR+ "\n";
	//	rt += "* Threshold of disturbance:\t" + Threshold_Disturb + "\n";
	//	rt += "* Threshold of aggressive:\t" + Threshold_Aggr + "\n";
		rt += "--------------------------------------------------\n";
		return rt;
	}

}

package engine;

import jpa.JpaManager;
import model.*;

public class ScenarioParameter {
	// a three dimension vector that represents patient's initial profile(0-1):
	// talkative, aggressivem chaotics.
	public class PatientInitialProfile {
		public double talktive;
		public double aggressive;
		public double chaotic;
	}

	public DoctorActor doctor;
	public PatientActor patient;
	public PatientInitialProfile pTypeV;
	// initial value of disturbance(0-100)
	public double dist_init;
	// initial value of trust(0-100)
	public double tr_init;
	// probability of not understanding the dialogue of the patient(0-1)
	public double beta;
	// probability of questioning by the patient(0-1)
	public double gamma;
	// threshold of disturbance
	public double Threshold_Disturb;
	// threshold of aggressive
	public double Threshold_Aggr;

	public ScenarioParameter() {
		pTypeV = new PatientInitialProfile();
		pTypeV.talktive = 0.5;
		pTypeV.aggressive = 0.2;
		pTypeV.chaotic = 0.2;
		
		
		doctor = JpaManager.<DoctorActor>findAll("DoctorActor").get(0);
		patient = JpaManager.<PatientActor>findAll("PatientActor").get(0);
		dist_init = 10;
		tr_init = 80;
		beta = 0.1;
		gamma = 0.5;

		Threshold_Disturb = 50;
		Threshold_Aggr = 50;

	}

	@Override
	public String toString() {
		String rt = "";
		rt += "--------------------------------------------------\n";
		rt += "Scenario Parameter:\n";
		rt += "* Patient talktive:\t" + pTypeV.talktive + "\n";
		rt += "* Patient aggressive:\t" + pTypeV.aggressive + "\n";
		rt += "* Patient chaotic:\t" + pTypeV.chaotic + "\n";
		rt += "* Initial disturbance:\t" + dist_init + "\n";
		rt += "* beta:		\t" + beta + "\n";
		rt += "* gamma:		\t" + gamma + "\n";
		rt += "* Threshold of disturbance:\t" + Threshold_Disturb + "\n";
		rt += "* Threshold of aggressive:\t" + Threshold_Aggr + "\n";
		rt += "--------------------------------------------------\n";
		return rt;
	}

}

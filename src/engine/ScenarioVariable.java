package engine;
import java.util.*;

import model.APatientInformation;

public class ScenarioVariable {
	// List of already got Patient Information.
	public List<APatientInformation> sGotPatientInfo = new ArrayList<APatientInformation>();
	
	// ScenarioParameter
	public ScenarioParameter scPara;
	// number of actions effected by the doctor.
	public int n;
	// confidence value of the patient towards the doctor,
	// pt_Trust = tr_init + sum(dActEffTi)
	public double pt_Trust;
	// disturbance value of the patient.
	// pt_Dist = dist_init + 10*n*c + sum(dActEffDi)
	public double pt_Dist;
	// aggressive value of the patient.
	// pt_Aggr = (1+a)(pt_Dist+(100-pt_Trust))/2
	public double pt_Aggr;
	// boolean status of disturbance
	public boolean stat_Dist;
	// boolean status of aggressive
	public boolean stat_Aggr;
	
	// dialogue state.
	public DialogueState dialSt = DialogueState.N;
	// dialogue old state.
	public DialogueState oldDialSt = DialogueState.N;

	public ScenarioVariable(ScenarioParameter p_scPara) {
		scPara = p_scPara;
		n = 0;
		pt_Trust = scPara.tr_init;
		pt_Dist = scPara.dist_init;
		pt_Aggr = scPara.pTypeV.aggressive;
		stat_Dist = pt_Dist > scPara.Threshold_Disturb;
		stat_Aggr = pt_Aggr > scPara.Threshold_Aggr;
	}

	// calculate once as per the Doctor action.
	public void calcOnce(double dActEffTi, double dActEffDi) {
		n++;
		pt_Trust += dActEffTi;
		pt_Dist += (10 * n * scPara.pTypeV.chaotic + dActEffDi);
		pt_Aggr += ((pt_Dist + (100 - pt_Trust)) / 2);
		stat_Dist = pt_Dist > scPara.Threshold_Disturb;
		stat_Aggr = pt_Aggr > scPara.Threshold_Aggr;
		
		oldDialSt = dialSt;
		//TODO: How to calculate new state?
		dialSt = DialogueState.N;
		
		// check state, same state except normal state cumulate for twice.
		if ((oldDialSt == dialSt) && DialogueState.N != dialSt){
			dialSt = DialogueState.END;
		}
		
		//TODO: same information required twice by the doctor, should calculate based on patient info.
		if(sGotPatientInfo.contains(null)){
			oldDialSt = dialSt;
			dialSt = DialogueState.R;
		}
	}

	@Override
	public String toString() {
		String rt = "";
		rt += "--------------------------------------------------\n";
		rt += "Scenario Variable:\n";
		rt += "* Doctor actions' number:\t" + n + "\n";
		rt += "* Patient's trust:	\t" + pt_Trust + "\n";
		rt += "* Patient's Disturbance:\t" + pt_Dist + "\n";
		rt += "* Patient's aggressive:	\t" + pt_Aggr + "\n";
		rt += "* State of disturbance:	\t" + stat_Dist + "\n";
		rt += "* State of aggressive:	\t" + stat_Aggr + "\n";
		rt += "--------------------------------------------------\n";
		return rt;
	}
}

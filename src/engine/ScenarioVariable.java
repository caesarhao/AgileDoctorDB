package engine;
import java.util.*;

import model.*;

public class ScenarioVariable {
	// List of already got Patient Information.
	public List<APatientInformation> sGotPatientInfoByQuery = new ArrayList<APatientInformation>();  // got by query
	public List<APatientInformation> sGotPatientInfoByDefault = new ArrayList<APatientInformation>(); // got by consulting to files
	public List<APatientInformation> sGotPatientInfoAllRoot = new ArrayList<APatientInformation>(); // all got info
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
	
	// boolean state of session Don't Understand
	public double p_DU;
	public double p_AbNor;
	
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

	public void calcOnce(DoctorPhrase dp) {
		Random random1 = new Random();
	//	Random random2 = new Random();
		int num = 0;
		num = random1.nextInt(101);
	//	System.out.println("got random"+num);
		n++;
		pt_Trust += dp.getEffTrust();
		pt_Dist += (10 * n * scPara.pTypeV.chaotic + dp.getEffDisturbance());
		pt_Aggr += ((pt_Dist + (100 - pt_Trust)) / 2);
		stat_Dist = pt_Dist > scPara.Threshold_Disturb;
		stat_Aggr = pt_Aggr > scPara.Threshold_Aggr;
		
		oldDialSt = dialSt;
		
		// calculate State of session
		
		//enter into DUnderstand?
		int bvarDist = stat_Dist?1:0;
		int bvarAggr = stat_Aggr?1:0;
		
    	p_DU = (bvarDist * 0.5 + 1) * scPara.beta /(dp.getvalClarity() + 0.0001);
    	p_AbNor = (bvarAggr*0.2+1)*(1-1.1*Math.pow((pt_Trust/100), 1/3));
    	//normalisation
    	if(p_DU<0) p_DU=0.0;
    	if(p_DU>1.0) p_DU=1.0;

    	if(p_AbNor<0) p_AbNor=0.0;
    	if(p_AbNor>1.0) p_AbNor=1.0;
    	double num_percent = num/100.0;
    	
    	double num_percent2 = random1.nextInt(101)/100.0;
    //	System.out.println("probability DU:"+ p_DU+", rand value:"+num_percent);
    	if(num_percent < p_DU) {
    		dialSt = DialogueState.DU;
    		
    	}
    	
    	// enter into Questionnement or Refuse? 

    	else if(num_percent2 < p_AbNor) {
        	double num_percent3 = random1.nextInt(101)/100.0;
        	System.out.println("probability AbNor:"+ p_AbNor+", rand value:"+num_percent2);
    		if(num_percent3 < scPara.gamma) dialSt = DialogueState.Q;
    		else dialSt = DialogueState.R;
    		
    	}
    	
    	// enter State Normal
    	else{
    		dialSt = DialogueState.N;
    		
    	}
		
		

		
		// check state, same state except normal state cumulate for twice.
		if ((oldDialSt == dialSt) && DialogueState.N != dialSt){
			dialSt = DialogueState.END;
		}
		
		//TODO: same information required twice by the doctor, should calculate based on patient info.
		if(sGotPatientInfoByQuery.contains(null)){
			oldDialSt = dialSt;
			dialSt = DialogueState.R;
		}
	}
	
	// argument pp: PatientPhrase is preserved for additional usage.
	public void calcOnce(DoctorPhrase dp, PatientPhrase pp) {
		
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

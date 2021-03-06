package engine;
import java.util.*;

import model.*;

public class ScenarioVariable {
	// List of already got Patient Information.
	public List<APatientInformation> sGotPatientInfo = new ArrayList<APatientInformation>();  // got
	public List<APatientInformation> sGotPatientInfoByQuery = new ArrayList<APatientInformation>();  // got by query
	public List<APatientInformation> sGotPatientInfoByDefault = new ArrayList<APatientInformation>(); // got by consulting to files
	public List<APatientInformation> sGotPatientInfoAllRoot = new ArrayList<APatientInformation>(); // all got info
	// ScenarioParameter
	public String myRes = "";
	public ScenarioParameter scPara;
	// number of actions effected by the doctor.
	public int n;
	
	// confidence value of the patient towards the doctor,
	// pt_Trust = init_tr + sum(dActEffTi)
	// 0-100
	public double pt_Trust;
	
	// disturbance value of the patient.
	// pt_Dist = init_dist + 10*n*c + sum(dActEffDist)
	// 0-100
	public double pt_Dist;
	
	// aggressive value of the patient.
	// pt_Aggr = init_aggr+(1+coeff_aggr)sum(dActEffAggr)
	// 0-100
	public double pt_Aggr;
	
	//talkative value of the patient
	//TODO change formule
	//pt_Talk = init_talk
	public double pt_Talk;
	
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
		initVariables();
	}
	
	// Should be call again, if scPara changes.
	public void initVariables(){
		n = 0;
		pt_Trust = scPara.init_tr;
		pt_Dist = scPara.pTypeV.init_dist;
		pt_Aggr = scPara.pTypeV.init_aggr;
		pt_Talk = scPara.pTypeV.init_talk;

		//pt_Aggr = scPara.pTypeV.aggressive;
		//TODO error here
	}
	
	// the range stays in [0,100]
	public double normalisation(double number){
		double result;
		if(number < 0){
			result = 0;
		}
		else if (number > 100){
			result = 100;
		}
		else result = number;
		return result;
	}

	// calculate once as per the Doctor action.

	public void calcOnce(DoctorPhrase dp) {
		Random random1 = new Random();
	//	Random random2 = new Random();
		int num = 0;
		num = random1.nextInt(101);
	//	System.out.println("got disturbance"+pt_Dist);
		n++;

		
		pt_Trust += dp.getEffTrust();
		pt_Dist += (1+ scPara.pTypeV.coef_chaotic)* dp.getEffDisturbance();

		// Notice: aggr doesn't have intial value defined; it's based on trust and disturbance
		
		pt_Aggr += dp.getEffAggr()*(1+scPara.pTypeV.coef_aggressive);
		
		pt_Dist = normalisation(pt_Dist);
		pt_Trust = normalisation(pt_Trust);
		pt_Aggr = normalisation(pt_Aggr);
		
		//stat_Dist = pt_Dist > scPara.Threshold_Disturb;
		//stat_Aggr = pt_Aggr > scPara.Threshold_Aggr;
	//	System.out.println("pt_aggr:"+pt_Aggr+", dp eff:"+dp.getEffAggr());
		oldDialSt = dialSt;
		
		// calculate State of session
		
		//enter into DUnderstand?
	//	int bvarDist = stat_Dist?1:0;
	//	int bvarAggr = stat_Aggr?1:0;
		
    //	p_DU = (bvarDist * 0.5 + 1) * scPara.probDU * (1/(dp.getvalClarity() + 0.0001))-1;
    //	p_AbNor = (bvarAggr*0.2+1)*(1-1.1*Math.pow((pt_Trust/100), 1/3));
		p_DU = (0.2+pt_Dist/100)*scPara.probDU;
	//	p_DU = pt_Dist/100*scPara.probDU+1-pt_Dist/100;
		p_DU += dp.getEffDisturbance()/60;
		
	
		//	p_DU = pt_Dist/100*(scPara.probDU+100/pt_Dist-1);
		
		double t = pt_Trust/100;
		//double b_t = p0*Math.pow((1-t),3)+3p1*t*Math.pow((1-t),2)+3p2*Math.pow(t,2)*(1-t)+p3*Math.pow(t,3);
		//p_AbNor=pt_Aggr*(b_t+100/pt_Aggr-1);
		
		//temp:
		if(pt_Trust>80) {
			p_AbNor = 0;
		}
		if(pt_Trust>=60 && pt_Trust<=80) {
			p_AbNor = 0.1;
		}
		if(pt_Trust<60 && pt_Trust>=30) {
			p_AbNor = 0.2;
		}
		if(pt_Trust<30) {
			p_AbNor = 0.4;
		}
    	//normalisation
    	if(p_DU<0.1) p_DU=0.1;
    	if(p_DU>0.9) p_DU=0.9;

    	if(p_AbNor<0.1) p_AbNor=0.1;
    	if(p_AbNor>0.9) p_AbNor=0.9;
    	double num_percent = (double)num/100.0;
    
    	double num_percent2 = random1.nextInt(101)/100.0;
    	//System.out.println(scPara.toString());
    	
    //	System.out.println("probability Abnor:"+p_AbNor+"probability DU:"+ p_DU);
    	
    	if(num_percent < p_DU) {
    		dialSt = DialogueState.DU;
    		
    	}
    	
    	// enter into Questionnement or Refuse? 

    	else if(num_percent2 < p_AbNor) {
        	double num_percent3 = random1.nextInt(101)/100.0;
        	System.out.println("probability AbNor:"+ p_AbNor+", rand value:"+num_percent2);
    		if(num_percent3 < scPara.probR) dialSt = DialogueState.Q;
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
		myRes += outputCSVresult();
	}
	public String getResOneByOne(){
		return this.myRes;
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
	
	public String outputCSVtitle(){
		String result = "";
		result += "Doctor's Actions";
		result += ", ";
		result += "pt_Trust";
		result += ", ";
		result += "pt_Dist";
		result += ", ";
		result += "pt_Aggr";
		result += ", ";
		result += "pt_Talk";
		result += ", ";	
		result += "dialStatus";
		return result;
	}

	public String outputCSVresult(){
		String result = "";
		result += this.n;
		result += ", ";
		result += this.pt_Trust;
		result += ", ";
		result += this.pt_Dist;
		result += ", ";
		result += this.pt_Aggr;
		result += ", ";
		result += this.pt_Talk;
		result += ", ";
		result += this.dialSt;
		result += "\n";
		return result;
	}
	public String outputPStatusResult(){
		String result = "";
		result += this.n;
		result += ", ";
		result += this.pt_Trust;
		result += ", ";
		result += this.pt_Dist;
		result += ", ";
		result += this.pt_Aggr;	
		result += ", ";
		result += this.pt_Talk;
		result += "\n";
		return result;
	}
	public String outputPStatusTitle(){
		String result = "";
		result += "DActions Nb";
		result += ", ";
		result += "pt_Trust";
		result += ", ";
		result += "pt_Dist";
		result += ", ";
		result += "pt_Aggr";
		result += ", ";
		result += "pt_Talk";
		result += "\n";
		return result;
	}
	
}

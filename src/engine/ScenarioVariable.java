package engine;

public class ScenarioVariable {
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
		// TODO: What does the C mean??
		pt_Dist += (10 * n * 1/* c */ + dActEffDi);
		pt_Aggr += ((pt_Dist + (100 - pt_Trust)) / 2);
		stat_Dist = pt_Dist > scPara.Threshold_Disturb;
		stat_Aggr = pt_Aggr > scPara.Threshold_Aggr;
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

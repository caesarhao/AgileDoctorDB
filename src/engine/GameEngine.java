package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jpa.JpaManager;
import model.*;


public class GameEngine {
	private ScenarioParameter scPara;
	private ScenarioVariable scVar;
	

	//
	public GameEngine() {
		scPara = new ScenarioParameter();
		// System.out.println(scPara);
		scVar = new ScenarioVariable(scPara);
		// System.out.println(scVar);
	}

	public ScenarioParameter getScPara() {
		return scPara;
	}

	public ScenarioVariable getScVar() {
		return scVar;
	}
	
	public PatientPhrase getBestPatientPhrase(List<PatientPhrase> pps){
		PatientPhrase rpp = null;
	
		double minDistance = 999999999;
		double currentDistance = 0;
		for (PatientPhrase pp: pps){
			// to modify pTypeV factor in the formula
			currentDistance = Math.pow(pp.getAggressiveLevel().ordinal()*50 - scVar.pt_Aggr, 2)
			+ Math.pow((100-pp.getClearLevel().ordinal()*100 - scVar.pt_Dist), 2)
			+ Math.pow(pp.getLongLevel().ordinal()*50 - scPara.pTypeV.talktive*100, 2);
			if(currentDistance<minDistance){
				minDistance=currentDistance;
				rpp = pp;
			}
		}
		return rpp;
	}
	
	public void simulateBest(){
		List<DialogueSession> selectedDSs = new ArrayList<DialogueSession>();
		DoctorPhrase bestDChoice = null;
		
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "Welcome");
		List<MicroSequence> mss = JpaManager.<MicroSequence>findWithNamedQuery(namedQuery, queryParams);

		for(DialogueSession ds: mss.get(0).getDialogueSessions()){
			List<Pair> pairs = ds.getPairs();
			for (Pair pair: pairs){
				double eff_high = 0;
				double eff_curr = 0;
				bestDChoice = null;
				for (DoctorPhrase dp : pair.getPossibleDoctorPhrases()) {
					eff_curr = dp.effTrust - dp.effDisturbance;
					if (eff_curr > eff_high) {
						eff_high = eff_curr;
						bestDChoice = dp;
					}
				}
				if(eff_high>0 || 1 > pair.getPossibleDoctorPhrases().size()) {
					selectedDSs.add(ds);
					if (null != bestDChoice){
						System.out.println("Doctor "+bestDChoice.getPhraseActor().getName()+": "+bestDChoice.getExpression());
						
						//update system variables after selection of doctor.
						scVar.calcOnce(bestDChoice);
					}
					//Get corresponding patient phrase.
					PatientPhrase bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
					System.out.println("Patient " + bestPP.getPhraseActor() + ": "+bestPP.getExpression());
						
				}
			}
		}
	}
	public void simulateWorst(){
	
		DoctorPhrase worstDChoice = null;
		
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "Welcome");
		List<MicroSequence> mss = JpaManager.<MicroSequence>findWithNamedQuery(namedQuery, queryParams);

		for(DialogueSession ds: mss.get(0).getDialogueSessions()){
			List<Pair> pairs = ds.getPairs();
			for (Pair pair: pairs){
				double eff_low = 10000;
				double eff_curr = 0;
				worstDChoice = null;
				for (DoctorPhrase dp : pair.getPossibleDoctorPhrases()) {
					eff_curr = dp.effTrust - dp.effDisturbance;
					if (eff_curr <= eff_low) {
						eff_low = eff_curr;
						worstDChoice = dp;
					}
				}
				
				if (null != worstDChoice){
					System.out.println("Doctor "+worstDChoice.getPhraseActor().getName()+": "+worstDChoice.getExpression());
					
					//update system variables after selection of doctor.
					scVar.calcOnce(worstDChoice);
				}
				//Get corresponding patient phrase.
				PatientPhrase bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
				System.out.println("Patient " + bestPP.getPhraseActor() + ": "+bestPP.getExpression());
						
			}
		}
		
	}


	public void simulate_old() {
		int randNum = 0;
		APatientInformation currentInfo = null;
		List<APatientInformation> lPInfo = null;
		Random rand = new Random();
		String namedQuery = "APatientInformation.findBySuperInformation";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		System.out.println("Initial Parameters:");
		System.out.println(scPara);
		System.out.println(scVar);
		System.out.println("Simulation:");
		List<APatientInformation> lPAllInfo = JpaManager.<APatientInformation>findAll("APatientInformation");
	
		while (true) {
			if (null == currentInfo){
				
				lPInfo = JpaManager.findWithNamedQuery("APatientInformation.findSuperInformationNull", null);
			}
			else{
				queryParams.clear();
				queryParams.put("superInformation", currentInfo);
				lPInfo = JpaManager.findWithNamedQuery(namedQuery, queryParams);
				if (1 > lPInfo.size()){
					currentInfo = null;
					continue;
				}
			}
			
			randNum = rand.nextInt(lPInfo.size());
		
			currentInfo = lPInfo.get(randNum);
			if (scVar.sGotPatientInfo.contains(currentInfo)){// it's already got
				continue;
			}
			// get possible doctor phrases.
			List<DoctorPhrase> dps = new ArrayList<DoctorPhrase>(currentInfo.getPossibleAskPhrases());
			randNum = rand.nextInt(dps.size());
			DoctorPhrase dp = dps.get(randNum);
			System.out.println("Doctor "+dp.getPhraseActor().getName()+": "+dp.getExpression());
			
			
			//update system variables after selection of doctor.
			scVar.calcOnce(dp);					
			System.out.println("now clarity is "+dp.getvalClarity());
			System.out.println("now state is "+scVar.dialSt.toString());
			
			// Prepare possible patient phrases.
			List<PatientPhrase> pps = new ArrayList<PatientPhrase>(currentInfo.getPossibleResponsePhrases());  // all phrases
			List<PatientPhrase> pps_n = new ArrayList<PatientPhrase>();  // Normal phrases
			List<PatientPhrase> pps_du = new ArrayList<PatientPhrase>();  // don'tUnderstand phrases
			List<PatientPhrase> pps_q = new ArrayList<PatientPhrase>();  // questioning phrases
			List<PatientPhrase> pps_r = new ArrayList<PatientPhrase>();  // refuse phrases

			for(PatientPhrase ps:pps){
	
				if(ps.getPrimitiveType().toString().equals("DontUnderstand")) pps_du.add(ps);
				else if(ps.getPrimitiveType().toString().equals("Disagree")) pps_q.add(ps);
				else if(ps.getPrimitiveType().toString().equals("Questioning")) pps_r.add(ps);
				else pps_n.add(ps);
				
			}
			PatientPhrase pp_0=pps.get(0);
			PatientPhrase pp;
			// get possible patient phrase
			switch (scVar.dialSt){
			case DU:  
				if(pps_du.size()>0){   //has customer DU phrases
					randNum = rand.nextInt(pps_du.size());
					pp = pps_du.get(randNum);
				}
				else{
					pp = new PatientPhrase("I don't know what do you mean.",pp_0.phraseActor);
				}
				break;
			case Q:  
				if(pps_q.size()>0){
					randNum = rand.nextInt(pps_q.size());
					pp = pps_q.get(randNum);
				}
				else{
					pp = new PatientPhrase("Why do you ask?",pp_0.phraseActor);
				}
				break;
			case R:  
				if(pps_r.size()>0){
					randNum = rand.nextInt(pps_r.size());
					pp = pps_r.get(randNum);
				}
				else{
					pp = new PatientPhrase("I don't want to talk about it.",pp_0.phraseActor);
				}
				break;
			case N:  
				if(pps_n.size()>0){
					randNum = rand.nextInt(pps_n.size());
					pp = pps_n.get(randNum);
					
				}
				else{
					pp = new PatientPhrase("Well, ok...",pp_0.phraseActor);
				}
				scVar.sGotPatientInfo.add(currentInfo);
				break;
			default:
				if(pps_n.size()>0){
					randNum = rand.nextInt(pps_n.size());
					pp = pps_n.get(randNum);
					
				}
				else{
					pp = new PatientPhrase("Well, ok...",pp_0.phraseActor);
				}
				break;
					
			}
			
			//TODO: should select patient phrase by algorithm instead of random
			
			System.out.println(pp.getPhraseActor().getName()+": "+pp.getExpression());
		

			
			//scVar.sGotPatientInfo.add(currentInfo);    //should be got only when state is normal
			
			if (scVar.sGotPatientInfo.size() >= lPAllInfo.size()){
				break;
			}
			else if (DialogueState.END == scVar.dialSt) { // game over.
				break;
			}
			else{
				continue;
			}
		}
		System.out.println("\n After simulation:");
		System.out.println(scPara);
		System.out.println(scVar);
		
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		// set parameter of game engine.
		ge.getScPara().tr_init = 20;
		System.out.println("********Best result**********");
		ge.simulateBest();
		System.out.println("********Worst result**********");
		ge.simulateWorst();
		/* run 3 times
		for(int i =0; i<3;i++){
		ge.simulate();
		}*/
	}

}

package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jpa.JpaManager;
import model.APatientInformation;
import model.DoctorPhrase;
import model.PatientPhrase;

public class GameEngine {
	private ScenarioParameter scPara;
	private ScenarioVariable scVar;

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

	public void simulate() {
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
		// TODO; Simulation process.
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
			System.out.println(dp.getPhraseActor().getName()+": "+dp.getExpression());
			List<PatientPhrase> pps = new ArrayList<PatientPhrase>(currentInfo.getPossibleResponsePhrases());
			randNum = rand.nextInt(dps.size());
			PatientPhrase pp = pps.get(randNum);
			System.out.println(pp.getPhraseActor().getName()+": "+pp.getExpression());
			scVar.calcOnce(2, 3);
			scVar.sGotPatientInfo.add(currentInfo);
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
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		// set parameter of game engine.
		ge.getScPara().tr_init = 20;
		ge.simulate();
	}

}

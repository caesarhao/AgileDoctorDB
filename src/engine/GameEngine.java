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
	
	DoctorPhrase lastDoctorPhrase = null;
	public void processPairWithBestStrategy(Pair pair) {
		DoctorPhrase bestDChoice = null;

		// Get corresponding patient phrase.
		PatientPhrase bestPP = null;
		boolean flgInitByPatient = false;
		double eff_high = 0;
		double eff_curr = 0;
		flgInitByPatient = (pair.getPossibleDoctorPhrases().size() > 0)?false:true;
		for (DoctorPhrase dp : pair.getPossibleDoctorPhrases()) {
			eff_curr = dp.effTrust - dp.effDisturbance;
			if (eff_curr > eff_high) {
				eff_high = eff_curr;
				bestDChoice = dp;
			}
		}
		if (eff_high > 0 && !flgInitByPatient) {
			
			if (null != bestDChoice) {
				if (bestDChoice == lastDoctorPhrase){
					System.out.println(
							"Doctor " + bestDChoice.getPhraseActor().getName() + ": Je veux dire que " + bestDChoice.getExpression());

				}
				else{
					System.out.println(
							"Doctor " + bestDChoice.getPhraseActor().getName() + ": " + bestDChoice.getExpression());

				}
				
				// update system variables after selection of doctor.
				scVar.calcOnce(bestDChoice);
			}
			System.out.println(scVar.dialSt+"+++++");
			lastDoctorPhrase = bestDChoice;
			switch (scVar.dialSt) {
			case N:
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Statement));
				// getBestPatientPhrase(pair.getPossiblePatientPhrases());
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": ...");
				} else
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				break;
			case DU:
				bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						APhrase.PrimitiveType.DontUnderstand));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
				} else{
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				}
				processPairWithBestStrategy(pair);
				break;
			case Q:
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Questioning));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
				} else
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				break;
			case R:
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Disagree));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Je ne veux plus parler de ça...");
				} else
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				break;
			case END:
				System.out.println("Doctor " + scPara.doctor + ": OK, on parle plus sur ça.");
				break;
			default:
				System.out.println("Patient " + scPara.patient + ": Au revoir");
				break;

			}

		}
		else{// initialized by patient
			bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
			System.out.println("Patient " + scPara.patient + ": "+bestPP.getExpression());
		}
		

	}
	
	public void simulateBest(){
		List<DialogueSession> selectedDSs = new ArrayList<DialogueSession>();
		
		
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "Welcome");
		List<MicroSequence> mss = JpaManager.<MicroSequence>findWithNamedQuery(namedQuery, queryParams);

		for(DialogueSession ds: mss.get(0).getDialogueSessions()){
			List<Pair> pairs = ds.getPairs();
			for (Pair pair: pairs){
				processPairWithBestStrategy(pair);
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

	public List<DoctorPhrase> getDPhrasesInPairByType(List<DoctorPhrase> ps, APhrase.PrimitiveType pType){
		if (null == pType){
			return ps;
		}
		List<DoctorPhrase> pps = new ArrayList<DoctorPhrase>();
		for(DoctorPhrase ap:ps){
			if(ap.getPrimitiveType().equals(pType)){
				pps.add(ap);
			}
		}
		return pps;
	}
	
	public List<DoctorPhrase> getDPhrasesInPairByTypeS(List<DoctorPhrase> ps, List<APhrase.PrimitiveType> pTypes){
		if (null == pTypes || 0 == pTypes.size()){
			return ps;
		}
		List<DoctorPhrase> pps = new ArrayList<DoctorPhrase>();
		for(APhrase.PrimitiveType type:pTypes){
			pps.addAll(getDPhrasesInPairByType(ps, type));
		}
		return pps;
	}
	
	public List<PatientPhrase> getPPhrasesInPairByType(List<PatientPhrase> ps, APhrase.PrimitiveType pType){
		if (null == pType){
			return ps;
		}
		List<PatientPhrase> pps = new ArrayList<PatientPhrase>();
		for(PatientPhrase ap:ps){
			if(ap.getPrimitiveType().equals(pType)) pps.add(ap);
		}
		return pps;
	}
	
	public List<PatientPhrase> getPPhrasesInPairByTypeS(List<PatientPhrase> ps, List<APhrase.PrimitiveType> pTypes){
		if (null == pTypes || 0 == pTypes.size()){
			return ps;
		}
		List<PatientPhrase> pps = new ArrayList<PatientPhrase>();
		for(APhrase.PrimitiveType type:pTypes){
			pps.addAll(getPPhrasesInPairByType(ps, type));
		}
		return pps;
	}
	// information tree based strategy simulation: random select to find all nodes
	public void simulateInfoBased(){
		int randNum = 0;
		FamilyInformation currentInfo = null;
		List<FamilyInformation> lPInfo = null;
				
		Random rand = new Random();
		String namedQuery = "FamilyInformation.findBySuperInformation";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		List<FamilyInformation> lPAllInfo = JpaManager.<FamilyInformation>findAll("FamilyInformation");
	
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
			if (scVar.sGotPatientInfoByQuery.contains(currentInfo)){// it's already got
				continue;
			}
			PatientPhrase bestPP = null;
			//get Pairs in Info node
			List<Pair> pairs = new ArrayList<Pair>(currentInfo.getPairs());
			for(Pair pair:pairs){
				List<DoctorPhrase> dps = new ArrayList<DoctorPhrase>(pair.getPossibleDoctorPhrases());
				
				// has Doctor Phrase in the pair
				if(0 < dps.size()){
					randNum = rand.nextInt(dps.size());
					DoctorPhrase dp = dps.get(randNum);
					System.out.println("Doctor "+dp.getPhraseActor().getName()+": "+dp.getExpression());
					
					
					//update system variables after selection of doctor.
					scVar.calcOnce(dp);		
					

					switch (scVar.dialSt) {
					case N:
						// no info, find in type Confirmation.
						if(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.AnswerWithInfo).size()<1){
							bestPP = getBestPatientPhrase(
									getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Confirmation));
				
						}
						else{
							bestPP = getBestPatientPhrase(
									getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.AnswerWithInfo));
								
							
						}
						if(bestPP == null){
							System.out.println("Patient " + scPara.patient + ": ...(Warning: no data)");
						}
						else{
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
							scVar.sGotPatientInfoByQuery.add(currentInfo);
						}
						
						break;
					case DU:
						bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.DontUnderstand));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
						} else{
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						}
						break;
					case Q:
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Questioning));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case R:
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Disagree));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je ne veux plus parler de ça...");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case END:
						System.out.println("Doctor " + scPara.doctor + ": OK, on parle plus sur ça.");
						break;
					default:
						System.out.println("Patient " + scPara.patient + ": Au revoir");
						break;
					}
					
				}
				else{
					
					bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
					System.out.println("Patient " + bestPP.getPhraseActor() + ": "+bestPP.getExpression());
							
				}
		
											
			}

			
			if (scVar.sGotPatientInfoByQuery.size() >= lPAllInfo.size()){
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
	public void setKnownNode(){
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "AskReason");
		List<MicroSequence> mss = JpaManager.<MicroSequence>findWithNamedQuery(namedQuery, queryParams);
		List<MedicalInformation> minfos= mss.get(0).getMedicalInfos();
		// Cheat info: MI1 known, MI2 unkown.
		for(MedicalInformation info:minfos){
			if(info.getName().equalsIgnoreCase("ConsultReason1")){
				scVar.sGotPatientInfoByDefault.add(info);
				scVar.sGotPatientInfoAllRoot.add(info);
			}
		}

		
		
	}
	
	public void simulateMedicalInfoBasedTest(){
		//setKnownNode();
		int randNum = 0;
		//boolean hasUnknownNode = false;
		int closeModeCounter = 0;
		boolean isCloseQMode = false;
		Random rand = new Random();
	
		List<MedicalInformation> acquiredNodes = new ArrayList<MedicalInformation>();
		MedicalInformation currentInfo = null;
		// List<MedicalInformation> lPInfo = new ArrayList<MedicalInformation>();
		List<MedicalInformation> lPAllInfo = null;
		
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "AskReason");
		List<MicroSequence> mss = JpaManager.<MicroSequence>findWithNamedQuery(namedQuery, queryParams);
		lPAllInfo= mss.get(0).getMedicalInfos();
		
		while (true) {
			/*
			if (null == currentInfo){
				for(MedicalInformation info:lPAllInfo){
					if(info.superInformation == null){  // add all root nodes
						lPInfo.add(info);
					}
				}
				if(scVar.sGotPatientInfoAllRoot.size()<lPInfo.size()){   //not all root nodes have been known
					hasUnknownNode = true;
				}
			}
			else{
				for(MedicalInformation info:lPAllInfo){
					if(info.superInformation == currentInfo){  // add all children nodes of current info
						lPInfo.add(info);
					}
				}
				if (1 > lPInfo.size()){   // don't have child node, is a leaf node, end the parcours of this branch
					currentInfo = null;
					continue;
				}
				
			}
			*/
			
			// process the node: currentInfo
			/*
			randNum = rand.nextInt(lPInfo.size());
			currentInfo = lPInfo.get(randNum);

			if(scVar.sGotPatientInfoByQuery.contains(currentInfo)){
				continue;
			}
			*/
			if (!isCloseQMode){ // normal mode.
				randNum = rand.nextInt(lPAllInfo.size());
				currentInfo = lPAllInfo.get(randNum);
				if (scVar.sGotPatientInfo.contains(currentInfo)){// already got.
					continue;
				}
				else if (null == currentInfo.getSuperInformation()){ // root node, can be asked directly.
					// not only acquired in document but also asked by the doctor, can be asked here, nothing to do here.
				}
				else{ // leave node.
					if (!scVar.sGotPatientInfo.contains(currentInfo.getSuperInformation())){ // parent node hasn't been got.
						continue;
					}
				}
			}
			else{ // close question mode.
				closeModeCounter--;
				namedQuery = "MedicalInformation.findBySuperInformation";
				queryParams.clear();
				queryParams.put("superInformation", currentInfo);
				List<MedicalInformation> subMIs = JpaManager.<MedicalInformation>findWithNamedQuery(namedQuery, queryParams);
				randNum = rand.nextInt(subMIs.size());
				currentInfo = subMIs.get(randNum);
			}
			PatientPhrase bestPP = null;
			//TODO: check if all root are known by query
			// 			
			//cheat: Know there is at least one unknown node in the root nodes. so choose openQuestion
			// Cheat info: MI1 known, MI2 unkown.
			//cheat: proceed directly with openquestion
			//List<Pair> pairs = new ArrayList<Pair>(currentInfo.getPairs());
			// the returned value is already a list, no need to create it again.
			List<Pair> pairs = currentInfo.getPairs();
			for(Pair pair:pairs){
				//List<DoctorPhrase> dps = new ArrayList<DoctorPhrase>(pair.getPossibleDoctorPhrasesByType(APhrase.PrimitiveType.OpenQuestion));
				List<DoctorPhrase> dps = pair.getPossibleDoctorPhrases();
				
				if(0 < dps.size()){  // has Doctor Phrase in the pair
					randNum = rand.nextInt(dps.size());  //random choose open question
					DoctorPhrase dp = dps.get(randNum);
					if (!isCloseQMode){
						System.out.println("Doctor "+dp.getPhraseActor().getName()+": "+dp.getExpression());
						
						//update system variables after selection of doctor.
						scVar.calcOnce(dp);		
					}
					else{
						if(closeModeCounter < 1){
							isCloseQMode = false;
						}
					}
					switch (scVar.dialSt) {
					case N:
						// no info, find in type Confirmation.
						if(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.AnswerWithInfo).size()<1){
							bestPP = getBestPatientPhrase(
									getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Confirmation));
				
						}
						else{
							bestPP = getBestPatientPhrase(
									getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.AnswerWithInfo));
								
							
						}
						
						if(bestPP == null){
							System.out.println("Patient " + scPara.patient + ": ...(Warning: no data)");
						}
						else{
							String add = "";
							if(dp.getPrimitiveType().compareTo(APhrase.PrimitiveType.ClosedQuestion) == 0){//answer more.
								add = "Oui,,,";
								System.out.println("Patient " + bestPP.getPhraseActor() + ": " + add+ bestPP.getExpression());
								scVar.sGotPatientInfo.add(currentInfo);
								namedQuery = "MedicalInformation.findBySuperInformation";
								queryParams.clear();
								queryParams.put("superInformation", currentInfo);
								if (!JpaManager.findWithNamedQuery(namedQuery, queryParams).isEmpty()){
									closeModeCounter = 1;
									isCloseQMode = true;
									//TODO: the method to determine the number of phrases in a close question.
								}
							}
							else if(APhrase.PrimitiveType.OpenQuestion == dp.getPrimitiveType()){ // just answer this one.
								System.out.println("Patient " + bestPP.getPhraseActor() + ": " + add+ bestPP.getExpression());
								scVar.sGotPatientInfo.add(currentInfo);
							}
							//System.out.println("Patient " + bestPP.getPhraseActor() + ": " + add+ bestPP.getExpression());
							//scVar.sGotPatientInfoByQuery.add(currentInfo);
							//scVar.sGotPatientInfoAllRoot.add(currentInfo);
							//scVar.sGotPatientInfo.add(currentInfo);
						}
						
						break;
					case DU:
						bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.DontUnderstand));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
						} else{
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						}
						break;
					case Q:
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Questioning));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case R:
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Disagree));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je ne veux plus parler de ça...");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case END:
						System.out.println("Doctor " + scPara.doctor + ": OK, on parle plus sur ça.");
						break;
					default:
						System.out.println("Patient " + scPara.patient + ": Au revoir");
						break;
					}
					
				}
				else{   //No open Question defined, not on the root level

				/*	List<DoctorPhrase> closeddps = new ArrayList<DoctorPhrase>(pair.getPossibleDoctorPhrasesByType(APhrase.PrimitiveType.ClosedQuestion));
					randNum = rand.nextInt(dps.size());  //random choose possible closed questions
					DoctorPhrase cdp = closeddps.get(randNum);
					System.out.println("Doctor "+cdp.getPhraseActor().getName()+": "+cdp.getExpression());
					
					//update system variables after selection of doctor.
					scVar.calcOnce(cdp);		
					

					switch (scVar.dialSt) {
					case N:
						// no info, find in type Confirmation.
						if(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.AnswerWithInfo).size()<1){
							bestPP = getBestPatientPhrase(
									getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Confirmation));
				
						}
						else{
							bestPP = getBestPatientPhrase(
									getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.AnswerWithInfo));
								
							
						}
						
						if(bestPP == null){
							System.out.println("Patient " + scPara.patient + ": ...(Warning: no data)");
						}
						else{
							
							System.out.println("Patient " + bestPP.getPhraseActor() + ": Oui,,," + bestPP.getExpression());
							scVar.sGotPatientInfoByQuery.add(currentInfo);
							scVar.sGotPatientInfoAllRoot.add(currentInfo);
						}
						
						break;
					case DU:
						bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.DontUnderstand));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
						} else{
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						}
						break;
					case Q:
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Questioning));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case R:
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Disagree));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je ne veux plus parler de ça...");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case END:
						System.out.println("Doctor " + scPara.doctor + ": OK, on parle plus sur ça.");
						break;
					default:
						System.out.println("Patient " + scPara.patient + ": Au revoir");
						break;
					}*/
					
					
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());	
				}
			}
			if (scVar.sGotPatientInfo.size() >= lPAllInfo.size()){  //All asked
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
			
			
		
		
		
	
/*	public void simulate_old() {
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
	*/

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		// set parameter of game engine.
		ge.getScPara().tr_init = 20;
	/*	System.out.println("********Best result**********");
		ge.simulateBest();
		ge.simulateInfoBased();
		System.out.println("********Worst result**********");
		ge.simulateWorst();*/
		ge.simulateMedicalInfoBasedTest();
		/* run 3 times
		for(int i =0; i<3;i++){
		ge.simulate();
		}*/
	}

}

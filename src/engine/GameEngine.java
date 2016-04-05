package engine;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public String outputCSVtitle(){
		String result = "";
		result += scPara.outputCSVtitle();
		result += ", ";
		result += scVar.outputCSVtitle();
		return result;
	}
	
	public String outputCSVresult(){
		String result = "";
		result += scPara.outputCSVresult();
		result += ", ";
		result += scVar.getResOneByOne();
		return result;
	}

	public ScenarioParameter getScPara() {
		return scPara;
	}

	public ScenarioVariable getScVar() {
		return scVar;
	}
	public void debugVar(PatientPhrase pp){
		System.out.println("phase:\n"+" Agressive:"+(pp.getAggressiveLevel().ordinal()+1) * 25 
			+"\n pt_Agree:"+scVar.pt_Aggr
			+"\n Distubane:"+(pp.getClearLevel().ordinal()*40+30)+"\n pt_Dist"+scVar.pt_Dist
			+"\n trust:"+scVar.pt_Trust
			+"\n Longinite:"+(pp.getLongLevel().ordinal()+1)*25+"\n Talktive"+scVar.pt_Talk);
	}

	public PatientPhrase getBestPatientPhrase(List<PatientPhrase> pps) {
		PatientPhrase rpp = null;

		double minDistance = 999999999;
		double currentDistance = 0;
		int i = 0;
		for (PatientPhrase pp : pps) {
			
			// to modify pTypeV factor in the formula
			currentDistance = Math.pow((pp.getAggressiveLevel().ordinal()+1) * 25  - scVar.pt_Aggr, 2)
					+ Math.pow((100 - (pp.getClearLevel().ordinal()*40+30) - scVar.pt_Dist), 2)
					+ Math.pow((pp.getLongLevel().ordinal()+1)*25 - scVar.pt_Talk, 2);
			//debugVar(pp);
			System.out.println("phase"+ i+" distance:"+currentDistance);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				rpp = pp;

			}
			i++;
		}
		System.out.println("min distance:"+ minDistance);

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
		flgInitByPatient = (pair.getPossibleDoctorPhrases().size() > 0) ? false : true;
		for (DoctorPhrase dp : pair.getPossibleDoctorPhrases()) {
			eff_curr = dp.effTrust - dp.effDisturbance;
			if (eff_curr > eff_high) {
				eff_high = eff_curr;
				bestDChoice = dp;
			}
		}
		if (eff_high > 0 && !flgInitByPatient) {

			if (null != bestDChoice) {
				if (bestDChoice == lastDoctorPhrase) {
					System.out.println("Doctor " + bestDChoice.getPhraseActor().getName() + ": Je veux dire que "
							+ bestDChoice.getExpression());

				} else {
					System.out.println(
							"Doctor " + bestDChoice.getPhraseActor().getName() + ": " + bestDChoice.getExpression());

				}

				// update system variables after selection of doctor.
				scVar.calcOnce(bestDChoice);
			}
			System.out.println(scVar.dialSt + "+++++");
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
				//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
				//		APhrase.PrimitiveType.DontUnderstand));
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
								Arrays.asList(APhrase.PrimitiveType.DontUnderstand))));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
				} else {
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				}
				processPairWithBestStrategy(pair);
				break;
			case Q:
				//bestPP = getBestPatientPhrase(
				//		getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Questioning));
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
								Arrays.asList( APhrase.PrimitiveType.Questioning))));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
				} else{
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				}
				processPairWithBestStrategy(pair);
				break;
			case R:
				//bestPP = getBestPatientPhrase(
				//		getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Disagree));
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
								Arrays.asList( APhrase.PrimitiveType.Disagree))));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Je ne veux plus parler de ça...");
				} else
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				processPairWithBestStrategy(pair);
				break;
			case END:
				System.out.println("Doctor " + scPara.doctor + ": OK, on parle plus sur ça.");
				break;
			default:
				System.out.println("Patient " + scPara.patient + ": Au revoir");
				break;

			}

		} else {// initialized by patient
			bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
			System.out.println("Patient " + scPara.patient + ": " + bestPP.getExpression());
		}

	}

	public void processPairWithWorstStrategy(Pair pair) {
		DoctorPhrase worstDChoice = null;

		// Get corresponding patient phrase.
		PatientPhrase bestPP = null;
		boolean flgInitByPatient = false;
		double eff_low = 0;
		double eff_curr = 0;
		flgInitByPatient = (pair.getPossibleDoctorPhrases().size() > 0) ? false : true;
		for (DoctorPhrase dp : pair.getPossibleDoctorPhrases()) {
			eff_curr = dp.effTrust - dp.effDisturbance;
			if (eff_curr < eff_low) {
				eff_low = eff_curr;
				worstDChoice = dp;
			}
		}
		if (!flgInitByPatient) {

			if (null != worstDChoice) {
				if (worstDChoice == lastDoctorPhrase) { // same choice of doctor
					System.out.println("Doctor " + worstDChoice.getPhraseActor().getName() + ": Ben je veux dire que "
							+ worstDChoice.getExpression());

				} else {
					System.out.println(
							"Doctor " + worstDChoice.getPhraseActor().getName() + ": " + worstDChoice.getExpression());

				}

				// update system variables after selection of doctor.
				scVar.calcOnce(worstDChoice);
			}
			System.out.println(scVar.dialSt + "+++++");
			lastDoctorPhrase = worstDChoice;
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
				} else {
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				}
				processPairWithWorstStrategy(pair);
				break;
			case Q:
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Questioning));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
				} else
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				processPairWithWorstStrategy(pair);
				break;
			case R:
				bestPP = getBestPatientPhrase(
						getPPhrasesInPairByType(pair.getPossiblePatientPhrases(), APhrase.PrimitiveType.Disagree));
				if (bestPP == null) {
					System.out.println("Patient " + scPara.patient + ": Je ne veux plus parler de ça...");
				} else
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				processPairWithWorstStrategy(pair);
				break;
			case END:
				System.out.println("Doctor " + scPara.doctor + ": OK, on parle plus sur ça.");
				break;
			default:
				System.out.println("Patient " + scPara.patient + ": Au revoir");
				break;

			}

		} else {// initialized by patient
			bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
			System.out.println("Patient " + scPara.patient + ": " + bestPP.getExpression());
		}

	}

	public void simulateBest() {
		List<DialogueSession> selectedDSs = new ArrayList<DialogueSession>();
/* new common method has been introduced, no need to search in named query now. 14/03/2016
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "Welcome");
		List<MicroSequence> mss = JpaManager.<MicroSequence> findWithNamedQuery(namedQuery, queryParams);
*/
		MicroSequence ms = MicroSequence.findByName(MicroSequence.class, "Welcome");
		for (DialogueSession ds : ms.getDialogueSessions()) {
			List<Pair> pairs = ds.getPairs();
			for (Pair pair : pairs) {
				processPairWithBestStrategy(pair);
			}
		}
	}

	public void simulateWorst() {
/* new common method has been introduced, no need to search in named query now. 14/03/2016
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "Welcome");
		List<MicroSequence> mss = JpaManager.<MicroSequence> findWithNamedQuery(namedQuery, queryParams);
*/
		MicroSequence ms = MicroSequence.findByName(MicroSequence.class, "Welcome");
		for (DialogueSession ds : ms.getDialogueSessions()) {
			List<Pair> pairs = ds.getPairs();
			for (Pair pair : pairs) {
				processPairWithWorstStrategy(pair);
			}
		}
	}

	
	public List<DoctorPhrase> getDPhrasesInPairByType(List<DoctorPhrase> ps, APhrase.PrimitiveType pType) {
		if (null == pType) {
			return ps;
		}
		List<DoctorPhrase> pps = new ArrayList<DoctorPhrase>();
		for (DoctorPhrase ap : ps) {
			if (ap.getPrimitiveType().equals(pType)) {
				pps.add(ap);
			}
		}
		return pps;
	}

	public List<DoctorPhrase> getDPhrasesInPairByTypeS(List<DoctorPhrase> ps, List<APhrase.PrimitiveType> pTypes) {
		if (null == pTypes || 0 == pTypes.size()) {
			return ps;
		}
		List<DoctorPhrase> pps = new ArrayList<DoctorPhrase>();
		for (APhrase.PrimitiveType type : pTypes) {
			pps.addAll(getDPhrasesInPairByType(ps, type));
		}
		return pps;
	}

	public List<PatientPhrase> getPPhrasesInPairByType(List<PatientPhrase> ps, APhrase.PrimitiveType pType) {
		if (null == pType) {
			return ps;
		}
		List<PatientPhrase> pps = new ArrayList<PatientPhrase>();
		for (PatientPhrase ap : ps) {
			if (ap.getPrimitiveType().equals(pType))
				pps.add(ap);
		}
		return pps;
	}

	public List<PatientPhrase> getPPhrasesInPairByTypeS(List<PatientPhrase> ps, List<APhrase.PrimitiveType> pTypes) {
		if (null == pTypes || 0 == pTypes.size()) {
			return ps;
		}
		List<PatientPhrase> pps = new ArrayList<PatientPhrase>();
		for (APhrase.PrimitiveType type : pTypes) {
			pps.addAll(getPPhrasesInPairByType(ps, type));
		}
		return pps;
	}

	// information tree based strategy simulation: random select to find all
	// nodes
	public void simulateInfoBased() {
		int randNum = 0;
		FamilyInformation currentInfo = null;
		List<FamilyInformation> lPInfo = null;

		Random rand = new Random();
		String namedQuery = "FamilyInformation.findBySuperInformation";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		List<FamilyInformation> lPAllInfo = JpaManager.<FamilyInformation> findAll("FamilyInformation");

		while (true) {
			if (null == currentInfo) {

				lPInfo = JpaManager.findWithNamedQuery("APatientInformation.findSuperInformationNull", null);
			} else {
				queryParams.clear();
				queryParams.put("superInformation", currentInfo);
				lPInfo = JpaManager.findWithNamedQuery(namedQuery, queryParams);
				if (1 > lPInfo.size()) {
					currentInfo = null;
					continue;
				}
			}

			randNum = rand.nextInt(lPInfo.size());

			currentInfo = lPInfo.get(randNum);
			if (scVar.sGotPatientInfoByQuery.contains(currentInfo)) {// it's
																		// already
																		// got
				continue;
			}
			PatientPhrase bestPP = null;
			// get Pairs in Info node
			List<Pair> pairs = new ArrayList<Pair>(currentInfo.getPairs());
			for (Pair pair : pairs) {
				List<DoctorPhrase> dps = new ArrayList<DoctorPhrase>(pair.getPossibleDoctorPhrases());

				// has Doctor Phrase in the pair
				if (0 < dps.size()) {
					randNum = rand.nextInt(dps.size());
					DoctorPhrase dp = dps.get(randNum);
					System.out.println("Doctor " + dp.getPhraseActor().getName() + ": " + dp.getExpression());

					// update system variables after selection of doctor.
					scVar.calcOnce(dp);

					switch (scVar.dialSt) {
					case N:
						// no info, find in type Confirmation.
						if (getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.AnswerWithInfo).size() < 1) {
							bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
									APhrase.PrimitiveType.Confirmation));

						} else {
							bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
									APhrase.PrimitiveType.AnswerWithInfo));

						}
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": ...(Warning: no data)");
						} else {
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
							scVar.sGotPatientInfoByQuery.add(currentInfo);
						}

						break;
					case DU:
						bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.DontUnderstand));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
						} else {
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						}
						break;
					case Q:
						bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.Questioning));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case R:
						bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.Disagree));
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

				} else {

					bestPP = getBestPatientPhrase(pair.getPossiblePatientPhrases());
					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());

				}

			}

			if (scVar.sGotPatientInfoByQuery.size() >= lPAllInfo.size()) {
				break;
			} else if (DialogueState.END == scVar.dialSt) { // game over.
				break;
			} else {
				continue;
			}
		}

	}

	public void setKnownNode() {
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "AskReason");
		List<MicroSequence> mss = JpaManager.<MicroSequence> findWithNamedQuery(namedQuery, queryParams);
		List<MedicalInformation> minfos = mss.get(0).getMedicalInfos();
		// Cheat info: MI1 known, MI2 unkown.
		for (MedicalInformation info : minfos) {
			if (info.getName().equalsIgnoreCase("ConsultReason1")) {
				scVar.sGotPatientInfoByDefault.add(info);
				scVar.sGotPatientInfoAllRoot.add(info);
			}
		}

	}
	public int countNodes(APatientInformation pI){
		String namedQuery = "";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		namedQuery = "APatientInformation.findBySuperInformation";
		queryParams.clear();
		queryParams.put("superInformation", pI);
		List<APatientInformation> subMIs = JpaManager.<APatientInformation> findWithNamedQuery(namedQuery,
				queryParams);
		if (0 < subMIs.size()){
			return 1 + countNodes(subMIs.get(0));
		}
		else{
			return 0;
		}
	}
	public void simulateMedicalInfoBasedTest() {

		int randNum = 0;
		int closeModeCounter = 0;
		boolean isCloseQMode = false;
		Random rand = new Random();

		// List<MedicalInformation> acquiredNodes = new
		// ArrayList<MedicalInformation>();
		MedicalInformation currentInfo = null;
		List<MedicalInformation> lPAllInfo = null;
		String namedQuery = "";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		/* new common method has been introduced, no need to search in named query now. 14/03/2016
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "AskReason");
		List<MicroSequence> mss = JpaManager.<MicroSequence> findWithNamedQuery(namedQuery, queryParams);
		*/
		MicroSequence ms = MicroSequence.findByName(MicroSequence.class, "AskReason");
		lPAllInfo = ms.getMedicalInfos();

		while (true) {

			if (!isCloseQMode) { // normal mode.
				randNum = rand.nextInt(lPAllInfo.size());
				currentInfo = lPAllInfo.get(randNum);
				if (scVar.sGotPatientInfo.contains(currentInfo)) {// already got.
					continue;
				} else if (null == currentInfo.getSuperInformation()) { // root node, can be asked directly.
					// not only acquired in document but also asked by the doctor, can be asked here, nothing to do here.
				} else { // leaf node.
					if (!scVar.sGotPatientInfo.contains(currentInfo.getSuperInformation())) { // parent node hasn't been got.
						continue;
					}
				}
			} else { // close question mode.
				closeModeCounter--;
				namedQuery = "MedicalInformation.findBySuperInformation";
				queryParams.clear();
				queryParams.put("superInformation", currentInfo);
				List<MedicalInformation> subMIs = JpaManager.<MedicalInformation> findWithNamedQuery(namedQuery,
						queryParams);
				randNum = rand.nextInt(subMIs.size());
				currentInfo = subMIs.get(randNum);
			}
			PatientPhrase bestPP = null;
			// TODO: check if all root are known by query
			//
			// cheat: Know there is at least one unknown node in the root nodes.
			// so choose openQuestion
			// Cheat info: MI1 known, MI2 unkown.
			// cheat: proceed directly with openquestion

			// the returned value is already a list, no need to create it again.
			List<Pair> pairs = currentInfo.getPairs();
			for (Pair pair : pairs) {
				// List<DoctorPhrase> dps = new
				// ArrayList<DoctorPhrase>(pair.getPossibleDoctorPhrasesByType(APhrase.PrimitiveType.OpenQuestion));
				List<DoctorPhrase> dps = pair.getPossibleDoctorPhrases();

				if (0 < dps.size()) { // has Doctor Phrase in the pair
					randNum = rand.nextInt(dps.size()); // random choose open
														// question
					DoctorPhrase dp = dps.get(randNum);
					if (!isCloseQMode) {
						System.out.println("Doctor " + dp.getPhraseActor().getName() + ": " + dp.getExpression());

						// update system variables after selection of doctor.
						scVar.calcOnce(dp);
					} else {
						if (closeModeCounter < 1) {
							isCloseQMode = false;
						}
					}
					switch (scVar.dialSt) {
					case N:
						// no info, find in type Confirmation.
						if (getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.AnswerWithInfo).size() < 1) {
							bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
									APhrase.PrimitiveType.Confirmation));

						} else {
							bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
									APhrase.PrimitiveType.AnswerWithInfo));

						}

						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": ...(Warning: no data)");
						} else {
							String add = "";
							if (dp.getPrimitiveType().compareTo(APhrase.PrimitiveType.ClosedQuestion) == 0) {// answer
																												// more.
								add = "Ah oui,,,";
								System.out.println(
										"Patient " + bestPP.getPhraseActor() + ": " + add + bestPP.getExpression());
								scVar.sGotPatientInfo.add(currentInfo);
								namedQuery = "MedicalInformation.findBySuperInformation";
								queryParams.clear();
								queryParams.put("superInformation", currentInfo);
								if (!JpaManager.findWithNamedQuery(namedQuery, queryParams).isEmpty()) {
									
									/*
									 * formula to calculate the percentage of the close question concerned numbers.
									 */
									 closeModeCounter = 1;
									//closeModeCounter = (int)Math.floor(((double)countNodes(currentInfo))*(1-scPara.pTypeV.talktive)*(scVar.pt_Trust/100+scPara.pTypeV.talktive/(1-scPara.pTypeV.talktive)));
									System.out.println("closeModeCounter "+ closeModeCounter);
									isCloseQMode = true;
									
								}
							} else if (APhrase.PrimitiveType.OpenQuestion == dp.getPrimitiveType()) { // just
																										// answer
																										// this
																										// one.
								System.out.println(
										"Patient " + bestPP.getPhraseActor() + ": " + add + bestPP.getExpression());
								scVar.sGotPatientInfo.add(currentInfo);
							}
							// System.out.println("Patient " +
							// bestPP.getPhraseActor() + ": " + add+
							// bestPP.getExpression());
							// scVar.sGotPatientInfoByQuery.add(currentInfo);
							// scVar.sGotPatientInfoAllRoot.add(currentInfo);
							// scVar.sGotPatientInfo.add(currentInfo);
						}

						break;
					case DU:
						//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						//		APhrase.PrimitiveType.DontUnderstand));
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
										Arrays.asList( APhrase.PrimitiveType.DontUnderstand))));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
						} else {
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						}
						break;
					case Q:
						//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						//		APhrase.PrimitiveType.Questioning));
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
										Arrays.asList(APhrase.PrimitiveType.Questioning))));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case R:
						//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						//		APhrase.PrimitiveType.Disagree));
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
										Arrays.asList(APhrase.PrimitiveType.Disagree))));
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

				} else { // No open Question defined, not on the root level

					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				}
			}
			if (scVar.sGotPatientInfo.size() >= lPAllInfo.size()) { // All asked
				break;
			} else if (DialogueState.END == scVar.dialSt) { // game over.
				break;
			} else {
				continue;
			}

		}
	}
	public void simulateInfoBasedTest(String msName) {

		int randNum = 0;
		int closeModeCounter = 0;
		boolean isCloseQMode = false;
		Random rand = new Random();

		// List<MedicalInformation> acquiredNodes = new
		// ArrayList<MedicalInformation>();
		APatientInformation currentInfo = null;
		List<APatientInformation> lPAllInfo = null;
		String namedQuery = "";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		/* new common method has been introduced, no need to search in named query now. 14/03/2016
		String namedQuery = "MicroSequence.findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "AskReason");
		List<MicroSequence> mss = JpaManager.<MicroSequence> findWithNamedQuery(namedQuery, queryParams);
		*/
		MicroSequence ms = MicroSequence.findByName(MicroSequence.class, msName);
		lPAllInfo = new ArrayList<APatientInformation>();
		// add family and medical infomation into the info list.
		for(FamilyInformation fi: ms.getFamilyInfos()){
			lPAllInfo.add(fi);
		}
		for(MedicalInformation mi: ms.getMedicalInfos()){
			lPAllInfo.add(mi);
		}
		

		while (true) {

			if (!isCloseQMode) { // normal mode.
				randNum = rand.nextInt(lPAllInfo.size());
				currentInfo = lPAllInfo.get(randNum);
				if (scVar.sGotPatientInfo.contains(currentInfo)) {// already got.
					continue;
				} else if (null == currentInfo.getSuperInformation()) { // root node, can be asked directly.
					// not only acquired in document but also asked by the doctor, can be asked here, nothing to do here.
				} else { // leaf node.
					if (!scVar.sGotPatientInfo.contains(currentInfo.getSuperInformation())) { // parent node hasn't been got.
						continue;
					}
				}
			} else { // close question mode.
				closeModeCounter--;
				namedQuery = "APatientInformation.findBySuperInformation";
				queryParams.clear();
				queryParams.put("superInformation", currentInfo);
				List<APatientInformation> subMIs = JpaManager.<APatientInformation> findWithNamedQuery(namedQuery,
						queryParams);
				randNum = rand.nextInt(subMIs.size());
				currentInfo = subMIs.get(randNum);
			}
			PatientPhrase bestPP = null;
			// TODO: check if all root are known by query
			//
			// cheat: Know there is at least one unknown node in the root nodes.
			// so choose openQuestion
			// Cheat info: MI1 known, MI2 unkown.
			// cheat: proceed directly with openquestion

			// the returned value is already a list, no need to create it again.
			List<Pair> pairs = currentInfo.getPairs();
			for (Pair pair : pairs) {
				// List<DoctorPhrase> dps = new
				// ArrayList<DoctorPhrase>(pair.getPossibleDoctorPhrasesByType(APhrase.PrimitiveType.OpenQuestion));
				List<DoctorPhrase> dps = pair.getPossibleDoctorPhrases();

				if (0 < dps.size()) { // has Doctor Phrase in the pair
					randNum = rand.nextInt(dps.size()); // random choose open
														// question
					DoctorPhrase dp = dps.get(randNum);
					if (!isCloseQMode) {
						System.out.println("Doctor " + dp.getPhraseActor().getName() + ": " + dp.getExpression());

						// update system variables after selection of doctor.
						scVar.calcOnce(dp);
					} else {
						if (closeModeCounter < 1) {
							isCloseQMode = false;
						}
					}
					switch (scVar.dialSt) {
					case N:
						// no info, find in type Confirmation.
						if (getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
								APhrase.PrimitiveType.AnswerWithInfo).size() < 1) {
							bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
									APhrase.PrimitiveType.Confirmation));

						} else {
							bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
									APhrase.PrimitiveType.AnswerWithInfo));

						}

						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": ...(Warning: no data)");
						} else {
							String add = "";
							if (dp.getPrimitiveType().compareTo(APhrase.PrimitiveType.ClosedQuestion) == 0) {// answer
																												// more.
								add = "Ah oui,,,";
								System.out.println(
										"Patient " + bestPP.getPhraseActor() + ": " + add + bestPP.getExpression());
								scVar.sGotPatientInfo.add(currentInfo);
								namedQuery = "APatientInformation.findBySuperInformation";
								queryParams.clear();
								queryParams.put("superInformation", currentInfo);
								if (!JpaManager.findWithNamedQuery(namedQuery, queryParams).isEmpty()) {
									
									/*
									 * formula to calculate the percentage of the close question concerned numbers.
									 */
									 closeModeCounter = 1;
									//closeModeCounter = (int)Math.floor(((double)countNodes(currentInfo))*(1-scPara.pTypeV.talktive)*(scVar.pt_Trust/100+scPara.pTypeV.talktive/(1-scPara.pTypeV.talktive)));
									System.out.println("closeModeCounter "+ closeModeCounter);
									isCloseQMode = true;
									
								}
							} else if (APhrase.PrimitiveType.OpenQuestion == dp.getPrimitiveType()) { // just
																										// answer
																										// this
																										// one.
								System.out.println(
										"Patient " + bestPP.getPhraseActor() + ": " + add + bestPP.getExpression());
								scVar.sGotPatientInfo.add(currentInfo);
							}
							// System.out.println("Patient " +
							// bestPP.getPhraseActor() + ": " + add+
							// bestPP.getExpression());
							// scVar.sGotPatientInfoByQuery.add(currentInfo);
							// scVar.sGotPatientInfoAllRoot.add(currentInfo);
							// scVar.sGotPatientInfo.add(currentInfo);
						}

						break;
					case DU:
						//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						//		APhrase.PrimitiveType.DontUnderstand));
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
										Arrays.asList( APhrase.PrimitiveType.DontUnderstand))));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Je n'ai pas compris...");
						} else {
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						}
						break;
					case Q:
						//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						//		APhrase.PrimitiveType.Questioning));
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
										Arrays.asList(APhrase.PrimitiveType.Questioning))));
						if (bestPP == null) {
							System.out.println("Patient " + scPara.patient + ": Pourquoi vous me demandez ça?");
						} else
							System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
						break;
					case R:
						//bestPP = getBestPatientPhrase(getPPhrasesInPairByType(pair.getPossiblePatientPhrases(),
						//		APhrase.PrimitiveType.Disagree));
						bestPP = getBestPatientPhrase(
								getPPhrasesInPairByTypeS(pair.getPossiblePatientPhrases(), new ArrayList<APhrase.PrimitiveType>(
										Arrays.asList(APhrase.PrimitiveType.Disagree))));
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

				} else { // No open Question defined, not on the root level

					System.out.println("Patient " + bestPP.getPhraseActor() + ": " + bestPP.getExpression());
				}
			}
			if (scVar.sGotPatientInfo.size() >= lPAllInfo.size()) { // All asked
				break;
			} else if (DialogueState.END == scVar.dialSt) { // game over.
				break;
			} else {
				continue;
			}

		}
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		// set parameter of game engine.
/*		ge.getScPara().tr_init = 20;
		System.out.println("********Best result**********");
		ge.simulateBest();
		//ge.simulateInfoBased();
		System.out.println("********Worst result**********");
		ge.simulateWorst();*/

		/*
		 * run trail 1 A patient talkative, aggressive and chaotic, low on
		 * comprehension .
		 * 
		 */

		ge.scPara.setPatientParameter(0.8, 0.8, 0.8, 80.0, 80.0,80.0,20.0, 0.2, 0.3);
		ge.scVar.initVariables();
		System.out.println("Trail 1 :A patient talkative, aggressive and chaotic, low on comprehension .");
		System.out.println("*****trail 1*****");
		System.out.println(ge.scPara.toString());
		ge.simulateBest();
	//	ge.simulateMedicalInfoBasedTest();
		System.out.println("*****end trail 1*****");

		/*
		 * run trail 2 A patient normal talkative, aggressive, clear, high on
		 * comprehension, low on trust .
		 * 
		 */
		
		GameEngine ge2 = new GameEngine();
		ge2.scPara.setPatientParameter(0.8, 0.0, 0.0, 80.0, 0.0,0.0,80.0, 0.1, 0.3);
		ge2.scVar.initVariables();
		System.out.println(
				"Trail 2 :A patient normal talkative, aggressive, clear, high on comprehension, low on trust .");
		System.out.println("*****trail 2*****");
		System.out.println(ge2.scPara.toString());
		ge2.simulateBest();
	//	ge2.simulateMedicalInfoBasedTest();
		System.out.println("*****end trail 2*****");

	}

}

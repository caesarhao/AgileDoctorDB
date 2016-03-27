package control;

import java.util.List;

import engine.GameEngine;
import jpa.JpaManager;
import model.*;
import model.APhrase.*;
import model.PatientPhrase.*;
import view.*;

public class Controller {
	
	private DoctorActor da1;
	private PatientActor pa1;
	
	private PatientActor pa2;
	private Scenario s1;
	
	/* order of data:
	 * Scenario -> MicroSequence -> Dialogue Session   -> Pair -> <DoctorPhrase, PatientPhrase>
	 * Scenario -> MicroSequence -> Medical Information-> Pair -> <DoctorPhrase, PatientPhrase>
	 */
		private MicroSequence ms0;
			private DialogueSession ms0ds1;
				private Pair ms0ds1p1;
				private Pair ms0ds1p2;
			private DialogueSession ms0ds2;
				private Pair ms0ds2p1;
			private DialogueSession ms0ds3;
				private Pair ms0ds3p1;
				private Pair ms0ds3p2;
		private MicroSequence ms1;
			private MedicalInformation ms1mi1;
				private Pair ms1mi1p1;				
			private MedicalInformation ms1mi2;
				private Pair ms1mi2p1;
				
			// root element	
			private MedicalInformation ms1mi0;
				private Pair ms1dmi0p1;
				
			
				
				
			//Special:General Question, no micro-sequence order
			private	FamilyInformation fi1;
				private Pair fi1p1;
				private Pair fi1p2;
			private FamilyInformation fi11;
				private Pair fi11p1;
				private Pair fi11p2;
			private FamilyInformation fi2;
				private Pair fi2p1;
				private Pair fi2p2;
			
		////////TODO	
		private MicroSequence ms2;
		private MicroSequence ms3;
		
	public static boolean needFillingDatabase() {
		List<Scenario> scenarios = JpaManager.<Scenario> findWithNamedQuery("Scenario.findAll", null);
		if (scenarios.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}
	
	private void fillActors(){
		// PhraseActors, doctors.
		da1 = new DoctorActor();
		da1.setName("Dupont");
		da1.setSex(true);
		da1.persist();
		
		// PhraseActors, patients.
		pa1 = new PatientActor();
		pa1.setName("Cathy");
		pa1.setSex(false);
		pa1.setAggressiveLevel(80);
		pa1.setClearLevel(50);
		pa1.setConfidentLevel(50);
		pa1.setLongPhraseLevel(80);
		pa1.persist();
		
		pa2 = new PatientActor();
		pa2.setName("Jovial");
		pa2.setSex(false);
		pa2.setAggressiveLevel(20);
		pa2.setClearLevel(70);
		pa2.setConfidentLevel(70);
		pa2.setLongPhraseLevel(50);
		pa2.persist();
	}
	private void fillScenario(){
		s1 = new Scenario();
		s1.setName("DemoSenario");
		s1.persist();
		fillMS0();
		fillMS1();
		//fillMS2();
		//fillMS3();
		//fillMedicalInfo();
		fillMedicalInfo();
		fillFamilyInfo();
		
	}
	private void fillMS0() {
		// MicroSequences
		ms0 = new MicroSequence();
		ms0.setName("Welcome");
		ms0.persist();
		s1.getMicroSequences().add(ms0);
		fillMS0DS1();
		fillMS0DS2();
		fillMS0DS3();
	}
	private void fillMS1() {
		// MicroSequences
		ms1 = new MicroSequence();
		ms1.setName("AskReason");
		ms1.persist();
		s1.getMicroSequences().add(ms1);
			//fillMS1DS1();
	}
/*	private void fillMS2() {
		// MicroSequences
		ms2 = new MicroSequence();
		ms2.setName("GeneralQuestion");
		ms2.persist();
		s1.getMicroSequences().add(ms2);
		//fillMS2DS1();
	}
	private void fillMS3() {
		// MicroSequences
		ms3 = new MicroSequence();
		ms3.setName("AskSymptom");
		ms3.persist();
		s1.getMicroSequences().add(ms3);
		//fillMS3DS1();
	}*/
	private void fillMS0DS1(){
		ms0ds1 = new DialogueSession();
		ms0ds1.setName("SayHello");
		ms0ds1.persist();
		ms0.getDialogueSessions().add(ms0ds1);
		fillMS0DS1P1();
		fillMS0DS1P2();
	}
	private void fillMS0DS1P1(){
		//Pair for ds1
		ms0ds1p1 = new Pair();
		ms0ds1p1.setName(ms0ds1.getName()+"P1");
		ms0ds1p1.persist();
		ms0ds1.getPairs().add(ms0ds1p1);
		ms0ds1.update();
		
		// Phrases
		DoctorPhrase ds1p1dp1 = new DoctorPhrase();
		ds1p1dp1.setName("Pair1DoctorWelcomesPatient1");
		ds1p1dp1.setEffTrust(-5.0);
		ds1p1dp1.setEffDisturbance(5.0);
	
		ds1p1dp1.setPrimitiveType(PrimitiveType.Statement);
		ds1p1dp1.setPhraseActor(da1);
		ds1p1dp1.setExpression("Bonjour.");
		ds1p1dp1.persist();
			
		DoctorPhrase ds1p1dp2 = new DoctorPhrase();
		ds1p1dp2.setName("Pair1DoctorWelcomesPatient2");
		ds1p1dp2.setEffTrust(5.0);
		ds1p1dp2.setEffDisturbance(0.0);
		ds1p1dp2.setPrimitiveType(PrimitiveType.Statement);
		ds1p1dp2.setPhraseActor(da1);
		
		ds1p1dp2.setExpression("Bonjour " + pa1.getTitle());
		ds1p1dp2.persist();
		
		DoctorPhrase ds1p1dp3 = new DoctorPhrase();
		ds1p1dp3.setName("Pair1DoctorWelcomesPatient3");
		ds1p1dp3.setEffTrust(10.0);
		ds1p1dp3.setEffDisturbance(-10.0);
		ds1p1dp3.setPrimitiveType(PrimitiveType.Statement);
		ds1p1dp3.setPhraseActor(da1);
		ds1p1dp3.setExpression("Bonjour "+ pa1.getTitle() + ", je suis Docteur "+ds1p1dp3.getPhraseActor().getName());
		ds1p1dp3.persist();
		
		PatientPhrase ds1p1pp1 = new PatientPhrase();
		ds1p1pp1.setName("Pair1AnswerDoctorWelcomesPatient1");
		ds1p1pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		ds1p1pp1.setClearLevel(ClearLevel.Clear);
		ds1p1pp1.setLongLevel(LongLevel.Concise);
		ds1p1pp1.setPrimitiveType(PrimitiveType.Statement);
		ds1p1pp1.setPhraseActor(pa1);
		ds1p1pp1.setExpression("Bonjour.");
		ds1p1pp1.persist();
		
		PatientPhrase ds1p1pp2 = new PatientPhrase();
		ds1p1pp2.setName("Pair1AnswerDoctorWelcomesPatient2");
		ds1p1pp2.setAggressiveLevel(AggressiveLevel.Polite);
		ds1p1pp2.setClearLevel(ClearLevel.Clear);
		ds1p1pp2.setLongLevel(LongLevel.Concise);
		ds1p1pp2.setPrimitiveType(PrimitiveType.Statement);
		ds1p1pp2.setPhraseActor(pa1);
		ds1p1pp2.setExpression("Bonjour Docteur.");
		ds1p1pp2.persist();
		
		//add phrase to pair
		ms0ds1p1.getPossibleDoctorPhrases().add(ds1p1dp1);
		ms0ds1p1.getPossibleDoctorPhrases().add(ds1p1dp2);
		ms0ds1p1.getPossibleDoctorPhrases().add(ds1p1dp3);
		ms0ds1p1.getPossiblePatientPhrases().add(ds1p1pp1);
		ms0ds1p1.getPossiblePatientPhrases().add(ds1p1pp2);
		ms0ds1p1.update();
	}
	private void fillMS0DS1P2(){
		//Pair for ds1
		ms0ds1p2 = new Pair();
		ms0ds1p2.setName(ms0ds1.getName()+"P2");
		ms0ds1p2.persist();
		//add pair to ds1
		ms0ds1.getPairs().add(ms0ds1p2);
		ms0ds1.update();
		
		
		// Phrases
		DoctorPhrase ds1p2dp1 = new DoctorPhrase();
		ds1p2dp1.setName("Pair2DoctorWelcomesPatient1");
		ds1p2dp1.setEffTrust(5.0);
		ds1p2dp1.setEffDisturbance(0.0);
		ds1p2dp1.setPrimitiveType(PrimitiveType.Statement);
		ds1p2dp1.setPhraseActor(da1);
		ds1p2dp1.setExpression("Vous allez bien ?");
		ds1p2dp1.persist();
			
		DoctorPhrase ds1p2dp2 = new DoctorPhrase();
		ds1p2dp2.setName("Pair2DoctorWelcomesPatient2");
		ds1p2dp2.setEffTrust(0.0);
		ds1p2dp2.setEffDisturbance(5.0);
		ds1p2dp2.setPrimitiveType(PrimitiveType.Statement);
		ds1p2dp2.setPhraseActor(da1);	
		ds1p2dp2.setExpression("Comment-va tu ?");
		ds1p2dp2.persist();
		
		DoctorPhrase ds1p2dp3 = new DoctorPhrase();
		ds1p2dp3.setName("Pair2DoctorWelcomesPatient3");
		ds1p2dp3.setEffTrust(5.0);
		ds1p2dp3.setEffDisturbance(-5.0);
		ds1p2dp3.setPrimitiveType(PrimitiveType.Statement);
		ds1p2dp3.setPhraseActor(da1);	
		ds1p2dp3.setExpression("Comment allez-vous " + pa1.getTitle() + " ?");
		ds1p2dp3.persist();
		
		PatientPhrase ds1p2pp1 = new PatientPhrase();
		ds1p2pp1.setName("Pair2AnswerDoctorWelcomesPatient1");
		ds1p2pp1.setAggressiveLevel(AggressiveLevel.Polite);
		ds1p2pp1.setClearLevel(ClearLevel.Clear);
		ds1p2pp1.setLongLevel(LongLevel.Concise);
		ds1p2pp1.setPrimitiveType(PrimitiveType.Statement);
		ds1p2pp1.setPhraseActor(pa1);
		ds1p2pp1.setExpression("(souriant) Ça va, merci.");
		ds1p2pp1.persist();
		
		PatientPhrase ds1p2pp2 = new PatientPhrase();
		ds1p2pp2.setName("Pair2AnswerDoctorWelcomesPatient2");
		ds1p2pp2.setAggressiveLevel(AggressiveLevel.Aggressive);
		ds1p2pp2.setClearLevel(ClearLevel.Clear);
		ds1p2pp2.setLongLevel(LongLevel.Concise);
		ds1p2pp2.setPrimitiveType(PrimitiveType.Statement);
		ds1p2pp2.setPhraseActor(pa1);
		ds1p2pp2.setExpression("(indifférent) Ça va.");
		ds1p2pp2.persist();
		
		PatientPhrase ds1p2pp3 = new PatientPhrase();
		ds1p2pp3.setName("Pair2AnswerDoctorWelcomesPatient3");
		ds1p2pp3.setAggressiveLevel(AggressiveLevel.Neutral);
		ds1p2pp3.setClearLevel(ClearLevel.Unclear);
		ds1p2pp3.setLongLevel(LongLevel.Normal);
		ds1p2pp3.setPrimitiveType(PrimitiveType.Statement);
		ds1p2pp3.setPhraseActor(pa1);
		ds1p2pp3.setExpression("Oh, rien de particulier.");
		ds1p2pp3.persist();
		
		//add phrase to pair
		ms0ds1p2.getPossibleDoctorPhrases().add(ds1p2dp1);
		ms0ds1p2.getPossibleDoctorPhrases().add(ds1p2dp2);
		ms0ds1p2.getPossibleDoctorPhrases().add(ds1p2dp3);
		ms0ds1p2.getPossiblePatientPhrases().add(ds1p2pp1);
		ms0ds1p2.getPossiblePatientPhrases().add(ds1p2pp2);
		ms0ds1p2.getPossiblePatientPhrases().add(ds1p2pp3);
		ms0ds1p2.update();
	}
	private void fillMS0DS2(){
		ms0ds2 = new DialogueSession();
		ms0ds2.setName("InviteToSit");
		ms0ds2.persist();
		ms0.getDialogueSessions().add(ms0ds2);
		fillMS0DS2P1();			
	}
	private void fillMS0DS2P1(){
		//Pair for ds2
		ms0ds2p1 = new Pair();
		ms0ds2p1.setName(ms0ds2.getName()+"P1");
		ms0ds2p1.persist();
		ms0ds2.getPairs().add(ms0ds2p1);
		ms0ds2.update();
		
		// Phrases
		DoctorPhrase ds2p1dp1 = new DoctorPhrase();
		ds2p1dp1.setName("Pair1DoctorInviteSit1");
		ds2p1dp1.setEffTrust(5.0);
		ds2p1dp1.setEffDisturbance(-5.0);
		ds2p1dp1.setPrimitiveType(PrimitiveType.Statement);
		ds2p1dp1.setPhraseActor(da1);
		ds2p1dp1.setExpression("Suivez-moi. Veuillez-vous asseoir. ");
		ds2p1dp1.persist();
			
		DoctorPhrase ds2p1dp2 = new DoctorPhrase();
		ds2p1dp2.setName("Pair1DoctorInviteSit2");
		ds2p1dp2.setEffTrust(0.0);
		ds2p1dp2.setEffDisturbance(5.0);
		ds2p1dp2.setPrimitiveType(PrimitiveType.Statement);
		ds2p1dp2.setPhraseActor(da1);		
		ds2p1dp2.setExpression("Vous pouvez vous asseoir.");
		ds2p1dp2.persist();
		
		DoctorPhrase ds2p1dp3 = new DoctorPhrase();
		ds2p1dp3.setName("Pair1DoctorInviteSit3");
		ds2p1dp3.setEffTrust(0.0);
		ds2p1dp3.setEffDisturbance(-5.0);
		ds2p1dp3.setPrimitiveType(PrimitiveType.Statement);
		ds2p1dp3.setPhraseActor(da1);
		ds2p1dp3.setExpression("Asseyez-vous,"+ pa1.getTitle());
		ds2p1dp3.persist();
		
		PatientPhrase ds2p1pp1 = new PatientPhrase();
		ds2p1pp1.setName("Pair1AnswerDoctorInviteSit1");
		ds2p1pp1.setAggressiveLevel(AggressiveLevel.Polite);
		ds2p1pp1.setClearLevel(ClearLevel.Clear);
		ds2p1pp1.setLongLevel(LongLevel.Concise);
		ds2p1pp1.setPrimitiveType(PrimitiveType.Statement);
		ds2p1pp1.setPhraseActor(pa1);
		ds2p1pp1.setExpression("Ah merci. (s'asseoir)");
		ds2p1pp1.persist();
		
		PatientPhrase ds2p1pp2 = new PatientPhrase();
		ds2p1pp2.setName("Pair1AnswerDoctorInviteSit2");
		ds2p1pp2.setAggressiveLevel(AggressiveLevel.Aggressive);
		ds2p1pp2.setClearLevel(ClearLevel.Clear);
		ds2p1pp2.setLongLevel(LongLevel.Concise);
		ds2p1pp2.setPrimitiveType(PrimitiveType.Statement);
		ds2p1pp2.setPhraseActor(pa1);
		ds2p1pp2.setExpression("(s'asseoir)");
		ds2p1pp2.persist();
		
		PatientPhrase ds2p1pp3 = new PatientPhrase();
		ds2p1pp3.setName("Pair1AnswerDoctorInviteSit3");
		ds2p1pp3.setAggressiveLevel(AggressiveLevel.Neutral);
		ds2p1pp3.setClearLevel(ClearLevel.Clear);
		ds2p1pp3.setLongLevel(LongLevel.Concise);
		ds2p1pp3.setPrimitiveType(PrimitiveType.Statement);
		ds2p1pp3.setPhraseActor(pa1);
		ds2p1pp3.setExpression("D'accord. (s'asseoir)");
		ds2p1pp3.persist();
		//add phrase to pair
		ms0ds2p1.getPossibleDoctorPhrases().add(ds2p1dp1);
		ms0ds2p1.getPossibleDoctorPhrases().add(ds2p1dp2);
		ms0ds2p1.getPossibleDoctorPhrases().add(ds2p1dp3);
		ms0ds2p1.getPossiblePatientPhrases().add(ds2p1pp1);
		ms0ds2p1.getPossiblePatientPhrases().add(ds2p1pp2);
		ms0ds2p1.getPossiblePatientPhrases().add(ds2p1pp3);
		ms0ds2p1.update();
	}
	private void fillMS0DS3(){
		ms0ds3 = new DialogueSession();
		ms0ds3.setName("ComplainWaiting");
		ms0ds3.persist();
		ms0.getDialogueSessions().add(ms0ds3);
		fillMS0DS3P1();	
		fillMS0DS3P2();	
		
	}
	private void fillMS0DS3P1(){
		//Pair1 for ds3
		ms0ds3p1 = new Pair();
		ms0ds3p1.setName(ms0ds3.getName()+"P1");
		ms0ds3p1.persist();
		ms0ds3.getPairs().add(ms0ds3p1);
		ms0ds3.update();
		
		// Phrases
		// no Doctor's Phrases (patient initial ds)
		
		PatientPhrase ds3p1pp1 = new PatientPhrase();
		ds3p1pp1.setName("Pair1ComplainWaiting1");
		ds3p1pp1.setAggressiveLevel(AggressiveLevel.Polite);
		ds3p1pp1.setClearLevel(ClearLevel.Clear);
		ds3p1pp1.setLongLevel(LongLevel.TooLong);
		ds3p1pp1.setPrimitiveType(PrimitiveType.Statement);
		ds3p1pp1.setPhraseActor(pa1);
		ds3p1pp1.setExpression("Vous savez ça fait plus d'une heure que j'attends et... enfin... bon... maintenant que je suis là...");
		ds3p1pp1.persist();
		
		PatientPhrase ds3p1pp2 = new PatientPhrase();
		ds3p1pp2.setName("Pair1ComplainWaiting2");
		ds3p1pp2.setAggressiveLevel(AggressiveLevel.Neutral);
		ds3p1pp2.setClearLevel(ClearLevel.Clear);
		ds3p1pp2.setLongLevel(LongLevel.Concise);
		ds3p1pp2.setPrimitiveType(PrimitiveType.Statement);
		ds3p1pp2.setPhraseActor(pa1);
		ds3p1pp2.setExpression("C'est que j'attends depuis plus d'une heure....");
		ds3p1pp2.persist();
		
		//add phrase to pair
		ms0ds3p1.getPossiblePatientPhrases().add(ds3p1pp1);
		ms0ds3p1.getPossiblePatientPhrases().add(ds3p1pp2);

		ms0ds3p1.update();
	}
	private void fillMS0DS3P2(){
		//Pair2 for ds3
		ms0ds3p2 = new Pair();
		ms0ds3p2.setName(ms0ds3.getName()+"P2");
		ms0ds3p2.persist();
		ms0ds3.getPairs().add(ms0ds3p2);
		ms0ds3.update();
		
		// Phrases
		DoctorPhrase ds3p2dp1 = new DoctorPhrase();
		ds3p2dp1.setName("Pair2ReplyComplainWaiting1");
		ds3p2dp1.setEffTrust(5.0);
		ds3p2dp1.setEffDisturbance(0.0);
		ds3p2dp1.setPrimitiveType(PrimitiveType.Statement);
		ds3p2dp1.setPhraseActor(da1);
		ds3p2dp1.setExpression("Et oui j'ai dû passer plus de temps que prévu avec le patient précédent et en plus j'ai eu une urgence en début d'après-midi...");
		ds3p2dp1.persist();
			
		DoctorPhrase ds3p2dp2 = new DoctorPhrase();
		ds3p2dp2.setName("Pair2ReplyComplainWaiting2");
		ds3p2dp2.setEffTrust(-5.0);
		ds3p2dp2.setEffDisturbance(5.0);
		ds3p2dp2.setPrimitiveType(PrimitiveType.Statement);
		ds3p2dp2.setPhraseActor(da1);		
		ds3p2dp2.setExpression("Certains patients ont besoin qu'on leur consacre plus de temps que d'autres...");
		ds3p2dp2.persist();
		
		DoctorPhrase ds3p2dp3 = new DoctorPhrase();
		ds3p2dp3.setName("Pair2ReplyComplainWaiting3");
		ds3p2dp3.setEffTrust(5.0);
		ds3p2dp3.setEffDisturbance(0.0);
		ds3p2dp3.setPrimitiveType(PrimitiveType.Statement);
		ds3p2dp3.setPhraseActor(da1);
		ds3p2dp3.setExpression("Je m'excuse de vous avoir fait attendre");
		ds3p2dp3.persist();
		
		PatientPhrase ds3p2pp1 = new PatientPhrase();
		ds3p2pp1.setName("Pair2AnswerReplyComplainWaiting1");
		ds3p2pp1.setAggressiveLevel(AggressiveLevel.Polite);
		ds3p2pp1.setClearLevel(ClearLevel.Clear);
		ds3p2pp1.setLongLevel(LongLevel.Concise);
		ds3p2pp1.setPrimitiveType(PrimitiveType.Statement);
		ds3p2pp1.setPhraseActor(pa1);
		ds3p2pp1.setExpression("Oui bien sûr, je comprends...");
		ds3p2pp1.persist();
		
		PatientPhrase ds3p2pp2 = new PatientPhrase();
		ds3p2pp2.setName("Pair2AnswerReplyComplainWaiting2");
		ds3p2pp2.setAggressiveLevel(AggressiveLevel.Neutral);
		ds3p2pp2.setClearLevel(ClearLevel.Clear);
		ds3p2pp2.setLongLevel(LongLevel.Concise);
		ds3p2pp2.setPrimitiveType(PrimitiveType.Statement);
		ds3p2pp2.setPhraseActor(pa1);
		ds3p2pp2.setExpression("Bon d'accord");
		ds3p2pp2.persist();
		
		PatientPhrase ds3p2pp3 = new PatientPhrase();
		ds3p2pp3.setName("Pair2AnswerReplyComplainWaiting3");
		ds3p2pp3.setAggressiveLevel(AggressiveLevel.Aggressive);
		ds3p2pp3.setClearLevel(ClearLevel.Clear);
		ds3p2pp3.setLongLevel(LongLevel.Concise);
		ds3p2pp3.setPrimitiveType(PrimitiveType.Disagree);
		ds3p2pp3.setPhraseActor(pa1);
		ds3p2pp3.setExpression("Chaque fois c'est comme ça ! Mais j'ai pris un rdv avec l'heure et minute...");
		ds3p2pp3.persist();
		
		PatientPhrase ds3p2pp4 = new PatientPhrase();
		ds3p2pp4.setName("Pair2AnswerReplyComplainWaiting4");
		ds3p2pp4.setAggressiveLevel(AggressiveLevel.Neutral);
		ds3p2pp4.setClearLevel(ClearLevel.Clear);
		ds3p2pp4.setLongLevel(LongLevel.Concise);
		ds3p2pp4.setPrimitiveType(PrimitiveType.Disagree);
		ds3p2pp4.setPhraseActor(pa1);
		ds3p2pp4.setExpression("Oui, mais c'est quand même pénible.");
		ds3p2pp4.persist();
		
		//add phrase to pair
		ms0ds3p2.getPossibleDoctorPhrases().add(ds3p2dp1);
		ms0ds3p2.getPossibleDoctorPhrases().add(ds3p2dp2);
		ms0ds3p2.getPossibleDoctorPhrases().add(ds3p2dp3);
		ms0ds3p2.getPossiblePatientPhrases().add(ds3p2pp1);
		ms0ds3p2.getPossiblePatientPhrases().add(ds3p2pp2);
		ms0ds3p2.getPossiblePatientPhrases().add(ds3p2pp3);
		ms0ds3p2.getPossiblePatientPhrases().add(ds3p2pp4);
		ms0ds3p2.update();
	}
	
	
	

/*	private void fillMS1DS2(){
		ms1ds2 = new DialogueSession();
		ms1ds2.setName("AskdogDeadReason");
		ms1ds2.persist();
		ms1.getDialogueSessions().add(ms1ds2);
		fillMS1DS2P1();	
		fillMS1DS2P2();	
		
	}
	private void fillMS1DS2P1(){
		
	}
	private void fillMS1DS2P2(){
		
	}
	private void fillMS1DS3(){
		
	}
	private void fillMS1DS3P1(){
		
	}
	private void fillMS1DS3P2(){
		
	}*/
	
	
	/*------info tree based---------*/
	
	// fill info-based data

	
	//TO be modified cause change of model
/*	private void fillMedicalInfo(){
		// Get Information mode, ask if the patient smokes and the frequency
		MedicalInformation mi1 = new MedicalInformation();
		mi1.setName("Smoke");
		mi1.setImportance(80);
		mi1.setPriority(60);
		mi1.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		mi1.setSuperInformation(null);
		JpaManager.persist(mi1);
		

		
		DoctorPhrase mi1_d_p1 = new DoctorPhrase();
		mi1_d_p1.setName("DoctorAskSmoke1");
		//mi1_d_p1.setAggressiveLevel(AggressiveLevel.Polite);
		//mi1_d_p1.setClearLevel(ClearLevel.Clear);
		//mi1_d_p1.setLongLevel(LongLevel.Normal);
		mi1_d_p1.setEffTrust(10.0);
		mi1_d_p1.setEffDisturbance(0.0);
		mi1_d_p1.setvalClarity(0.99);
		mi1_d_p1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		mi1_d_p1.setPhraseActor(da1);
		mi1_d_p1.setExpression("Do you keep smoking?");
		JpaManager.persist(mi1_d_p1);
		
		DoctorPhrase mi1_d_p2 = new DoctorPhrase();
		mi1_d_p2.setName("DoctorAskSmoke2");
		mi1_d_p2.setEffTrust(-10.0);
		mi1_d_p2.setEffDisturbance(5.0);
		mi1_d_p2.setvalClarity(0.99);
		mi1_d_p2.setPrimitiveType(PrimitiveType.ClosedQuestion);
		mi1_d_p2.setPhraseActor(da1);
		mi1_d_p2.setExpression("You smoke a lot, right? You smell like a cigarette.");
		JpaManager.persist(mi1_d_p2);
		
		DoctorPhrase mi1_d_p3 = new DoctorPhrase();
		mi1_d_p3.setName("DoctorAskSmoke3");
		mi1_d_p3.setEffTrust(0.0);
		mi1_d_p3.setEffDisturbance(0.0);
		mi1_d_p3.setvalClarity(0.99);
		mi1_d_p3.setPrimitiveType(PrimitiveType.OpenQuestion);
		mi1_d_p3.setPhraseActor(da1);
		mi1_d_p3.setExpression("And the cigarette?");
		JpaManager.persist(mi1_d_p3);
		
		DoctorPhrase mi1_d_p4 = new DoctorPhrase();
		mi1_d_p4.setName("DoctorAskSmoke4");
		mi1_d_p4.setEffTrust(0.0);
		mi1_d_p4.setEffDisturbance(0.0);
		mi1_d_p4.setvalClarity(0.99);
		mi1_d_p4.setPrimitiveType(PrimitiveType.OpenQuestion);
		mi1_d_p4.setPhraseActor(da1);
		mi1_d_p4.setExpression("What's your current thought about the smoking?");
		JpaManager.persist(mi1_d_p4);
		
		
		mi1.getPossibleAskPhrases().add(mi1_d_p1);
		mi1.getPossibleAskPhrases().add(mi1_d_p2);
		mi1.getPossibleAskPhrases().add(mi1_d_p3);
		mi1.getPossibleAskPhrases().add(mi1_d_p4);
		JpaManager.update(mi1);
		
		PatientPhrase mi1_p_p1 = new PatientPhrase();
		mi1_p_p1.setName("PatientAnswerSmoke1");
		mi1_p_p1.setAggressiveLevel(AggressiveLevel.Polite);
		mi1_p_p1.setClearLevel(ClearLevel.Clear);
		mi1_p_p1.setLongLevel(LongLevel.Concise);
		mi1_p_p1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		mi1_p_p1.setPhraseActor(pa1);
		mi1_p_p1.setExpression("I smoke.");
		JpaManager.persist(mi1_p_p1);
		
		PatientPhrase mi1_p_p2 = new PatientPhrase();
		mi1_p_p2.setName("PatientAnswerSmoke2");
		mi1_p_p2.setAggressiveLevel(AggressiveLevel.Polite);
		mi1_p_p2.setClearLevel(ClearLevel.Clear);
		mi1_p_p2.setLongLevel(LongLevel.TooLong);
		mi1_p_p2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		mi1_p_p2.setPhraseActor(pa1);
		mi1_p_p2.setExpression("I keep smoking. I can't... live without it. It's wonderful.I don't worry about it.");
		JpaManager.persist(mi1_p_p2);
		
		PatientPhrase mi1_p_p3 = new PatientPhrase();
		mi1_p_p3.setName("PatientAnswerSmoke3");
		mi1_p_p3.setAggressiveLevel(AggressiveLevel.Polite);
		mi1_p_p3.setClearLevel(ClearLevel.Unclear);
		mi1_p_p3.setLongLevel(LongLevel.Normal);
		mi1_p_p3.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		mi1_p_p3.setPhraseActor(pa1);
		mi1_p_p3.setExpression("Smoking helps to release stress, why not?");
		JpaManager.persist(mi1_p_p3);
		
		PatientPhrase mi1_p_p4 = new PatientPhrase();
		mi1_p_p4.setName("PatientAnswerSmokeDU1");
		mi1_p_p4.setAggressiveLevel(AggressiveLevel.Polite);
		mi1_p_p4.setClearLevel(ClearLevel.Unclear);
		mi1_p_p4.setLongLevel(LongLevel.Normal);
		mi1_p_p4.setPrimitiveType(PrimitiveType.DontUnderstand);
		mi1_p_p4.setPhraseActor(pa1);
		mi1_p_p4.setExpression("Eh, I don't know that do you mean.");
		JpaManager.persist(mi1_p_p4);
		
		
		mi1.getPossibleResponsePhrases().add(mi1_p_p1);
		mi1.getPossibleResponsePhrases().add(mi1_p_p2);	
		mi1.getPossibleResponsePhrases().add(mi1_p_p3);
		mi1.getPossibleResponsePhrases().add(mi1_p_p4);
		JpaManager.update(mi1);

		MedicalInformation mi2 = new MedicalInformation();
		mi2.setName("SmokeFrequency");
		mi2.setImportance(80);
		mi2.setPriority(60);
		mi2.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		mi2.setSuperInformation(mi1);
		JpaManager.persist(mi2);
		//mi1.getSubInformations().add(mi2);
		//JpaManager.update(mi1);
		
		DoctorPhrase mi2_d_p1 = new DoctorPhrase();
		mi2_d_p1.setName("DoctorAskSmokeFrequency1");
		//mi2_d_p1.setAggressiveLevel(AggressiveLevel.Polite);
		//mi2_d_p1.setClearLevel(ClearLevel.Clear);
		//mi2_d_p1.setLongLevel(LongLevel.Normal);
		mi2_d_p1.setEffTrust(10.0);
		mi2_d_p1.setEffDisturbance(0.0);
	//	mi2_d_p1.setvalClarity(0.6);
		mi2_d_p1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		mi2_d_p1.setPhraseActor(da1);
		mi2_d_p1.setExpression("Do you often smoke?");
		JpaManager.persist(mi2_d_p1);
		
		DoctorPhrase mi2_d_p2 = new DoctorPhrase();
		mi2_d_p2.setName("DoctorAskSmokeFrequency2");
		//mi2_d_p2.setAggressiveLevel(AggressiveLevel.Aggressive);
		//mi2_d_p2.setClearLevel(ClearLevel.Clear);
		//mi2_d_p2.setLongLevel(LongLevel.Normal);
		mi2_d_p2.setEffTrust(0.0);
		mi2_d_p2.setEffDisturbance(-5.0);
	//	mi2_d_p2.setvalClarity(1.0);
		mi2_d_p2.setPrimitiveType(PrimitiveType.ClosedQuestion);
		mi2_d_p2.setPhraseActor(da1);
		mi2_d_p2.setExpression("How many cigarettes do you take everyday?");
		JpaManager.persist(mi2_d_p2);
		
		DoctorPhrase mi2_d_p3 = new DoctorPhrase();
		mi2_d_p3.setName("DoctorAskSmokeFrequency3");
		mi2_d_p3.setEffTrust(-10.0);
		mi2_d_p3.setEffDisturbance(10.0);
		mi2_d_p3.setvalClarity(0.2);
		mi2_d_p3.setPrimitiveType(PrimitiveType.ClosedQuestion);
		mi2_d_p3.setPhraseActor(da1);
		mi2_d_p3.setExpression("How about your intake?");
		JpaManager.persist(mi2_d_p3);
		
		mi2.getPossibleAskPhrases().add(mi2_d_p1);
		mi2.getPossibleAskPhrases().add(mi2_d_p2);
		mi2.getPossibleAskPhrases().add(mi2_d_p3);
		JpaManager.update(mi2);
		
		PatientPhrase mi2_p_p1 = new PatientPhrase();
		mi2_p_p1.setName("PatientAnswerSmokeFrequency1");
		mi2_p_p1.setAggressiveLevel(AggressiveLevel.Polite);
		mi2_p_p1.setClearLevel(ClearLevel.Clear);
		mi2_p_p1.setLongLevel(LongLevel.Normal);
		mi2_p_p1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		mi2_p_p1.setPhraseActor(pa1);
		mi2_p_p1.setExpression("2 cigarettes everyday.");
		JpaManager.persist(mi2_p_p1);
		
		PatientPhrase mi2_p_p2 = new PatientPhrase();
		mi2_p_p2.setName("PatientAnswerSmokeFrequency2");
		mi2_p_p2.setAggressiveLevel(AggressiveLevel.Aggressive);
		mi2_p_p2.setClearLevel(ClearLevel.Unclear);
		mi2_p_p2.setLongLevel(LongLevel.TooLong);
		mi2_p_p2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		mi2_p_p2.setPhraseActor(pa1);
		mi2_p_p2.setExpression("Maybe 2, maybe 10, who knows?");
		JpaManager.persist(mi2_p_p2);
		
		PatientPhrase mi2_p_p3 = new PatientPhrase();
		mi2_p_p3.setName("PatientAnswerSmokeFrequency3");
		mi2_p_p3.setAggressiveLevel(AggressiveLevel.Neutral);
		mi2_p_p3.setClearLevel(ClearLevel.Unclear);
		mi2_p_p3.setLongLevel(LongLevel.Concise);
		mi2_p_p3.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		mi2_p_p3.setPhraseActor(pa1);
		mi2_p_p3.setExpression("A lot.");
		JpaManager.persist(mi2_p_p3);
		
		mi2.getPossibleResponsePhrases().add(mi2_p_p1);
		mi2.getPossibleResponsePhrases().add(mi2_p_p2);
		mi2.getPossibleResponsePhrases().add(mi2_p_p3);
		JpaManager.update(mi2);
	}

	
	private void fillFamilyInfo(){
		FamilyInformation fi1 = new FamilyInformation();
		fi1.setName("Marriage");
		fi1.setImportance(50);
		fi1.setPriority(20);
		fi1.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		fi1.setSuperInformation(null);
		JpaManager.persist(fi1);
		
		DoctorPhrase fi1_d_p1 = new DoctorPhrase();
		fi1_d_p1.setName("DoctorAskMarriage1");
		//fi1_d_p1.setAggressiveLevel(AggressiveLevel.Polite);
		//fi1_d_p1.setClearLevel(ClearLevel.Clear);
		//fi1_d_p1.setLongLevel(LongLevel.Normal);
		fi1_d_p1.setEffTrust(5.0);
		fi1_d_p1.setEffDisturbance(10.0);
	//	fi1_d_p1.setvalClarity(1.0);
		fi1_d_p1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi1_d_p1.setPhraseActor(da1);
		fi1_d_p1.setExpression("Are you married?");
		JpaManager.persist(fi1_d_p1);
		
		DoctorPhrase fi1_d_p2 = new DoctorPhrase();
		fi1_d_p2.setName("DoctorAskMarriage2");
		//fi1_d_p2.setAggressiveLevel(AggressiveLevel.Aggressive);
		//fi1_d_p2.setClearLevel(ClearLevel.Clear);
		//fi1_d_p2.setLongLevel(LongLevel.Normal);
		fi1_d_p2.setEffTrust(-5.0);
		fi1_d_p2.setEffDisturbance(-5.0);
	//	fi1_d_p2.setvalClarity(1.0);
		fi1_d_p2.setPrimitiveType(APhrase.PrimitiveType.ClosedQuestion);
		fi1_d_p2.setPhraseActor(da1);
		fi1_d_p2.setExpression("You live with your family or alone?");
		JpaManager.persist(fi1_d_p2);
		
		fi1.getPossibleAskPhrases().add(fi1_d_p1);
		fi1.getPossibleAskPhrases().add(fi1_d_p2);
		JpaManager.update(fi1);
		
		PatientPhrase fi1_p_p1 = new PatientPhrase();
		fi1_p_p1.setName("PatientAnswerMarriage1");
		fi1_p_p1.setAggressiveLevel(AggressiveLevel.Polite);
		fi1_p_p1.setClearLevel(ClearLevel.Clear);
		fi1_p_p1.setLongLevel(LongLevel.Normal);
		fi1_p_p1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi1_p_p1.setPhraseActor(pa1);
		fi1_p_p1.setExpression("Oh, I'm married.");
		JpaManager.persist(fi1_p_p1);
		
		PatientPhrase fi1_p_p2 = new PatientPhrase();
		fi1_p_p2.setName("PatientAnswerMarriage2");
		fi1_p_p2.setAggressiveLevel(AggressiveLevel.Polite);
		fi1_p_p2.setClearLevel(ClearLevel.Unclear);
		fi1_p_p2.setLongLevel(LongLevel.TooLong);
		fi1_p_p2.setPrimitiveType(PrimitiveType.Statement);
		fi1_p_p2.setPhraseActor(pa1);
		fi1_p_p2.setExpression("It's unbelievable to live alone at my age, don't you think so?");
		JpaManager.persist(fi1_p_p2);
		
		fi1.getPossibleResponsePhrases().add(fi1_p_p1);
		fi1.getPossibleResponsePhrases().add(fi1_p_p2);
		JpaManager.update(fi1);
		
		FamilyInformation fi2 = new FamilyInformation();
		fi2.setName("Children");
		fi2.setImportance(80);
		fi2.setPriority(90);
		fi2.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		fi2.setSuperInformation(fi1);
		JpaManager.persist(fi2);
		
		DoctorPhrase fi2_d_p1 = new DoctorPhrase();
		fi2_d_p1.setName("DoctorAskChildren1");
		//fi2_d_p1.setAggressiveLevel(AggressiveLevel.Polite);
		//fi2_d_p1.setClearLevel(ClearLevel.Clear);
		//fi2_d_p1.setLongLevel(LongLevel.Normal);
		fi2_d_p1.setEffTrust(10.0);
		fi2_d_p1.setEffDisturbance(0.0);
	//	fi2_d_p1.setvalClarity(1.0);
		fi2_d_p1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi2_d_p1.setPhraseActor(da1);
		fi2_d_p1.setExpression("Do you have any child?");
		JpaManager.persist(fi2_d_p1);
		
		DoctorPhrase fi2_d_p2 = new DoctorPhrase();
		fi2_d_p2.setName("DoctorAskChildren2");
		//fi2_d_p2.setAggressiveLevel(AggressiveLevel.Aggressive);
		//fi2_d_p2.setClearLevel(ClearLevel.Clear);
		//fi2_d_p2.setLongLevel(LongLevel.Normal);
		fi2_d_p2.setEffTrust(0.0);
		fi2_d_p2.setEffDisturbance(5.0);
	//	fi2_d_p2.setvalClarity(1.0);
		fi2_d_p2.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi2_d_p2.setPhraseActor(da1);
		fi2_d_p2.setExpression("Don't you have any child?");
		JpaManager.persist(fi2_d_p2);
		
		fi2.getPossibleAskPhrases().add(fi2_d_p1);
		fi2.getPossibleAskPhrases().add(fi2_d_p2);
		JpaManager.update(fi2);
		
		PatientPhrase fi2_p_p1 = new PatientPhrase();
		fi2_p_p1.setName("PatientAnswerChildren1");
		fi2_p_p1.setAggressiveLevel(AggressiveLevel.Polite);
		fi2_p_p1.setClearLevel(ClearLevel.Clear);
		fi2_p_p1.setLongLevel(LongLevel.Normal);
		fi2_p_p1.setPrimitiveType(PrimitiveType.Statement);
		fi2_p_p1.setPhraseActor(pa1);
		fi2_p_p1.setExpression("Yes, I've 1 son and 1 daughter.");
		JpaManager.persist(fi2_p_p1);
		
		PatientPhrase fi2_p_p2 = new PatientPhrase();
		fi2_p_p2.setName("PatientAnswerChildren2");
		fi2_p_p2.setAggressiveLevel(AggressiveLevel.Polite);
		fi2_p_p2.setClearLevel(ClearLevel.Unclear);
		fi2_p_p2.setLongLevel(LongLevel.TooLong);
		fi2_p_p2.setPrimitiveType(PrimitiveType.Statement);
		fi2_p_p2.setPhraseActor(pa1);
		fi2_p_p2.setExpression("Of course, I have some children. I've 1 son and 1 daughter.");
		JpaManager.persist(fi2_p_p2);
		
		fi2.getPossibleResponsePhrases().add(fi2_p_p1);
		fi2.getPossibleResponsePhrases().add(fi2_p_p2);
		JpaManager.update(fi2);
	}	
	*/
	
	

	private void fillFI1CategorieDog(){
		fi1 = new FamilyInformation();
		fi1.setName("Dog");
		fi1.setImportance(50);
		fi1.setPriority(20);
		fi1.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		fi1.setSuperInformation(null);
		JpaManager.persist(fi1);
		
		//Pair 1 + Pair 2
		fi1p1 = new Pair();
		fi1p1.setName(fi1.getName()+"P1");
		fi1p1.persist();
		fi1p2 = new Pair();
		fi1p2.setName(fi1.getName()+"P2");
		fi1p2.persist();
		
		fi1.getPairs().add(fi1p1);
		fi1.getPairs().add(fi1p2);
		fi1.update();
		
		// Phrases for FI1 P1
			
		DoctorPhrase fi1_p1dp1 = new DoctorPhrase();
		fi1_p1dp1.setName("DoctorAskDog1P1");
		fi1_p1dp1.setEffTrust(-5.0);
		fi1_p1dp1.setEffDisturbance(10.0);
		fi1_p1dp1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi1_p1dp1.setPhraseActor(da1);
		fi1_p1dp1.setExpression("Vous baladez toujours avec votre chien ? C’est un caniche, non ?");
		JpaManager.persist(fi1_p1dp1);
		

		PatientPhrase fi1_p1pp1 = new PatientPhrase();
		fi1_p1pp1.setName("PatientAnswerDog1P1");
		fi1_p1pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		fi1_p1pp1.setClearLevel(ClearLevel.Clear);
		fi1_p1pp1.setLongLevel(LongLevel.Normal);
		fi1_p1pp1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi1_p1pp1.setPhraseActor(pa1);
		fi1_p1pp1.setExpression("(frustré) Il est mort il y a quelque mois..");
		JpaManager.persist(fi1_p1pp1);
		
		PatientPhrase fi1_p1pp2 = new PatientPhrase();
		fi1_p1pp2.setName("PatientAnswerDog2P1");
		fi1_p1pp2.setAggressiveLevel(AggressiveLevel.Aggressive);
		fi1_p1pp2.setClearLevel(ClearLevel.Clear);
		fi1_p1pp2.setLongLevel(LongLevel.Normal);
		fi1_p1pp2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi1_p1pp2.setPhraseActor(pa1);
		fi1_p1pp2.setExpression("(Fâché) Vous ne savez pas qu’il est mort ?! ");
		JpaManager.persist(fi1_p1pp2);
		
		fi1p1.getPossibleDoctorPhrases().add(fi1_p1dp1);
		fi1p1.getPossiblePatientPhrases().add(fi1_p1pp1);
		fi1p1.getPossiblePatientPhrases().add(fi1_p1pp2);
		JpaManager.update(fi1p1);
		
		//Phrases for FI1 P2
		
		DoctorPhrase fi1_p2dp1 = new DoctorPhrase();
		fi1_p2dp1.setName("DoctorAskDog1P2");
		fi1_p2dp1.setEffTrust(5.0);
		fi1_p2dp1.setEffDisturbance(0.0);
		fi1_p2dp1.setPrimitiveType(PrimitiveType.Confirmation);
		fi1_p2dp1.setPhraseActor(da1);
		fi1_p2dp1.setExpression("Ah je suis désolé.");
		JpaManager.persist(fi1_p2dp1);
		
		DoctorPhrase fi1_p2dp2 = new DoctorPhrase();
		fi1_p2dp2.setName("DoctorAskDog2P2");
		fi1_p2dp2.setEffTrust(-5.0);
		fi1_p2dp2.setEffDisturbance(5.0);
		fi1_p2dp2.setPrimitiveType(PrimitiveType.Confirmation);
		fi1_p2dp2.setPhraseActor(da1);
		fi1_p2dp2.setExpression("Ah bon ? ");
		JpaManager.persist(fi1_p2dp2);
				
		PatientPhrase fi1_p2pp1 = new PatientPhrase();
		fi1_p2pp1.setName("PatientAnswerDog1P2");
		fi1_p2pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		fi1_p2pp1.setClearLevel(ClearLevel.Clear);
		fi1_p2pp1.setLongLevel(LongLevel.Normal);
		fi1_p2pp1.setPrimitiveType(PrimitiveType.Confirmation);
		fi1_p2pp1.setPhraseActor(pa1);
		fi1_p2pp1.setExpression("(triste) Ça va.");
		JpaManager.persist(fi1_p2pp1);
	
		fi1p2.getPossibleDoctorPhrases().add(fi1_p2dp1);
		fi1p2.getPossibleDoctorPhrases().add(fi1_p2dp2);	
		fi1p2.getPossiblePatientPhrases().add(fi1_p2pp1);
		JpaManager.update(fi1p2);
		///
		
		// FamilyInfo FI11 (child of FI1)
		fi11 = new FamilyInformation();
		fi11.setName("DogReason");
		fi11.setImportance(50);
		fi11.setPriority(20);
		fi11.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		fi11.setSuperInformation(fi1);
		JpaManager.persist(fi11);
		
		//Add Pair1+Pair2
		
		fi11p1 = new Pair();
		fi11p1.setName(fi11.getName()+"P1");
		fi11p1.persist();
		fi11p2 = new Pair();
		fi11p2.setName(fi11.getName()+"P2");
		fi11p2.persist();
		
		fi11.getPairs().add(fi11p1);
		fi11.getPairs().add(fi11p2);
		fi11.update();
		
	
		
		// Phrases Pair 1
		DoctorPhrase fi11_p1dp1 = new DoctorPhrase();
		fi11_p1dp1.setName("DoctorAskDogReason1P1");
		fi11_p1dp1.setEffTrust(5.0);
		fi11_p1dp1.setEffDisturbance(5.0);
		fi11_p1dp1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi11_p1dp1.setPhraseActor(da1);
		fi11_p1dp1.setExpression("Comment ça ?");
		JpaManager.persist(fi11_p1dp1);
		
		DoctorPhrase fi11_p1dp2 = new DoctorPhrase();
		fi11_p1dp2.setName("DoctorAskDogReason2P1");
		fi11_p1dp2.setEffTrust(5.0);
		fi11_p1dp2.setEffDisturbance(5.0);
		fi11_p1dp2.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi11_p1dp2.setPhraseActor(da1);
		fi11_p1dp2.setExpression("Qu’est ce qui s’est passé ?");
		JpaManager.persist(fi11_p1dp2);

		PatientPhrase fi11_p1pp1 = new PatientPhrase();
		fi11_p1pp1.setName("PatientAnswerDogReason1P1");
		fi11_p1pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		fi11_p1pp1.setClearLevel(ClearLevel.Clear);
		fi11_p1pp1.setLongLevel(LongLevel.TooLong);
		fi11_p1pp1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi11_p1pp1.setPhraseActor(pa1);
		fi11_p1pp1.setExpression("(triste) C’était brusque.  Blablabla...");
		JpaManager.persist(fi11_p1pp1);
		
		PatientPhrase fi11_p1pp2 = new PatientPhrase();
		fi11_p1pp2.setName("PatientAnswerDogReason2P1");
		fi11_p1pp2.setAggressiveLevel(AggressiveLevel.Aggressive);
		fi11_p1pp2.setClearLevel(ClearLevel.Clear);
		fi11_p1pp2.setLongLevel(LongLevel.TooLong);
		fi11_p1pp2.setPrimitiveType(PrimitiveType.Disagree);
		fi11_p1pp2.setPhraseActor(pa1);
		fi11_p1pp2.setExpression("(fâché) Alors docteur, on peut commencer à traiter mes problèmes au lieu de parler mon ancien chien ?");
		JpaManager.persist(fi11_p1pp2);
		
		PatientPhrase fi11_p1pp3 = new PatientPhrase();
		fi11_p1pp3.setName("PatientAnswerDogReason3P1");
		fi11_p1pp3.setAggressiveLevel(AggressiveLevel.Neutral);
		fi11_p1pp3.setClearLevel(ClearLevel.Clear);
		fi11_p1pp3.setLongLevel(LongLevel.TooLong);
		fi11_p1pp3.setPrimitiveType(PrimitiveType.Disagree);
		fi11_p1pp3.setPhraseActor(pa1);
		fi11_p1pp3.setExpression("(triste) Je ne vois pas trop la relation avec mon problème mais je ne veux plus parler sur ça.");
		JpaManager.persist(fi11_p1pp3);
		
		//add phrase to pair
		fi11p1.getPossibleDoctorPhrases().add(fi11_p1dp1);
		fi11p1.getPossibleDoctorPhrases().add(fi11_p1dp2);
		fi11p1.getPossiblePatientPhrases().add(fi11_p1pp1);
		fi11p1.getPossiblePatientPhrases().add(fi11_p1pp2);
		fi11p1.getPossiblePatientPhrases().add(fi11_p1pp3);

		fi11p1.update();
		
		//Pair 2
	
		DoctorPhrase fi11_p2dp1 = new DoctorPhrase();
		fi11_p2dp1.setName("DoctorAskDogReason1P2");
		fi11_p2dp1.setEffTrust(5.0);
		fi11_p2dp1.setEffDisturbance(-5.0);
		fi11_p2dp1.setPrimitiveType(PrimitiveType.Confirmation);
		fi11_p2dp1.setPhraseActor(da1);
		fi11_p2dp1.setExpression("C’était triste, pauvre chien. Mais il faut quand même faire un peu plus de la marche, ou quoi que soit.");
		JpaManager.persist(fi11_p2dp1);
		
		DoctorPhrase fi11_p2dp2 = new DoctorPhrase();
		fi11_p2dp2.setName("DoctorAskDogReason2P2");
		fi11_p2dp2.setEffTrust(-5.0);
		fi11_p2dp2.setEffDisturbance(5.0);
		fi11_p2dp2.setPrimitiveType(PrimitiveType.Confirmation);
		fi11_p2dp2.setPhraseActor(da1);
		fi11_p2dp2.setExpression("Ah oui? Dommage");
		JpaManager.persist(fi11_p2dp2);
		
		PatientPhrase fi11_p2pp1 = new PatientPhrase();
		fi11_p2pp1.setName("PatientAnswerDogReasonP2");
		fi11_p2pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		fi11_p2pp1.setClearLevel(ClearLevel.Clear);
		fi11_p2pp1.setLongLevel(LongLevel.Normal);
		fi11_p2pp1.setPrimitiveType(PrimitiveType.Confirmation);
		fi11_p2pp1.setPhraseActor(pa1);
		fi11_p2pp1.setExpression("(triste) Ouais, je sais, mais c’est dur..");
		JpaManager.persist(fi11_p2pp1);
		
		//Add Phrases to Pair 2
		fi11p2.getPossibleDoctorPhrases().add(fi11_p2dp1);
		fi11p2.getPossibleDoctorPhrases().add(fi11_p2dp2);
		
		fi11p2.getPossiblePatientPhrases().add(fi11_p2pp1);
		// same in case of Refuse with Pair1
		fi11p2.getPossiblePatientPhrases().add(fi11_p1pp2);
		fi11p2.getPossiblePatientPhrases().add(fi11_p1pp3);

		fi11p2.update();
		
	}
	private void fillFI2CategorieGarden(){
		// node Garden info FI2
		fi2 = new FamilyInformation();
		fi2.setName("Garden");
		fi2.setImportance(50);
		fi2.setPriority(20);
		fi2.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		fi2.setSuperInformation(null);
		JpaManager.persist(fi2);
		
		//Add Pair1+Pair2
		
		fi2p1 = new Pair();
		fi2p1.setName(fi2.getName()+"P1");
		fi2p1.persist();
		fi2p2 = new Pair();
		fi2p2.setName(fi2.getName()+"P2");
		fi2p2.persist();
		
		fi2.getPairs().add(fi2p1);
		fi2.getPairs().add(fi2p2);
		fi2.update();
				
		// Phrases for Pair 1
		DoctorPhrase fi2_p1dp1 = new DoctorPhrase();
		fi2_p1dp1.setName("DoctorAskGardenP1");
		fi2_p1dp1.setEffTrust(5.0);
		fi2_p1dp1.setEffDisturbance(-5.0);
		fi2_p1dp1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi2_p1dp1.setPhraseActor(da1);
		fi2_p1dp1.setExpression("Sinon vous continuez à jardiner ? Vous jardinez, c’est bien ça ?");
		JpaManager.persist(fi2_p1dp1);
		
		PatientPhrase fi2_p1pp1 = new PatientPhrase();
		fi2_p1pp1.setName("PatientAnsweGarden1P1");
		fi2_p1pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		fi2_p1pp1.setClearLevel(ClearLevel.Clear);
		fi2_p1pp1.setLongLevel(LongLevel.Normal);
		fi2_p1pp1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi2_p1pp1.setPhraseActor(pa1);
		fi2_p1pp1.setExpression("Oui, autant que possible...");
		JpaManager.persist(fi2_p1pp1);
		
		PatientPhrase fi2_p1pp2 = new PatientPhrase();
		fi2_p1pp2.setName("PatientAnsweGarden2P1");
		fi2_p1pp2.setAggressiveLevel(AggressiveLevel.Neutral);
		fi2_p1pp2.setClearLevel(ClearLevel.Clear);
		fi2_p1pp2.setLongLevel(LongLevel.Concise);
		fi2_p1pp2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi2_p1pp2.setPhraseActor(pa1);
		fi2_p1pp2.setExpression("Oui.");
		JpaManager.persist(fi2_p1pp2);
		
		fi2p1.getPossibleDoctorPhrases().add(fi2_p1dp1);
		fi2p1.getPossiblePatientPhrases().add(fi2_p1pp1);
		fi2p1.getPossiblePatientPhrases().add(fi2_p1pp2);
		fi2p1.update();
		
		//Phrases for Pair 2
		DoctorPhrase fi2_p2dp1 = new DoctorPhrase();
		fi2_p2dp1.setName("DoctorAskGarden1P2");
		fi2_p2dp1.setEffTrust(5.0);
		fi2_p2dp1.setEffDisturbance(-5.0);
		fi2_p2dp1.setPrimitiveType(PrimitiveType.Confirmation);
		fi2_p2dp1.setPhraseActor(da1);
		fi2_p2dp1.setExpression("(Hochement de la tête) en hein");
		JpaManager.persist(fi2_p2dp1);
		
		DoctorPhrase fi2_p2dp2 = new DoctorPhrase();
		fi2_p2dp2.setName("DoctorAskGarden2P2");
		fi2_p2dp2.setEffTrust(0.0);
		fi2_p2dp2.setEffDisturbance(-5.0);
		fi2_p2dp2.setPrimitiveType(PrimitiveType.ClosedQuestion);
		fi2_p2dp2.setPhraseActor(da1);
		fi2_p2dp2.setExpression("Vous pouvez me dire plus ?");
		JpaManager.persist(fi2_p2dp2);
		
		PatientPhrase fi2_p2pp1 = new PatientPhrase();
		fi2_p2pp1.setName("PatientAnsweGarden1P2");
		fi2_p2pp1.setAggressiveLevel(AggressiveLevel.Polite);
		fi2_p2pp1.setClearLevel(ClearLevel.Clear);
		fi2_p2pp1.setLongLevel(LongLevel.TooLong);
		fi2_p2pp1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi2_p2pp1.setPhraseActor(pa1);
		fi2_p2pp1.setExpression("J’ai des tomates à planter, vous comprenez. Je dois me pencher et franchement, c’est dur...");
		JpaManager.persist(fi2_p2pp1);
		
		PatientPhrase fi2_p2pp2 = new PatientPhrase();
		fi2_p2pp2.setName("PatientAnsweGarden2P2");
		fi2_p2pp2.setAggressiveLevel(AggressiveLevel.Neutral);
		fi2_p2pp2.setClearLevel(ClearLevel.Clear);
		fi2_p2pp2.setLongLevel(LongLevel.Concise);
		fi2_p2pp2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		fi2_p2pp2.setPhraseActor(pa1);
		fi2_p2pp2.setExpression("Ben je plante. C’est dur.");
		JpaManager.persist(fi2_p2pp2);
		fi2p2.getPossibleDoctorPhrases().add(fi2_p2dp1);
		fi2p2.getPossibleDoctorPhrases().add(fi2_p2dp2);
		fi2p2.getPossiblePatientPhrases().add(fi2_p2pp1);
		fi2p2.getPossiblePatientPhrases().add(fi2_p2pp2);
		fi2p2.update();
		
	}
	
	private void fillFamilyInfo(){
		//FamilyInfo 1
		fillFI1CategorieDog();
		//FamilyInfo 2
		fillFI2CategorieGarden();	
	}
	
	// Medical Info
	private void fillMS1MIConsultReason0(){
		
	}
	private void fillMS1MIConsultReason1(){
		ms1mi1 = new MedicalInformation();
		ms1mi1.setName("ConsultReason1");
		ms1mi1.setImportance(100);
		ms1mi1.setPriority(90);
		ms1mi1.setAcquiringMethod(APatientInformation.AcquiringMethod.ConsultToFile);
		ms1mi1.setSuperInformation(null);
		ms1mi1.persist();
		
		ms1.getMedicalInfos().add(ms1mi1);
	
		
		fillMS1MI1Pair();	
		
		
	}
	DoctorPhrase ms1mi1p1dpOQ1 = null;
	DoctorPhrase ms1mi1p1dpOQ2 = null; 
	
	//Refuse: no info
	PatientPhrase ms1mi1p1pp3 = null;
	PatientPhrase ms1mi1p1pp4 = null;
	PatientPhrase ms1mi1p1pp5 = null;
	private void fillMS1MI1Pair(){
	
		//Add Pair1
		
		ms1mi1p1 = new Pair();
		ms1mi1p1.setName(ms1mi1.getName()+"P1");
		ms1mi1p1.persist();
		
		ms1mi1.getPairs().add(ms1mi1p1);
		ms1mi1.update();
		
		// Phrases for Pair 1
		//open question
		ms1mi1p1dpOQ1 = new DoctorPhrase();
		ms1mi1p1dpOQ1.setName("DoctorAskConsultReason_OQ1");
		ms1mi1p1dpOQ1.setEffTrust(0.0);
		ms1mi1p1dpOQ1.setEffDisturbance(0.0);
		ms1mi1p1dpOQ1.setPrimitiveType(PrimitiveType.OpenQuestion);
		ms1mi1p1dpOQ1.setPhraseActor(da1);
		ms1mi1p1dpOQ1.setExpression("Je vous écoute.");
		JpaManager.persist(ms1mi1p1dpOQ1);
		
		ms1mi1p1dpOQ2 = new DoctorPhrase();
		ms1mi1p1dpOQ2.setName("DoctorAskConsultReason_OQ2");
		ms1mi1p1dpOQ2.setEffTrust(0.0);
		ms1mi1p1dpOQ2.setEffDisturbance(0.0);
		ms1mi1p1dpOQ2.setPrimitiveType(PrimitiveType.OpenQuestion);
		ms1mi1p1dpOQ2.setPhraseActor(da1);
		ms1mi1p1dpOQ2.setExpression("Dites-moi pourquoi vous êtes venu.");
		JpaManager.persist(ms1mi1p1dpOQ2);
		
		
		DoctorPhrase ms1mi1p1dp1 = new DoctorPhrase();
		ms1mi1p1dp1.setName("DoctorAskConsultReason11");
		ms1mi1p1dp1.setEffTrust(5.0);
		ms1mi1p1dp1.setEffDisturbance(-5.0);
		ms1mi1p1dp1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		ms1mi1p1dp1.setPhraseActor(da1);
		ms1mi1p1dp1.setExpression("Je vois que vous avez des ordonnances qui arrivent à expiration, il faut peut-être les renouveler...");
		JpaManager.persist(ms1mi1p1dp1);
		
		DoctorPhrase ms1mi1p1dp2 = new DoctorPhrase();
		ms1mi1p1dp2.setName("DoctorAskConsultReason12");
		ms1mi1p1dp2.setEffTrust(5.0);
		ms1mi1p1dp2.setEffDisturbance(5.0);
		ms1mi1p1dp2.setPrimitiveType(PrimitiveType.ClosedQuestion);
		ms1mi1p1dp2.setPhraseActor(da1);
		ms1mi1p1dp2.setExpression("Vous ne devrez pas renouveler votre ordonnance par hasard ?");
		JpaManager.persist(ms1mi1p1dp2);
		
		PatientPhrase ms1mi1p1pp1 = new PatientPhrase();
		ms1mi1p1pp1.setName("PatientAnsweConsultReason11");
		ms1mi1p1pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi1p1pp1.setClearLevel(ClearLevel.Clear);
		ms1mi1p1pp1.setLongLevel(LongLevel.Normal);
		ms1mi1p1pp1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		ms1mi1p1pp1.setPhraseActor(pa1);
		ms1mi1p1pp1.setExpression("C’est pour renouveler l’ordonnance pour mon diabète.");
		JpaManager.persist(ms1mi1p1pp1);
		
		PatientPhrase ms1mi1p1pp2 = new PatientPhrase();
		ms1mi1p1pp2.setName("PatientAnsweConsultReason12");
		ms1mi1p1pp2.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi1p1pp2.setClearLevel(ClearLevel.Unclear);
		ms1mi1p1pp2.setLongLevel(LongLevel.Normal);
		ms1mi1p1pp2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		ms1mi1p1pp2.setPhraseActor(pa1);
		ms1mi1p1pp2.setExpression("Alors j’ai mon ordonnance qui sera expirée bientôt.");
		JpaManager.persist(ms1mi1p1pp2);
		
		PatientPhrase ms1mi1p1pp3 = new PatientPhrase();
		ms1mi1p1pp3.setName("PatientAnsweConsultReasonDeny1");
		ms1mi1p1pp3.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi1p1pp3.setClearLevel(ClearLevel.Unclear);
		ms1mi1p1pp3.setLongLevel(LongLevel.Normal);
		ms1mi1p1pp3.setPrimitiveType(PrimitiveType.Disagree);
		ms1mi1p1pp3.setPhraseActor(pa1);
		ms1mi1p1pp3.setExpression("Non pas grand-chose, attendez que je me souvienne....");
		JpaManager.persist(ms1mi1p1pp3);
		
		PatientPhrase ms1mi1p1pp4 = new PatientPhrase();
		ms1mi1p1pp4.setName("PatientAnsweConsultReasonDeny2");
		ms1mi1p1pp4.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi1p1pp4.setClearLevel(ClearLevel.Unclear);
		ms1mi1p1pp4.setLongLevel(LongLevel.Normal);
		ms1mi1p1pp4.setPrimitiveType(PrimitiveType.Disagree);
		ms1mi1p1pp4.setPhraseActor(pa1);
		ms1mi1p1pp4.setExpression("Bon... euh.... je me suis dit que ça faisait longtemps que j'étais pas venu....  ");
		JpaManager.persist(ms1mi1p1pp4);
		
		PatientPhrase ms1mi1p1pp5 = new PatientPhrase();
		ms1mi1p1pp5.setName("PatientAnsweConsultReasonDeny3");
		ms1mi1p1pp5.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi1p1pp5.setClearLevel(ClearLevel.Unclear);
		ms1mi1p1pp5.setLongLevel(LongLevel.Normal);
		ms1mi1p1pp5.setPrimitiveType(PrimitiveType.Disagree);
		ms1mi1p1pp5.setPhraseActor(pa1);
		ms1mi1p1pp5.setExpression("Bah non rien de spécial, le train-train quoi.");
		JpaManager.persist(ms1mi1p1pp5);
		
		ms1mi1p1.getPossibleDoctorPhrases().add(ms1mi1p1dpOQ1);
		ms1mi1p1.getPossibleDoctorPhrases().add(ms1mi1p1dpOQ2);
		
		ms1mi1p1.getPossibleDoctorPhrases().add(ms1mi1p1dp1);
		ms1mi1p1.getPossibleDoctorPhrases().add(ms1mi1p1dp2);
		ms1mi1p1.getPossiblePatientPhrases().add(ms1mi1p1pp1);
		ms1mi1p1.getPossiblePatientPhrases().add(ms1mi1p1pp2);
		ms1mi1p1.getPossiblePatientPhrases().add(ms1mi1p1pp3);
		ms1mi1p1.getPossiblePatientPhrases().add(ms1mi1p1pp4);
		ms1mi1p1.getPossiblePatientPhrases().add(ms1mi1p1pp5);
		ms1mi1p1.update();
		

		
	}
	private void fillMS1MIConsultReason2(){
		ms1mi2 = new MedicalInformation();
		ms1mi2.setName("ConsultReason2");
		ms1mi2.setImportance(50);
		ms1mi2.setPriority(100);
		ms1mi2.setAcquiringMethod(APatientInformation.AcquiringMethod.ConsultToFile);
		ms1mi2.setSuperInformation(null);
		ms1mi2.persist();
		

		ms1.getMedicalInfos().add(ms1mi2);
		
		
		fillMS1MI2Pair();	
		
	}
	private void fillMS1MI2Pair(){
		//Add Pair1
		
		ms1mi2p1 = new Pair();
		ms1mi2p1.setName(ms1mi2.getName()+"P1");
		ms1mi2p1.persist();
		
		ms1mi2.getPairs().add(ms1mi2p1);
		ms1mi2.update();
		
		// Phrases for Pair 1
		DoctorPhrase ms1mi2p1dp1 = new DoctorPhrase();
		ms1mi2p1dp1.setName("DoctorAskConsultReason21");
		ms1mi2p1dp1.setEffTrust(0.0);
		ms1mi2p1dp1.setEffDisturbance(-5.0);
		ms1mi2p1dp1.setPrimitiveType(PrimitiveType.ClosedQuestion);
		ms1mi2p1dp1.setPhraseActor(da1);
		ms1mi2p1dp1.setExpression("Donc on fait juste un petit bilan ?");
		JpaManager.persist(ms1mi2p1dp1);
			
		PatientPhrase ms1mi2p1pp1 = new PatientPhrase();
		ms1mi2p1pp1.setName("PatientAnsweConsultReason21");
		ms1mi2p1pp1.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi2p1pp1.setClearLevel(ClearLevel.Clear);
		ms1mi2p1pp1.setLongLevel(LongLevel.Normal);
		ms1mi2p1pp1.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		ms1mi2p1pp1.setPhraseActor(pa1);
		ms1mi2p1pp1.setExpression("Pour vous me examinez un peu...");
		JpaManager.persist(ms1mi2p1pp1);
		
		PatientPhrase ms1mi2p1pp2 = new PatientPhrase();
		ms1mi2p1pp2.setName("PatientAnsweConsultReason22");
		ms1mi2p1pp2.setAggressiveLevel(AggressiveLevel.Neutral);
		ms1mi2p1pp2.setClearLevel(ClearLevel.Clear);
		ms1mi2p1pp2.setLongLevel(LongLevel.TooLong);
		ms1mi2p1pp2.setPrimitiveType(PrimitiveType.AnswerWithInfo);
		ms1mi2p1pp2.setPhraseActor(pa1);
		ms1mi2p1pp2.setExpression("Pour faire un contrôle ? Vous me dites de revenir de temps en temps..");
		JpaManager.persist(ms1mi2p1pp2);
		
		ms1mi2p1.getPossibleDoctorPhrases().add(ms1mi2p1dp1);
		ms1mi2p1.getPossiblePatientPhrases().add(ms1mi2p1pp1);
		ms1mi2p1.getPossiblePatientPhrases().add(ms1mi2p1pp2);
		
		//add OpenQuestion already defined for Reason 1
		ms1mi2p1.getPossibleDoctorPhrases().add(ms1mi1p1dpOQ1);
		ms1mi2p1.getPossibleDoctorPhrases().add(ms1mi1p1dpOQ2);
		
		//add Refuse Answers of patient
		ms1mi2p1.getPossiblePatientPhrases().add(ms1mi1p1pp3);
		ms1mi2p1.getPossiblePatientPhrases().add(ms1mi1p1pp4);
		ms1mi2p1.getPossiblePatientPhrases().add(ms1mi1p1pp5);
		
		
		ms1mi2p1.update();
		
	}

	private void fillMedicalInfo(){
		//Medical Info 1: consultation reasons
		fillMS1MIConsultReason1();
		fillMS1MIConsultReason2();
		
	}
	public void fillDatabase() {
		// PhraseActors
		fillActors();
		
		// Scenarios
		fillScenario();
		
	}

	public static void main(String[] args) {
		Controller ctl = new Controller();
		if (ctl.needFillingDatabase()) {
			ctl.fillDatabase();
		}
		List<Scenario> scenarios = JpaManager.<Scenario> findWithNamedQuery("Scenario.findAll", null);
		System.out.println("" + scenarios.size());
		System.out.println(scenarios.get(0).getName());
		List<DoctorActor> doctors = JpaManager.<DoctorActor> findWithNamedQuery("DoctorActor.findAll", null);
		System.out.println("" + doctors.size());
		System.out.println(doctors.get(0).getName());
		List<PatientActor> patients = JpaManager.<PatientActor> findWithNamedQuery("PatientActor.findAll", null);
		System.out.println("" + patients.size());
		System.out.println(patients.get(0).getName());

		JMain jm = new JMain("AgileDoctorDB 1.0.0-1");
		jm.setVisible(true);
		//ModelManager mm = new ModelManager(new JModelManagement("AgileDoctorDB Model Management"), null);
		//mm.initilizeControl();
		//Play pl = new Play(new JPlay("AgileDoctorDB Play"), null);
		//pl.initilizeControl();
		
	/*	GameEngine ge = new GameEngine();
		ge.simulate();*/
	}

}

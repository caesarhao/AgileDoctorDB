package control;

import java.util.List;

import engine.GameEngine;
import jpa.JpaManager;
import model.*;
import view.*;

public class Controller {
	public static boolean needFillingDatabase() {
		List<Scenario> scenarios = JpaManager.<Scenario> findWithNamedQuery("Scenario.findAll", null);
		if (scenarios.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public static void fillDatabase() {
		// Scenarios
		Scenario s1 = new Scenario();
		s1.setName("DemoSenario");
		JpaManager.persist(s1);
		
		// PhraseActors, doctors.
		DoctorActor da1 = new DoctorActor();
		da1.setName("Dupont");
		da1.setSex(true);
		JpaManager.persist(da1);
		//DoctorActor da2 = new DoctorActor();
		//da2.setName("Vidal");
		//JpaManager.persist(da2);
		
		// PhraseActors, patients.
		PatientActor pa1 = new PatientActor();
		pa1.setName("Cathy");
		pa1.setSex(false);
		pa1.setAggressiveLevel(50);
		pa1.setClearLevel(60);
		pa1.setConfidentLevel(70);
		pa1.setLongPhraseLevel(80);
		JpaManager.persist(pa1);
		//PatientActor pa2 = new PatientActor();
		//pa2.setName("Toto");
		//pa2.setAggressiveLevel(80);
		//pa2.setClearLevel(70);
		//pa2.setConfidentLevel(60);
		//pa2.setLongPhraseLevel(50);
		//JpaManager.persist(pa2);
		
		// MicroSequences
		MicroSequence ms0 = new MicroSequence();
		ms0.setName("Welcome");
		JpaManager.persist(ms0);
		MicroSequence ms1 = new MicroSequence();
		ms1.setName("AskReason");
		JpaManager.persist(ms1);
		MicroSequence ms2 = new MicroSequence();
		ms2.setName("InviteToPhysicExam");
		JpaManager.persist(ms2);
		MicroSequence ms3 = new MicroSequence();
		ms3.setName("QueryDuringExam");
		JpaManager.persist(ms3);
		MicroSequence ms4 = new MicroSequence();
		ms4.setName("GiveDiagnosis");
		JpaManager.persist(ms4);
		MicroSequence ms5 = new MicroSequence();
		ms5.setName("ExplainPrescription");
		JpaManager.persist(ms5);
		MicroSequence ms6 = new MicroSequence();
		ms6.setName("GiveHealthAdvice");
		JpaManager.persist(ms6);
		MicroSequence ms7 = new MicroSequence();
		ms7.setName("FixNextInterview");
		JpaManager.persist(ms7);
		MicroSequence ms8 = new MicroSequence();
		ms8.setName("Payment");
		JpaManager.persist(ms8);
		MicroSequence ms9 = new MicroSequence();
		ms9.setName("GoodBye");
		JpaManager.persist(ms9);
		
		// Add microsequences in senario.
		s1.getMicroSequences().add(ms0);
		s1.getMicroSequences().add(ms1);
		s1.getMicroSequences().add(ms2);
		s1.getMicroSequences().add(ms3);
		s1.getMicroSequences().add(ms4);
		s1.getMicroSequences().add(ms5);
		s1.getMicroSequences().add(ms6);
		s1.getMicroSequences().add(ms7);
		s1.getMicroSequences().add(ms8);
		s1.getMicroSequences().add(ms9);
		JpaManager.update(s1);
		
		// DialogueSessions
		DialogueSession ds1 = new DialogueSession();
		ds1.setName("QuerySmoking");
		JpaManager.persist(ds1);
		DialogueSession ds2 = new DialogueSession();
		ds2.setName("QueryFatigue");
		JpaManager.persist(ds2);
		
		// Phrases
		DoctorPhrase p1 = new DoctorPhrase();
		p1.setName("DoctorWelcomesPatient");
		p1.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		p1.setClearLevel(APhrase.ClearLevel.Clear);
		p1.setLongLevel(APhrase.LongLevel.Normal);
		p1.setPrimitiveType(APhrase.PrimitiveType.Statement);
		p1.setPhraseActor(da1);
		p1.setExpression("Good morning, please come in and sit down!");
		JpaManager.persist(p1);
		
		// Get Information mode, ask if the patient smokes and the frequency
		MedicalInformation mi1 = new MedicalInformation();
		mi1.setName("Smoke");
		mi1.setImportance(50);
		mi1.setPriority(60);
		mi1.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		mi1.setSuperInformation(null);
		JpaManager.persist(mi1);
		
		DoctorPhrase mi1_d_p1 = new DoctorPhrase();
		mi1_d_p1.setName("DoctorAskSmoke1");
		mi1_d_p1.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		mi1_d_p1.setClearLevel(APhrase.ClearLevel.Clear);
		mi1_d_p1.setLongLevel(APhrase.LongLevel.Normal);
		mi1_d_p1.setPrimitiveType(APhrase.PrimitiveType.CloseQuestion);
		mi1_d_p1.setPhraseActor(da1);
		mi1_d_p1.setExpression("Do you smoke?");
		JpaManager.persist(mi1_d_p1);
		
		DoctorPhrase mi1_d_p2 = new DoctorPhrase();
		mi1_d_p2.setName("DoctorAskSmoke2");
		mi1_d_p2.setAggressiveLevel(APhrase.AggressiveLevel.Aggressive);
		mi1_d_p2.setClearLevel(APhrase.ClearLevel.Clear);
		mi1_d_p2.setLongLevel(APhrase.LongLevel.Normal);
		mi1_d_p2.setPrimitiveType(APhrase.PrimitiveType.CloseQuestion);
		mi1_d_p2.setPhraseActor(da1);
		mi1_d_p2.setExpression("Do you enjoy the fucking smoking?");
		JpaManager.persist(mi1_d_p2);
		
		mi1.getPossibleAskPhrases().add(mi1_d_p1);
		mi1.getPossibleAskPhrases().add(mi1_d_p2);
		JpaManager.update(mi1);
		
		PatientPhrase mi1_p_p1 = new PatientPhrase();
		mi1_p_p1.setName("PatientAnswerSmoke1");
		mi1_p_p1.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		mi1_p_p1.setClearLevel(APhrase.ClearLevel.Clear);
		mi1_p_p1.setLongLevel(APhrase.LongLevel.Normal);
		mi1_p_p1.setPrimitiveType(APhrase.PrimitiveType.Statement);
		mi1_p_p1.setPhraseActor(pa1);
		mi1_p_p1.setExpression("Yes, I smoke.");
		JpaManager.persist(mi1_p_p1);
		
		PatientPhrase mi1_p_p2 = new PatientPhrase();
		mi1_p_p2.setName("PatientAnswerSmoke2");
		mi1_p_p2.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		mi1_p_p2.setClearLevel(APhrase.ClearLevel.Unclear);
		mi1_p_p2.setLongLevel(APhrase.LongLevel.TooLong);
		mi1_p_p2.setPrimitiveType(APhrase.PrimitiveType.Statement);
		mi1_p_p2.setPhraseActor(pa1);
		mi1_p_p2.setExpression("Smoking can help me to decrease stress, why not?");
		JpaManager.persist(mi1_p_p2);
		
		mi1.getPossibleResponsePhrases().add(mi1_p_p1);
		mi1.getPossibleResponsePhrases().add(mi1_p_p2);
		JpaManager.update(mi1);

		MedicalInformation mi2 = new MedicalInformation();
		mi2.setName("SmokeFrequency");
		mi2.setImportance(80);
		mi2.setPriority(50);
		mi2.setAcquiringMethod(APatientInformation.AcquiringMethod.AskedByDoctor);
		mi2.setSuperInformation(mi1);
		JpaManager.persist(mi2);
		//mi1.getSubInformations().add(mi2);
		//JpaManager.update(mi1);
		
		DoctorPhrase mi2_d_p1 = new DoctorPhrase();
		mi2_d_p1.setName("DoctorAskSmokeFrequency1");
		mi2_d_p1.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		mi2_d_p1.setClearLevel(APhrase.ClearLevel.Clear);
		mi2_d_p1.setLongLevel(APhrase.LongLevel.Normal);
		mi2_d_p1.setPrimitiveType(APhrase.PrimitiveType.CloseQuestion);
		mi2_d_p1.setPhraseActor(da1);
		mi2_d_p1.setExpression("Do you often smoke?");
		JpaManager.persist(mi2_d_p1);
		
		DoctorPhrase mi2_d_p2 = new DoctorPhrase();
		mi2_d_p2.setName("DoctorAskSmokeFrequency2");
		mi2_d_p2.setAggressiveLevel(APhrase.AggressiveLevel.Aggressive);
		mi2_d_p2.setClearLevel(APhrase.ClearLevel.Clear);
		mi2_d_p2.setLongLevel(APhrase.LongLevel.Normal);
		mi2_d_p2.setPrimitiveType(APhrase.PrimitiveType.CloseQuestion);
		mi2_d_p2.setPhraseActor(da1);
		mi2_d_p2.setExpression("Do you have too many cigarettes everyday?");
		JpaManager.persist(mi2_d_p2);
		
		mi2.getPossibleAskPhrases().add(mi2_d_p1);
		mi2.getPossibleAskPhrases().add(mi2_d_p2);
		JpaManager.update(mi2);
		
		PatientPhrase mi2_p_p1 = new PatientPhrase();
		mi2_p_p1.setName("PatientAnswerSmokeFrequency1");
		mi2_p_p1.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		mi2_p_p1.setClearLevel(APhrase.ClearLevel.Clear);
		mi2_p_p1.setLongLevel(APhrase.LongLevel.Normal);
		mi2_p_p1.setPrimitiveType(APhrase.PrimitiveType.Statement);
		mi2_p_p1.setPhraseActor(pa1);
		mi2_p_p1.setExpression("2 cigarettes everyday.");
		JpaManager.persist(mi2_p_p1);
		
		PatientPhrase mi2_p_p2 = new PatientPhrase();
		mi2_p_p2.setName("PatientAnswerSmokeFrequency2");
		mi2_p_p2.setAggressiveLevel(APhrase.AggressiveLevel.Polite);
		mi2_p_p2.setClearLevel(APhrase.ClearLevel.Unclear);
		mi2_p_p2.setLongLevel(APhrase.LongLevel.TooLong);
		mi2_p_p2.setPrimitiveType(APhrase.PrimitiveType.Statement);
		mi2_p_p2.setPhraseActor(pa1);
		mi2_p_p2.setExpression("Maybe 2, maybe 10, who knows?");
		JpaManager.persist(mi2_p_p2);
		
		mi2.getPossibleResponsePhrases().add(mi2_p_p1);
		mi2.getPossibleResponsePhrases().add(mi2_p_p2);
		JpaManager.update(mi2);
		
	}

	public static void main(String[] args) {
		if (needFillingDatabase()) {
			fillDatabase();
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

		//JMain jm = new JMain("xx");
		//jm.setVisible(true);
		ModelManager mm = new ModelManager(new JModelManagement("AgileDoctorDB Model Management"));
		mm.initilizeControl();
		
		GameEngine ge = new GameEngine();
		ge.simulate();
	}

}

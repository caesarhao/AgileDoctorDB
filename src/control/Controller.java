package control;

import java.util.List;

import engine.GameEngine;
import jpa.JpaManager;
import model.*;
import model.APhrase.*;
import model.PatientPhrase.*;
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
		pa1.setAggressiveLevel(80);
		pa1.setClearLevel(50);
		pa1.setConfidentLevel(50);
		pa1.setLongPhraseLevel(80);
		JpaManager.persist(pa1);
		
		PatientActor pa2 = new PatientActor();
		pa2.setName("Jovial");
		pa2.setSex(false);
		pa2.setAggressiveLevel(20);
		pa2.setClearLevel(70);
		pa2.setConfidentLevel(70);
		pa2.setLongPhraseLevel(50);
		JpaManager.persist(pa2);
		
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
		p1.setName("DoctorWelcomesPatient1");
		//p1.setAggressiveLevel(AggressiveLevel.Polite);
		//p1.setClearLevel(ClearLevel.Clear);
		//p1.setLongLevel(LongLevel.Normal);
		p1.setEffTrust(-5.0);
		p1.setEffDisturbance(0.0);
	//	p1.setvalClarity(1.0);
		p1.setPrimitiveType(PrimitiveType.Statement);
		p1.setPhraseActor(da1);
		p1.setExpression("Bonjour.");
		JpaManager.persist(p1);
		
		DoctorPhrase p2 = new DoctorPhrase();
		p2.setName("DoctorWelcomesPatient2");
		p2.setEffTrust(5.0);
		p2.setEffDisturbance(0.0);
	//	p2.setvalClarity(1.0);
		p2.setPrimitiveType(PrimitiveType.Statement);
		p2.setPhraseActor(da1);
		String title = (pa2.sex)?" M.":"Mme";
		p2.setExpression("Bonjour"+ title +pa2.name);
		JpaManager.persist(p2);
		
		DoctorPhrase p3 = new DoctorPhrase();
		p3.setName("DoctorWelcomesPatient3");
		p3.setEffTrust(10.0);
		p3.setEffDisturbance(0.0);
	//	p3.setvalClarity(1.0);
		p3.setPrimitiveType(PrimitiveType.Statement);
		p3.setPhraseActor(da1);
		p3.setExpression("Bonjour. [serrez la main]");
		JpaManager.persist(p3);
		
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
		mi1_d_p1.setPrimitiveType(PrimitiveType.CloseQuestion);
		mi1_d_p1.setPhraseActor(da1);
		mi1_d_p1.setExpression("Do you keep smoking?");
		JpaManager.persist(mi1_d_p1);
		
		DoctorPhrase mi1_d_p2 = new DoctorPhrase();
		mi1_d_p2.setName("DoctorAskSmoke2");
		mi1_d_p2.setEffTrust(-10.0);
		mi1_d_p2.setEffDisturbance(5.0);
		mi1_d_p2.setvalClarity(0.99);
		mi1_d_p2.setPrimitiveType(PrimitiveType.CloseQuestion);
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
		mi2_d_p1.setPrimitiveType(PrimitiveType.CloseQuestion);
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
		mi2_d_p2.setPrimitiveType(PrimitiveType.CloseQuestion);
		mi2_d_p2.setPhraseActor(da1);
		mi2_d_p2.setExpression("How many cigarettes do you take everyday?");
		JpaManager.persist(mi2_d_p2);
		
		DoctorPhrase mi2_d_p3 = new DoctorPhrase();
		mi2_d_p3.setName("DoctorAskSmokeFrequency3");
		mi2_d_p3.setEffTrust(-10.0);
		mi2_d_p3.setEffDisturbance(10.0);
		mi2_d_p3.setvalClarity(0.2);
		mi2_d_p3.setPrimitiveType(PrimitiveType.CloseQuestion);
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
		fi1_d_p1.setPrimitiveType(PrimitiveType.CloseQuestion);
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
		fi1_d_p2.setPrimitiveType(APhrase.PrimitiveType.CloseQuestion);
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
		fi2_d_p1.setPrimitiveType(PrimitiveType.CloseQuestion);
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
		fi2_d_p2.setPrimitiveType(PrimitiveType.CloseQuestion);
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

		JMain jm = new JMain("AgileDoctorDB 1.0.0-1");
		jm.setVisible(true);
		//ModelManager mm = new ModelManager(new JModelManagement("AgileDoctorDB Model Management"), null);
		//mm.initilizeControl();
		//Play pl = new Play(new JPlay("AgileDoctorDB Play"), null);
		//pl.initilizeControl();
		
		GameEngine ge = new GameEngine();
		ge.simulate();
	}

}

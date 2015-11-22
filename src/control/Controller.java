package control;

import java.util.List;

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
		JpaManager.persist(da1);
		DoctorActor da2 = new DoctorActor();
		da2.setName("Vidal");
		JpaManager.persist(da2);
		
		// PhraseActors, patients.
		PatientActor pa1 = new PatientActor();
		pa1.setName("Cathy");
		pa1.setAggressiveLevel(50);
		pa1.setClearLevel(60);
		pa1.setConfidentLevel(70);
		pa1.setLongPhraseLevel(80);
		JpaManager.persist(pa1);
		PatientActor pa2 = new PatientActor();
		pa2.setName("Toto");
		pa2.setAggressiveLevel(80);
		pa2.setClearLevel(70);
		pa2.setConfidentLevel(60);
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
		Phrase p1 = new Phrase();
		p1.setName("DoctorWelcomesPatient");
		p1.setAggressiveLevel(Phrase.AggressiveLevel.Polite);
		p1.setClearLevel(Phrase.ClearLevel.Clear);
		p1.setLongLevel(Phrase.LongLevel.Normal);
		p1.setPrimitiveType(Phrase.PrimitiveType.Statement);
		p1.setPhraseActor(da1);
		p1.setExpression("Good morning, please come in and sit down!");
		JpaManager.persist(p1);
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
		
	}

}

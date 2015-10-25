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
		s1.setName("s1");
		JpaManager.persist(s1);
		
		// PhraseActors.
		DoctorActor da1 = new DoctorActor();
		da1.setName("Dupont");
		JpaManager.persist(da1);
		DoctorActor da2 = new DoctorActor();
		da2.setName("Vidal");
		JpaManager.persist(da2);
		PatientActor pa1 = new PatientActor();
		pa1.setName("Cathy");
		JpaManager.persist(pa1);
		PatientActor pa2 = new PatientActor();
		pa2.setName("Toto");
		JpaManager.persist(pa2);
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
	}

}

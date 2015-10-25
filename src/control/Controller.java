package control;

import java.util.List;

import jpa.JpaManager;
import model.*;

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
		JpaManager.create(s1);
	}

	public static void main(String[] args) {
		if (needFillingDatabase()) {
			fillDatabase();
		}
		List<Scenario> scenarios = JpaManager.<Scenario> findWithNamedQuery("Scenario.findAll", null);
		System.out.println("" + scenarios.size());
		System.out.println(scenarios.get(0).getName());
	}

}

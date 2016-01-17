package engine;

public class GameEngine {
	private ScenarioParameter scPara;
	private ScenarioVariable scVar;

	public GameEngine() {
		scPara = new ScenarioParameter();
		//TODO: set the value of scPara.
		System.out.println(scPara);
		scVar = new ScenarioVariable(scPara);
		System.out.println(scVar);
	}

}

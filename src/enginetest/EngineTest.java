package enginetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import engine.GameEngine;

public class EngineTest {
	
	public static void testWithParaSet1(){
		pStatusFinal();
		sessionStatusSythese();
		pairNumber();
	}
	public void testWithParaSet2(){
		
	}
	public void testWithParaSet3(){
		
	}
	
	public static void pStatusFinal(){
		File csvFile = new File("./sStatus3.csv");
		FileWriter fWriter = null;
		
		try {
			fWriter = new FileWriter(csvFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameEngine geTitle = new GameEngine();
		try {
			fWriter.write(geTitle.outputCSVtitle()+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//-------------------test part start--------------------------
		for (int i = 0; i < 100; i++){
			GameEngine ge = new GameEngine();
	//		ge.getScPara().setPatientParameter(0.8, 0.8, 0.8, 80.0, 80.0,80.0,20.0, 0.2, 0.3);
			//ge.getScPara().setPatientParameter(0.8, 0.1, 0.1, 80.0, 0.0,0.0,80.0, 0.1, 0.3);
			ge.getScPara().setPatientParameter(0.5, 0.5, 0.5, 50.0, 50,50,50.0, 0.5, 0.5);
			ge.getScVar().initVariables();
			ge.simulateBest();
			try {
				fWriter.write(ge.outputCSVresult());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
//-------------------test part end----------------------------
		try {
			fWriter.flush();
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	public static void sessionStatusSythese(){
		
	}
	public static void pairNumber(){
		
	}

	public static void main(String[] args) {
		testWithParaSet1();
	}

}

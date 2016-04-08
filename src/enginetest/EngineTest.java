package enginetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import engine.GameEngine;

public class EngineTest {
	
	public static void testWithParaSet1(){
				
		double[] params = {0.8, 0.8, 0.8, 80.0, 80.0,80.0,20.0, 0.2, 0.3};
		sessionStatus("RandresultFinal1.csv","RandresultEach1.csv",params);
	}
	public static void testWithParaSet2(){
		double[] params = {0.8, 0.1, 0.1, 80.0, 0.0,0.0,80.0, 0.1, 0.3};
		sessionStatus("RandresultFinal2.csv","RandresultEach2.csv",params);

		
	}
	public static void testWithParaSet3(){
		double[] params = {0.5, 0.5, 0.5, 50.0, 50,50,50.0, 0.5, 0.5};
		sessionStatus("RandresultFinal3.csv","RandresultEach3.csv",params);
		
	}
	public static void sessionStatus(String file1, String file2, double[] param){
		File csvFile1 = new File(file1);
		FileWriter fWriter1 = null;
		File csvFile2 = new File(file2);
		FileWriter fWriter2 = null;
		
		try {
			fWriter1 = new FileWriter(csvFile1);
			fWriter2 = new FileWriter(csvFile2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameEngine geTitle = new GameEngine();
		try {
			fWriter1.write(geTitle.outputPSTitle()+"\n");
			fWriter2.write(geTitle.outputCSVtitle()+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
//-------------------test part start--------------------------
		for (int i = 0; i < 100; i++){
			GameEngine ge = new GameEngine();
			
			ge.getScPara().setPatientParameter(param[0],param[1],param[2],param[3],param[4],param[5],param[6],param[7],param[8]);
		//	ge.getScPara().setPatientParameter(0.8, 0.8, 0.8, 80.0, 80.0,80.0,20.0, 0.2, 0.3);
			//ge.getScPara().setPatientParameter(0.8, 0.1, 0.1, 80.0, 0.0,0.0,80.0, 0.1, 0.3);
		//	ge.getScPara().setPatientParameter(0.5, 0.5, 0.5, 50.0, 50,50,50.0, 0.5, 0.5);
			ge.getScVar().initVariables();
			//ge.simulateBest();
			//ge.simulateWorst();
			ge.simulateRandom();
			try {
				fWriter1.write(ge.outputPSResult());
				fWriter2.write(ge.outputCSVresult());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
//-------------------test part end----------------------------
		try {
			fWriter1.flush();
			fWriter2.flush();
			fWriter1.close();
			fWriter2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
/*	public static void sessionStatus1(){
		File csvFile1 = new File("./newpsStatus1.csv");
		FileWriter fWriter1 = null;
		File csvFile2 = new File("./newall1.csv");
		FileWriter fWriter2 = null;
		
		try {
			fWriter1 = new FileWriter(csvFile1);
			fWriter2 = new FileWriter(csvFile2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameEngine geTitle = new GameEngine();
		try {
			fWriter1.write(geTitle.outputPSTitle()+"\n");
			fWriter2.write(geTitle.outputCSVtitle()+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//-------------------test part start--------------------------
		for (int i = 0; i < 100; i++){
			GameEngine ge = new GameEngine();
			ge.getScPara().setPatientParameter(0.8, 0.8, 0.8, 80.0, 80.0,80.0,20.0, 0.2, 0.3);
			//ge.getScPara().setPatientParameter(0.8, 0.1, 0.1, 80.0, 0.0,0.0,80.0, 0.1, 0.3);
		//	ge.getScPara().setPatientParameter(0.5, 0.5, 0.5, 50.0, 50,50,50.0, 0.5, 0.5);
			ge.getScVar().initVariables();
			ge.simulateBest();
			try {
				fWriter1.write(ge.outputPSResult());
				fWriter2.write(ge.outputCSVresult());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
//-------------------test part end----------------------------
		try {
			fWriter1.flush();
			fWriter2.flush();
			fWriter1.close();
			fWriter2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}*/
		
	
	

	public static void main(String[] args) {
		testWithParaSet1();
		testWithParaSet2();
		testWithParaSet3();
	}

}

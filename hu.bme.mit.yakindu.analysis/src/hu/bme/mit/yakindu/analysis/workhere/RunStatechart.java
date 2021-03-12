package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		Scanner scanner = new Scanner(System.in);
		String str;
		while((str = scanner.nextLine())!=null){
			if(str.equals("Start")) {
				s.raiseStart();
				s.runCycle();
				print(s);}
			else if(str.equals("White")) {
				s.raiseWhite();
				s.runCycle();
				print(s);}
			else if(str.equals("Black")) {
				s.raiseBlack();
				s.runCycle();
				print(s);}
			else if(str.equals("exit")) {
				System.exit(0);
			}else {
				System.out.println("invalid command");
	}}}
		public static void print(IExampleStatemachine s) {
			System.out.println("WhiteTime = " + s.getSCInterface().getWhiteTime());
			System.out.println("BlackTime = " + s.getSCInterface().getBlackTime());
	}

}



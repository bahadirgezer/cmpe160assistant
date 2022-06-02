package project.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class LogWriter {
	
	private static LogWriter logWriter;
	private static BufferedWriter writer;
	
	private LogWriter(){
		super();
	}
	
	public static void set(String outputFile) {
		try {
			writer = new BufferedWriter(new FileWriter(new File(outputFile)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void write(String action) {
		if (logWriter == null) {
			logWriter = new LogWriter();
		}
		try {
			writer.write(action + "\n,");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void finish() {
		if (logWriter == null) {
			return;
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

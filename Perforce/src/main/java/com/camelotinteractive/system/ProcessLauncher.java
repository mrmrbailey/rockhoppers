package com.camelotinteractive.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs commands.
 *
 */
public class ProcessLauncher {
	
	/**
	 * Runs cmd and returns the output as a List of Strings.
	 * @param args the windows cmd to run.
	 * @return A List of the output String from the cmd.
	 * @throws IOException
	 */
	public static List<String> doProcess(String args[]) throws IOException {
		
		if (args.length <= 0) {
			System.err.println("Need command to run");
			throw new IllegalArgumentException("Need command to run");
		}
		
		List<String> output = new ArrayList<String>();
		
		ProcessBuilder process = new ProcessBuilder(args);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				process.start().getInputStream()));
		
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				output.add(line);
			}
		} finally {
			br.close();
		}		
		return output;
	}
	
	
}

package com.camelotinteractive.perforce.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.camelotinteractive.perforce.command.parser.FstatParser;
import com.camelotinteractive.perforce.command.parser.Parser;
import com.camelotinteractive.system.ProcessLauncher;

public class Fstat {
	
	private static final String REPOSITORY = "//ESDIRECT/ESD/DEV/...";
	
	/**
	 * Gets a list of filenames against the specified changelist. 
	 * @param changelist the changelist the files are required for. 
	 * @return a List of filenames
	 */
	public List<String> getFilenamesInChangelist(String changelist) {
		
		List<String> cmdOutput;
		
		try {
			String[] cmd = new String[5];
			cmd[0] = Commands.P4.getCommand();
			cmd[1] = Commands.FSTAT.getCommand();
			cmd[2] = "-e";
			cmd[3] = changelist;
			cmd[4] = REPOSITORY;
			cmdOutput = ProcessLauncher.doProcess(cmd);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}		

		return getFilenames(cmdOutput);		
	}
	
	/**
	 * Gets a list of new filenames against the specified changelist.
	 * New files are files that are the head revision in the changelist. 
	 * @param changelist the changelist the files are required for. 
	 * @return a List of filenames
	 */
	public List<String> getNewFilenamesInChangelist(String changelist) {
		
		List<String> cmdOutput;
		
		try {
			String[] cmd = new String[7];
			cmd[0] = Commands.P4.getCommand();
			cmd[1] = Commands.FSTAT.getCommand();
			cmd[2] = "-e";
			cmd[3] = changelist;
			cmd[4] = "-F";
			cmd[5] = "\"headAction=add\"";
			cmd[6] = REPOSITORY;
			cmdOutput = ProcessLauncher.doProcess(cmd);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}		

		return getFilenames(cmdOutput);		
	}	
	
	private List<String> getFilenames(List<String> cmdOutput) {
		List<String> files = new ArrayList<String>();
		Parser parser = getParser();
		for (String line : cmdOutput) {
			if (parser.isParsable(line)){			
				files.add(parser.parse(line));
			}
		}
		return files;			
	}
	
	// unit testing
	Parser parser;

	private Parser getParser() {
		if (parser == null) {
			parser = new FstatParser();
		}
		return parser;
	}
	
	protected void setParser(Parser parser) {
		this.parser = parser;
	}	
}

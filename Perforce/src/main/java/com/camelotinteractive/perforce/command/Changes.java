package com.camelotinteractive.perforce.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.camelotinteractive.perforce.command.parser.ChangesParser;
import com.camelotinteractive.perforce.command.parser.Parser;
import com.camelotinteractive.system.ProcessLauncher;

public class Changes {

	public List<String> getChangelists(String repository) {
		List<String> cmdOutput;
		
		try {
			String[] cmd = new String[3];
			cmd[0] = Commands.P4.getCommand();
			cmd[1] = Commands.CHANGES.getCommand();
			cmd[2] = repository;
			cmdOutput = ProcessLauncher.doProcess(cmd);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		
		List<String> parsedChangelist = new ArrayList<String>();
		Parser parser = getParser();
		for (String line : cmdOutput) {
			if (parser.isParsable(line)){
				parsedChangelist.add(parser.parse(line));
			}
		}
		return parsedChangelist;
	}

	// unit testing
	Parser parser;

	private Parser getParser() {
		if (parser == null) {
			parser = new ChangesParser();
		}
		return parser;
	}
	
	protected void setParser(Parser parser) {
		this.parser = parser;
	}
}

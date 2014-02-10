package com.camelotinteractive.perforce.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.camelotinteractive.perforce.command.parser.FixesParser;
import com.camelotinteractive.perforce.command.parser.Parser;
import com.camelotinteractive.system.ProcessLauncher;

public class Fixes {

	public List<String> getChangelistForJob(String jobNumber) {
		List<String> cmdOutput;

		try {
			String[] cmd = new String[4];
			cmd[0] = Commands.P4.getCommand();
			cmd[1] = Commands.FIXES.getCommand();
			cmd[2] = "-j";
			cmd[3] = jobNumber;
			cmdOutput = ProcessLauncher.doProcess(cmd);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

		List<String> parsedChangelist = new ArrayList<String>();
		Parser parser = getParser();
		for (String line : cmdOutput) {
			if (parser.isParsable(line)) {
				parsedChangelist.add(parser.parse(line));
			}
		}
		return parsedChangelist;
	}

	// unit testing
	Parser parser;

	private Parser getParser() {
		if (parser == null) {
			parser = new FixesParser();
		}
		return parser;
	}

	protected void setParser(Parser parser) {
		this.parser = parser;
	}

}

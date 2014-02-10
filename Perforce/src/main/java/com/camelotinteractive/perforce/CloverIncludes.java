package com.camelotinteractive.perforce;

import java.util.ArrayList;
import java.util.List;

import com.camelotinteractive.clover.CloverFormatter;
import com.camelotinteractive.perforce.command.Changes;
import com.camelotinteractive.perforce.command.Fstat;

public class CloverIncludes implements Perforce {

	private List<String> filenames;

	public void doCommand(String[] args) {

		String repository = "//ESDIRECT/ESD/DEV/2012/MB612ESD/wsad/was5/...";
		if (args.length > 1) {
			repository = args[1];
		}

		Changes changes = new Changes();
		List<String> changelists = changes.getChangelists(repository);
		// No need to read the last one as it was the one that created the branch
		changelists.remove(changelists.size() - 1);

		Fstat fstat = new Fstat();
		filenames = new ArrayList<String>();

		for (String changelist : changelists) {
			filenames.addAll(fstat.getNewFilenamesInChangelist(changelist));
		}
	}

	public List<String> getOutput() {
		List<String> output = new ArrayList<String>();
		CloverFormatter format = new CloverFormatter();
		for (String filename : filenames) {
			if (format.isFileIncluded(filename)) {
				output.add(format.getIncludedFile(filename));
			}
		}
		return output;
	}
}

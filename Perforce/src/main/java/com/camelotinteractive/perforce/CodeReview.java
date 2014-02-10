package com.camelotinteractive.perforce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.camelotinteractive.perforce.command.Fixes;
import com.camelotinteractive.perforce.command.Fstat;

public class CodeReview implements Perforce {
	
	private Map<String, List<String>> filenames;

	public void doCommand(String[] args) {

		String jobNumber = "GBNA-5186";
		if (args.length > 1) {
			jobNumber = args[1];
		}

		Fixes fixes = new Fixes();
		List<String> changelists = fixes.getChangelistForJob(jobNumber);

		Fstat fstat = new Fstat();
		filenames = new HashMap<String, List<String>>();
		for (String changelist : changelists) {
			filenames.put(changelist,fstat.getFilenamesInChangelist(changelist));
		}
		
		for (String changelist : changelists) {
			System.out.println(changelist);
			for (String filename : filenames.get(changelist)) {
				System.out.println(filename);	
			}
		}
	}

	public List<String> getOutput() {
		List<String> output = new ArrayList<String>();
		output.add("getOutput");
		for (String changelist : filenames.keySet()) {
			output.addAll(filenames.get(changelist));	
		}
		return output;
	}
}

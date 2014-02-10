package com.camelotinteractive.perforce;

import java.lang.reflect.Constructor;
import java.util.List;

public class PerforceLauncher {

	public static void main(String[] args) throws Exception {
		
		RunMode mode = RunMode.CLOVER;
		if (args.length > 0) {
			mode = RunMode.getRunMode(args[0]);
		}
		
		final Class<? extends Perforce> clazz = Class.forName(mode.getClassName()).asSubclass(Perforce.class);
		final Constructor<? extends Perforce> constructor = clazz.getDeclaredConstructor();
		Perforce p = constructor.newInstance();
		p.doCommand(args);
		
		List<String> output = p.getOutput();
		for(String line : output) {
			System.out.println(line);
		}
	}
}

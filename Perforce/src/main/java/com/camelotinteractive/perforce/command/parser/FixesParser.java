package com.camelotinteractive.perforce.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixesParser implements Parser {
	
	private static final String CHANGELIST_PATTERN = "\\s\\d+?\\s";

	/**
	 * Takes a line of text and parses it to return a changelist number.
	 */
	public String parse(String text) {
		Pattern p = Pattern.compile(CHANGELIST_PATTERN);
		Matcher m = p.matcher(text);
		if (m.find()) {
			return text.substring(m.start()+1, m.end()-1);
		} else {
			return null;
		}
	}

	/**
	 * Checks if a text string is parsable to produce a changelist.
	 * @param text a string of text to be checked.
	 * @return true if a changelist can be read otherwise false.
	 */
	public boolean isParsable(String text) {
		Pattern p = Pattern.compile(CHANGELIST_PATTERN);
		Matcher m = p.matcher(text);
		return m.find();
	}
}
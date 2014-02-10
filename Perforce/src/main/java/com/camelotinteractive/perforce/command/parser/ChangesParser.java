package com.camelotinteractive.perforce.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the changes perforce output.
 *
 */
public class ChangesParser implements Parser {
	
	private static final String CHANGELIST_PATTERN = "\\d+?\\s";

	/**
	 * Takes a line of text and parses it to return a changelist number.
	 */
	public String parse(String text) {
		Pattern p = Pattern.compile(CHANGELIST_PATTERN);
		Matcher m = p.matcher(text);
		if (m.find()) {
			return text.substring(m.start(), m.end()-1);
		} else {
			return null;
		}
	}

	/**
	 * 
	 */
	public boolean isParsable(String text) {
		Pattern p = Pattern.compile(CHANGELIST_PATTERN);
		Matcher m = p.matcher(text);
		return m.find();
	}
}

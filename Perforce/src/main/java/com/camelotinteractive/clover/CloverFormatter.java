package com.camelotinteractive.clover;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloverFormatter {

	private final static String INCLUDE_HEADER = "<include name =\"**";
	private final static String INCLUDE_FOOTER = "\"/>";

	private static final String JAVA_PATTERN = "\\.java";

	/**
	 * Checks if a filename is included in clover includes.
	 * 
	 * @param filename
	 *            the filename to be checked.
	 * @return true if a filename can be included otherwise false.
	 */
	public boolean isFileIncluded(String filename) {
		Pattern javaPattern = Pattern.compile(JAVA_PATTERN);
		return javaPattern.matcher(filename).find();
	}

	public String getIncludedFile(String filename) {

		Pattern srcMainJavaPrefixPattern = Pattern.compile("src\\/main\\/java");
		Matcher srcMainJavaPrefixMatcher = srcMainJavaPrefixPattern.matcher(filename);
		if (srcMainJavaPrefixMatcher.find()) {
			return wrapInclude(filename.substring(srcMainJavaPrefixMatcher.end()));
		}
		
		Pattern srcMainTestPrefixPattern = Pattern.compile("src\\/test\\/java");
		Matcher srcMainTestPrefixMatcher = srcMainTestPrefixPattern.matcher(filename);
		if (srcMainTestPrefixMatcher.find()) {
			return wrapInclude(filename.substring(srcMainTestPrefixMatcher.end()));
		}
		
		Pattern packagePrefixPattern = Pattern.compile("src");
		Matcher packagePrefixMatcher = packagePrefixPattern.matcher(filename);
		if (packagePrefixMatcher.find()) {
			return wrapInclude(filename.substring(packagePrefixMatcher.end()));
		}

		return null;
	}
	
	private String wrapInclude(String filename) {
		return INCLUDE_HEADER+ filename + INCLUDE_FOOTER;
	}

}

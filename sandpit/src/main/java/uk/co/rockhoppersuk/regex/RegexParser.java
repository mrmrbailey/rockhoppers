package uk.co.rockhoppersuk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
	
	Pattern pattern;
	
	public RegexParser(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	public boolean isMatch(String text) {
		return pattern.matcher(text).matches();
	}

    public Matcher getMatcher(String text) {
        return pattern.matcher(text);
    }
}

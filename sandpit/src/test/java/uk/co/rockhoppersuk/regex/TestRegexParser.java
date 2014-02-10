package uk.co.rockhoppersuk.regex;
import static org.junit.Assert.*;

import org.junit.Test;


public class TestRegexParser {

	@Test
	public final void testIsMatch() {
		String testPattern = ".*APPLOGGER\\.isDebugEnabled.*";
		RegexParser r = new RegexParser(testPattern); 

		String testString = "if (APPLOGGER.isDebugEnabled()) {";
		assertTrue(r.isMatch(testString));
	}

	
	@Test
	public final void testIsMatchRaffle() {
		String testPattern = "\\d{4}[A-Z]{6}";
		RegexParser r = new RegexParser(testPattern); 

		String testString = "0001DDERDE";
		assertTrue(r.isMatch(testString));

		testString = "001DDERDE";
		assertFalse(r.isMatch(testString));
	
		testString = "0001dDERDE";
		assertFalse(r.isMatch(testString));

		testString = "00011DERDE";
		assertFalse(r.isMatch(testString));
	
		testString = "000DDDERDE";
		assertFalse(r.isMatch(testString));
	}
	
	@Test
	public final void testIsDateTime() {
		
		String testPattern = "[\\d-]*";
		testPattern = "\\d{4}(-\\d{2}){2} \\d{2}:\\d{2}";
		RegexParser r = new RegexParser(testPattern); 

		String testString = "2011-06-11 11:45";
		assertTrue(r.isMatch(testString));

	}	
}

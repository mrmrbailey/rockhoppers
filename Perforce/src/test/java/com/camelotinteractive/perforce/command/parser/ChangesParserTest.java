/**
 * 
 */
package com.camelotinteractive.perforce.command.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.camelotinteractive.perforce.command.parser.ChangesParser;
import com.camelotinteractive.perforce.command.parser.Parser;

/**
 * @author mxbailey
 *
 */
public class ChangesParserTest {
	
	private final static String VALID_CHANGELIST_TEXT = "Change 37111 on 2011/10/25 by g_nnshet@Eleven_NS 'GBNA-3982: Removed unwanted fil'";
	private final static String VALID_CHANGELIST = "37111";
	private final static String INVALID_CHANGELIST_TEXT = "This line should not be pasable";

	/**
	 * Test method for {@link com.camelotinteractive.perforce.command.parser.ChangesParser#parse(java.lang.String)}.
	 */
	@Test
	public final void testParse() {
		Parser parser = new ChangesParser();
		assertEquals(VALID_CHANGELIST, parser.parse(VALID_CHANGELIST_TEXT));
	}
	
	/**
	 * Test method for {@link com.camelotinteractive.perforce.command.parser.ChangesParser#parse(java.lang.String)}.
	 */
	@Test
	public final void testParseNull() {
		Parser parser = new ChangesParser();
		assertNull(parser.parse(INVALID_CHANGELIST_TEXT));
	}
	
	/**
	 * Test method for {@link com.camelotinteractive.perforce.command.parser.ChangesParser#isParsable(java.lang.String)}.
	 */
	@Test
	public final void testIsParsable() {
		Parser parser = new ChangesParser();
		assertTrue(parser.isParsable(VALID_CHANGELIST_TEXT));
		assertFalse(parser.isParsable(INVALID_CHANGELIST_TEXT));
	}	
	
	

}

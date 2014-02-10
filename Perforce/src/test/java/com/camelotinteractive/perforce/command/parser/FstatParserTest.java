/**
 * 
 */
package com.camelotinteractive.perforce.command.parser;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author mxbailey
 *
 */
public class FstatParserTest {
	
	private final static String VALID_FILENAME_TEXT = "... depotFile //ESDIRECT/ESD/DEV/2011/Eleven/wsad/was5/footballfever-bundle/src/xmlSource/Response/FootballFeverWagerParameterSet.xml";
	private final static String VALID_FILENAME = "//ESDIRECT/ESD/DEV/2011/Eleven/wsad/was5/footballfever-bundle/src/xmlSource/Response/FootballFeverWagerParameterSet.xml";
	private final static String INVALID_FILENAME_TEXT = "... headAction edit";
	

	/**
	 * Test method for {@link com.camelotinteractive.perforce.command.parser.FstatParser#parse(java.lang.String)}.
	 */
	@Test
	public final void testParse() {
		Parser parser = new FstatParser();
		assertEquals(VALID_FILENAME, parser.parse(VALID_FILENAME_TEXT));
	}
	
	/**
	 * Test method for {@link com.camelotinteractive.perforce.command.parser.FstatParser#parse(java.lang.String)}.
	 */
	@Test
	public final void testParseNull() {
		Parser parser = new FstatParser();
		assertNull(parser.parse(INVALID_FILENAME_TEXT));
	}	

	/**
	 * Test method for {@link com.camelotinteractive.perforce.command.parser.FstatParser#isParsable(java.lang.String)}.
	 */
	@Test
	public final void testIsParsable() {
		Parser parser = new FstatParser();
		assertTrue(parser.isParsable(VALID_FILENAME_TEXT));
		assertFalse(parser.isParsable(INVALID_FILENAME_TEXT));
	}

}

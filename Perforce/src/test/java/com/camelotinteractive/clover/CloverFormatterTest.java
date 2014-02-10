/**
 * 
 */
package com.camelotinteractive.clover;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author mxbailey
 *
 */
public class CloverFormatterTest {
	
	private final static String JAVA_FILENAME = "//ESDIRECT/ESD/DEV/2011/Eleven/wsad/was5/ESDEntity/src/main/java/com/camelotinteractive/entity/drawgame/Board.java";

	/**
	 * Test method for {@link com.camelotinteractive.clover.CloverFormatter#isFileIncluded(java.lang.String)}.
	 */
	@Test
	public final void testIsFileIncluded() {
		CloverFormatter formatter = new CloverFormatter();
		assertTrue(formatter.isFileIncluded(JAVA_FILENAME));
		assertFalse(formatter.isFileIncluded("//ESDIRECT/ESD/DEV/2011/Eleven/wsad/was5/footballfever-bundle/src/xmlSource/Response/FootballFeverWagerParameterSet.xml"));
	}

	/**
	 * Test method for {@link com.camelotinteractive.clover.CloverFormatter#getIncludedFile(java.lang.String)}.
	 */
	@Test
	public final void testGetIncludedFile() {
		
		CloverFormatter formatter = new CloverFormatter();
		String expected = "<include name =\"**/com/camelotinteractive/entity/drawgame/Board.java\"/>";
		String actual = formatter.getIncludedFile(JAVA_FILENAME);
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link com.camelotinteractive.clover.CloverFormatter#getIncludedFile(java.lang.String)}.
	 */
	@Test
	public final void testGetIncludedFileTest() {
		
		CloverFormatter formatter = new CloverFormatter();
		String expected = "<include name =\"**/com/camelotinteractive/entity/drawgame/DrawStateCodeTest.java\"/>";
		String actual = formatter.getIncludedFile("//ESDIRECT/ESD/DEV/2011/Eleven/wsad/was5/ESDEntity/src/test/java/com/camelotinteractive/entity/drawgame/DrawStateCodeTest.java");
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link com.camelotinteractive.clover.CloverFormatter#getIncludedFile(java.lang.String)}.
	 */
	@Test
	public final void testGetIncludedFileSrc() {
		
		CloverFormatter formatter = new CloverFormatter();
		String expected = "<include name =\"**/com/camelotinteractive/site/pub/drawgame/action/CancelPlayslipAction.java\"/>";
		String actual = formatter.getIncludedFile("//ESDIRECT/ESD/DEV/2011/Eleven/wsad/was5/player-war/src/com/camelotinteractive/site/pub/drawgame/action/CancelPlayslipAction.java");
		assertEquals(expected, actual);
	}		

	/**
	 * Test method for {@link com.camelotinteractive.clover.CloverFormatter#getIncludedFile(java.lang.String)}.
	 */
	@Test
	public final void testGetIncludedFileNull() {
		CloverFormatter formatter = new CloverFormatter();
		assertNull(formatter.getIncludedFile("thisisnotright"));
	}		
}

/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */

package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;


/**
 * A test for ApplicationHubAction.
 *
 * @author mbailey
 * @version 1.0
 */
public class ApplicationHubActionTest extends StrutsTestCase {

    /**
     * Test of execute method, of class ApplicationHubAction.
     * @throws Exception
     */
    @Test
    public void testApplicationHubAction() throws Exception {

        ApplicationHubAction action = new ApplicationHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!",
                ActionSupport.SUCCESS.equals(result));
        assertTrue("Expected the default message!",
                action.getText(action.WELCOME_MESSAGE).equals(action.getWelcomeMessage()));

    }
}
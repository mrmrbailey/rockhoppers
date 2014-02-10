package uk.co.rockhoppersuk.strings;

import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringSpliterTest {

	@Test
	public final void test() {
		StringSpliter s = new StringSpliter();
		
        ArrayList<String> boardStr = new ArrayList<String>();
        String encodedBoard = "1,2,3,4,5,6,7,8,9";
        
        int index = 0;
        int nextIndex = encodedBoard.indexOf(",", index);
        while (nextIndex != -1) {
            boardStr.add(encodedBoard.substring(index, nextIndex));
            index = nextIndex + 1;
            nextIndex = encodedBoard.indexOf(",", index);
        }
        boardStr.add(encodedBoard.substring(index));
        
        assertEquals(boardStr, s.getList(encodedBoard));
	}

}

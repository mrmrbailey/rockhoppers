/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mxbailey
 */
public class TBparser extends FinalTicketParser {

    private static final String GROUP_NAME_BOARD_ID = "boardId";
    private static final String GROUP_NAME_MATRIX_1 = "matrix1";
    private static final String GROUP_NAME_MATRIX_2 = "matrix2";

    private static final String BOARD_ID_PATTERN = "\\s(?<"+GROUP_NAME_BOARD_ID+">[A-F])\\s";
    private static final String MATRIX_1_PATTERN = "(?<"+GROUP_NAME_MATRIX_1+">(?:\\d{2}\\s*){5})";
    private static final String MATRIX_2_PATTERN = "-\\s*(?<"+GROUP_NAME_MATRIX_2+">\\d{2})";

    @Override
    protected String parseBoards(Matcher matcher) {

        String boardText = matcher.group("boards");

        String[] boardIds = parseBoardElement(boardText, BOARD_ID_PATTERN, GROUP_NAME_BOARD_ID);

        String[] matrix1Boards = parseBoardElement(boardText, MATRIX_1_PATTERN, GROUP_NAME_MATRIX_1);
        String[] matrix2Boards = parseBoardElement(boardText, MATRIX_2_PATTERN, GROUP_NAME_MATRIX_2);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardIds.length; i++) {
            sb.append(boardIds[i]);
            sb.append(" ");
            sb.append(matrix1Boards[i]);
            sb.append(" ");
            sb.append(matrix2Boards[i]);
            sb.append(":");
        }

        return sb.toString();

    }

    private String[] parseBoardElement(String boardText, String pattern, String groupName) {
        Matcher boardMatcher = Pattern.compile(pattern).matcher(boardText);
        String[] boards = new String[6];
        int index = 0;
        while (boardMatcher.find()) {
            boards[index++] = boardMatcher.group(groupName).trim();
        }
        return boards;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.entity;

/**
 *
 * @author mxbailey
 */
public class BoardBuilder {

    private final Ticket ticket;
    private final String boardId;
    private final String matrix1Numbers;
    private String matrix2Numbers;
    private String raffleNumber;

    // whilst required will be added by the service.
    private int runId;

    /**
     * Constructor for the BoardBuilder, each board must have matrix 1 numbers.
     * <p>
     * @param ticket         the ticket.
     * @param boardId        the board id for the ticket.
     * @param matrix1Numbers the matrix 1 numbers for the board.
     */
    public BoardBuilder(final Ticket ticket, final String boardId, final String matrix1Numbers) {
        this.ticket = ticket;
        this.boardId = boardId;
        this.matrix1Numbers = matrix1Numbers;
    }

    /**
     * Sets the matrix 2 numbers for the board.
     * <p>
     * @param matrix2Numbers the matrix 2 numbers for the board.
     * @return the BoardBuilder being built.
     */
    public BoardBuilder matrix2Numbers(final String matrix2Numbers) {
        this.matrix2Numbers = matrix2Numbers;
        return this;
    }

    /**
     * Sets the raffle number for the board.
     * <p>
     * @param raffleNumber the raffle number for the board.
     * @return the BoardBuilder being built.
     */
    public BoardBuilder raffleNumber(final String raffleNumber) {
        this.raffleNumber = raffleNumber;
        return this;
    }

    /**
     * Builds the board.
     * <p>
     * @return a new board.
     */
    public Board build() {
        return new Board(ticket, boardId, matrix1Numbers, matrix2Numbers, raffleNumber);
    }

}

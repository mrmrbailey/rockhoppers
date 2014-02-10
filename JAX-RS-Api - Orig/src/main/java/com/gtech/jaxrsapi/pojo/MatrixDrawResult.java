/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.pojo;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public class MatrixDrawResult extends DrawResult {

    private String matrix1main;
    private String matrix1bonusColumn;
    private String matrix2main;
    private String matrix2bonusColumn;
    private String raffleNumberColumn;
    private int nextNumOfRafflePrizes;
    private BigDecimal nextRafflePrizeAmount;
    private transient List<Integer> matrix1DrawnOrder;

    public List<Integer> getMatrix1DrawnOrder() {
        return matrix1DrawnOrder;
    }

    public void setMatrix1DrawnOrder(List<Integer> matrix1DrawnOrder) {
        this.matrix1DrawnOrder = matrix1DrawnOrder;
    }

    public String getMatrix1bonusColumn() {
        return matrix1bonusColumn;
    }

    public void setMatrix1bonusColumn(String matrix1bonusColumn) {
        this.matrix1bonusColumn = matrix1bonusColumn;
    }

    public String getMatrix1main() {
        return matrix1main;
    }

    public void setMatrix1main(String matrix1main) {
        this.matrix1main = matrix1main;
    }

    public String getMatrix2bonusColumn() {
        return matrix2bonusColumn;
    }

    public void setMatrix2bonusColumn(String matrix2bonusColumn) {
        this.matrix2bonusColumn = matrix2bonusColumn;
    }

    public String getMatrix2main() {
        return matrix2main;
    }

    public void setMatrix2main(String matrix2main) {
        this.matrix2main = matrix2main;
    }

    public int getNextNumOfRafflePrizes() {
        return nextNumOfRafflePrizes;
    }

    public void setNextNumOfRafflePrizes(int nextNumOfRafflePrizes) {
        this.nextNumOfRafflePrizes = nextNumOfRafflePrizes;
    }

    public BigDecimal getNextRafflePrizeAmount() {
        return nextRafflePrizeAmount;
    }

    public void setNextRafflePrizeAmount(BigDecimal nextRafflePrizeAmount) {
        this.nextRafflePrizeAmount = nextRafflePrizeAmount;
    }

    public String getRaffleNumberColumn() {
        return raffleNumberColumn;
    }

    public void setRaffleNumberColumn(String raffleNumberColumn) {
        this.raffleNumberColumn = raffleNumberColumn;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author mxbailey
 */

/*@JsonIgnoreProperties({"drawResultPK", "drawSequence", "drawTypeId", "numbersDrawn", "raffleDrawn", "drawDay",
    "resultConfirmedInd", "delayedDraw", "operatorInsert", "operatorVerify", "insertTs", "updateTs",
    "updateBy", "prizes", "lastClaimDate", "parimutuelJackpot", "matrix1main", "matrix1bonusColumn", "matrix2main",
    "matrix1DrawnOrder", "matrix2DrawnOrder", "matrix1Encoding", "matrix1Encoding", "matrix1BonusEncoding",
    "matrix2Encoding", "matrix2BonusEncoding", "raffleNumberColumn", "nextNumOfRafflePrizes",
    "nextRafflePrizeAmount"})
*/
@JsonIgnoreProperties({
    "resultConfirmedInd",
    "operatorInsert",
    "operatorVerify",
    "insertTs",
    "updateTs",
    "updateBy",
    "lastClaimDate",
    "pariMutuelJackpot",
    "prizeTypes",    
    "nextNumOfRafflePrizes",
    "nextRafflePrizeAmount",
    "matrix1DrawnOrder"})
public interface MatrixDrawResultPublicView {
    
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    List<Integer> getMatrix1();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    List<Integer> getMatrix1Bonus();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    List<Integer> getMatrix2();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    List<Integer> getMatrix2Bonus();

    @JsonProperty("raffleNumbers")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    List<String> getRaffleNumberList();    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.pojo;

import com.gtech.jaxrsapi.feed.DrawResultPublicView;
import com.gtech.jaxrsapi.feed.MatrixDrawResultPublicView;
import com.gtech.jaxrsapi.feed.PrizePublicView;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author mxbailey
 */
public class MatrixDrawResultTest {
    
    /**
     * Test of getMatrix1DrawnOrder method, of class MatrixDrawResult.
     */
    @Test
    public void testWriteOutMixIn() throws IOException {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().addMixInAnnotations(MatrixDrawResult.class, MatrixDrawResultPublicView.class);
        mapper.getSerializationConfig().addMixInAnnotations(Prize.class, PrizePublicView.class);

        MatrixDrawResult matrixDrawResult = getLottoResut();
        System.out.println(mapper.writeValueAsString(matrixDrawResult));
        System.out.println(mapper.writeValueAsString(getPrize()));
        
        assertTrue(mapper.writeValueAsString(matrixDrawResult).contains("drawDate"));
    }

    private MatrixDrawResult getLottoResut() {
        MatrixDrawResult result = new MatrixDrawResult();
        result.setBallset("lottoMallSet");
        result.setDrawDate(getDate());
        result.setInsertTs(getDate());
        result.setLastClaimDate(getDate());
        result.setMatrix1DrawnOrder(getMatrix1());
        result.setMachineName("lottoMachineName");
        result.setMatrix1bonusColumn("2");
        result.setMatrix1main("2,3,4,5,6,7");
//        result.setMatrix2bonusColumn(null);
//        result.setMatrix2main(null);
        result.setNextDrawDate(getDate());
        result.setNextEstJackpot(BigDecimal.TEN);
//        result.setNextNumOfRafflePrizes(1);
//        result.setNextRafflePrizeAmount(BigDecimal.TEN);
        result.setNumRollovers(getShort());
        result.setOperatorInsert("bob");
        result.setOperatorVerify("notBob");
        result.setPariMutuelJackpot(false);
        result.setPrizeTypes(getPrizeTypes());
        result.setPrizes(getPrizes());
//        result.setRaffleNumberColumn(null);
        result.setResultConfirmedInd('c');
        result.setUpdateBy("another");
        result.setUpdateTs(getDate());
        return result;
    }
    
    private Date getDate() {
        return new Date();
    }
    
    private short getShort() {
        return 1;
    }
    
    private List<Integer> getMatrix1() {
        List<Integer> matrix1 = new ArrayList<Integer>();
        matrix1.add(1);
        matrix1.add(2);
        matrix1.add(3);
        matrix1.add(4);
        matrix1.add(5);
        matrix1.add(6);
        return matrix1;
    }
    
    private List<PrizeType> getPrizeTypes() {
        List<PrizeType> pt = new ArrayList<PrizeType>();
        return pt;
    }
    
    private List<Prize> getPrizes() {
        List<Prize> p = new ArrayList<Prize>();
        p.add(getPrize());
        return p;
    }    
    
    private Prize getPrize() {
        Prize p = new Prize();
        p.setInsertTS(getDate());
        p.setNumWinners(1);
//        p.setNumWinnersIntl(0);
        p.setPrizeFund(BigDecimal.TEN);
//        p.setPrizeFundIntl(BigDecimal.ZERO);
        p.setUpdateBy("bob");
        p.setUpdateTS(getDate());
        
        return p;
    }

}

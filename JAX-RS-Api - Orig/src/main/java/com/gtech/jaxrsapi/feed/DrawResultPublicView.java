/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

import com.gtech.jaxrsapi.pojo.Prize;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author mxbailey
 */
public interface DrawResultPublicView {

    @JsonIgnore
    char getResultConfirmedInd();

    @JsonIgnore
    Date getNextDrawDate();

    @JsonIgnore
    BigDecimal getNextEstJackpot();

    @JsonIgnore
    short getNumRollovers();

    @JsonIgnore
    String getOperatorInsert();

    @JsonIgnore
    String getOperatorVerify();

    @JsonIgnore
    Date getInsertTs();

    @JsonIgnore
    Date getUpdateTs();

    @JsonIgnore
    String getUpdateBy();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    String getMachineName();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    String getBallset();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    List<Prize> getPrizes();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

import java.math.BigDecimal;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author mxbailey
 */
@JsonIgnoreProperties({"insertTS",
    "updateTS",
    "updateBy"})
public interface PrizePublicView {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    int getNumWinners();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    int getNumWinnersIntl();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    BigDecimal getPrizeFund();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    BigDecimal getPrizeFundIntl();
}

package com.gtech.game;

import java.math.BigDecimal;

public interface PrizeParameters {

    public int getTierCount();

    public int getPrizeTypeId(int i);

    public String getPrizeTypeDescription(int i);

    public BigDecimal getAmount(int i);
}

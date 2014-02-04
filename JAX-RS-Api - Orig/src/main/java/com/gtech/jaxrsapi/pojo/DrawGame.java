package com.gtech.jaxrsapi.pojo;

import java.math.BigDecimal;
import java.util.List;

/**
 * a draw game pojo.
 */
public class DrawGame implements java.io.Serializable {

    private String gameName;
    private List<String> drawDays;
    private List<String> anotherList;
    private BigDecimal price = BigDecimal.ZERO;

    public DrawGame() {
    }

    public List<String> getDrawDays() {
        return drawDays;
    }

    public void setDrawDays(List<String> drawDays) {
        this.drawDays = drawDays;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getAnotherList() {
        return anotherList;
    }

    public void setAnotherList(List<String> anotherList) {
        this.anotherList = anotherList;
    }
    
    
    
    
}

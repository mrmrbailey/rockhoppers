/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.struts.helloworld.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public class Draws extends ActionSupport {
    private List<String> draws;
    
    @Override
    public String execute() throws Exception {
        draws = new ArrayList<String>();
        draws.add("One");
        draws.add("two");
        draws.add("three");
        
        
        return SUCCESS;
    }

    public List<String> getDraws() {
        return draws;
    }

    public void setDraws(List<String> draws) {
        this.draws = draws;
    }
    
    
    
}

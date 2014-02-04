/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.services;

import java.util.List;
import uk.co.rockhoppersuk.bean.Bean;

/**
 *
 * @author mxbailey
 */
public interface BeanService {
    
    public Bean getBean(int id);
    public List<Bean> getBeans();
    
}

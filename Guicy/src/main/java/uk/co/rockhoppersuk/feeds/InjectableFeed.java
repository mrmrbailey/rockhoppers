/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds;

import java.util.List;
import javax.inject.Inject;
import uk.co.rockhoppersuk.bean.Bean;
import uk.co.rockhoppersuk.services.BeanService;

/**
 *
 * @author mxbailey
 */
public class InjectableFeed {
    @Inject
    private BeanService beanService;

    public List<Bean> getBeans() {
        return beanService.getBeans();
    }

    public Bean getBean(int id) {
        return beanService.getBean(id);
    }
    
}

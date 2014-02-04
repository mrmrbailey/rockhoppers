/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.services;

import java.util.ArrayList;
import java.util.List;
import uk.co.rockhoppersuk.bean.Bean;

/**
 *
 * @author mxbailey
 */
public class BeanServiceImpl implements BeanService {

    @Override
    public Bean getBean(int id) {
        return getSampleBean(id);
    }

    @Override
    public List<Bean> getBeans() {
        List<Bean> beans = new ArrayList<Bean>();
        beans.add(getBean(1));
        beans.add(getBean(2));
        beans.add(getBean(3));
        return beans;
    }
    
    private Bean getSampleBean(int id) {
        Bean aBean = new Bean();
        aBean.setId(id);
        aBean.setValue("aValue:" + id);
        aBean.setNonPublicThing("should not see: " + id);
        return aBean;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.modules;

import com.google.inject.Binder;
import uk.co.rockhoppersuk.services.BeanService;
import uk.co.rockhoppersuk.services.BeanServiceImpl;

/**
 *
 * @author mxbailey
 */
public class Module implements com.google.inject.Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(BeanService.class).to(BeanServiceImpl.class);
    }
}

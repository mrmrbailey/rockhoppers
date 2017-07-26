/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist;

import com.google.inject.persist.PersistService;
import javax.inject.Inject;

/**
 *
 * @author mxbailey
 */
public class ApplicationInitialiser {

    @Inject
    ApplicationInitialiser(PersistService service) {
        service.start();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.gtech.guicepersist.service.ControlServiceDAO;
import com.gtech.guicepersist.service.ControlService;

/**
 *
 * @author mxbailey
 */
public class ControlJobProcessor {

    public static void main(String[] args) {
        Injector injector  = Guice.createInjector(new JpaPersistModule("openjpa"));
        injector.getInstance(ApplicationInitialiser.class);

        ControlServiceDAO dao = injector.getInstance(ControlService.class);

        System.out.println(dao.getCurrentRunId());
        System.out.println(dao.getCurrentControlJob());
//        System.out.println(dao.createControlJob("a job name", 2));

        System.out.println(dao.getCurrentCancellations().size());

    }

}

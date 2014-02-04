/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.services;

/**
 *
 * @author mxbailey
 */
public class BeanServiceFactory {

//    private BeanServiceFactory() {}
    private static BeanService service = new BeanServiceImpl();

    public static BeanService getInstance() {
        return service;
    }
}

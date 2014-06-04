/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SOExample;

import javax.inject.Inject;

/**
 *
 * @author mxbailey
 */
public class AServiceImpl implements AService{

    @Inject
    AnotherService anotherService;

    @Override
    public void doSomething() {
        System.out.println("Done Something");
        anotherService.doSomethingElse();
    }

}

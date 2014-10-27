/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.guiceberry;

import com.google.common.testing.TearDown;
import com.google.common.testing.TearDownAccepter;
import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.TestId;
import com.google.guiceberry.TestScoped;
import com.google.guiceberry.TestWrapper;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class NewEmptyJUnitTest1 {

    @Rule
    public final GuiceBerryRule guiceBerry = new GuiceBerryRule(Env.class);

    @Inject
    private TearDownAccepter tearDownAccepter;

    private int firstItem;
    private int secondItem;
    private boolean throwExceptionOnSecondItemDeleter = false;

    @Before
    public void setUp() throws Exception {
        tearDownAccepter.addTearDown(new FirstItemResetter());
        firstItem = 1;

        tearDownAccepter.addTearDown(new SecondItemResetter());
        secondItem = 2;
    }

    @Test
    public void testOne() throws Exception {
        System.out.println("Inside testOne");
    }

    @Test
    public void testTwo() throws Exception {
        System.out.println("Inside testTwo");
    }

    @After
    public void tearDown() throws Exception {
        // @Afters happen before the tear downs
        assertEquals(1, firstItem);
        assertEquals(2, secondItem);
    }

    @Test
    public void testOne_4() throws Exception {
        assertEquals(1, firstItem);
        assertEquals(2, secondItem);
    }

    @Test
    public void testTwoFailsWithException() throws Exception {
        assertEquals(1, firstItem);
        throwExceptionOnSecondItemDeleter = true;
        assertEquals(2, secondItem);
    }

    private class FirstItemResetter implements TearDown {

        @Override
        public void tearDown() {
            System.out.println("first item delete");
            firstItem = 0;
        }
    }

    private class SecondItemResetter implements TearDown {

        @Override
        public void tearDown() throws Exception {
            System.out.println("second item delete");
            // This assertion passes because TearDownAccepter guarantees to call
            // tearDowns in a reverse order. Since setUp adds SecondItemResetter after
            // FirstItemResetter, the first item still has not been cleared.
            assertEquals(1, firstItem);

            if (throwExceptionOnSecondItemDeleter) {
                // We'll simulate a situation where a tear down goes wrong -- like getting
                // an exception trying to close a File or DB connection or whatever.
                throw new Exception("This test is expected to fail! Let's say something went wrong here.");
            } else {
                secondItem = 0;
            }
        }
    }

    public static final class Env extends AbstractModule {

        @Override
        protected void configure() {
            install(new GuiceBerryModule());
        }
        /*
         @Provides
         TestWrapper getTestWrapper(final TestId testId,
         final TearDownAccepter tearDownAccepter) {

         return new TestWrapper() {

         @Override
         public void toRunBeforeTest() {
         System.out.println("MARK");
         tearDownAccepter.addTearDown(new TearDown() {

         @Override
         public void tearDown() throws Exception {
         System.out.println("Ending: " + testId);
         }
         });
         System.out.println("Beginning: " + testId);
         }
         };
         }*/
    }
}

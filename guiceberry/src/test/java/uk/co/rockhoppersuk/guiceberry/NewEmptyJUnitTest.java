/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.guiceberry;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.TestScoped;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class NewEmptyJUnitTest {

    @Rule
    public final GuiceBerryRule guiceBerry = new GuiceBerryRule(Env.class);

    @Inject
    @NumberOneHundred
    private int number;

    @Inject
    @UnscopedIncrementingNumber
    private Provider<Integer> unscopedIncrementingNumber;

    @Inject
    @TestScopedIncrementingNumber
    private Provider<Integer> testScopedIncrementingNumber;

    @Inject
    @SingletonScopedIncrementingNumber
    private Provider<Integer> singletonScopedIncrementingNumber;

    @Test
    public void testHello() throws Exception {
        assertEquals(100, number);
    }

    @Test
    public void testOne() throws Exception {
        assertEquals(300, singletonScopedIncrementingNumber.get().intValue());
        assertEquals(300, singletonScopedIncrementingNumber.get().intValue());

        assertEquals(200, testScopedIncrementingNumber.get().intValue());
        assertEquals(200, testScopedIncrementingNumber.get().intValue());

        assertEquals(100, unscopedIncrementingNumber.get().intValue());
        assertEquals(101, unscopedIncrementingNumber.get().intValue());
    }

    @Test
    public void testTwo() throws Exception {
        assertEquals(300, singletonScopedIncrementingNumber.get().intValue());
        assertEquals(300, singletonScopedIncrementingNumber.get().intValue());

        assertEquals(201, testScopedIncrementingNumber.get().intValue());
        assertEquals(201, testScopedIncrementingNumber.get().intValue());

        assertEquals(102, unscopedIncrementingNumber.get().intValue());
        assertEquals(103, unscopedIncrementingNumber.get().intValue());
    }

    public static final class Env extends AbstractModule {

        private static final class IncrementingProvider implements Provider<Integer> {

            private int number;

            public IncrementingProvider(int seed) {
                this.number = seed;
            }

            public Integer get() {
                return number++;
            }
        }

        @Override
        protected void configure() {
            install(new GuiceBerryModule());
            IncrementingProvider unscopedIncrementingNumberProvider
                    = new IncrementingProvider(100);
            IncrementingProvider testScopedIncrementingNumberProvider
                    = new IncrementingProvider(200);
            IncrementingProvider singletonScopedIncrementingNumberProvider
                    = new IncrementingProvider(300);
            bind(Integer.class)
                    .annotatedWith(UnscopedIncrementingNumber.class)
                    .toProvider(unscopedIncrementingNumberProvider);
            bind(Integer.class)
                    .annotatedWith(TestScopedIncrementingNumber.class)
                    .toProvider(testScopedIncrementingNumberProvider)
                    .in(TestScoped.class);
            bind(Integer.class)
                    .annotatedWith(SingletonScopedIncrementingNumber.class)
                    .toProvider(singletonScopedIncrementingNumberProvider)
                    .in(Scopes.SINGLETON);
            bind(Integer.class).annotatedWith(NumberOneHundred.class).toInstance(100);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @BindingAnnotation
    private @interface UnscopedIncrementingNumber {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @BindingAnnotation
    private @interface SingletonScopedIncrementingNumber {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @BindingAnnotation
    private @interface NumberOneHundred {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @BindingAnnotation
    private @interface TestScopedIncrementingNumber {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.tutorial;

import com.gtech.tutorial.Product;
import com.gtech.tutorial.ShoppingCart;
import com.gtech.tutorial.User;
import java.util.List;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mxbailey
 */
public class AddProductToShoppingCart extends JUnitStory {

    private User user;
    private ShoppingCart shoppingCart;
    private Product product;

    @Given("a user")
    public void aUser() {
        user = new User();
    }

    @Given("a shopping cart")
    public void aShoppingCart() {
        shoppingCart = new ShoppingCart();
    }

    @Given("a product")
    public void aProduct() {
        product = new Product();
        product.setName("Coffee");
    }

    @When("the user adds the product to the shopping cart")
    public void userAddsProductToShoppingCart() {
        shoppingCart.add(user, product);
    }

    @Then("the product must be included in the list of the shoppingcart's entries")
    public void productMustBeListed() {
        List<Product> entries = shoppingCart.getProductsByUser(user);
        Assert.assertTrue(entries.contains(product));
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(getClass().getClassLoader())).useStoryReporterBuilder(new StoryReporterBuilder().withFormats(Format.CONSOLE));
    }

    @Override
    public List<CandidateSteps> candidateSteps() {
        return new InstanceStepsFactory(configuration(), this).createCandidateSteps();
    }

    @Override
    @Test
    public void run() throws Throwable {
        super.run();
    }
}

package com.browserstack.cucumber.steps;

import com.browserstack.cucumber.tasks.AddProduct;
import com.browserstack.cucumber.tasks.Login;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.ensure.Ensure;

import static com.browserstack.cucumber.steps.Hooks.driver;
import static com.browserstack.cucumber.steps.Hooks.user;
import static com.browserstack.cucumber.userinterface.ProductPage.BTN_REMOVE_OF_CART;

public class BuyingProductsStepDefinition {

    @Given("that {word} signed-in SwagLabs app")
    public void actorWantsBuyShirts(String actor) {
        user = Actor.named(actor);
        user.can(BrowseTheWeb.with(driver).asActor(user));
        user.attemptsTo(Login.atSwagLabs());
    }

    @When("He puts in the cart the {string}")
    public void actorPutShirtCart(String nameProduct) {
        user.attemptsTo(AddProduct.toTheCart(nameProduct));
    }

    @Then("He should see the btn REMOVE")
    public void actorShouldSeeRemoveBtn() {
        user.attemptsTo(Ensure.that(BTN_REMOVE_OF_CART).isDisplayed());
    }
}
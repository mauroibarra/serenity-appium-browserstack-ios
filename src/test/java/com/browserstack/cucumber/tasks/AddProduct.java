package com.browserstack.cucumber.tasks;

import com.browserstack.cucumber.interactions.Scroll;
import com.browserstack.cucumber.models.ScrollDirection;
import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

import static com.browserstack.cucumber.userinterface.ProductPage.BTN_ADD_TO_CART;

public class AddProduct{

    public static Performable toTheCart(String nameProduct) {
        return Task.where(
                actor -> {
                    // Construir el XPath para localizar el elemento basado en su texto
                    String xpathLocator = String.format("//*[contains(@value, '%s')]", nameProduct);
                    Target product = Target.the("Title with the name of the product").located(AppiumBy.xpath(xpathLocator));
                    // Realizar acciones sobre el elemento encontrado
                    actor.attemptsTo(
                            Scroll.untilVisibleTarget(product).direction(ScrollDirection.TO_BOTTOM).untilMaxAttempts(3),
                            Click.on(product));
                    actor.attemptsTo(
                            Scroll.untilVisibleTarget(BTN_ADD_TO_CART).direction(ScrollDirection.TO_BOTTOM).untilMaxAttempts(3),
                            Click.on(BTN_ADD_TO_CART));
                }
        );
    }

}

package com.browserstack.cucumber.userinterface;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class CheckoutCompletadoPage {
    public static final Target LBL_CHECKOUT_COMPLETE = Target.the("Title Checkout Complete")
            .located(AppiumBy.xpath("//*[@name='CHECKOUT: COMPLETE!']"));
}
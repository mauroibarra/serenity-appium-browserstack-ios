package com.browserstack.cucumber.interactions;

import com.browserstack.cucumber.interactions.builders.ScrollBuilder;
import com.browserstack.cucumber.models.ScrollDirection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getProxiedDriver;


public class Scroll implements Interaction {
    private final Target target;
    private final int attempts;
    private final ScrollDirection direction;

    public Scroll(Target target, ScrollDirection direction, int attempts) {
        this.target = target;
        this.direction = direction;
        this.attempts = attempts;
    }

    public static ScrollBuilder untilVisibleTarget(Target target){
        return new ScrollBuilder().untilVisibleTarget(target);
    }

    public static ScrollBuilder direction(ScrollDirection direction){
        return new ScrollBuilder().direction(direction);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        AppiumDriver driver = (AppiumDriver) BrowseTheWeb.as(actor).getDriver();
        var dimension = driver.manage().window().getSize();
        int centerX = dimension.width / 2;
        int startY = dimension.height / 2;
        int endY = (direction.equals(ScrollDirection.TO_BOTTOM)) ? (dimension.height / 4) : (dimension.height - (dimension.height / 4));

        var currentAttempt = 0;

        while (!target.resolveFor(actor).isVisible() && currentAttempt <= attempts){
            //Type of Pointer Input
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
            //Creating Sequence object to add actions
            Sequence scroll = new Sequence(finger,1);
            //Move finger into starting position
            scroll.addAction(finger.createPointerMove(Duration.ofSeconds(0),PointerInput.Origin.viewport(),centerX,startY));
            //Finger comes down into contact with screen
            scroll.addAction(finger.createPointerDown(0));
            //Finger moves to end position
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(700),
                    PointerInput.Origin.viewport(),centerX, endY));
            //Get up Finger from Srceen
            scroll.addAction(finger.createPointerUp(0));

            //Perform the actions
            driver.perform(List.of(scroll));

            currentAttempt++;
        }

    }
}

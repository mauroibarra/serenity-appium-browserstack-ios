package com.browserstack.cucumber.interactions;


import com.browserstack.cucumber.interactions.builders.DragBuilder;
import com.browserstack.cucumber.models.DragDirection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import static java.util.logging.Logger.getAnonymousLogger;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getProxiedDriver;

public class Drag implements Interaction {

    private final Target target;
    private final DragDirection direction;

    public Drag(Target target, DragDirection direction) {
        this.target = target;
        this.direction = direction;
    }

    public static DragBuilder theElement(Target target){
        return new DragBuilder().theElement(target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        AppiumDriver driver = (AppiumDriver) BrowseTheWeb.as(actor).getDriver();

        Point elementLocation = target.resolveFor(actor).getLocation();
        Point destinationLocation = finalPointDefinition(elementLocation);

        //Type of Pointer Input
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        //Creating Sequence object to add actions
        Sequence drag = new Sequence(finger,1);

        try {
            //Move finger into starting position
            drag.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(),elementLocation));
            //Finger comes down into contact with screen
            drag.addAction(finger.createPointerDown(0));
            //Finger moves to end position
            drag.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(),destinationLocation));
            //Get up Finger from Srceen
            drag.addAction(finger.createPointerUp(0));

            //Perform the actions
            driver.perform(List.of(drag));
        } catch (Exception e) {
            getAnonymousLogger().log(Level.INFO, "DragElement - TouchAction FAILED\n Target: ".concat(target.toString()), e);
        }
    }

    private Point finalPointDefinition(Point elementPosition) {
        Point finalPoint;
        var driverDimension = getProxiedDriver().manage().window().getSize();
        switch (direction) {
            case UP:
                finalPoint = new Point(elementPosition.getX(), 0);
                break;
            case DOWN:
                finalPoint = new Point(elementPosition.getX(), driverDimension.getHeight());
                break;
            case LEFT:
                finalPoint = new Point(0, elementPosition.getY());
                break;
            case RIGHT:
                finalPoint = new Point(driverDimension.getWidth(), elementPosition.getY());
                break;
            default:
                throw new IllegalArgumentException("drag(): direction: '" + direction + "' NOT supported");
        }
        return finalPoint;
    }

}

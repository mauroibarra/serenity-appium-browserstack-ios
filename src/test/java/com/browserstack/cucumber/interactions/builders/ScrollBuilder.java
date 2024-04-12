package com.browserstack.cucumber.interactions.builders;

import com.browserstack.cucumber.interactions.Scroll;
import com.browserstack.cucumber.models.ScrollDirection;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;


import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ScrollBuilder {

    private Target target;
    private ScrollDirection direction;

    public ScrollBuilder untilVisibleTarget(Target target) {
        this.target=target;
        return this;
    }

    public ScrollBuilder direction(ScrollDirection direction){
        this.direction = direction;
        return this;
    }

    public Performable untilMaxAttempts(int attempts){
        return  instrumented (Scroll.class, target, direction, attempts);
    }

}

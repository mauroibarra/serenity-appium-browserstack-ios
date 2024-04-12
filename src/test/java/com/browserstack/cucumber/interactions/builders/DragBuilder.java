package com.browserstack.cucumber.interactions.builders;

import com.browserstack.cucumber.interactions.Drag;
import com.browserstack.cucumber.models.DragDirection;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;


import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DragBuilder {
    private Target target;

    public DragBuilder theElement(Target target){
        this.target=target;
        return this;
    }

    public Performable to(DragDirection direction) {
        return instrumented(Drag.class, target, direction);
    }

}

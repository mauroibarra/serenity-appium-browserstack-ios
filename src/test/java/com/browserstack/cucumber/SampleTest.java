package com.browserstack.cucumber;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/buying_products.feature",
        tags = "@test1",
        glue = "com.browserstack.cucumber.steps",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class SampleTest {
}

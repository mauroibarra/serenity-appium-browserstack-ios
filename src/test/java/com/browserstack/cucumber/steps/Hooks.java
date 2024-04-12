package com.browserstack.cucumber.steps;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Hooks {
    public static IOSDriver driver;
    public static Actor user;


    @Before
    public void setup() throws InterruptedException, MalformedURLException {

        OnStage.setTheStage(new OnlineCast());
        MutableCapabilities options = new XCUITestOptions();
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String url = "https://" + variables.getProperty("browserstack.userName") + ":" + variables.getProperty("browserstack.accessKey") + "@hub.browserstack.com/wd/hub";
        System.out.println("URL server: " + url);
        try {
            driver = new IOSDriver(new URL(url), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @After()
    public void tearDown() throws Exception {

        final JavascriptExecutor jse = (JavascriptExecutor) driver;

        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        int maxRetries = 3;
        File archivo = new File("attemptsTest.txt");
        int attempts = 0;

        if (archivo.exists()) {
            try {
                // Leer el número del archivo
                Scanner scanner = new Scanner(archivo);
                if (scanner.hasNextInt()) {
                    attempts = scanner.nextInt();
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Incrementar el número
        attempts++;

        // Guardar el nuevo número en el archivo
        try {
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(attempts));
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Retry Count: " + attempts);

        TestOutcome testOutcome = StepEventBus.getEventBus().getBaseStepListener().latestTestOutcome().orElse(null);

        if (testOutcome != null) {
            TestResult resultado = testOutcome.getResult();
            if (resultado == TestResult.SUCCESS) {
                System.out.println("La prueba pasó.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Execution without problems!\"}}");
                archivo.delete();
            } else if (resultado == TestResult.FAILURE && attempts >= maxRetries) {
                System.out.println("La prueba falló.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test Failure\"}}");
                archivo.delete();
            } else if (resultado == TestResult.ERROR && attempts >= maxRetries) {
                System.out.println("La prueba tuvo un error.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test with errors\"}}");
                archivo.delete();
            } else if (resultado == TestResult.COMPROMISED && attempts >= maxRetries) {
                System.out.println("La prueba está comprometida.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test with compromised\"}}");
                archivo.delete();
            } else if (resultado == TestResult.IGNORED && attempts >= maxRetries) {
                System.out.println("La prueba fue ignorada.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test ignored\"}}");
                archivo.delete();
            } else if (resultado == TestResult.PENDING && attempts >= maxRetries) {
                System.out.println("La prueba está pendiente.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test pending\"}}");
                archivo.delete();
            } else if (resultado == TestResult.SKIPPED && attempts >= maxRetries) {
                System.out.println("La prueba fue omitida.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test skipped\"}}");
                archivo.delete();
            } else if (resultado == TestResult.UNDEFINED && attempts >= maxRetries) {
                System.out.println("Resultado de la prueba no definido.");
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Test undefined\"}}");
                archivo.delete();
            }
        }

        driver.quit();

    }
}

package com.serenitydojo.playwright.cucumber.stepCatalogStepDefinitions;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import org.junit.jupiter.api.BeforeEach;

import static com.serenitydojo.playwright.PlaywrightLocatorsTest.*;

public class PlaywrightCucumberFixtures {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    @Before(order = 100)
    public void setUpBrowserContext() {
        browserContext = browser.newContext(
                new Browser.NewContextOptions()
                        .setLocale("en-US")
        );
        page = browserContext.newPage();
    }

    @After(order = 100)
    public void closeContext() {
        browserContext.close();
    }

    @AfterAll
    public static void tearDown() {
        browser.close();
        playwright.close();
    }
}

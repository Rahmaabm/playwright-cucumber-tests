package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.serenitydojo.playwright.fixtures.ScreenShotManager.takeScreenshot;

@UsePlaywright(HeadlessChromeOptions.class)
public class ASimplePlaywrightTest {

    //We can replace ce code setup et teardown par @UsePlaywright
   /* private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;

    Page page;

    @BeforeAll
    public static void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions","--disable-gpu"))
        );
        browserContext = browser.newContext();
    }

    @BeforeEach
    public void setup() {
        page = browserContext.newPage();
    }

    @AfterAll
    public static void teardown() {
        browser.close();
        playwright.close();
    }*/
    @BeforeEach
    void setupTrace(BrowserContext context) {
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)

        );
    }

    @AfterEach
    void recordTrace(BrowserContext context) {
        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get("trace.zip"))
        );
    }
    // Le parametre Page page doit etre le dernier parametre si on a d'autre data params

    @Test
    void shouldShowThePageTitle(Page page) {

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));
    }

    @Test
    public void shouldSearchByKeyword(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");

        //        page.locator("[placeholder=Rechercher]").fill("Pliers");
        page.getByPlaceholder("Rechercher").fill("Pliers");

        //        page.locator("button:has-text('Rechercher')").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Rechercher")).click();

//        int matchingSearchResults = page.locator(".card").count();
        PlaywrightAssertions.assertThat(page.locator(".card")).hasCount(4);
        //List<String> productNames =page.getByTestId("product-name").allTextContents();
        //Assertions.assertThat(productNames).allMatch(name -> name.contains("Pliers"));

//        Locator outOfStockItem = page.locator(".card")
//                .filter(new Locator.FilterOptions().setHasText("Rupture de stock"))
//                .getByTestId("product-name");
//
//        PlaywrightAssertions.assertThat(outOfStockItem).hasCount(1);
//
//        Assertions.assertTrue(matchingSearchResults > 0);
    }

    @Test
    void selectMenuItem(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByRole(AriaRole.MENUBAR)
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Accueil")).click();
    }

}

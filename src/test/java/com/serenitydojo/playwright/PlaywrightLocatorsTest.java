package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.SelectOption;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.serenitydojo.playwright.fixtures.ScreenShotManager.takeScreenshot;

@Feature("Locators")
public class PlaywrightLocatorsTest {
    //We can replace ce code setup et teardown par @UsePlaywright
    private static Playwright playwright;
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

    @AfterEach
    void takeFinalScreenshot() {
        takeScreenshot(page,"End of test");
    }

    @AfterAll
    public static void teardown() {
        browser.close();
        playwright.close();
    }

    public void openPage() {
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @DisplayName("By id")
    @Nested
    class LocatingUsingCSS  {

        @BeforeEach
        void openContactPage() {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("By id")
        @Test
        void locateTheFirstNameFieldByID()  {
            page.locator("#first_name").fill("Sarah"); //id="first_name"
            assertThat(page.locator("#first_name")).hasValue("Sarah");
        }

        @Story("ByClassClass")
        @DisplayName("By CSS class")
        @Test
        void locateTheSendButtonByCssClass() {
            page.locator("#first_name").fill("Sarah");
            page.locator(".btnSubmit").click();
            List<String> alertMessages = page.locator(".alert").allTextContents();
            Assertions.assertTrue(!alertMessages.isEmpty());

        }

        @DisplayName("By attribute")
        @Test
        void locateTheSendButtonByAttribute() {
            page.locator("input[placeholder='Votre nom de famille *']").fill("Smith");
            assertThat(page.locator("#last_name")).hasValue("Smith");
        }

        @DisplayName("Drop down")
        @Test
        void locateDropDown() {
            Locator subjectField = page.getByLabel("Sujet");
            subjectField.selectOption(new SelectOption().setIndex(2));
            assertThat(subjectField).hasValue("webmaster");
        }

        @Test
        void uploadFile() throws URISyntaxException {

            Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/hello.txt").toURI());
            page.setInputFiles("#attachment", fileToUpload);

        }
    }
}

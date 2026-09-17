package com.serenitydojo.playwright.fixtures;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class ScreenShotManager {

    public static void takeScreenshot(Page page, String name) {
        var screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }
}

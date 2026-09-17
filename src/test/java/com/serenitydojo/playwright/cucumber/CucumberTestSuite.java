package com.serenitydojo.playwright.cucumber;
import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.serenitydojo.playwright.cucumber.stepCatalogStepDefinitions")
@ConfigurationParameter(
        key="cucumber.plugin",
        value="io.qameta.allure.cucumber," +
                "pretty"
)
public class CucumberTestSuite {

}

package com.serenitydojo.playwright.cucumber.stepCatalogStepDefinitions;

import com.microsoft.playwright.Page;
import com.serenitydojo.playwright.ASimplePlaywrightTest;
import com.serenitydojo.playwright.PlaywrightLocatorsTest;
import com.serenitydojo.playwright.domain.ProductList;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class ProductCatalogStepDefinitions {
     ProductList productList;
    PlaywrightLocatorsTest playwrightLocators = new PlaywrightLocatorsTest();
    ASimplePlaywrightTest playwrightTest = new ASimplePlaywrightTest();
    Page page;

    @Given("Sally on the home page")
    public  void sally_on_the_home_page() {
        //write code here
        playwrightLocators.openPage();
        throw new io.cucumber.java.PendingException();
    }
    @When("Sally searches for an {string}")
    public  void sally_searches_for(String string) {
        //write code here
        playwrightTest.shouldSearchByKeyword(page);
        System.out.println("hello");
        throw new io.cucumber.java.PendingException();
    }
    @Then("the {string} product should be displayed")
    public  void the_product_should_be_displayed(String productName) {
        //write code here
        var matchingProducts = productList.getProductNames();
        Assertions.assertThat(matchingProducts).contains(productName);
        throw new io.cucumber.java.PendingException();
    }
}

package com.serenitydojo.playwright.login;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import com.serenitydojo.playwright.domain.Address;
import com.serenitydojo.playwright.domain.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@UsePlaywright
public class RegisterUserAPITest {

    private APIRequestContext request;

    @BeforeEach
    void setup(Playwright playwright) {
        request =playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://api.practicesoftwaretesting.com/")
        );
    }

    @AfterEach
    void tearDown() {
        if (request != null) {
            request.dispose();
        }
    }

    @Test
    void should_register_user() {
        User validUser = User.randomUser();
        var response = request.post("/users/register",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(validUser));

        System.out.println("Status: " + response.status());
        System.out.println("Body: " + response.text());

        String responseBody= response.text();
        Gson gson = new Gson(); //Constructs a Json object with default configuration.
        User createdUser = gson.fromJson(responseBody, User.class);

        JsonObject responseObject = gson.fromJson(responseBody,JsonObject.class);

        //assertThat(response.status()).isEqualTo(201);
        //assertThat(createdUser).isEqualTo(validUser.withPassword(null));
        assertSoftly(softly -> {
            softly.assertThat(response.status())
                    .as("Registration should return 201 created status code")
                    .isEqualTo(201);

            softly.assertThat(createdUser)
                    .as("created user should match the specified user without pwd")
                    .isEqualTo(validUser.withPassword(null));

            softly.assertThat(responseObject.get("id").getAsString())
                    .as("register user should have an id ")
                    .isNotEmpty();
        });

    }

    @Test
    void first_name_is_mandatory() {
        User userWithNoName = new User(
                null,
                "ABDELMALEK",
                new Address("some street",
                        "some city",
                        "some state",
                        "some country",
                        "some post code"
                ),
                "43552223",
                "1994-01-01",
                "ABC12$b!z",
                "sample@gmail.com");

        Gson gson = new Gson();
        var response = request.post("/users/register",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setData(userWithNoName));

        assertSoftly(softly -> {
            softly.assertThat(response.status())
                    .isEqualTo(422);

            JsonObject responseObject = gson.fromJson(response.text(),JsonObject.class);

            assertThat(responseObject.has("first_name")).isTrue();

            String errorMessage = responseObject.get("first_name").getAsString();

            assertThat(errorMessage).isEqualTo("The first name field is required.");
    });
}
}

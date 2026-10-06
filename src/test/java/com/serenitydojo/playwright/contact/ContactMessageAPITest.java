package com.serenitydojo.playwright.contact;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class ContactMessageAPITest {

    @Test
    void should_send_contact_message() {
        Map<String, String> payload = Map.of(
                "first_name", "Alice",
                "last_name", "Test",
                "email", "alice@example.com",
                "subject", "question",
                "message", "Hello from API test"
        );

        given()
                .baseUri("https://api.practicesoftwaretesting.com")
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/messages")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("email", equalTo("alice@example.com"))
                .body("subject", equalTo("question"))
                .body("message", equalTo("Hello from API test"))
                .body("status", equalTo("NEW"))
                .body("id", notNullValue());
    }
}


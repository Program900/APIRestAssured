package org.example.getAPIRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class getAPIBookings {
    @Test
    void geAllBookings(){
        System.out.println("Get all bookings");
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com").when().get("/booking").
                then().assertThat().statusCode(200).statusLine("HTTP/1.1 200 OK").extract().response();
        Assert.assertTrue(response.getBody().asString().contains("bookingid"));

    }
}

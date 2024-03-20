package org.example.postAPIRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

public class PostAPIBookings {
    @Test
    //prepare the request body  for the booking api https://restful-booker.herokuapp.com booking api and create a booking using post request method and validate the response

    void createBooking(){
        System.out.println("Create booking");
        JSONObject requestBody = new JSONObject();
        JSONObject bookingDates = new JSONObject();

        requestBody.put("firstname", "Jim");
        requestBody.put("lastname", "Brown");
        requestBody.put("totalprice", 111);
        requestBody.put("depositpaid", true);
        requestBody.put("bookingdates", bookingDates);
        bookingDates.put("checkin", "2021-07-01");
        bookingDates.put("checkout", "2021-07-01");
        requestBody.put("additionalneeds", "Breakfast");
        RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com").body(requestBody.toJSONString()).when().post("/booking").
                then().assertThat().statusCode(200).statusLine("HTTP/1.1 200 OK").extract().response();

    }
}

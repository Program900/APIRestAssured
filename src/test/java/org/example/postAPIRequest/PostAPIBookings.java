package org.example.postAPIRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
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
                .baseUri("https://restful-booker.herokuapp.com").body(requestBody.toJSONString()).
                log().all().
                when().post("/booking").
                then().assertThat().
                log().body().
                statusCode(200).
                body("booking.firstname", org.hamcrest.Matchers.equalTo("Jim")).
                body("booking.lastname", org.hamcrest.Matchers.equalTo("Brown")).
                body("booking.totalprice", org.hamcrest.Matchers.equalTo(111)).
                body("booking.depositpaid", org.hamcrest.Matchers.equalTo(true)).
                body("booking.bookingdates.checkin", org.hamcrest.Matchers.equalTo("2021-07-01")).
                body("booking.bookingdates.checkout", org.hamcrest.Matchers.equalTo("2021-07-01")).
                body("booking.additionalneeds", org.hamcrest.Matchers.equalTo("Breakfast"));

    }
    @Test
        //prepare the request body  for the booking api https://restful-booker.herokuapp.com booking api and create a booking using post request method and validate the response

    void createBookingForValidationFails(){
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
                .baseUri("https://restful-booker.herokuapp.com").body(requestBody.toJSONString()).
                when().post("/booking").
                then().assertThat().
                log().ifValidationFails().
                statusCode(200).
                body("booking.firstname", Matchers.equalTo("Jim1"));
    }
    @Test
        //prepare the request body  for the booking api https://restful-booker.herokuapp.com booking api and create a booking using post request method and validate the response

    void validatetheResponse(){
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
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com").body(requestBody.toJSONString()).
                log().all().
                when().post("/booking").
                then().assertThat().
                log().body().
                statusCode(200)
                .extract().response();
        System.out.println(response.getBody().asString());

        int bookingId = response.jsonPath().get("bookingid");
        System.out.println("Booking id is: "+bookingId);

        RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com").when().get("/booking/"+bookingId).
                then().assertThat().statusCode(200).
                body("firstname", Matchers.equalTo("Jim"))
                .body("lastname", Matchers.equalTo("Brown"))
                .body("totalprice", Matchers.equalTo(111))
                .body("depositpaid", Matchers.equalTo(true))
                .body("bookingdates.checkin", Matchers.equalTo("2021-07-01"))
                .body("bookingdates.checkout", Matchers.equalTo("2021-07-01"))
                .body("additionalneeds", Matchers.equalTo("Breakfast"));
    }


}

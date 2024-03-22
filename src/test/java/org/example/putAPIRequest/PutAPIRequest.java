package org.example.putAPIRequest;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.example.utils.FileNameConstants;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PutAPIRequest {
    //prepare the request body  for the booking api https://restful-booker.herokuapp.com booking api and create a booking using put request method using the token generated in the previous test case


   @Test
    void updateBookingForValidationFails() {
       //post api call
       JSONObject postRequestBody = new JSONObject();
       JSONObject bookingDates = new JSONObject();
       postRequestBody.put("firstname", "Jim");
       postRequestBody.put("lastname", "Brown");
       postRequestBody.put("totalprice", 111);
       postRequestBody.put("depositpaid", true);
       postRequestBody.put("bookingdates", bookingDates);
       bookingDates.put("checkin", "2021-07-01");
       bookingDates.put("checkout", "2021-07-01");
       postRequestBody.put("additionalneeds", "Breakfast");

       Response response = RestAssured.
               given().contentType(ContentType.JSON)
                       .baseUri("https://restful-booker.herokuapp.com").body(postRequestBody.toJSONString()).
                       log().all().
               when().post("/booking").
               then().assertThat().
                       log().body().statusCode(200).
               body("booking.firstname", org.hamcrest.Matchers.equalTo("Jim")).
               body("booking.lastname", org.hamcrest.Matchers.equalTo("Brown")).
               body("booking.totalprice", org.hamcrest.Matchers.equalTo(111)).
               body("booking.depositpaid", org.hamcrest.Matchers.equalTo(true)).
               body("booking.bookingdates.checkin", org.hamcrest.Matchers.equalTo("2021-07-01")).
               body("booking.bookingdates.checkout", org.hamcrest.Matchers.equalTo("2021-07-01")).
               body("booking.additionalneeds", org.hamcrest.Matchers.equalTo("Breakfast")).extract().response();

       JSONArray jsonArray = JsonPath.read(response.body().asString(),"$.booking..firstname");
       String firstName = (String) jsonArray.get(0);

       Assert.assertEquals(firstName, "Jim");

       int bookingId = JsonPath.read(response.body().asString(),"$.bookingid");

       //get api call
       RestAssured
               .given()
               .contentType(ContentType.JSON)
               .baseUri("https://restful-booker.herokuapp.com/booking")
               .when()
               .get("/{bookingId}",bookingId)
               .then()
               .assertThat()
               .statusCode(200);

       //token generation
       JSONObject tokenAPIRequestBody = new JSONObject();
            tokenAPIRequestBody.put("username", "admin");
            tokenAPIRequestBody.put("password", "password123");

       Response tokenAPIresponse =RestAssured.
               given().contentType(ContentType.JSON)
                      .baseUri("https://restful-booker.herokuapp.com").body(tokenAPIRequestBody).log().all().
               when().post("/auth").
               then().assertThat().
                      log().body().statusCode(200).body("token", Matchers.notNullValue()).extract().response();
       //print the response body
       System.out.println(tokenAPIresponse.getBody().asString());
       //print the token
       System.out.println(tokenAPIresponse.path("token"));
       //store the token in a string
       String token = tokenAPIresponse.path("token");

       //put api call
         JSONObject putAPIRequestBody = new JSONObject();
            JSONObject putBookingDates = new JSONObject();
       putAPIRequestBody.put("firstname", "Jim");
       putAPIRequestBody.put("lastname", "Brown");
       putAPIRequestBody.put("totalprice", 111);
       putAPIRequestBody.put("depositpaid", true);
       putAPIRequestBody.put("bookingdates", putBookingDates);
       putBookingDates.put("checkin", "2021-07-01");
       putBookingDates.put("checkout", "2021-07-01");
       putAPIRequestBody.put("additionalneeds", "Breakfast");

       RestAssured
               .given().contentType(ContentType.JSON).body(putAPIRequestBody).
                        header("Cookie", "token="+token).baseUri("https://restful-booker.herokuapp.com/booking")
               .when().put("/{bookingId}",bookingId)
               .then()
                      .assertThat().statusCode(200)
               .body("firstname", Matchers.equalTo("Jim"))
               .body("lastname", Matchers.equalTo("Brown"))
               .body("totalprice", org.hamcrest.Matchers.equalTo(111)).
               body("depositpaid", org.hamcrest.Matchers.equalTo(true)).
               body("bookingdates.checkin", org.hamcrest.Matchers.equalTo("2021-07-01")).
               body("bookingdates.checkout", org.hamcrest.Matchers.equalTo("2021-07-01")).
               body("additionalneeds", org.hamcrest.Matchers.equalTo("Breakfast"));;


   }

}


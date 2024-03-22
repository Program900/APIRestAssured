package org.example.postAPIRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.example.utils.FileNameConstants.*;

public class PostAPIRequestUsingFile {
    @Test //post api using file
    void postAPIUsingFile() throws IOException {

     //read File to string using FileUtils   //
   //   FileUtils.readFileToString(new File(BASE_PATH), StandardCharsets.UTF_8);
        File file = new File(TEST_DATA_JSON);
        String postAPIRequestBody = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        System.out.println(postAPIRequestBody);
        RestAssured.given().contentType(ContentType.JSON).baseUri(BASE_URI).body(postAPIRequestBody).log().all().
                when().post(BOOKING).
                then().assertThat().log().body().statusCode(200);
        //Validate Api response
        Response response = RestAssured.given().contentType(ContentType.JSON).baseUri(BASE_URI).body(postAPIRequestBody).log().all().
                when().post(BOOKING).
                then().assertThat().log().body().statusCode(200).extract().response();
        JSONArray jsonArray = com.jayway.jsonpath.JsonPath.read(response.body().asString(),"$.booking..firstname");
        String firstName = (String) jsonArray.get(0);

        JsonPath jsonPath = response.jsonPath();
        System.out.println("Booking id is: " + jsonPath.get("bookingid"));
        //read the firstname from the response body
        System.out.println("firstname  is: " + jsonPath.get("booking.firstname"));
      //Assert the firstname    //
        String firstname = jsonPath.get("booking.firstname");
        System.out.println("firstname is: " + firstname);
        //Assert the firstname
        String lastname = jsonPath.get("booking.lastname");
        System.out.println("lastname is: " + lastname);
        //Assert the totalprice
        int totalprice = jsonPath.get("booking.totalprice");
        System.out.println("totalprice is: " + totalprice);
        //Assert the depositpaid
        boolean depositpaid = jsonPath.get("booking.depositpaid");
        System.out.println("depositpaid is: " + depositpaid);
        //Assert the checkin
        String checkin = jsonPath.get("booking.bookingdates.checkin");
        System.out.println("checkin is: " + checkin);
        //Assert the checkout
        String checkout = jsonPath.get("booking.bookingdates.checkout");
        System.out.println("checkout is: " + checkout);
        //Assert the additionalneeds
        String additionalneeds = jsonPath.get("booking.additionalneeds");
        System.out.println("additionalneeds is: " + additionalneeds);

    }
}

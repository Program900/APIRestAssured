package org.example.postAPIRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.example.pojos.Booking;
import org.example.pojos.BookingDates;
import org.example.utils.FileNameConstants;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PostAPIRequestUsingPojos {


    @Test //create a post request Method using Pojo classes and validate the response
    public void createBookingUsingPojo() throws IOException {
  //read the expected json schema file
        String expectedJsonSchema=   FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA), StandardCharsets.UTF_8);
        //create an object of the BookingDates class
        BookingDates bookingDates = new BookingDates("2021-07-01", "2021-07-01");
        //create an object of the Booking class
        Booking booking = new Booking("Jim", "Brown", 111, true, bookingDates, "Breakfast");

        //Object Mapper
        //Serialization - Java Object to JSON
        //create an object of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //convert the booking object to a JSON string
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
            //print the JSON string
            System.out.println(requestBody);

            //Deserialization - JSON to Java Object
            //Deserialization - JSON to Java Object - convert the JSON string to a booking object
            Booking bookingDetails = objectMapper.readValue(requestBody, Booking.class);
            getBookingDetails(bookingDetails);


            //get response from the post request
                Response response = RestAssured.given().contentType(ContentType.JSON).baseUri("https://restful-booker.herokuapp.com").body(requestBody).log().all().
                    when().post("/booking").
                    then().assertThat().log().body().statusCode(200).extract().response();
            //get the booking id from the response body using the path method
            int bookingId = response.path("bookingid");
            //print the booking id
            System.out.println("Booking id is: " + bookingId);
            //get the booking details using the booking id
            Response getResponse = RestAssured.given().contentType(ContentType.JSON).baseUri("https://restful-booker.herokuapp.com").log().all().
                    when().get("/booking/" + bookingId).
                    then().assertThat().log().body().statusCode(200).extract().response();
             //validate the response body using the expected json schema
            RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                    .get("/{bookingId}",bookingId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body(JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));


        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    private void getBookingDetails(Booking bookingDetails) {
        //print the booking firstname
        System.out.println(bookingDetails.getFirstname());
        //print the booking totalprice
        System.out.println(bookingDetails.getTotalprice());
        //print the booking checkin date
        System.out.println(bookingDetails.getBookingdates().getCheckin());
        //print the booking checkout date
        System.out.println(bookingDetails.getBookingdates().getCheckout());


    }

}

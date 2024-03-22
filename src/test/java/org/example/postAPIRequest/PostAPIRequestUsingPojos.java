package org.example.postAPIRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.pojos.Booking;
import org.example.pojos.BookingDates;
import org.testng.annotations.Test;

public class PostAPIRequestUsingPojos {


    @Test //create a post request Method using Pojo classes and validate the response
    public void createBookingUsingPojo() {
        //create an object of the BookingDates class
        BookingDates bookingDates = new BookingDates("2021-07-01", "2021-07-01");
        //create an object of the Booking class
        Booking booking = new Booking("Jim", "Brown", 111, true, bookingDates, "Breakfast");

        //Object Mapper
//Serialization - Java Object to JSON
//Deserialization - JSON to Java Object
        //create an object of the ObjectMapper class
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //convert the booking object to a JSON string
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
            //print the JSON string
            System.out.println(requestBody);
            
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

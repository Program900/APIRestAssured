package org.example.postAPIRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
            Booking booking1 = objectMapper.readValue(requestBody, Booking.class);
            //print the booking object
            System.out.println(booking1);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}

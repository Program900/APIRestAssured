package org.example.utils;

public class FileNameConstants {
    public static final String BASE_PATH = "src/test/resources/";
    public static final String BASE_URI = "https://restful-booker.herokuapp.com";
    public static final String BOOKING = "/booking";
    //json schema path  //
    public static final String JSON_SCHEMA = BASE_PATH+"expectedJsonSchema.json";
    public static final String BOOKING_ID = "/booking/{bookingid}";
    public static final String AUTH = "/auth";
    public static final String HEALTH = "/ping";
    public static final String TOKEN    = "token";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "password123";
    public static final String FIRSTNAME = "Jim";
    public static final String LASTNAME = "Brown";
    public static final int TOTALPRICE = 111;
    public static final boolean DEPOSITPAID = true;
    public static final String CHECKIN = "2021-07-01";
    public static final String CHECKOUT = "2021-07-01";
    public static final String ADDITIONALNEEDS = "Breakfast";

    public static final String TEST_DATA_JSON = BASE_PATH+"testData.json";
    //Json put request body path
    public static final String PUT_REQUEST_JSON = BASE_PATH+"putRequest.json";
    //Json patch request body path
    public static final String PATCH_REQUEST_JSON = BASE_PATH+ "patchRequest.json";
    //Json post request body path
    public static final String POST_REQUEST_JSON = BASE_PATH+"postRequest.json";

    //Json token body path
    public static final String TOKEN_REQUEST_JSON = BASE_PATH+"tokenRequest.json";




}

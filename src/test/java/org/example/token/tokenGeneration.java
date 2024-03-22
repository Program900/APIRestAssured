package org.example.token;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.example.utils.FileNameConstants;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class tokenGeneration {
    //token Generation with Json body and get response from the post request
    @Test
    void generateToken(){
        System.out.println("Generate Token");
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "admin");
        requestBody.put("password", "password123");
        Response tokenAPIresponse =RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com").body(requestBody.toJSONString()).
                log().all().
                when().post("/auth").
                then().assertThat().
                log().body().
                statusCode(200).
                body("token", Matchers.notNullValue()).extract().response();
    }
    //Token generation using File and get response from the post request

    @Test
    void generateTokenUsingFile() throws IOException {
        System.out.println("Generate Token using File");
        //Read the token using file Utils
        String tokenRequestBody =FileUtils.readFileToString(new File(FileNameConstants.TOKEN_REQUEST_JSON), StandardCharsets.UTF_8);
        Response tokenAPIresponse =RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com").body(tokenRequestBody).
                log().all().
                when().post("/auth").
                then().assertThat().
                log().body().
                statusCode(200).
                body("token", Matchers.notNullValue()).extract().response();
        //print the response body
        System.out.println(tokenAPIresponse.getBody().asString());
        //print the token
       // System.out.println(tokenAPIresponse.path("token"));
        //store the token in a string
        String token = tokenAPIresponse.path("$.token");
    }
}

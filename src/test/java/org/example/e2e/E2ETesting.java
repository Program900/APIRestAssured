package org.example.e2e;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.example.utils.FileNameConstants;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class E2ETesting {

        @Test
        public void E2EAPIRequest() {

            try {
                String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_REQUEST_JSON),"UTF-8");

                String tokenAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.TOKEN_REQUEST_JSON),"UTF-8");

                String putAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PUT_REQUEST_JSON),"UTF-8");
                String patchtAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PATCH_REQUEST_JSON), "UTF-8");

                //post api call
                Response response =
                        RestAssured
                                .given()
                                .contentType(ContentType.JSON)
                                .body(postAPIRequestBody)
                                .baseUri("https://restful-booker.herokuapp.com/booking")
                                .when()
                                .post()
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .extract()
                                .response();

                JSONArray jsonArray = JsonPath.read(response.body().asString(),"$.booking..firstname");
                String firstName = (String) jsonArray.get(0);

                Assert.assertEquals(firstName, "api testing");

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
                Response tokenAPIResponse =
                        RestAssured
                                .given()
                                .contentType(ContentType.JSON)
                                .body(tokenAPIRequestBody)
                                .baseUri("https://restful-booker.herokuapp.com/auth")
                                .when()
                                .post()
                                .then()
                                .assertThat()
                                .statusCode(200)
                                .extract()
                                .response();

                String token = JsonPath.read(tokenAPIResponse.body().asString(),"$.token");

                //put api call
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .body(putAPIRequestBody)
                        .header("Cookie", "token="+token)
                        .baseUri("https://restful-booker.herokuapp.com/booking")
                        .when()
                        .put("/{bookingId}",bookingId)
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .body("firstname", Matchers.equalTo("Specflow"))
                        .body("lastname", Matchers.equalTo("Selenium C#"));

                //patch api call
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .body(patchtAPIRequestBody)
                        .header("Cookie", "token=" + token)
                        .baseUri("https://restful-booker.herokuapp.com/booking")
                        .when()
                        .patch("/{bookingId}", bookingId)
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .body("firstname", Matchers.equalTo("Testers Talk"))
                        .body("lastname", Matchers.equalTo("Selenium C#"));

                //delete api request to clean up the data
                RestAssured
                        .given()
                        .contentType(ContentType.JSON)
                        .header("Cookie", "token=" + token)
                        .baseUri("https://restful-booker.herokuapp.com/booking")
                        .when()
                        .delete("/{bookingId}", bookingId)
                        .then()
                        .assertThat()
                        .statusCode(201);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }



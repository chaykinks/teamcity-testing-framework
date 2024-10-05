package org.example.teamcity.api;

import org.junit.Test;
import org.apache.http.HttpStatus;
import io.restassured.RestAssured;
import org.example.teamcity.api.models.User;
import org.example.teamcity.api.spec.Specifications;

public class BuildConfigurationTest extends BaseApiTest{
    @Test
    public void buildConfigurationTest(){
        var user = User.builder()
                .username("admin")
                .password("admin")
                .build();

        var token = RestAssured
                .given()
                .spec(Specifications.getSpec().authorizedSpec(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();

        System.out.println(token);
    }
}

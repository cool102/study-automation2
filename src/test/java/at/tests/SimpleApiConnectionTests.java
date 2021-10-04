package at.tests;

import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.dto.users.UserDto;
import at.study.redmine.api.dto.users.UserInfoDto;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.Collections;

public class SimpleApiConnectionTests {

    private final RequestSpecification ADMIN_REQUEST_SPECIFICATION = RestAssured.given()
            .baseUri("http://edu-at.dfu.i-teco.ru")
            .header("X-Redmine-API-Key", "55dfd83d5c925f999826c683114e589a4dd9f7e6");

    @Test
    public void testSimpleRequest() {
        ADMIN_REQUEST_SPECIFICATION
                .log().all()
                .request(Method.GET, "/users.json")
                .then()
                .log().all()
                .statusCode(200);

    }

    /**
     * создали объект типа UserInfoDto У которого всего 1 поле User
     * вызвали setter в классе UserInfoDto В который передали объект класса UserDto в котором уже есть нужные поля
     * вызвали сеттер и установили имя и т.д.
     */
    @Test
    public void userCreationTest() {

        UserInfoDto body = new UserInfoDto(new UserDto());


        ADMIN_REQUEST_SPECIFICATION
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(body))
                .request(Method.POST, "/users.json")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setIsAdmin(true);
        user.setTokens(Collections.singletonList(new Token(user)));

        user.create();

        RestApiClient apiClient = new RestAssuredClient(user);

        apiClient.execute(new RestAssuredRequest(
                RestMethod.GET,
                "/users.json",
                null,
                null,
                null
                ));
    }
}

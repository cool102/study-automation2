package at.tests;

import at.study.redmine.allure.asserts.AllureAssert;
import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestRequest;
import at.study.redmine.api.client.RestResponse;
import at.study.redmine.api.dto.users.UserDto;
import at.study.redmine.api.dto.users.UserInfoDto;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.model.user.Email;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static at.study.redmine.api.rest_assured.GsonProvider.GSON;

/**
 * Создание пользователя. Пользователь без прав администратора
 */
public class TestCaseTwo {
   private User userOne;
   private UserInfoDto body;
   private RestApiClient client;
   private RestRequest request;

   private String bodyAsString;

    @BeforeMethod
    public void prepareFixtures() {
        userOne = new User() {{

            setTokens(Collections.singletonList(new Token(this)));
            setEmails(Collections.singletonList(new Email(this)));
        }}.create();
    }

    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Создание пользователя. Пользователь без прав администратора")
    public void noAdminUserCreateAnotherUserTest() {
        client = new RestAssuredClient(userOne);

        body = new UserInfoDto(new UserDto(null, userOne.getLogin(), userOne.getIsAdmin(),
                userOne.getFisrtName(),
                userOne.getStatus().statusCode, userOne.getLastName(),
                userOne.getEmails().get(0).getAddress(),
                userOne.getPassword(),
                userOne.getCreatedOn(),
                userOne.getLastLoginOn()
        ));

        bodyAsString = GSON.toJson(body, UserInfoDto.class);

        request = new RestAssuredRequest(RestMethod.POST,
                "/users.json", null, null, bodyAsString);


        RestResponse response = client.execute(request);
        AllureAssert.assertEquals(response.getStatusCode(), 403, "Проверка равенства кодов");

    }


}

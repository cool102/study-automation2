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
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * Получение пользователей. Пользователь без прав администратора
 */
public class TestCaseThree {
    private User userOne;
    private User userTwo;

    @BeforeMethod
    public void prepareFixtures() {
        Allure.step("Создан пользователь 1");
        userOne = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
            setEmails(Collections.singletonList(new Email(this)));
        }}.create();
        Allure.step("Создан пользователь 2");
        userTwo = new User() {{

        }}.create();
    }

    @Test(description = "Получение пользователей. Пользователь без прав администратора")
    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.MINOR)
    public void noAdminUserGetUserTest() {
        int idUserOne = userOne.getId();
        int idUserTwo = userTwo.getId();

        String urlFirstReq = "/users/" + idUserOne + ".json";
        String urlSecondReq = "/users/" + idUserTwo + ".json";

        RestApiClient client = new RestAssuredClient(userOne);

        RestRequest requestOne = new RestAssuredRequest(RestMethod.GET,
                urlFirstReq, null, null, null);

        test3point1(client, requestOne);


        //Map<String,String> headerWithApiKeyUserOne = new HashMap<String,String>(){{
        //    put("X-Redmine-API-Key", userOne.getTokens().get(0).getValue());
        //}};

        RestRequest requestTwo = new RestAssuredRequest(RestMethod.GET,
                urlSecondReq, null, null, null);

        test3point2(client, requestTwo);

    }

    @Step("Отправить запрос GET на получение пользователя 1, используя ключ API пользователя 1 ")
    private void test3point1(RestApiClient client, RestRequest request) {
        RestResponse response = client.execute(request);

        AllureAssert.assertEquals(response.getStatusCode(), 200, "Проверка равенства кодов");
        UserInfoDto userInfoDto = response.getPayload(UserInfoDto.class);
        UserDto userDto = userInfoDto.getUser();

        AllureAssert.assertEquals(userDto.getLogin(), userOne.getLogin(), "Проверка равенства логинов");

        AllureAssert.assertTrue(response.getPayLoad().contains("\"admin\":false"), "Проверка равенства");

        String apiValue = userOne.getTokens().get(0).getValue();
        AllureAssert.assertTrue(response.getPayLoad().contains(apiValue), "Проверка равенства");
    }

    @Step(" Отправить запрос GET на получения пользователя 2, используя ключ API пользователя 1")
    private void test3point2(RestApiClient client, RestRequest requestTwo) {
        RestResponse response = client.execute(requestTwo);
        AllureAssert.assertEquals(response.getStatusCode(), 200, "Проверка равенства кодов");
        AllureAssert.assertFalse(response.getPayLoad().contains("admin"), "Проверка условия");
        AllureAssert.assertFalse(response.getPayLoad().contains("api_key"), "Проверка условия");


    }


}

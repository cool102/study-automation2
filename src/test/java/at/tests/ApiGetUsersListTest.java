package at.tests;

import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestRequest;
import at.study.redmine.api.client.RestResponse;
import at.study.redmine.api.dto.users.UsersListDto;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static at.study.redmine.api.client.RestMethod.GET;

public class ApiGetUsersListTest {
    RestApiClient client;
    RestRequest request;

    @BeforeMethod
    public void prepareFixtures() {
        User user = new User() {{
            setIsAdmin(true);
            setTokens(Arrays.asList(new Token(this)));
        }}.create();

        client = new RestAssuredClient(user);
        request = new RestAssuredRequest(GET, "/users.json", null, null, null);

    }

    @Test (description = "Проверка получения списка пользователей по API запросу")
    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.MINOR)
    public void apiGetUsersListTest() {
        RestResponse response = client.execute(request);
        Allure.step("Проверка получения кода 200");
        Assert.assertEquals(response.getStatusCode(), 200);

        UsersListDto responseData = response.getPayload(UsersListDto.class);
        Assert.assertEquals(responseData.getLimit(), Integer.valueOf(25));
    }
}

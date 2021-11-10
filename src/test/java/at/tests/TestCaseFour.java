package at.tests;

import at.study.redmine.allure.asserts.AllureAssert;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestResponse;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Удаление пользователей. Пользователь без прав администратора
 */
public class TestCaseFour {
    private User noAdminUser;
    private User user;


    @BeforeMethod
    public void prepareFixtures() {
        Allure.step("Создание пользователя 1");
        noAdminUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();
        Allure.step("Создание пользователя 2");
        user = new User().create();
    }

    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Удаление пользователей. Пользователь без прав администратора")
    public void noAdminUserDeleteAnotherUserTest() {

        int userId = user.getId();
        int noAdminUserId = noAdminUser.getId();
        String urlOne = "/users/" + userId + ".json";
        String urlTwo = "/users/" + noAdminUserId + ".json";
        String query = "SELECT * FROM users WHERE id = ?";

        RestAssuredClient client = new RestAssuredClient(noAdminUser);

        test4point1(userId, urlOne, query, client);

        test4point2(noAdminUserId, urlTwo, query, client);

    }

    @Step(" Отправить запрос DELETE на удаление пользователя 1,пользователем 1 (удаление себя)")
    private void test4point2(int noAdminUserId, String urlTwo, String query, RestAssuredClient client) {
        RestAssuredRequest requestTwo = new RestAssuredRequest(RestMethod.DELETE,
                urlTwo, null, null, null);

        RestResponse responseTwo = client.execute(requestTwo);

        AllureAssert.assertEquals(responseTwo.getStatusCode(), 403, "Проверка равенства кодов");

        List<Map<String, Object>> resultTwo = PostgresConnection.INSTANCE.executeQuery(
                query, noAdminUserId);
        AllureAssert.assertFalse(resultTwo.isEmpty(), "Проверка условия");
        AllureAssert.assertEquals(resultTwo.get(0).get("id"), noAdminUserId, "Проверка равенства id");
    }

    @Step("Отправить запрос DELETE на удаление пользователя 2, пользователем 1 (удаление другого пользователя)")
    private void test4point1(int userId, String urlOne, String query, RestAssuredClient client) {
        RestAssuredRequest requestOne = new RestAssuredRequest(RestMethod.DELETE,
                urlOne, null, null, null);
        RestResponse responseOne = client.execute(requestOne);
        AllureAssert.assertEquals(responseOne.getStatusCode(), 403, "Проверка равенства кодов");

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query, userId);
        AllureAssert.assertFalse(result.isEmpty(), "Проверка условия");
        AllureAssert.assertEquals(result.get(0).get("id"), userId, "Проверка равенства id");
    }

}

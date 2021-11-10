package at.tests;

import at.study.redmine.allure.asserts.AllureAssert;
import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestRequest;
import at.study.redmine.api.client.RestResponse;
import at.study.redmine.api.dto.errors.ErrorDto;
import at.study.redmine.api.dto.users.UserDto;
import at.study.redmine.api.dto.users.UserInfoDto;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.model.user.Email;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import at.study.redmine.utils.StringUtils;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static at.study.redmine.api.rest_assured.GsonProvider.GSON;

/**
 * Создание, изменение, получение, удаление пользователя. Администратор системы
 */

public class TestCaseOne {
    RestApiClient client;
    RestRequest request;
    UserInfoDto body;
    User createdUser;
    String bodyAsString;
    User userAdmin;
    UserInfoDto responseInfoDataForPutReq;
    Integer idForTest1point5;

    @BeforeMethod
    public void prepareFixtures() {
        userAdmin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));

        }}.create();

    }
    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void TestCase1() {

        createdUser = new User() {{
            setStatus(Status.UNACCEPTED);
            setEmails(Collections.singletonList(new Email(this)));
        }};


        body = new UserInfoDto(new UserDto(null, createdUser.getLogin(), createdUser.getIsAdmin(),
                createdUser.getFisrtName(),
                createdUser.getStatus().statusCode, createdUser.getLastName(),
                createdUser.getEmails().get(0).getAddress(),
                createdUser.getPassword(),
                createdUser.getCreatedOn(),
                createdUser.getLastLoginOn()
        ));

        client = new RestAssuredClient(userAdmin);


        bodyAsString = GSON.toJson(body, UserInfoDto.class);
        request = new RestAssuredRequest(RestMethod.POST,
                "/users.json", null, null, bodyAsString);


        test1point1();

        test1point2();

        test1point3();

        test1point4();

        test1point5();

        test1point6();

        test1point7();

    }

    @Step("Отправить запрос POST на создание пользователя (пользователь должен иметь status = 2)")
    private void test1point1() {
        RestResponse response = client.execute(request);
        AllureAssert.assertEquals(response.getStatusCode(), 201, "Проверка получения кода 201");

        UserInfoDto responseInfoData = response.getPayload(UserInfoDto.class);
        AllureAssert.assertEquals(responseInfoData.getUser().getLogin(), createdUser.getLogin(), "Проверка совпадения логинов в API-ответе с созданным пользователем");


        AllureAssert.assertNotNull(responseInfoData.getUser().getId(), "Проверка, что id пользователя в API ответе не равен null");
        int idApi = responseInfoData.getUser().getId();
        int idDataBase = userAdmin.read(idApi).getId();

        AllureAssert.assertEquals(idDataBase, idApi, "Проверка равенства id в API-ответе и в БД");
        responseInfoDataForPutReq = responseInfoData;
    }

    @Step("Отправка POST запроса на создание пользователя повторно с тем же телом запроса")
    private void test1point2() {
        RestResponse responseRepeat = client.execute(request);
        AllureAssert.assertEquals(responseRepeat.getStatusCode(), 422, "Проверка получения кода 422 в API ответе");

        ErrorDto errors = responseRepeat.getPayload(ErrorDto.class);

        AllureAssert.assertEquals(errors.getErrList().get(0), "Email уже существует", "Проверка получения сообщений: \"Email уже существует\"");
        AllureAssert.assertEquals(errors.getErrList().get(1), "Пользователь уже существует", "Проверка получения сообщений: \"Пользователь уже существует\"");
    }

    @Step("Отправка POST на создание пользователя повторно с тем же телом запроса, при этом изменив \"email\" на невалидный, а \"password\" - строку из 4 символов")
    public void test1point3() {
        Allure.step("Создан невалидный email и добавлен в список email этого пользователя");
        String invalidEmailAddress = createdUser.getEmails().get(0).getAddress() + "@";
        Email invalidEmail = new Email() {{
            setAddress(invalidEmailAddress);
        }};
        List<Email> invalidEmailList = new ArrayList<>();
        invalidEmailList.add(invalidEmail);
        createdUser.setEmails(invalidEmailList);

        Allure.step("Создан невалидный password и установлен пользователю");
        String invalidPassword = StringUtils.randomEnglishString(4);
        createdUser.setPassword(invalidPassword);

        body = new UserInfoDto(new UserDto(null, createdUser.getLogin(), createdUser.getIsAdmin(),
                createdUser.getFisrtName(),
                createdUser.getStatus().statusCode, createdUser.getLastName(),
                createdUser.getEmails().get(0).getAddress(),
                createdUser.getPassword(),
                createdUser.getCreatedOn(),
                createdUser.getLastLoginOn()
        ));

        bodyAsString = GSON.toJson(body, UserInfoDto.class);
        request = new RestAssuredRequest(RestMethod.POST,
                "/users.json", null, null, bodyAsString);
        RestResponse responseRepeatWithWorongPassAndMail = client.execute(request);

        AllureAssert.assertEquals(responseRepeatWithWorongPassAndMail.getStatusCode(), 422, "Проверка получения кода 422");
        ErrorDto errors = responseRepeatWithWorongPassAndMail.getPayload(ErrorDto.class);
        Allure.step("Проверка получения сообщений в API ответе:\"Email имеет неверное значение\"," +
                "\"Пользователь уже существует\",\"Пароль недостаточной длины (не может быть меньше 8 символа)\"");
        AllureAssert.assertEquals(errors.getErrList().get(0), "Email имеет неверное значение", "Проверка получения сообщений в API ответе:\"Email имеет неверное значение\"");
        AllureAssert.assertEquals(errors.getErrList().get(1), "Пользователь уже существует", "Проверка получения сообщений в API ответе:\"Пользователь уже существует\"");
        AllureAssert.assertEquals(errors.getErrList().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)", "Проверка получения сообщений в API ответе:\"Пароль недостаточной длины (не может быть меньше 8 символа)\"");
    }

    @Step("Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1")
    public void test1point4() {

        UserDto userDtoFromResponse = responseInfoDataForPutReq.getUser();

        int id = userDtoFromResponse.getId();
        String newUrlForPutReq = "/users/" + id + ".json";
        Allure.step("Установка статуса пользователя равным 1");
        UserInfoDto body = new UserInfoDto(userDtoFromResponse.setStatus(1));
        bodyAsString = GSON.toJson(body, UserInfoDto.class);


        request = new RestAssuredRequest(RestMethod.PUT,
                newUrlForPutReq, null, null, bodyAsString);
        RestResponse responseAfterPutReq = client.execute(request);

        AllureAssert.assertEquals(responseAfterPutReq.getStatusCode(), 204, "Проверка получения кода 204");
        User dbUser = userAdmin.read(id);

        AllureAssert.assertEquals(dbUser.getStatus().statusCode, 1, "Проверка статуса пользователя");
        idForTest1point5 = id;
    }

    @Step("Отправить запрос GET на получение пользователя")
    public void test1point5() {
        String newUrl = "/users/" + idForTest1point5 + ".json";
        request = new RestAssuredRequest(RestMethod.GET,
                newUrl, null, null, null);
        RestResponse responseFromGet = client.execute(request);

        AllureAssert.assertEquals(responseFromGet.getStatusCode(), 200, "Проверка получения кода");

        UserInfoDto userInfoDto = responseFromGet.getPayload(UserInfoDto.class);
        UserDto userDto = userInfoDto.getUser();

        AllureAssert.assertEquals(userDto.getStatus().intValue(), 1, "Проверка статуса пользователя ");

    }

    @Step("Отправить запрос DELETE на удаление пользователя")
    public void test1point6() {
        String newUrl = "/users/" + idForTest1point5 + ".json";
        request = new RestAssuredRequest(RestMethod.DELETE,
                newUrl, null, null, null);

        RestResponse apiResponse = client.execute(request);

        AllureAssert.assertEquals(apiResponse.getStatusCode(), 204, "Проверка получения кода");
        System.out.println(apiResponse.getStatusCode());

        try {
            User dbUSer = userAdmin.read(idForTest1point5);
        } catch (RuntimeException re) {

            AllureAssert.assertTrue(re.getMessage().equals("указанного пользователя не найдено"), "Проверка отсутствия пользователя в БД");
        }
    }

    @Step("Отправить запрос DELETE на удаление пользователя (повторно)")
    public void test1point7() {
        String newUrl = "/users/" + idForTest1point5 + ".json";
        request = new RestAssuredRequest(RestMethod.DELETE,
                newUrl, null, null, null);
        RestResponse apiResponseAfterSeconDeletion = client.execute(request);

        AllureAssert.assertEquals(apiResponseAfterSeconDeletion.getStatusCode(), 404, "Проверка получения кода");
    }

}

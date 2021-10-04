package at.tests;

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
import org.testng.Assert;
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

    @Test
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

    private void test1point2() {
        RestResponse responseRepeat = client.execute(request);
        Assert.assertEquals(responseRepeat.getStatusCode(), 422);

        ErrorDto errors = responseRepeat.getPayload(ErrorDto.class);
        Assert.assertEquals(errors.getErrList().get(0), "Email уже существует");
        Assert.assertEquals(errors.getErrList().get(1), "Пользователь уже существует");
    }

    private void test1point1() {
        RestResponse response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 201);

        UserInfoDto responseInfoData = response.getPayload(UserInfoDto.class);
        Assert.assertEquals(responseInfoData.getUser().getLogin(), createdUser.getLogin());

        Assert.assertNotNull(responseInfoData.getUser().getId());
        int idApi = responseInfoData.getUser().getId();
        int idDataBase = userAdmin.read(idApi).getId();
        Assert.assertEquals(idDataBase, idApi);
        responseInfoDataForPutReq = responseInfoData;
    }


    public void test1point3() {

        String invalidEmailAddress = createdUser.getEmails().get(0).getAddress() + "@";
        Email invalidEmail = new Email() {{
            setAddress(invalidEmailAddress);
        }};
        List<Email> invalidEmailList = new ArrayList<>();
        invalidEmailList.add(invalidEmail);
        createdUser.setEmails(invalidEmailList);

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

        Assert.assertEquals(responseRepeatWithWorongPassAndMail.getStatusCode(), 422);
        ErrorDto errors = responseRepeatWithWorongPassAndMail.getPayload(ErrorDto.class);
        Assert.assertEquals(errors.getErrList().get(0), "Email имеет неверное значение");
        Assert.assertEquals(errors.getErrList().get(1), "Пользователь уже существует");
        Assert.assertEquals(errors.getErrList().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

    public void test1point4() {

        UserDto userDtoFromResponse = responseInfoDataForPutReq.getUser();

        int id = userDtoFromResponse.getId();
        String newUrlForPutReq = "/users/" + id + ".json";

        UserInfoDto body = new UserInfoDto(userDtoFromResponse.setStatus(1));
        bodyAsString = GSON.toJson(body, UserInfoDto.class);


        request = new RestAssuredRequest(RestMethod.PUT,
                newUrlForPutReq, null, null, bodyAsString);
        RestResponse responseAfterPutReq = client.execute(request);
        Assert.assertEquals(responseAfterPutReq.getStatusCode(), 204);
        User dbUser = userAdmin.read(id);
        Assert.assertEquals(dbUser.getStatus().statusCode, 1);
        idForTest1point5 = id;
    }

    public void test1point5() {
        String newUrl = "/users/" + idForTest1point5 + ".json";
        request = new RestAssuredRequest(RestMethod.GET,
                newUrl, null, null, null);
        RestResponse responseFromGet = client.execute(request);
        Assert.assertEquals(responseFromGet.getStatusCode(), 200);

        UserInfoDto userInfoDto = responseFromGet.getPayload(UserInfoDto.class);
        UserDto userDto = userInfoDto.getUser();

        Assert.assertEquals(userDto.getStatus().intValue(), 1);

    }


    public void test1point6() {
        String newUrl = "/users/" + idForTest1point5 + ".json";
        request = new RestAssuredRequest(RestMethod.DELETE,
                newUrl, null, null, null);

        RestResponse apiResponse = client.execute(request);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
        System.out.println(apiResponse.getStatusCode());

        try {
            User dbUSer = userAdmin.read(idForTest1point5);
        } catch (RuntimeException re)
        {
            Assert.assertTrue(re.getMessage().equals("указанного пользователя не найдено"));
        }
    }
    public void test1point7() {
        String newUrl = "/users/" + idForTest1point5 + ".json";
        request = new RestAssuredRequest(RestMethod.DELETE,
                newUrl, null, null, null);
        RestResponse apiResponseAfterSeconDeletion = client.execute(request);
        Assert.assertEquals(apiResponseAfterSeconDeletion.getStatusCode(), 404);
    }

}

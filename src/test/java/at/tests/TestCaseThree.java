package at.tests;

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
import org.testng.Assert;
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

        userOne = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
            setEmails(Collections.singletonList(new Email(this)));
        }}.create();

        userTwo = new User() {{

        }}.create();
    }

    @Test
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

    private void test3point1(RestApiClient client, RestRequest request) {
        RestResponse response = client.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);
        UserInfoDto userInfoDto = response.getPayload(UserInfoDto.class);
        UserDto userDto = userInfoDto.getUser();

        Assert.assertEquals(userDto.getLogin(), userOne.getLogin());

        Assert.assertTrue(response.getPayLoad().contains("\"admin\":false"));

        String apiValue = userOne.getTokens().get(0).getValue();
        Assert.assertTrue(response.getPayLoad().contains(apiValue));
    }


    private void test3point2(RestApiClient client, RestRequest requestTwo) {
        RestResponse response = client.execute(requestTwo);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(response.getPayLoad().contains("admin"));
        Assert.assertFalse(response.getPayLoad().contains("api_key"));


    }


}

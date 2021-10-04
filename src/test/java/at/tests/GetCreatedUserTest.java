package at.tests;

import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestRequest;
import at.study.redmine.api.dto.users.UserDto;
import at.study.redmine.api.dto.users.UserInfoDto;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.api.rest_assured.RestAssuredResponse;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * создание пользователя и получения его другим пользователем по id
 */
public class GetCreatedUserTest {

    private
    User createdUser;
    private
    RestApiClient client;
    private
    RestRequest request;

    //User apiUser;

    @BeforeMethod

    public void prepareFixtures() {
        createdUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        User apiUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
            setIsAdmin(true);
        }}.create();

        client = new RestAssuredClient(apiUser);

        Integer createdUserId = createdUser.getId();

        request = new RestAssuredRequest(RestMethod.GET,
                "/users/" + createdUserId + ".json", null, null, null);

    }

    @Test

    public void getCreatedUserIdTest() {

        RestAssuredResponse response = (RestAssuredResponse) client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        Assert.assertEquals(responseUser.getLastName(), createdUser.getLastName());
        Assert.assertEquals(responseUser.getFirstName(), createdUser.getFisrtName());
        Assert.assertEquals(responseUser.getAdmin(), createdUser.getIsAdmin());
        Assert.assertNull(responseUser.getMail());
        Assert.assertEquals(responseUser.getStatus().intValue(),
                createdUser.getStatus().statusCode);


    }
}

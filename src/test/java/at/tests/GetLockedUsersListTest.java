package at.tests;

import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestRequest;
import at.study.redmine.api.client.RestResponse;
import at.study.redmine.api.dto.users.UserDto;
import at.study.redmine.api.dto.users.UsersListDto;
import at.study.redmine.api.rest_assured.RestAssuredClient;
import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.api.rest_assured.RestAssuredResponse;
import at.study.redmine.model.user.Email;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class GetLockedUsersListTest {

    private RestApiClient client;
    private RestRequest request;
    private  User lockedUser;

    @BeforeMethod
     public void prepareFixtures(){

        User user = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        lockedUser = new User(){{
            setStatus(Status.LOCKED);
            setEmails(Collections.singletonList(new Email(this)));
        }}.create();

        client = new RestAssuredClient(user);
        request = new RestAssuredRequest(RestMethod.GET, "/users.json",null,new HashMap<String,String>(){{
            put("status","3");

        }},null);

    }

    @Test
    public void getLockedUsersListTest(){
        RestResponse response = client.execute(request);
        Assert.assertEquals(response.getStatusCode(), 200);

        UsersListDto responseData = response.getPayload(UsersListDto.class);

        UserDto lockedUserFormApi = responseData.getUsers().stream()
                .filter(userDto -> userDto.getLogin().equals(lockedUser.getLogin()))
                .findFirst().get();

        Assert.assertEquals(lockedUserFormApi.getLogin(),lockedUser.getLogin());
        Assert.assertEquals(lockedUserFormApi.getMail(),lockedUser.getEmails().get(0).getAddress());

        System.out.println(lockedUserFormApi.getMail());
        System.out.println(lockedUser.getEmails().get(0).getAddress());

    }

}

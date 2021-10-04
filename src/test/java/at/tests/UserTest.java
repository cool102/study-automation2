package at.tests;

import at.study.redmine.api.rest_assured.RestAssuredRequest;
import at.study.redmine.model.user.User;
import org.testng.annotations.Test;

public class UserTest {
    @Test
    public void createUserObjectTest(){
        User user = new User();
        user.setFisrtName("Иван");
        user.setLastName("Петров");
    }
     }

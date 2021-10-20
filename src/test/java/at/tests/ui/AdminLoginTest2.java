package at.tests.ui;

import at.study.redmine.model.user.User;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AdminLoginTest2 extends BaseUITest {
    private User user;


    @BeforeMethod
    public void prepareFixtures() {
        user = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
    }

    @Test
    @SneakyThrows
    public void adminLoginTest() {


        headerPage.loginButton.click();
        loginPage.login(user);
        assertEquals(headerPage.myAccount.getText(), "Моя учётная запись");

    }

}

package at.tests.ui;

import at.study.redmine.model.user.User;
import at.tests.ui.BaseUITest;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AdminLoginTest extends BaseUITest {
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
        assertEquals(headerPage.homePage.getText(),"Домашняя страница");
        assertTrue(headerPage.homePage.isDisplayed());
        assertTrue(headerPage.myPage.isDisplayed());
        assertTrue(headerPage.projects.isDisplayed());
        assertTrue(headerPage.administration.isDisplayed());
        assertTrue(headerPage.help.isDisplayed());
        assertTrue(headerPage.myAccount.isDisplayed());
        assertTrue(headerPage.logOut.isDisplayed());

    }

}

package at.tests.ui;

import at.study.redmine.model.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ActiveUserLoginTest extends BaseUITest {
    User user;

    @BeforeMethod
    public void prepareFixture() {

        user = new User() {{

        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);

    }

    @Test(expectedExceptions = org.openqa.selenium.NoSuchElementException.class)

    public void activeUserLoginTests() {
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(headerPage.loggedAs.getText(), "Вошли как " + user.getLogin());
        assertEquals(headerPage.myAccount.getText(), "Моя учётная запись");
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertTrue(headerPage.homePage.isDisplayed());
        assertTrue(headerPage.myPage.isDisplayed());
        assertTrue(headerPage.projects.isDisplayed());
        assertTrue(headerPage.help.isDisplayed());
        assertTrue(headerPage.myAccount.isDisplayed());
        assertTrue(headerPage.logOut.isDisplayed());

        assertFalse(headerPage.administration.isDisplayed());
        assertFalse(headerPage.loginButton.isDisplayed());
        assertFalse(headerPage.register.isDisplayed());

        assertTrue(headerPage.findToolBar.isDisplayed());


    }
}

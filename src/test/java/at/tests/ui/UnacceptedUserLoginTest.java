package at.tests.ui;

import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UnacceptedUserLoginTest extends BaseUITest {

    User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);

    }

    @Test(expectedExceptions = org.openqa.selenium.NoSuchElementException.class)
    public void unacceptedUserLogin() {
        Assert.assertTrue(headerPage.homePage.isDisplayed());
        Assert.assertEquals(loginPage.flashError.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
        Assert.assertFalse(headerPage.myPage.isDisplayed());
        Assert.assertTrue(headerPage.loginButton.isDisplayed());
        Assert.assertTrue(headerPage.register.isDisplayed());

    }


}

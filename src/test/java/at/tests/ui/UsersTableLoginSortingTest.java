package at.tests.ui;

import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import at.study.redmine.utils.CompareUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class UsersTableLoginSortingTest extends BaseUITest {
    User admin;

    @BeforeMethod
    public void prepareFixture() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        new User() {{
            setLogin("Alpha");

        }}.create();

        new User() {{
            setLogin("Beta");
        }}.create();

        new User() {{
            setLogin("Zetta");
        }}.create();


    }

    @Test
    public void UsersTableLoginSortTest() {

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);

        Assert.assertTrue(headerPage.homePage.isDisplayed());

        headerPage.administration.click();
        Assert.assertTrue(administrationPage.content.isDisplayed());

        administrationPage.users.click();
        Assert.assertTrue(usersTablePage.button("Пользователь").isDisplayed());
        Assert.assertTrue(usersTablePage.button("Создано").isDisplayed());

        List<String> loginsBeforeClick = BrowserUtils.getElementsText(usersTablePage.login);
        CompareUtils.assertListSortedByLoginAsc(loginsBeforeClick);

        usersTablePage.button("Пользователь").click();
        List<String> loginsTextAfterClick = BrowserUtils.getElementsText(usersTablePage.login);
        CompareUtils.assertListSortedByLoginDesc(loginsTextAfterClick);


    }
}

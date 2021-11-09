package at.tests.ui;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.user.User;
import at.study.redmine.utils.StringUtils;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class AdministrationUserCreationTest extends BaseUITest {
    User admin;
    String randomLogin;
    String randomFirstName;
    String randomLastName;
    String randomEmail;

    @BeforeMethod
    public void prepareFixture() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        randomLogin = StringUtils.randomString("qazwsx", 5);
        randomFirstName = StringUtils.randomString("RFVBGTYHNMJU", 3);
        randomLastName = StringUtils.randomString("IKQALOP", 6);
        randomEmail = StringUtils.randomEmail();
    }

    @SneakyThrows
    @Test
    public void administrationUserCreationTest() {
        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);
        Assert.assertTrue(headerPage.homePage.isDisplayed());

        headerPage.administration.click();
        Assert.assertTrue(administrationPage.content.isDisplayed());

        administrationPage.users.click();
        administrationPage.newUser.click();

        newUserCreationPage.login.sendKeys(randomLogin);
        newUserCreationPage.firstName.sendKeys(randomFirstName);
        newUserCreationPage.lastName.sendKeys(randomLastName);
        newUserCreationPage.email.sendKeys(randomEmail);
        newUserCreationPage.passwordCreation.click();
        newUserCreationPage.submitUserCreationButton.click();
        String actualFlashNoticeText = newUserCreationPage.flashNotice.getText();
        Assert.assertEquals(actualFlashNoticeText, "Пользователь " + randomLogin + " создан.");
        System.out.println();


        String query = "SELECT * FROM users WHERE login =?";
        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query, randomLogin);
        String loginFromDb = result.get(0).get("login").toString();
        String firstnameFromDb = result.get(0).get("firstname").toString();
        String lastNameFromDb = result.get(0).get("lastname").toString();
        //  String emailFromDb = result.get(0).get("email").toString();

        Assert.assertEquals(randomLogin, loginFromDb);
        Assert.assertEquals(randomFirstName, firstnameFromDb);
        Assert.assertEquals(randomLastName, lastNameFromDb);
        //  Assert.assertEquals(randomLogin,loginFromDb);


    }
}

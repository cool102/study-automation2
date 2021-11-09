package at.tests.ui;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.user.User;
import at.study.redmine.utils.StringUtils;
import io.qameta.allure.Allure;
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
    @Test(description = "Администрирование. Создание пользователя")
    public void administrationUserCreationTest() {
        openBrowser();
        Allure.step("Клик по кнопке \"Войти \"");
        headerPage.loginButton.click();
        loginPage.login(admin);
        Allure.step("Проверка отображения домашней страницы");
        Assert.assertTrue(headerPage.homePage.isDisplayed());
        Allure.step("Клик по кнопке \"Админстрирование\"");
        headerPage.administration.click();
        Allure.step("Проверка отображению меню \"Админстрирование\"");
        Assert.assertTrue(administrationPage.content.isDisplayed());
        Allure.step("Клик по кнопке \"Пользователи \"");
        administrationPage.users.click();
        Allure.step("Клик по кнопке \"Новый пользователь\"");
        administrationPage.newUser.click();
        Allure.step("Заполнение логина");
        newUserCreationPage.login.sendKeys(randomLogin);
        Allure.step("Заполнение имени");
        newUserCreationPage.firstName.sendKeys(randomFirstName);
        Allure.step("Заполнение фамилии");
        newUserCreationPage.lastName.sendKeys(randomLastName);
        Allure.step("Заполнение email");
        newUserCreationPage.email.sendKeys(randomEmail);
        Allure.step("Клик по радиокнопке \"Создание пароля\"");
        newUserCreationPage.passwordCreation.click();
        Allure.step("Клик по кнопке \"Создать\"");
        newUserCreationPage.submitUserCreationButton.click();
        String actualFlashNoticeText = newUserCreationPage.flashNotice.getText();
        Allure.step("Проверка отображение сообщения \"Пользователь создан\" ", () -> {
                    Assert.assertEquals(actualFlashNoticeText, "Пользователь " + randomLogin + " создан.");
                }

        );
        System.out.println();


        String query = "SELECT * FROM users WHERE login =?";
        Allure.step("Поиск созданного пользователя через запрос в БД");
        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query, randomLogin);
        String loginFromDb = result.get(0).get("login").toString();
        String firstnameFromDb = result.get(0).get("firstname").toString();
        String lastNameFromDb = result.get(0).get("lastname").toString();
        //  String emailFromDb = result.get(0).get("email").toString();
        Allure.step("Проверка совпадения логина пользователя с логином найденным по запросу в БД");
        Assert.assertEquals(randomLogin, loginFromDb);
        Allure.step("Проверка совпадения имени пользователя с именем найденным по запросу в БД");
        Assert.assertEquals(randomFirstName, firstnameFromDb);
        Allure.step("Проверка совпадения фамилии пользователя с фамилией найденной по запросу в БД");
        Assert.assertEquals(randomLastName, lastNameFromDb);
        //  Assert.assertEquals(randomLogin,loginFromDb);


    }
}

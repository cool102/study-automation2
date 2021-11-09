package at.tests.ui;

import at.study.redmine.allure.asserts.AllureAssert;
import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ActiveUserLoginTest extends BaseUITest {
    User user;

    @BeforeMethod
    public void prepareFixture() {

        user = new User() {{

        }}.create();

        openBrowser();
        Allure.step("Кликнул по кнопке \"Войти\"", () -> {
            headerPage.loginButton.click();
        });
        loginPage.login(user);

    }

    @Owner("Саляхов Алмаз Фанилович")
    @Test(description = "Проверка авторизации пользователем")

    public void activeUserLoginTests() {
        AllureAssert.assertEquals(headerPage.homePage.getText(), "Домашняя страница", "Текстовка элемента совпадает");
        AllureAssert.assertEquals(headerPage.loggedAs.getText(), "Вошли как " + user.getLogin(), "Текстовка элемента совпадает");
        AllureAssert.assertEquals(headerPage.myAccount.getText(), "Моя учётная запись", "Текстовка элемента совпадает");
        AllureAssert.assertEquals(headerPage.homePage.getText(), "Домашняя страница", "Текстовка элемента совпадает");
        Allure.step("Проверка отображения элемента", () -> {
            assertTrue(headerPage.homePage.isDisplayed());
        });
        Allure.step("Проверка отображения элемента", () -> {
            assertTrue(headerPage.myPage.isDisplayed());
        });
        Allure.step("Проверка отображения элемента", () -> {
            assertTrue(headerPage.projects.isDisplayed());
        });
        Allure.step("Проверка отображения элемента", () -> {
            assertTrue(headerPage.help.isDisplayed());
        });
        Allure.step("Проверка отображения элемента", () -> {
            assertTrue(headerPage.myAccount.isDisplayed());
        });
        Allure.step("Проверка отображения элемента", () -> {
            assertTrue(headerPage.logOut.isDisplayed());
        });


        Allure.step("Проверка, что элемент Администрирование не отображется", () -> {
            assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.administration), "Не отображается элемент Администрирование");
        });
        Allure.step("Проверка, что элемент Войти не отображется", () -> {
            assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton),"Не отображется элемент Войти");
        });
        Allure.step("Проверка, что элемент Зарегистрироваться не отображется", () -> {
                    assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.register),"Не отображается элемент Зарегистрироваться");
                }
        );
        Allure.step("Проверка отображения элемента Поиск", () -> {
            assertTrue(headerPage.findToolBar.isDisplayed(),"Отображается элемент Поиск");
        });
    }
}

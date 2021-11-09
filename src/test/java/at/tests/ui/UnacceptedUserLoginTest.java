package at.tests.ui;

import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
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
        Allure.step("Кликнул кнопку \"Войти\"", () -> {
            headerPage.loginButton.click();
        });
        loginPage.login(user);

    }

    @Test(description = "Авторизация неподтвержденным пользователем")
    @Owner("Саляхов Алмаз Фанилович")
    public void unacceptedUserLogin() {
        Allure.step("Проверка, что отображается элемент \"Домашняя страница\"", () -> {
            Assert.assertTrue(headerPage.homePage.isDisplayed());
        });
        Allure.step("Проверка равенства текстовок баннера об ошибке", () -> {
            Assert.assertEquals(loginPage.flashError.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
        });
        Allure.step("Проверка, что элемент \"Моя страница\" не отображается", () -> {
                    Assert.assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.myPage));
                }
        );
        Allure.step("Проверка, что элемент \"Войти\" отображается", () -> {
            Assert.assertTrue(headerPage.loginButton.isDisplayed());
        });
        Allure.step("Проверка, что элемент \"Регистрация\" отображается", () -> {
            Assert.assertTrue(headerPage.register.isDisplayed());
        });


    }


}

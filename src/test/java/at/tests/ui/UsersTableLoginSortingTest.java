package at.tests.ui;

import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import at.study.redmine.utils.CompareUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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

    @Test(description = "Администрирование. Сортировка списка пользователей по пользователю")
    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.NORMAL)

    public void UsersTableLoginSortTest() {

        openBrowser();
        Allure.step("Нажали кнопку \"Войти\"", () -> {
                    headerPage.loginButton.click();
                }
        );
        loginPage.login(admin);

        Allure.step("Проверка отображения домашней страницы", () -> {
                    Assert.assertTrue(headerPage.homePage.isDisplayed());
                }

        );
        Allure.step("Произведен клик по кнопке \"Администрирование\"", () -> {
                    headerPage.administration.click();
                }

        );
        Allure.step("Проверка отображения страницы Администрирование", () -> {
                    Assert.assertTrue(administrationPage.content.isDisplayed());
                }

        );
        Allure.step("Клик по меню \"Пользователи\"", () -> {
                    administrationPage.users.click();
                }

        );
        Allure.step("Проверка отображения кнопок \"Пользователь\" и \"Создано\"", () -> {
                    Assert.assertTrue(usersTablePage.button("Пользователь").isDisplayed());
                    Assert.assertTrue(usersTablePage.button("Создано").isDisplayed());
                }

        );

        List<String> loginsBeforeClick = BrowserUtils.getElementsText(usersTablePage.login);
        Allure.step("Проверка, что изначально пользователи отсортированы по возрастанию", () -> {
                    CompareUtils.assertEqualsListSortedByTextAsc(loginsBeforeClick);
                }

        );
        Allure.step("Клик по кнопке \"Пользователь\"", () -> {
                    usersTablePage.button("Пользователь").click();
                }
        );


        List<String> loginsTextAfterClick = BrowserUtils.getElementsText(usersTablePage.login);
        Allure.step("Проверка, что пользователи отсортированы по убыванию", () -> {
                    CompareUtils.assertEqualsListSortedByTextDesc(loginsTextAfterClick);
                }

        );

    }
}

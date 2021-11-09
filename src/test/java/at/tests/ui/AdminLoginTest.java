package at.tests.ui;

import at.study.redmine.allure.asserts.AllureAssert;
import at.study.redmine.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AdminLoginTest extends BaseUITest {
    private User user;


    @BeforeMethod(description = "В системе заведен пользователь с правами администратора. Открыт браузер на главной странице.")
    public void prepareFixtures() {
        user = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
    }

    @Test(description = "Вход администратором. Проверка наличия элмента \"Моя учетная запись\"")
    @SneakyThrows
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Саляхов Алмаз Фанилович")
    public void adminLoginTest() {


        headerPage.loginButton.click();
        loginPage.login(user);
        AllureAssert.assertEquals(headerPage.myAccount.getText(),
                                "Моя учетная запись",
                                "Текст элемента" + "\" Моя учётная запись \"");

        AllureAssert.assertEquals(headerPage.homePage.getText(), "Домашняя страница", "Проверка отображения элемента" +
                "\" Домашняя страница \"");
        assertTrue(headerPage.homePage.isDisplayed());
        assertTrue(headerPage.myPage.isDisplayed());
        assertTrue(headerPage.projects.isDisplayed());
        assertTrue(headerPage.administration.isDisplayed());
        assertTrue(headerPage.help.isDisplayed());
        assertTrue(headerPage.myAccount.isDisplayed());
        assertTrue(headerPage.logOut.isDisplayed());

    }

}

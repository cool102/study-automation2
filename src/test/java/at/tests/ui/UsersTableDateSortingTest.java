package at.tests.ui;

import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import at.study.redmine.utils.CompareUtils;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class UsersTableDateSortingTest extends BaseUITest {


    @BeforeMethod (description = "Подготовительные действия")
    public void prepareFixture() {
        User admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
        Allure.step("Кликнул по кнопке \"Войти\"");
        headerPage.loginButton.click();
        loginPage.login(admin);
        Allure.step("Кликнул по кнопке \"Администрирование\"");
        headerPage.administration.click();
        Allure.step("Кликнул по кнопке \"Пользователи\"");
        administrationPage.users.click();

    }
    @Severity(SeverityLevel.NORMAL)
    @Owner("Саляхов Алмаз Фанилович")
    @Test (description = "Проверка сортировки по убыванию даты в таблице \"Пользователи\" ")
    public void testUsersTableSorting() {
        Allure.step("Кликнул по кнопке \"Создано\" в таблице \"Пользователи\"");
        usersTablePage.button("Создано").click();

        List<String> creationDates = BrowserUtils.getElementsText(usersTablePage.creationDates);
        CompareUtils.assertListSortedByDateDesc(creationDates);

    }
}

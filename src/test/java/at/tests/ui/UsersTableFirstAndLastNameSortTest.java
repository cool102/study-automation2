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

public class UsersTableFirstAndLastNameSortTest extends BaseUITest {
    User admin;

    @BeforeMethod
    public void prepareFixture() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        new User() {{
            setFisrtName("Amut");
            setLastName("Zeppa");

        }}.create();

        new User() {{
            setFisrtName("Master");
            setLastName("Afka");
        }}.create();

        new User() {{
            setFisrtName("Zatex");
            setLastName("Bossy");
        }}.create();
    }

    @Test(description = " Администрирование. Сортировка списка пользователей по имени и фамилии")
    @Owner("Саляхов Алмаз Фанилович")
    @Severity(SeverityLevel.TRIVIAL)

    public void usersTableFirstAndLastNameSortTest() {
        openBrowser();
        Allure.step("Клик по кнопке \"Войти\"");
        headerPage.loginButton.click();
        loginPage.login(admin);
        Allure.step("Проверка отображения домашней страницы");
        Assert.assertTrue(headerPage.homePage.isDisplayed());
        Allure.step("Клик по кнопке Администрирование");
        headerPage.administration.click();
        Allure.step("Проверка отображения страницы \"Администрирование\"");
        administrationPage.content.isDisplayed();
        Allure.step("Клик по меню \"Пользователи\"");
        administrationPage.users.click();
        Allure.step("Проверка отображения кнопки \"Создано\"");
        usersTablePage.button("Создано").isDisplayed();

        List<String> lastNameTextDefault = BrowserUtils.getElementsText(usersTablePage.lastName);
        Allure.step("Проверка, что пользователи неотсортированы по фамилии");
        CompareUtils.assertFalseListSortedByTextAsc(lastNameTextDefault);

        List<String> firstNameTextDefault = BrowserUtils.getElementsText(usersTablePage.firstName);
        Allure.step("Проверка, что пользователи неотсортированы по имени");
        CompareUtils.assertFalseListSortedByTextAsc(firstNameTextDefault);

        Allure.step("Кликнуть по кнопке \"Фамилия\"");
        usersTablePage.button("Фамилия").click();
        List<String> lastNameTextAfter1Click = BrowserUtils.getElementsText(usersTablePage.lastName);
        Allure.step("Проверка, что пользователи отсортированы по фамилии и по возрастанию");
        CompareUtils.assertEqualsListSortedByTextAsc(lastNameTextAfter1Click);
        Allure.step("Проверка, что пользователи неотсортированы по имени");
        CompareUtils.assertFalseListSortedByTextAsc(firstNameTextDefault);

        Allure.step("Кликнуть по кнопке \"Фамилия\"");
        usersTablePage.button("Фамилия").click();
        List<String> lastNameTextAfter2Click = BrowserUtils.getElementsText(usersTablePage.lastName);
        Allure.step("Проверка, что пользователи отсортированы по фамилии по убванию");
        CompareUtils.assertEqualsListSortedByTextDesc(lastNameTextAfter2Click);

        List<String> firstNameTextAfter2Click = BrowserUtils.getElementsText(usersTablePage.firstName);
        Allure.step("Проверка, что пользователи неотсортированы по имени");
        CompareUtils.assertFalseListSortedByTextAsc(firstNameTextAfter2Click);

        Allure.step("Кликнуть по кнопке \"Имя\"");
        usersTablePage.button("Имя").click();
        List<String> lastNameText4 = BrowserUtils.getElementsText(usersTablePage.lastName);
        Allure.step("Проверка, что пользователи неотсортированы по фамилии");
        CompareUtils.assertFalseListSortedByTextAsc(lastNameText4);
        List<String> firstNameText4 = BrowserUtils.getElementsText(usersTablePage.firstName);
        Allure.step("Проверка, что пользователи отсортированы по имени по возрастанию");
        CompareUtils.assertEqualsListSortedByTextAsc(firstNameText4);

        Allure.step("Кликнуть по кнопке \"Имя\"");
        usersTablePage.button("Имя").click();
        List<String> lastNameText5 = BrowserUtils.getElementsText(usersTablePage.lastName);
        Allure.step("Проверка, что пользователи неотсортированы по фамилии");
        CompareUtils.assertFalseListSortedByTextAsc(lastNameText5);

        List<String> firstNameText5 = BrowserUtils.getElementsText(usersTablePage.firstName);
        Allure.step("Проверка, что пользователи отсортированы по имени по убыванию");
        CompareUtils.assertEqualsListSortedByTextDesc(firstNameText5);


    }
}

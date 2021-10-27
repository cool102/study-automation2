package at.tests.ui;

import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import at.study.redmine.utils.CompareUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class UsersTableFirstAndLastNameSortTest extends BaseUITest{
    User admin;

    @BeforeMethod
    public void prepareFixture(){
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

    @Test
    public void usersTableFirstAndLastNameSortTest(){
        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);
        Assert.assertTrue(headerPage.homePage.isDisplayed());

        headerPage.administration.click();
        administrationPage.content.isDisplayed();

        administrationPage.users.click();
        usersTablePage.button("Создано").isDisplayed();

        List<String> lastNameTextDefault = BrowserUtils.getElementsText(usersTablePage.lastName);
        CompareUtils.assertFalseListSortedByTextAsc(lastNameTextDefault);

        List<String> firstNameTextDefault = BrowserUtils.getElementsText(usersTablePage.firstName);
        CompareUtils.assertFalseListSortedByTextAsc(firstNameTextDefault);


        usersTablePage.button("Фамилия").click();
        List<String> lastNameTextAfter1Click = BrowserUtils.getElementsText(usersTablePage.lastName);
        CompareUtils.assertEqualsListSortedByTextAsc(lastNameTextAfter1Click);


        CompareUtils.assertFalseListSortedByTextAsc(firstNameTextDefault);

        usersTablePage.button("Фамилия").click();
        List<String> lastNameTextAfter2Click = BrowserUtils.getElementsText(usersTablePage.lastName);
        CompareUtils.assertEqualsListSortedByTextDesc(lastNameTextAfter2Click);

        List<String> firstNameTextAfter2Click = BrowserUtils.getElementsText(usersTablePage.firstName);
        CompareUtils.assertFalseListSortedByTextAsc(firstNameTextAfter2Click);

        usersTablePage.button("Имя").click();
        List<String> lastNameText4 = BrowserUtils.getElementsText(usersTablePage.lastName);
        CompareUtils.assertFalseListSortedByTextAsc(lastNameText4);
        List<String> firstNameText4 = BrowserUtils.getElementsText(usersTablePage.firstName);
        CompareUtils.assertEqualsListSortedByTextAsc(firstNameText4);


        usersTablePage.button("Имя").click();
        List<String> lastNameText5 = BrowserUtils.getElementsText(usersTablePage.lastName);
        CompareUtils.assertFalseListSortedByTextAsc(lastNameText5);

        List<String> firstNameText5 = BrowserUtils.getElementsText(usersTablePage.firstName);
        CompareUtils.assertEqualsListSortedByTextDesc(firstNameText5);




    }
}

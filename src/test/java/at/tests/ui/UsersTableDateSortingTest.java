package at.tests.ui;

import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import at.study.redmine.utils.CompareUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class UsersTableDateSortingTest extends BaseUITest {


    @BeforeMethod
    public void prepareFixture() {
        User admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);
        headerPage.administration.click();
        administrationPage.users.click();

    }

    @Test
    public void testUsersTableSorting() {
        usersTablePage.button("Создано").click();
        List<String> creationDates = BrowserUtils.getElementsText(usersTablePage.creationDates);
        CompareUtils.assertListSortedByDateDesc(creationDates);

    }
}

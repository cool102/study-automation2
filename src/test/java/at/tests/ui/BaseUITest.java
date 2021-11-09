package at.tests.ui;

import at.study.redmine.ui.Browser;
import at.study.redmine.ui.BrowserManager;
import at.study.redmine.ui.pages.*;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;

public class BaseUITest {
    protected Browser browser;
    protected HeaderPage headerPage;
    protected LoginPage loginPage;
    protected ProjectPage projectPage;

    protected AdministrationPage administrationPage;
    protected UsersTablePage usersTablePage;

    protected NewUserCreationPage newUserCreationPage;

    @Step("Открыт браузер на главной странице")
    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        headerPage = Page.getPage(HeaderPage.class);
        loginPage = Page.getPage(LoginPage.class);
        projectPage = Page.getPage(ProjectPage.class);
        administrationPage = Page.getPage(AdministrationPage.class);
        usersTablePage = Page.getPage(UsersTablePage.class);
        newUserCreationPage= Page.getPage(NewUserCreationPage.class);
    }

    @AfterMethod (description = "Остановка браузера")
    public void tearDown() {
        browser.takeScreenshot();
        BrowserManager.removeBrowser();
    }
}

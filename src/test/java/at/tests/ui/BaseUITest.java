package at.tests.ui;

import at.study.redmine.ui.Browser;
import at.study.redmine.ui.BrowserManager;
import at.study.redmine.ui.pages.HeaderPage;
import at.study.redmine.ui.pages.LoginPage;
import at.study.redmine.ui.pages.ProjectPage;
import org.testng.annotations.AfterMethod;

public class BaseUITest {
    protected Browser browser;
    protected HeaderPage headerPage;
    protected LoginPage loginPage;

    protected ProjectPage projectPage;

    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        headerPage = new HeaderPage();
        loginPage = new LoginPage();
        projectPage = new ProjectPage();
    }

    @AfterMethod
    public void tearDown() {

        BrowserManager.removeBrowser();
    }
}

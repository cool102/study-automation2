package at.tests.ui;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static at.study.redmine.ui.BrowserUtils.isElementCurrentlyDisplayed;


public class NoAdministrationWhenNotAuthorizedTest extends BaseUITest {
    @BeforeMethod
    public void prepareFixture() {
        openBrowser();

    }

    @Test
    public void testNoAdmonistrationWhenNotAuthorized() {

        Assert.assertFalse(isElementCurrentlyDisplayed(headerPage.administration));
    }
}

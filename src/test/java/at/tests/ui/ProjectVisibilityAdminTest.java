package at.tests.ui;

import at.study.redmine.model.project.Project;
import at.study.redmine.model.user.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProjectVisibilityAdminTest extends BaseUITest {
    User user;
    WebElement projectElement;

    Project project;
    String projectIdentifyer;

    @BeforeMethod
    public void prepareFixture() {
        user = new User() {{
            setIsAdmin(true);
        }}.create();

        project = new Project() {{
            setIsPublic(false);
        }}.create();
        projectIdentifyer = project.getIdentifier();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);

        Assert.assertTrue(headerPage.homePage.isDisplayed());

        headerPage.projects.click();

        projectPage.addFilter.click();
        projectPage.addFilter.findElement(By.xpath("//*[@value='is_public']")).click();

        projectPage.isPublicFilter.click();  ////tr[@id='tr_is_public']//select[@id='values_is_public_1']
        projectPage.isPublicFilter.findElement(By.xpath("//option[@value='0']")).click();


        projectPage.filterSubmitButton.click();


    }

    @Test
    public void projectVisibilityAdmin() {

        projectElement = projectPage.projectsContent.findElement(By.xpath("//div[@id='content']//a[@href='/projects/" + projectIdentifyer + "']"));
        Assert.assertTrue(projectElement.isDisplayed());
    }
}

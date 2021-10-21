package at.tests.ui;

import at.study.redmine.model.project.Project;
import at.study.redmine.model.role.Permissions;
import at.study.redmine.model.role.Role;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ProjectVisibilityUser extends BaseUITest {
    User user;
    Role issuesWatcherRole;

    Project publicProject1;
    Project privateProject2;
    Project privateProject3;


    WebElement publicProject1Element;
    WebElement publicProject2Element;
    WebElement publicProject3Element;

    @BeforeMethod
    public void prepareFixture() {
        user = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        issuesWatcherRole = new Role() {{
            setPermissions(Permissions.VIEW_ISSUES);
        }}.create();

        publicProject1 = new Project() {{
        }}.create();

        privateProject2 = new Project() {{
            setIsPublic(false);
        }}.create();

        privateProject3 = new Project() {{
            setIsPublic(false);
        }}.create();

        List<Role> roles = new ArrayList<>();
        roles.add(issuesWatcherRole);

        privateProject3.addUserWithListRoletiProject(user, privateProject3, roles);

    }

    @Test(expectedExceptions = org.openqa.selenium.NoSuchElementException.class)
    public void projectVisibilityUser() {

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);
        headerPage.projects.click();

        //проверки

        Assert.assertTrue(headerPage.homePage.isDisplayed());
        Assert.assertTrue(projectPage.projectsContent.isDisplayed());

        String publicProject1Name = publicProject1.getName();
        publicProject1Element = projectPage.projectsContent.findElement(By.xpath(
                "//div[@id='content']//*[text()='" + publicProject1Name + "']"));
        Assert.assertTrue(publicProject1Element.isDisplayed());


        String privateProject2Name = privateProject2.getName();
        publicProject2Element = projectPage.projectsContent.findElement(By.xpath(
                "//div[@id='content']//*[text()='" + privateProject2Name + "']"
        ));
        Assert.assertFalse(publicProject2Element.isDisplayed());

        String privateProject3Name = privateProject3.getName();
        publicProject3Element = projectPage.projectsContent.findElement(By.xpath(
                "//div[@id='content']//*[text()='" + privateProject3Name + "']"
        ));
        Assert.assertTrue(publicProject3Element.isDisplayed());
    }
}

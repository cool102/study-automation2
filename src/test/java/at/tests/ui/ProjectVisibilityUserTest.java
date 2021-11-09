package at.tests.ui;

import at.study.redmine.model.project.Project;
import at.study.redmine.model.role.Permissions;
import at.study.redmine.model.role.Role;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ProjectVisibilityUserTest extends BaseUITest {
    User user;
    Role issuesWatcherRole;

    List<Role> roles;

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
        Allure.step("Создан проект 1 (публичный)");
        publicProject1 = new Project() {{
        }}.create();

        Allure.step("Создан проект 2 (приватный)");
        privateProject2 = new Project() {{
            setIsPublic(false);
        }}.create();

        Allure.step("Создан проект 3 (приватный)");
        privateProject3 = new Project() {{
            setIsPublic(false);
        }}.create();

        Allure.step("Создан список ролей. Роль добавлена в список ролей", () -> {
                    roles = new ArrayList<>();
                    roles.add(issuesWatcherRole);
                }

        );
        Allure.step("В проект 3 добавлена роль и пользователь", () -> {
                    privateProject3.addUserWithListRoletiProject(user, privateProject3, roles);
                }

        );
    }

    @Owner("Саляхов Алмаз Фанилович")
    @Test(description = "Видимость проектов для Пользователя")
    public void projectVisibilityUser() {

        openBrowser();
        Allure.step("Кликнуть кнопку \"Войти\"", () -> {
                    headerPage.loginButton.click();
                }

        );
        loginPage.login(user);
        Allure.step("Кликнуть кнопку \"Проекты\"", () -> {
                    headerPage.projects.click();
                }
        );
        Allure.step("Проверка отображения элемента Домашняя страница", () -> {
            Assert.assertTrue(headerPage.homePage.isDisplayed());
        });

        Allure.step("Проверка отображения списка Проектов", () -> {
            Assert.assertTrue(projectPage.projectsContent.isDisplayed());
        });

        Allure.step("Проверка отображения проекта 1 (публичный)", () -> {
                    String publicProject1Name = publicProject1.getName();
                    publicProject1Element = projectPage.projectsContent.findElement(By.xpath(
                            "//div[@id='content']//*[text()='" + publicProject1Name + "']"));
                    Assert.assertTrue(publicProject1Element.isDisplayed());
                }

        );

        Allure.step("Проверка, что не отображается проект 2 (приватный)", () -> {
                    String privateProject2Name = privateProject2.getName();

                    Assert.assertFalse((BrowserUtils.isElementPresent(privateProject2Name)));
                }
        );


        Allure.step("Проверка отображения приватного проекта 3", () -> {
                    String privateProject3Name = privateProject3.getName();
                    publicProject3Element = projectPage.projectsContent.findElement(By.xpath(
                            "//div[@id='content']//*[text()='" + privateProject3Name + "']"
                    ));
                    Assert.assertTrue(publicProject3Element.isDisplayed());
                }

        );
    }
}

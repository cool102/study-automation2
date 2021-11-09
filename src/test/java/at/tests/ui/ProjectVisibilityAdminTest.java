package at.tests.ui;

import at.study.redmine.model.project.Project;
import at.study.redmine.model.user.User;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
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
        Allure.step("Создал пользователя с правами Администратор");
        user = new User() {{
            setIsAdmin(true);
        }}.create();

        project = new Project() {{
            setIsPublic(false);
        }}.create();
        projectIdentifyer = project.getIdentifier();

        openBrowser();
        Allure.step("Нажата кнопка \"Войти\"", () -> {
            headerPage.loginButton.click();
        });

        loginPage.login(user);
        Allure.step("Проверка отображения элемента \"Домашняя страница\"");
        Assert.assertTrue(headerPage.homePage.isDisplayed());

        Allure.step("Кликнул по элементу \"Проекты\"", () -> {
            headerPage.projects.click();
        });

        Allure.step("Кликнул по элементу \"Добавить фильтр\"", () -> {
            projectPage.addFilter.click();
        });
        Allure.step("Выбрал фильтр \"Общедоступный\" в выпадающем списке", () -> {
            projectPage.addFilter.findElement(
                    By.xpath("//*[@value='is_public']")).click();
        });

        Allure.step("Кликнул по фильтру \"Общедоступный\"", () -> {
            projectPage.isPublicFilter.click();
        });


        Allure.step("Выбрал значение \"нет\" в выпадающем списке", () -> {
            projectPage.isPublicFilter.findElement(By.xpath("//option[@value='0']")).click();
        });


        Allure.step("Нажал кнопку \"Применить\"", () -> {
            projectPage.filterSubmitButton.click();
        });


    }

    @Test(description = "Проверка видимости проекта для Администратора")
    @Step("Проверка, что приватный проект отображается для Администратора")
    @Owner("Саляхов Алмаз Фанилович")
    public void projectVisibilityAdmin() {

        projectElement = projectPage.projectsContent.findElement(By.xpath("//div[@id='content']//a[@href='/projects/"
                + projectIdentifyer + "']"));
        Assert.assertTrue(projectElement.isDisplayed());
    }
}

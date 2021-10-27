package at.study.redmine.ui.pages;

import at.study.redmine.ui.BrowserManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UsersTablePage extends Page {
    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='created_on']")
    public List<WebElement> creationDates;

    @FindBy (xpath = "//table[@class='list users']/tbody//td[@class='username']/a")
    public List<WebElement> login;

    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='lastname']")
    public  List<WebElement> lastName;

    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='firstname']")
    public  List<WebElement> firstName;

    public WebElement button(String text) {
        return BrowserManager.getBrowser().getDriver().findElement(By.xpath(
                "//table[@class='list users']/thead//th[.='" + text + "']"));
    }



}

package at.study.redmine.ui.pages;

import at.study.redmine.model.user.User;
import at.study.redmine.ui.BrowserManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginPage extends Page {

    @FindBy(xpath = "//input[@id='username']")
    public WebElement userNameInput;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[@id='login-submit']")
    public WebElement loginButton;

    @FindBy(xpath = "//div[@id='flash_error']")
    public WebElement flashError;


    public void login(String login, String password) {
        userNameInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public void login(User user) {

        login(user.getLogin(), user.getPassword());
    }

}

package at.study.redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewUserCreationPage extends Page{
    @FindBy(xpath = "//form[@id='new_user']//div[@id='user_form']//input[@id='user_login']")
    public WebElement login;
    @FindBy(xpath = "//form[@id='new_user']//div[@id='user_form']//input[@id='user_firstname']")
    public WebElement firstName;
    @FindBy(xpath = "//form[@id='new_user']//div[@id='user_form']//input[@id='user_lastname']")
    public WebElement lastName;
    @FindBy(xpath = "//form[@id='new_user']//div[@id='user_form']//input[@id='user_mail']")
    public WebElement email;
    @FindBy(xpath = "//form[@id='new_user']//div[@id='user_form']//input[@id='user_generate_password']")
    public  WebElement passwordCreation;
    @FindBy(xpath = "//form[@id='new_user']//div[@id='user_form']/following-sibling::*/input[@name='commit' and @value='Создать']")
    public  WebElement submitUserCreationButton;
    @FindBy(xpath = "//div[@id='main']//div[@id='flash_notice']")
    public WebElement flashNotice;

}

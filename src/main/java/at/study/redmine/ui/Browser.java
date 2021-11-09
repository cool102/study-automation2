package at.study.redmine.ui;

import at.study.redmine.property.Property;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@Getter
public class Browser {
    private final WebDriver driver;
    private final WebDriverWait wait;

    Browser() {
        this("");
    }

    Browser(String uri) {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        int timeout = Property.getIntegerProperty("element.timeout");
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, timeout);
        get(uri);

    }

    public void get() {
        get("");
    }

    public void get(String uri) {
        getDriver().get(Property.getStringProperty("url") + uri);
    }
    @Step("Сделан скриншот")
    @Attachment("Скриншот браузера")
    public  byte[] takeScreenshot(){
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    @Step("Выполнен JavaScript")
    public  void executeJavaScript(String js, Object ... args){
        ((JavascriptExecutor) driver).executeScript(js,args);
    }


    public Actions actions(){
        return new Actions(driver);
    }
}

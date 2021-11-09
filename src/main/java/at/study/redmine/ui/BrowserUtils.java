package at.study.redmine.ui;

import at.study.redmine.property.Property;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openqa.selenium.NoSuchElementException;

public class BrowserUtils {
    public static List<String> getElementsText(List<WebElement> elements) {
        return elements.stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());


    }

    public static boolean isElementCurrentlyDisplayed(WebElement element) {

        try {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }

    public static boolean isElementPresent(String webElementName) {
        try {
            WebElement element = BrowserManager.getBrowser().getDriver().findElement(By.xpath(
                    "//div[@id='content']//*[text()='" + webElementName + "']"));
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }


}

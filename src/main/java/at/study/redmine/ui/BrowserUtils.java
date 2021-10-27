package at.study.redmine.ui;

import at.study.redmine.property.Property;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }


}

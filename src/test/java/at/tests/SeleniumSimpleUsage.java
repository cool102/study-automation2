package at.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SeleniumSimpleUsage {
    @Test
    public void openGooglePage() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://yandex.ru/");
        WebElement element = driver.findElement(By.xpath("//input[@id='text']"));
      element.sendKeys("Привет , мир");
     element.submit();

        Thread.sleep(3000);
        driver.quit();

    }
}

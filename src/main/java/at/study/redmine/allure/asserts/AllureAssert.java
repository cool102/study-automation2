package at.study.redmine.allure.asserts;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;


public class AllureAssert {
    @Step("Проверка равенства:  {2}")
    public static void  assertEquals(Object actual, Object expected, String message){
       Assert.assertEquals(actual,expected,message);
    }

    @Step("Проверка равенства ")
    public static void  assertEquals(Object actual, Object expected){
        Assert.assertEquals(actual,expected);
    }
    @Step("Проверка, что не равен null")
    public static void  assertNotNull(Object actual,String message){
        Assert.assertNotNull(actual,message);
    }

    @Step("Проверка получения true")
    public static void  assertTrue(Object actual,String message){
        Assert.assertNotNull(actual,message);
    }

    @Step("Проверка получения false")
    public static void  assertFalse(Object actual,String message){
        Assert.assertNotNull(actual,message);
    }
}
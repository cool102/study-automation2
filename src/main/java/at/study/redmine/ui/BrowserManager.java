package at.study.redmine.ui;

public class BrowserManager {

    //TODO: Использовать ThreadLocal
    private static Browser browser;

    public static Browser getBrowser() {
        if (browser == null) {
            browser = new Browser();
        }
        return browser;
    }

    public static Browser getBrowser(String uri) {
        if (browser == null) {
            browser = new Browser(uri);
        }
        return browser;
    }

    public static void removeBrowser(){
        browser.getDriver().quit();
        browser=null;
    }


}

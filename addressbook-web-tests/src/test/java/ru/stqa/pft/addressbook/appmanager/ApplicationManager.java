package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import sun.plugin2.util.BrowserType;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    WebDriver wd;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private ContactHelper contactHelper;
    private GroupHelper groupHelper;
    private int browser;

    public ApplicationManager(int browser) {

        this.browser = browser;
    }

    public void init() {
        if (browser == BrowserType.MOZILLA) {
            wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        } else if (browser == BrowserType.INTERNET_EXPLORER) {
            wd = new InternetExplorerDriver();
        } else if (browser == BrowserType.DEFAULT) {
            wd = new ChromeDriver();
        }

        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wd.get("http://localhost/addressbook/");
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper.login("admin", "secret");
    }

    public void stop() {
        wd.quit();
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }

    public ContactHelper getContactHelper() {
        return contactHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }
}

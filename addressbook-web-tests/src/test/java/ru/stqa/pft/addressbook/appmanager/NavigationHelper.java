package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void gotoGroupPage() {
       click(By.linkText("GROUPS"));
    }

    public void goToAddContact() {
        clickContact(By.linkText("ADD_NEW"));
    }
}

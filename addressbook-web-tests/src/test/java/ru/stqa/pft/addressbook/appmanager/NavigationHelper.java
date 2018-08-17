package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoGroupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("GROUPS")
                && isElementPresent(By.name("new"))) {
            return;
        }
       click(By.linkText("GROUPS"));
    }

    public void goToAddContact() {
        if (isElementPresent(By.tagName("h1")) && wd.findElement(By.tagName("h1")).getText().equals("EDIT_ADD_ENTRY")) {
            return;
        }
        clickContact(By.linkText("ADD_NEW"));
    }
}

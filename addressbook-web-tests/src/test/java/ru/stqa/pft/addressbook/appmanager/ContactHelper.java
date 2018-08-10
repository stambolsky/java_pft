package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        clickContact(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData) {
        typeContact(By.name("firstname"), contactData.getFirstname());
        typeContact(By.name("lastname"), contactData.getLastname());
        typeContact(By.name("address"), contactData.getAddress());
        typeContact(By.name("home"), contactData.getPhone());
        typeContact(By.name("email"), contactData.getEmail());
    }

    public void goToHome() {
        clickContact(By.linkText("HOME"));
    }

    public void alertAcceptContact() {
        Alert alert  = wd.switchTo().alert();
        alert.accept();
    }

    public void deleteSelectedContact() {
        clickContact(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void selectContact() {
        clickContact(By.name("selected[]"));
    }
}

package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
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
        //Alert alert  = wd.switchTo().alert();
        //alert.accept();
        wd.switchTo().alert().accept();
    }

    public void deleteSelectedContact() {
        clickContact(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void editContact(int index) {
        wd.findElements(By.xpath("//img[@title='EDIT']")).get(index).click();
        //clickContact(By.xpath("//img[@title='EDIT']"));
    }

    public void submitContactModification() {
        clickContact(By.name("update"));
    }

    public void goToAddContact() {
        if (isElementPresent(By.tagName("h1")) && wd.findElement(By.tagName("h1")).getText().equals("EDIT_ADD_ENTRY")) {
            return;
        }
        clickContact(By.linkText("ADD_NEW"));
    }

    public boolean isThereAContact() {
        try {
            wd.findElement(By.name("selected[]")).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
        //return isElementPresent(By.name("selected[]"));
    }


    public void create(ContactData contact) {
        goToAddContact();
        fillContactForm(contact);
        submitContactCreation();
        goToHome();
    }

    public void modify(ContactData contact, int index) {
        selectContact(index);
        editContact(index);
        fillContactForm(contact);
        submitContactModification();
        goToHome();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectedContact();
        alertAcceptContact();
        goToHome();
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements((By.name("entry")));
        for (WebElement element : elements) {
            String lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstname, lastname,null,null,null,null);
            contacts.add(contact);
        }
        return contacts;
    }
}

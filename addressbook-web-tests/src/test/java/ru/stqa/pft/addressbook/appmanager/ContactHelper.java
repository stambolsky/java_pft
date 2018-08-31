package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value ='" + id + "']")).click();
    }

    public void editContactById(int id) {
        wd.findElement(By.xpath("//input[@value='" + id + "']/../..//img[@title='EDIT']")).click();
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
        contactCashe = null;
        goToHome();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        editContactById(contact.getId());
        fillContactForm(contact);
        submitContactModification();
        contactCashe = null;
        goToHome();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        alertAcceptContact();
        contactCashe = null;
        goToHome();
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCashe = null;

    public Contacts all() {
        if (contactCashe != null) {
            return new Contacts(contactCashe);
        }

        contactCashe = new Contacts();
        List<WebElement> rows = wd.findElements((By.name("entry")));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(0).getText();
            String firstname = cells.get(0).getText();
            String allPhones = cells.get(5).getText();
            contactCashe.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).withAddress(null).withAllPhone(allPhones).withEmail(null).withGroup(null));
        }
        return new Contacts(contactCashe);
    }

    public ContactData InfoFromEditForm(ContactData contact) {
        InitContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }

    public void InitContactModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value ='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath(".//..//.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }
}

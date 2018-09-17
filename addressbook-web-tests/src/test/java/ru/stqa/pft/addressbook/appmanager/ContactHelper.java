package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

import static ru.stqa.pft.addressbook.tests.TestBase.app;

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
        //attach(By.name("photo"), contactData.getPhoto());
        typeContact(By.name("address"), contactData.getAddress());
        typeContact(By.name("home"), contactData.getHomePhone());
        typeContact(By.name("mobile"), contactData.getMobilePhone());
        typeContact(By.name("work"), contactData.getWorkPhone());
        typeContact(By.name("email"), contactData.getEmail());
        typeContact(By.name("email2"), contactData.getEmail2());
        typeContact(By.name("email3"), contactData.getEmail3());

        if (contactData.getGroups().size() > 0) {
            Assert.assertTrue(contactData.getGroups().size() == 1);
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText((contactData.getGroups().iterator().next().getName()));
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void addContact() {
        click(By.linkText("add new"));
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

    public void detailsContactById(int id) {
        wd.findElement(By.xpath("//input[@value='" + id + "']/../..//img[@title='DETAILS']")).click();
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

    public void detail(ContactData contact) {
        selectContactById(contact.getId());
        detailsContactById(contact.getId());
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
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contactCashe.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).withAddress(address).withAllEmails(allEmails)
                    .withAllPhone(allPhones));
        }
        return new Contacts(contactCashe);
    }

    public ContactData InfoFromEditForm(ContactData contact) {
        InitContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withAddress(address)
                .withEmail(email).withEmail2(email2).withEmail3(email3).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }

    public void InitContactModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value ='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath(".//..//.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

    public boolean isInGroups(Groups groups, GroupData groupData) {
        for (GroupData group : groups) {
            if (group.equals(groupData)) {
                return true;
            }
        }
        return false;
    }

    public void addGroup(ContactData contacts, int id) {
        selectContactById(contacts.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(id));
        click(By.name("add"));
    }

    public ContactData getContact(Contacts contacts, int id) {
        for (ContactData contactData : contacts) {
            if (contactData.getId() == id) {
                return contactData;
            }
        }
        return new ContactData();
    }

    public void isFromGroup(ContactData contacts, int id) {
        new Select(wd.findElement(By.name("group"))).selectByValue(String.valueOf(id));
        //boolean element = Assert.NotNull(wd.FindElement(By.name("entry")).Displayed);
        if (IsElementExists(By.name("entry")) == false) {
            Groups groups = app.db().groups();
            goToHome();
            create(new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                    .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                    .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354").inGroup(groups.iterator().next()));
        } else {
            selectContactById(contacts.getId());
            click(By.name("remove"));
        }
    }

    public boolean IsElementExists(By iClassName) {
        try {
            wd.findElement(iClassName);
            return true;
        }
        catch (NoSuchElementException ex) {
            return false;
        }
    }
}



package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Sergey", "Tambolsky", "test123", "+37512345678", "test@test.test", "test1"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().editContact(before.size() - 1);
        app.getContactHelper().fillContactForm(new ContactData("EditSergey", "TambolskyEdit", "testEdit123", "+37512345678", "testEdit@test.test", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().goToHome();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

    }
}

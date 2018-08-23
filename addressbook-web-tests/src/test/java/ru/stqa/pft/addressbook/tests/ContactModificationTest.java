package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        int before = app.getContactHelper().getContactCount();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Sergey", "Tambolsky", "test123", "+37512345678", "test@test.test", "test1"));
        }
        app.getContactHelper().selectContact(before - 1);
        app.getContactHelper().editContact(before - 1);
        app.getContactHelper().fillContactForm(new ContactData("SergeyEdit", "TambolskyEdit", "testEdit123", "+37512345678", "testEdit@test.test", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().goToHome();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);
    }
}

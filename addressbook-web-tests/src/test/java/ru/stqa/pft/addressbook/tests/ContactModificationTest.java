package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("SergeyEdit", "TambolskyEdit", "testEdit123", "+37512345678", "testEdit@test.test"));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().goToHome();
    }
}

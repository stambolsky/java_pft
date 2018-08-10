package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToAddContact();
        app.getContactHelper().fillContactForm(new ContactData("Sergey", "Tambolsky", "test123", "+37512345678", "test@test.test"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().goToHome();
    }
}

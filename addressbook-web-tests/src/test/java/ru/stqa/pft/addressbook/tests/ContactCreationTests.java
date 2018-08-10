package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.GotoContactPage();
        app.fillContactForm(new ContactData("Sergey", "Tambolsky", "test123", "+37512345678", "test@test.test"));
        app.submitContactCreation();
    }

}

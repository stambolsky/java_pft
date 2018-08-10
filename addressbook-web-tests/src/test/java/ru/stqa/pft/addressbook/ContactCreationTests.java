package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        GotoContactPage();
        fillContactForm(new ContactData("Sergey", "Tambolsky", "test123", "+37512345678", "test@test.test"));
        submitContactCreation();
    }

}

package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.contact().goToHome();
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("Sergey").withLastname("Tambolsky").withAddress("test123").withPhone("+37512345678").withEmail("test@test.test");
        app.contact().goToAddContact();
        app.contact().create(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}

package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditionsContact() {
        app.contact().goToHome();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData("Sergey", "Tambolsky", "test123", "+37512345678", "test@test.test", "test1"));
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"EditSergey", "TambolskyEdit", "testEdit123", "+37512345678", "testEdit@test.test", null);
        int index = before.size() - 1;
        app.contact().modify(contact, index);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }

}

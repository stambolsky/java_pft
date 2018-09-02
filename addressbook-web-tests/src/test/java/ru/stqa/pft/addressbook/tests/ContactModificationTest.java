package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditionsContact() {
        app.contact().goToHome();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withGroup("test1"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("SergeyEdit").withLastname("TambolskyEdit").withAddress("EditTest123").withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test");
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

    }

}

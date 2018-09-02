package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.contact().goToHome();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("Sergey").withLastname("Tambolsky").withAddress("test123").withHomePhone("+37512345678").withMobilePhone("+68387487934").withWorkPhone("+74298653676").withEmail("test@test.test").withEmail2("test2@test.test").withEmail3("test3@test.test");
        app.contact().goToAddContact();
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
}

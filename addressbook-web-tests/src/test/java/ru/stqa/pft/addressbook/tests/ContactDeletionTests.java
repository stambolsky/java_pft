package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditionsContact() {
        Groups groups = app.db().groups();
        verifyContactsZero();
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.db().contacts();
        ContactData deleteContact = before.iterator().next();
        app.contact().delete(deleteContact);
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deleteContact)));
        verifyContactListInUi();
    }

}

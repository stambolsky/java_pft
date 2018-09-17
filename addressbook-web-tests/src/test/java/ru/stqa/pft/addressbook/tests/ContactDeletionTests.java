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
        if (app.db().contacts().size() == 0) {
            app.contact().goToHome();
            app.contact().create(new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                    .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                    .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354").inGroup(groups.iterator().next()));
        }
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

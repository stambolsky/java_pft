package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.tests.TestBase.app;

public class DeleteContactFromGroupTest extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        Groups groups = app.db().groups();
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test"));
        }
        if (app.db().contacts().size() == 0) {
            groups = app.db().groups();
            app.goTo().goToHome();
            app.contact().create(new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                    .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                    .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354").inGroup(groups.iterator().next()));
        }
    }

    @Test
    public void testDeleteContactFromGroup() {
        Groups groups = app.db().groups();
        ContactData modifiedContact = app.db().contacts().iterator().next();
        Groups before = modifiedContact.getGroups();
        ContactHelper contactHelper = app.contact();
        for (GroupData groupdata : groups) {
            if (!contactHelper.isInGroups(modifiedContact.getGroups(), groupdata)) {
                app.goTo().goToHome();
                contactHelper.isFromGroup(modifiedContact, groupdata.getId());
                before.remove(groupdata);
                Groups after = contactHelper.getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
                assertThat(after, equalTo(before));
                return;
            }
        }
        GroupData group = groups.iterator().next();
        app.goTo().goToHome();
        contactHelper.addGroup(modifiedContact, group.getId());
        app.goTo().goToHome();
        contactHelper.isFromGroup(modifiedContact, group.getId());
        before.remove(group);
        Groups after = app.contact().getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
        assertThat(after, equalTo(before));
    }
}

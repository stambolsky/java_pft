package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroupTest extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        Contacts before = app.db().contacts();
        GroupData newGroup = new GroupData().withName("newGroup").withHeader("newHeader").withFooter("newFooter");
        ContactData newContact = new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354");
        if (app.db().contacts().size() == 0) {
            app.goTo().goToHome();
            app.contact().create(newContact);
        }

        if (before.contactAddGroup().size() == 0 && before.contactNotGroups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test"));
        }

        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(newGroup);
        }

        Contacts after = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData toAddContact = after.contactNotGroups().iterator().next();
        GroupData toAddGroup = groups.iterator().next();
        app.goTo().goToHome();
        app.contact().addContactToGroup(toAddContact, toAddGroup);
        app.goTo().goToHome();
    }

    @Test
    public void testDeleteContactFromGroup() {
        Contacts before = app.db().contacts();
        GroupData removeGroup = before.contactAddGroup().iterator().next().getGroups().iterator().next();
        ContactData removeContact = before.contactAddGroup().iterator().next();
        app.contact().removeGroupToContact(removeContact, removeGroup);
        Contacts after = app.db().contacts();
        Contacts delContact = after.stream().filter((ContactData c) -> c.getId() == removeContact.getId())
                .collect(Collectors.toCollection(Contacts::new));
        assertThat((delContact.iterator().next().getGroups().size()), equalTo(0));



        /*Groups groups = app.db().groups();
        ContactData modifiedContact = app.db().contacts().iterator().next();
        Groups before = modifiedContact.getGroups();
        ContactHelper contactHelper = app.contact();

        for (GroupData groupdata : groups) {
            if (!contactHelper.isInGroups(before, groupdata)) {
                removeGroup(modifiedContact, before, contactHelper, groupdata);
                return;
            }
        }
        verifyRemoveGroup(groups, modifiedContact, before, contactHelper);
    }*/
    }
}

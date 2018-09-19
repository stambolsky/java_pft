package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class AddedContactGroup extends TestBase {

    @BeforeMethod
    public void ensurePreconditionsGroupsAndContact() {
        Contacts contacts = app.db().contacts();
        Contacts notGroup = contacts.contactNotGroups();
        GroupData newGroup = new GroupData().withName("group").withFooter("footer").withHeader("header");
        ContactData newContact = new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354");

        if (app.db().groups().size() == 0) {
            GroupData group = new GroupData().withName("group1").withHeader("Header1").withFooter("Footer1");
            verifyCreateGroups(group);
        }
        if (app.db().contacts().size() == 0 && notGroup.size() == 0) {
            Groups groups = app.db().groups();
            createContact(groups);
        }

    }


    @Test
    public void addedContactToGroups() {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        Contacts notGroup = before.contactNotGroups();
        ContactData contactAdd = notGroup.iterator().next();
        GroupData groupAdd = groups.iterator().next();
        app.goTo().goToHome();
        app.contact().addContactToGroup(contactAdd, groupAdd);
        Contacts after = app.db().contacts();
        Contacts addContact = after.stream().filter((ContactData c) -> c.getId() == contactAdd.getId())
                .collect(Collectors.toCollection(Contacts::new));
        assertThat(addContact.iterator().next().getGroups(), equalTo(new Groups().withAdded(groupAdd)));
        app.goTo().goToHome();
    }
}


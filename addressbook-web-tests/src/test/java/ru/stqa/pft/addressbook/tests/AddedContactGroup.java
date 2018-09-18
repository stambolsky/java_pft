package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class AddedContactGroup extends TestBase {

    @BeforeMethod
    public void ensurePreconditionsGroupsAndContact() {
        if (app.db().groups().size() == 0) {
            GroupData group = new GroupData().withName("group1").withHeader("Header1").withFooter("Footer1");
            verifyCreateGroups(group);
        }
        verifyContactsZero();
    }

    @Test
    public void addedContactToGroups() {
        Groups groups = app.db().groups();
        ContactData modifiedContact = app.db().contacts().iterator().next();
        Groups before = modifiedContact.getGroups();
        ContactHelper contactHelper = app.contact();
        addContactInGroup(groups, modifiedContact, before, contactHelper);
        GroupData group = createGroup();
        int id = app.db().groups().stream().mapToInt(GroupData::getId).max().getAsInt();
        verifyAddGroup(modifiedContact, before, contactHelper, group, id);
    }

}


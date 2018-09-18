package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class DeleteContactFromGroupTest extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        verifyGroupsZero();
        verifyContactsZero();
    }

    @Test
    public void testDeleteContactFromGroup() {
        Groups groups = app.db().groups();
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
    }

}

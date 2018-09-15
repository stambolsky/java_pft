package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddedContactGroup extends TestBase {

    @BeforeMethod
    public void ensurePreconditionsGroupsAndContact() {

        if (app.db().groups().size() == 0) {
            GroupData newGroup = new GroupData().withName("group1").withHeader("Header1").withFooter("Footer1");
            app.goTo().groupPage();
            Groups before = app.db().groups();
            app.group().create(newGroup);
            assertThat(app.group().count(), equalTo(before.size() + 1));
            Groups after = app.db().groups();
            assertThat(after, equalTo(
                    before.withAdded(newGroup.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
            verifyGroupListInUi();
        }
        if (app.db().contacts().size() == 0) {
            app.contact().goToHome();
            app.contact().create(new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                    .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                    .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354"));
        }
    }

    @Test
    public void addedContactToGroups() {
        Groups groups = app.db().groups();
        ContactData modifiedContact = app.db().contacts().iterator().next();
        Groups before = modifiedContact.getGroups();
        ContactHelper contactHelper = app.contact();
        for (GroupData groupData : groups) {
            if (!contactHelper.isInGroups(before, groupData)) {
                app.contact().goToHome();
                contactHelper.addGroup(modifiedContact, groupData.getId());
                before.add(groupData);
                Groups after = contactHelper.getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
                assertThat(after, equalTo(before));
            }
        }

        app.goTo().groupPage();
        GroupData group = new GroupData().withName("group1").withHeader("Header1").withFooter("Footer1");
        app.group().create(group);
        int id = app.db().groups().stream().mapToInt(GroupData::getId).max().getAsInt();
        app.contact().goToHome();
        contactHelper.addGroup(modifiedContact, id);
        before.add(group.withId(id));
        Groups after = contactHelper.getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
        assertThat(after, equalTo(before));
    }
}


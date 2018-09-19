package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

    public static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("Stop test " + m.getName());
    }

    public void verifyGroupListInUi() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }

    public void verifyContactListInUi() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts();
            Contacts uiContatcs = app.contact().all();
            assertThat(uiContatcs, equalTo(dbContacts.stream()
                    .map((c) -> new ContactData().withId(c.getId()).withFirstname(c.getFirstname())
                            .withLastname(c.getLastname()).withAddress(c.getAddress()).withEmail(c.getEmail())
                            .withEmail2(c.getEmail2()).withEmail3(c.getEmail3()).withHomePhone(c.getHomePhone())
                            .withMobilePhone(c.getMobilePhone()).withWorkPhone(c.getWorkPhone()))
                    .collect(Collectors.toSet())));
        }
    }

    public void verifyCreateGroups(GroupData group) {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after = app.db().groups();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyGroupListInUi();
    }

    public void createContact(Groups groups) {
        app.goTo().goToHome();
        app.contact().create(new ContactData().withFirstname("SergeyEdit").withLastname("TambolskyEdit")
                .withAddress("EditTest123").withEmail("testEdit@test.test").withEmail2("test2Edit@test.test").withEmail3("test3Edit@test.test")
                .withHomePhone("+375987656553").withMobilePhone("+987654326").withWorkPhone("+987654332354"));
    }

    public void verifyContactsZero() {
        if (app.db().contacts().size() == 0) {
            Groups groups = app.db().groups();
            createContact(groups);
        }
    }

    public void verifyGroupsZero() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test"));
        }
    }

    public void addContactInGroup(Groups groups, ContactData modifiedContact, Groups before, ContactHelper contactHelper) {
        for (GroupData groupData : groups) {
            if (!contactHelper.isInGroups(before, groupData)) {
                app.contact().goToHome();
                contactHelper.addGroup(modifiedContact, groupData.getId());
                before.add(groupData);
                Groups after = contactHelper.getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
                assertThat(after, equalTo(before));
            }
        }
    }

    public void verifyAddGroup(ContactData modifiedContact, Groups before, ContactHelper contactHelper, GroupData group, int id) {
        app.contact().goToHome();
        contactHelper.addGroup(modifiedContact, id);
        before.add(group.withId(id));
        Groups after = contactHelper.getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
        assertThat(after, equalTo(before));
    }

    public GroupData createGroup() {
        app.goTo().groupPage();
        GroupData group = new GroupData().withName("group1").withHeader("Header1").withFooter("Footer1");
        app.group().create(group);
        return group;
    }

    public void verifyRemoveGroup(Groups groups, ContactData modifiedContact, Groups before, ContactHelper contactHelper) {
        GroupData group = groups.iterator().next();
        app.goTo().goToHome();
        contactHelper.addGroup(modifiedContact, group.getId());
        app.goTo().goToHome();
        contactHelper.isFromGroup(modifiedContact, group.getId());
        before.remove(group);
        Groups after = app.contact().getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
        assertThat(after, equalTo(before));
    }

    public void removeGroup(ContactData modifiedContact, Groups before, ContactHelper contactHelper, GroupData groupdata) {
        app.goTo().goToHome();
        contactHelper.isFromGroup(modifiedContact, groupdata.getId());
        before.remove(groupdata);
        Groups after = contactHelper.getContact(app.db().contacts(), modifiedContact.getId()).getGroups();
        assertThat(after, equalTo(before));
    }
}


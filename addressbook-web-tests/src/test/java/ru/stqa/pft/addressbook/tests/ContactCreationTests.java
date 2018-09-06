package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts(){
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/img.png");
        list.add(new Object[] {new ContactData().withFirstname("Sergey").withLastname("Tambolsky").withPhoto(photo).withAddress("Belarus").withEmail("test@test.test").withEmail2("test2@test.test").withEmail3("test3@test.test").withHomePhone("+17512345678").withMobilePhone("+18387487934").withWorkPhone("+14298653676")});
        list.add(new Object[] {new ContactData().withFirstname("Sergey2").withLastname("Tambolsky2").withPhoto(photo).withAddress("Belarus2").withEmail("test@test.test2").withEmail2("test2@test.test2").withEmail3("test3@test.test2").withHomePhone("+237512345678").withMobilePhone("+28387487934").withWorkPhone("+24298653676")});
        list.add(new Object[] {new ContactData().withFirstname("Sergey3").withLastname("Tambolsky3").withPhoto(photo).withAddress("Belarus3").withEmail("test@test.test3").withEmail2("test2@test.test3").withEmail3("test3@test.test3").withHomePhone("+37512345678").withMobilePhone("+38387487934").withWorkPhone("+34298653676")});
        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) {
        app.contact().goToHome();
        Contacts before = app.contact().all();
        app.contact().goToAddContact();
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    /*@Test
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/img.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }*/

}

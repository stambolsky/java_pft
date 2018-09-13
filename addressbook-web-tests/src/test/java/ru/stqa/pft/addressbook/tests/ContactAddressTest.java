package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {
    @Test
    public void testContactPhones() {
        app.contact().goToHome();
        ContactData contact = app.db().contacts().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().InfoFromEditForm(contact);

        assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
        verifyContactListInUi();
    }

    private String mergeAddress(ContactData contact) {
        return Arrays.asList(contact.getAddress())
                .stream().filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}


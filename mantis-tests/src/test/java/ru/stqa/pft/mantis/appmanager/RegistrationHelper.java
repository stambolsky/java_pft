package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.tests.TestBase;

import static ru.stqa.pft.mantis.tests.TestBase.app;

public class RegistrationHelper {
    private final ApplicationManager app;
    private WebDriver wd;

    public RegistrationHelper(ApplicationManager app) {
        this.app = app;
        wd = app.getDriver();
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    }
}

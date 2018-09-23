package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChangePassword extends HelperBase{
    public ChangePassword(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {
        type(By.xpath("//input[@name='username']"), username);
        type(By.xpath("//input[@name='password']"), password);
        click(By.xpath("//input[@value='Login']"));
    }

    public void selectRandomUser() {
        click(By.linkText("Manage Users"));
        wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<WebElement> rows = wd.findElements(By.xpath("//table//tbody//tr"));
        int count = rows.size() - 1;
        String randomUserNumber = getRandomUserNumber(3, count);
        click(By.xpath("//tbody//tr[" + randomUserNumber + "]//a"));
    }

    public static String getRandomUserNumber(int min, int max) {
        Random randomNum = new Random();
        String num = String.valueOf(randomNum.nextInt((max - min) + 1) + min);
        return num;
    }
    public String getRandomUserName() {
        String randomUserName = wd.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]/td[2]/input")).getAttribute("value");
        return randomUserName;
    }
    public String getRandomUserEmail() {
        String randomUserEmail = wd.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[4]/td[2]/input")).getAttribute("value");
        return randomUserEmail;
    }
    public void resetUserPassword() {
        click(By.xpath("/html/body/div[4]/form[1]/input[3]"));
    }

    public void changePassword(String confrimationLink, String password) {
        wd.get(confrimationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("/html/body/div[2]/form/table/tbody/tr[10]/td[2]/input"));
    }
}

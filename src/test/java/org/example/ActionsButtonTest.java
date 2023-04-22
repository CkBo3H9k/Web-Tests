package org.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ActionsButtonTest {
    public static WebDriver driver;
    @BeforeClass
    public static void setDriver() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("homepage"));
    }
    @Test
    public void buttonTest() {
        driver.findElement(By.xpath("//*[@id=\"rec572934623\"]/div/div/div[9]/div/a[4]")).click();
    }
    @AfterClass
    public static void tearDown () {
        driver.quit();
    }
}
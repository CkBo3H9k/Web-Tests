package org.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
public class LoginTest {
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static WebDriver driver;
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("loginpage"));
    }
    @Test
    public void loginTest() {
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        loginPage.inputPassword(ConfProperties.getProperty("password"));
        loginPage.clickLoginBtn();
    }
    @AfterClass
    public static void tearDown () {
        profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit();
    }
}
class ProfilePage {
    public WebDriver driver;
    public ProfilePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    @FindBy(xpath = "//*[@id=\"sf-app\"]/div/header/div/div/div/button")
    private WebElement userMenu;
    @FindBy(xpath = "//*[@id=\"sf-app\"]/div/header/div/div/div/div/div/ul/li[2]/a")
    private WebElement logoutBtn;
    public void entryMenu() {
        userMenu.click();
    }
    public void userLogout() {
        logoutBtn.click();
    }
}
class LoginPage {
    public WebDriver driver;
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    @FindBy(xpath = "//*[@id=\"sf-app\"]/div/main/div/form/div[1]/div/input")
    private WebElement loginField;
    @FindBy(xpath = "//*[@id=\"sf-app\"]/div/main/div/form/button")
    private WebElement loginBtn;
    @FindBy(xpath = "//*[@id=\"sf-app\"]/div/main/div/form/div[2]/div/input")
    private WebElement passwordField;

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }
    public void inputPassword(String password) {
        passwordField.sendKeys(password);
    }
    public void clickLoginBtn() {
        loginBtn.click();
    }
}
class ConfProperties {
    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;
    static {
        try {
            //указание пути до файла с настройками
            fileInputStream = new FileInputStream("src/test/resources/conf.properties");
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            //обработка возможного исключения (нет файла и т.п.)
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    /**
     * метод для возврата строки со значением из файла с настройками
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
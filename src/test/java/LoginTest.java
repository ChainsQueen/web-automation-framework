import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Run headless in CI (no visible browser window needed on a server)
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        Thread.sleep(4000);
        driver.quit();
    }

    // Happy paths
    @Test
    public void loginWithValidCredentials() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getTitle(), "Products");
    }

    // Unhappy paths
    @Test
    public void loginWithInValidPassword() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("unknown");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void loginWithInValidUsername() {
        loginPage.enterUsername("standard");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void loginWithInValidCredentials() {
        loginPage.enterUsername("standard");
        loginPage.enterPassword("unknown");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

    // Edge Cases
     @Test
    public void loginWithEmptyUsername() {
         loginPage.enterUsername("");
         loginPage.enterPassword("secret_sauce");
         loginPage.clickLoginButton();
         Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");

     }

    @Test
    public void loginWithEmptyPassword() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required");

    }

    @Test
    public void loginWithEmptyUsernameAndPassword() {
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");

    }

    @Test
    public void loginWithLockedOutUser() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Sorry, this user has been locked out.");

    }
}

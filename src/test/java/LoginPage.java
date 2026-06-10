import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(By.id("user-name")).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }
    public void clickLoginButton() {
        driver.findElement(By.id("login-button")).click();
    }
    public String getTitle() {
        return driver.findElement(By.className("title")).getText();
    }
    public String getErrorMessage(){
        return driver.findElement(By.className("error-message-container")).getText();
    }

}
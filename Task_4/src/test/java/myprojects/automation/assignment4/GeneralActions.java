package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */



    public void login(String login, String password) {
       driver.findElement(By.xpath("//input[@id='email']")).sendKeys(login);
       driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys(password);
       driver.findElement(By.xpath("//button[@name='submitLogin']")).click();

    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        throw new UnsupportedOperationException();
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded

        // wait.until(...);
        // ...
    }
}

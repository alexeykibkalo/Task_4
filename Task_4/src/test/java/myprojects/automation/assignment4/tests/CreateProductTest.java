package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;

import myprojects.automation.assignment4.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;

public class CreateProductTest extends BaseTest
{

    public ProductData product;

    @DataProvider(name = "signIn")
    public Object[][] getData(){
        return new Object[][]{
                {Properties.getLogin(),Properties.getPassword()}
        };
    }
    @Test(dataProvider = "signIn")
    public void createNewProduct(String Login,String Password)
    {
        signIn(Login, Password);
        addNewProduct();
    }
//------------------------------------------------------------------------------------
        //устройство сообщенька
//<div id="growls" class="default">
// <div class="growl growl-notice growl-medium">
 //     <div class="growl-close">×</div>
//      <div class="growl-title"></div>
//      <div class="growl-message">Настройки обновлены.</div>
// </div>
//</div>


//------------------------------------------------------------------------------------

    @Test(dependsOnMethods ={"createNewProduct"} )
    public void checkProduct()
    {
        searchProduct();
        checkProductData();
    }



    private void addNewProduct() {
        driver.findElement(By.xpath("//a[@id='page-header-desc-configuration-add']")).click();
        // set Name
        driver.findElement(By.xpath("//input[@id='form_step1_name_1']")).sendKeys(product.getName());
        // set Quantity
        WebElement count = driver.findElement(By.xpath("//input[@id='form_step1_qty_0_shortcut']"));
        count.sendKeys(Keys.BACK_SPACE);
        count.sendKeys(product.getQty().toString());
        // set Price
        Actions builder = new Actions(driver);
        WebElement price = driver.findElement(By.xpath("//input[@id='form_step1_price_shortcut']"));
        price.sendKeys("");//костыль, но по другому input не получает фокус
        builder.doubleClick(price).sendKeys(Keys.DELETE).sendKeys(product.getPrice()).build().perform();
        //activate
        builder.sendKeys(Keys.LEFT_CONTROL + "o").perform();
        //ожидание сообщения "Настройки обновлены."
        WebElement message = new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='growls']")));
        //save
        driver.findElement(By.xpath("//button[@class='btn btn-primary js-btn-save']")).click();
        message = new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='growls'][1]")));
    }

    private void signIn(String Login, String Password) {
        product = ProductData.generate();
        driver.get(Properties.getBaseAdminUrl());
        actions.login(Login, Password);
        driver.findElement(By.xpath("//li[@id='subtab-AdminCatalog']")).click();
    }


    private void checkProductData() {
        swithWindow();

        WebElement wait = new WebDriverWait(driver,15)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='container']")));

        Assert.assertEquals(driver.findElement(By.xpath("//h1[@class='h1']")).getText().toLowerCase(), product.getName().toLowerCase());
        CustomReporter.log("Name valid");
        Assert.assertTrue(driver.findElement(By.xpath("//span[@itemprop='price']")).getText().contains(product.getPrice()));
        CustomReporter.log("Price valid");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='product-quantities']//span"))
                .getText().contains(product.getQty().toString()));
        CustomReporter.log("Quantity valid");
    }

    private void searchProduct() {
        driver.get(Properties.getBaseUrl());

        driver.findElement(By.xpath("//a[@class='all-product-link pull-xs-left pull-md-right h4']")).click();

        swithWindow();

        WebElement targetProduct = new WebDriverWait(driver,15)
                 .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@class='h1']")));

        Actions builder = new Actions(driver);

        for(int i = 0 ;i<50; i++)
        {
            if (driver.findElements(By.xpath(
                    format("//a[contains(text(),'%s')]", product.getName()))).size()==0)
            {
               builder.moveToElement(driver.findElement(By.xpath("//a[@rel='next']")))
                        .click().build().perform();
                CustomReporter.log("Next page");
                WebElement wait = new WebDriverWait(driver,15)
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='products row']")));

            }
           else {
                CustomReporter.log("Product found");
                break;
                //return;
            }

        }

        driver.findElement(By.xpath(format("//a[contains(text(),'%s')]",product.getName()))).click();
    }

    private void swithWindow() {
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
    }


}

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RgsTest {

    static WebDriver driver;

    @BeforeClass
    public static void setup(){
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String url = "http://www.rgs.ru";
        driver.get(url);
    }

    @Test
    public void scenario(){
        WebElement menu = driver.findElement(By.xpath("//a[@class='hidden-xs' and @data-toggle='dropdown']"));
        menu.click();
        WebElement DMS = driver.findElement(By.xpath("//a[contains(text(), 'ДМС')]"));
        DMS.click();

        //Wait element
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='clearfix']/h1")));
        WebElement contentHeader = driver.findElement(By.xpath("//div[@class='clearfix']/h1"));

        // Check header
        Assert.assertEquals("Header does not meet the requirements",
                "ДМС — добровольное медицинское страхование", contentHeader.getText());

        WebElement button = driver.findElement(By.xpath("//a[contains(text(), 'Отправить заявку')]"));
        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(), 'Заявка на добровольное')]")));
        WebElement requestHeader = driver.findElement(By.xpath("//b[contains(text(), 'Заявка на добровольное')]"));

        //Check header on request window
        Assert.assertEquals("Header on request window dows not meet the requirements",
                "Заявка на добровольное медицинское страхование", requestHeader.getText());

        //Insert information
        WebElement lastName = driver.findElement(By.xpath("//input[@name='LastName']"));
        lastName.sendKeys("Фамилия");
        WebElement firstName = driver.findElement(By.xpath("//input[@name='FirstName']"));
        firstName.sendKeys("Имя");
        WebElement middleName = driver.findElement(By.xpath("//input[@name='MiddleName']"));
        middleName.sendKeys("Отчество");
        WebElement region = driver.findElement(By.xpath("//select[@name='Region']"));
        Select select = new Select(region);
        select.selectByVisibleText("Москва");
        WebElement phone = driver.findElement(By.xpath("//label[contains(text(), 'Телефон')]/../input"));
        phone.sendKeys("1111111111");
        WebElement email = driver.findElement(By.xpath("//input[@name='Email']"));
        email.sendKeys("qwertyqwerty");
        WebElement comment = driver.findElement(By.xpath("//textarea[@name='Comment']"));
        comment.sendKeys("My Comment");
        WebElement accept = driver.findElement(By.xpath("//label[@for='$prev']"));
        accept.click();

        //Check information
        Assert.assertEquals("Фамилия", lastName.getText());
        Assert.assertEquals("Имя", firstName.getText());

        WebElement submit = driver.findElement(By.id("button-m"));
        submit.click();





    }

    @AfterClass
    public static void tearDown(){
        driver.quit();
    }
}

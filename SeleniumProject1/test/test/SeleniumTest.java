package test;

import com.google.common.base.CharMatcher;
import java.io.File;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public class SeleniumTest {

    private static WebDriver driver;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @BeforeClass
    public static void setupDriver() {
        System.setProperty("webdriver.chrome.driver",
                "chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void releaseDriver() {
        driver.close();
    }

    @Test
    @Ignore
    public void test118() {
        driver.get("http://118.um.ac.ir");
        WebElement field = driver.findElement(By.name("allname"));
        field.sendKeys("پایدار");
        field.submit();
        WebElement resultElement = driver.findElement(By.xpath("//span[@class=\"digit\"]"));
        assertNotNull("No result", resultElement);
        String phone = resultElement.getText();
        String expected = "5184";
        assertEquals(expected, phone);
    }

    @Test
    @Ignore
    public void testBackAndForward() {
        driver.get("http://um.ac.ir");
        String expected = driver.getTitle();
        driver.get("http://ce.um.ac.ir");
        driver.get("http://end.um.ac.ir");
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().back();
        driver.navigate().back();
        String title = driver.getTitle();
        assertEquals(expected, title);
    }

    @Test
    @Ignore
    public void testContextMenu() {
        File file = new File("sample_web_pages\\context_menu\\index.html");
        driver.get(file.toURI().toString());
        WebElement element = driver.findElement(By.xpath("//a[@data-action=\"View\"]"));
        assertFalse(element.isDisplayed());
        element = driver.findElement(By.xpath("//a[@data-action=\"Edit\"]"));
        assertFalse(element.isDisplayed());
        element = driver.findElement(By.xpath("//a[@data-action=\"Delete\"]"));
        assertFalse(element.isDisplayed());

        element = driver.findElement(By.xpath("/html/body/main/div/ul/li[1]/div[1]"));
        (new Actions(driver)).contextClick(element).perform();
        element = driver.findElement(By.xpath("//a[@data-action=\"View\"]"));
        assertTrue(element.isDisplayed());
        element = driver.findElement(By.xpath("//a[@data-action=\"Edit\"]"));
        assertTrue(element.isDisplayed());
        element = driver.findElement(By.xpath("//a[@data-action=\"Delete\"]"));
        assertTrue(element.isDisplayed());
    }

    @Test
    @Ignore
    public void testImagesHaveAltAttribute() {
        driver.get("https://digikala.com/");
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement image : images) {
            String altValue = image.getAttribute("alt");
            boolean condition = altValue != null && !altValue.trim().isEmpty();
            //NOTICE: this will be displayed in output, not in the test results tree
            errorCollector.checkThat("Alt Value: " + altValue, condition, equalTo(true));
        }
    }

    @Test
    @Ignore
    public void testAllPagesHaveTitle() {
        driver.get("https://digikala.com/");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        int linkCount = links.size();
        for (int i = 0; i < linkCount; i++) {
            links = driver.findElements(By.tagName("a"));
            WebElement link = links.get(i);
            String href = link.getAttribute("href");
            if (href != null && !href.startsWith("javascript")) {
                if (!href.contains("digikala.com")) {
                    href = "https://www.digikala.com" + href;
                }
                System.out.println("HREF: " + href);
                driver.navigate().to(href);
                String title = driver.getTitle();
                System.out.println("TITLE: " + title);
                assertTrue(title != null
                        && (title.contains("دیجی کالا")
                        || title.toLowerCase().contains("digikala")));
                driver.navigate().back();
            }

        }
    }

    @Test
    public void testAllPagesHaveTitle2() {
        driver.get("http://um.ac.ir");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        int linkCount = links.size();
        for (int i = 0; i < linkCount; i++) {
            links = driver.findElements(By.tagName("a"));
            WebElement link = links.get(i);
            String href = link.getAttribute("href");
            if (href != null && !href.startsWith("javascript")) {
                if (!href.contains("um.ac.ir")) {
                    href = "http://um.ac.ir/" + href;
                }
                System.out.println("HREF: " + href);
                driver.navigate().to(href);
                String title = driver.getTitle();
                System.out.println("TITLE: " + title);
                assertTrue(title != null);
                driver.navigate().back();
            }

        }
    }
}

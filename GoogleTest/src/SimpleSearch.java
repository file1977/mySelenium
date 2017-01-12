import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/**
 * Created by wenjia on 11/17/2014.
 */
public class SimpleSearch {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
//        WebDriver driver = new FirefoxDriver();

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        Proxy proxy = new Proxy();
        String PROXY = "proxy.houston.hpecorp.net:8080";
        proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        WebDriver driver = new ChromeDriver(capabilities);


        // And now use this to visit Google
//        driver.get("http://www.baidu.com");
//        driver.get("http://www.sina.com");
//        driver.get("http://www.hp.com");
        driver.get("http://www.google.com.hk");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());


//        Cookie cookie = new Cookie("key", "value");
//        driver.manage().addCookie(cookie);

// And now output all the available cookies for the current URL
        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie loadedCookie : allCookies) {
            System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
        }

        //Baidu
        driver.navigate().to("http://www.baidu.com");
        System.out.println("Navigate to baidu");
//        driver.get("http://www.baidu.com");

        final String  toBeSearch = "Selenium";
        element = driver.findElement(By.name("wd"));
        element.sendKeys(toBeSearch);
        element.submit();
        System.out.println("The title of Baidu page is: " + driver.getTitle());

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return webDriver.getTitle().toLowerCase().startsWith(toBeSearch.toLowerCase());
            }
        });

        allCookies = driver.manage().getCookies();
        for (Cookie loadedCookie : allCookies) {
            System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
        }

        //Close the browser
        driver.quit();
    }
}

package my.com.myElement.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by wenjia on 1/12/2017.
 */
public class GetWebDriver {
    private static WebDriver driver;
    private String PROXY = "proxy.houston.hpecorp.net:8080";

    public static WebDriver chrome() {
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
//        capabilities.setCapability(CapabilityType.PROXY, proxy);
//        WebDriver driver = new ChromeDriver(capabilities);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        WebDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().implicitlyWait(Util.WAIT_FOR_ELEMENT_TIMEOUT, TimeUnit.SECONDS);

        driver = chromeDriver;

        return chromeDriver;
    }

    public static WebDriver ie() {
        WebDriver ieDriver = new InternetExplorerDriver();
        ieDriver.manage().timeouts().implicitlyWait(Util.WAIT_FOR_ELEMENT_TIMEOUT, TimeUnit.SECONDS);

        driver = ieDriver;

        return ieDriver;
    }

    public WebDriver firefox() {
        ProfilesIni allProfiles = new ProfilesIni();
        FirefoxProfile profile = allProfiles.getProfile("WebDriver");
        profile.setPreference("foo.bar", 23);
        WebDriver ffDriver = new FirefoxDriver(profile);
        ffDriver.manage().timeouts().implicitlyWait(Util.WAIT_FOR_ELEMENT_TIMEOUT, TimeUnit.SECONDS);

        driver = ffDriver;

        return ffDriver;
    }

    public static WebDriver currentDriver() {
        return driver;
    }
}

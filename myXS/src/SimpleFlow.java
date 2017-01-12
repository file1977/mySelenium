import my.com.myElement.*;
import my.com.myElement.util.*;
import my.com.myElement.elements.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 * Created by Wen Jian on 11/17/2014.
 */
public class SimpleFlow {
    private static Long startTime;
    private static Long lastTime;
    private static final Logger log = Logger.getLogger("myLog");
    private static int runs=0;
    private static int succeed = 0;
    private static int failed = 0;

    private void getDuration(String msg) {
        Long currentTime = System.currentTimeMillis();
        Long total = currentTime - startTime;
        Long duration = currentTime - lastTime;
        lastTime = currentTime;
        log.info("--------- Action: <<< " + msg + " >>> | Total: " + total + " | Duration: " + duration);
    }

    private void run() throws Exception {
        startTime=System.currentTimeMillis();
        lastTime=startTime;

        final String BASE_URL="https://stmaster.hpeswlab.net:5443";


        WebDriver driver = GetWebDriver.chrome();

        driver.manage().window().maximize();

        try {
            //Open the page
            driver.get(BASE_URL);

            // Login
            WebElement userName = driver.findElement(By.name("username"));
            WebElement password = driver.findElement(By.id("mng-login-pwd"));
            WebElement loginBtn = driver.findElement(By.className("btn-container"));
            userName.sendKeys("admin");
            password.sendKeys("Admin111");
            loginBtn.click();

            // Check the title of the page
            System.out.println("Page title is: " + driver.getTitle());
            WebElement menuNav = driver.findElement(By.xpath("/html/body/kd-chrome/md-content/kd-nav/md-sidenav"));
            WebElement mainContentBox = driver.findElement(By.xpath("/html/body/kd-chrome/md-content/md-content"));

            // Check menu area
            WebElement navBtn = driver.findElement(By.xpath("/html/body/kd-chrome/md-content/div/kd-nav-hamburger/md-icon"));
            showElementStatus("Menu area", menuNav);
            navBtn.click();
            waitSec(1);
            showElementStatus("Menu area", menuNav);
            navBtn.click();
            waitSec(1);
            showElementStatus("Menu area", menuNav);

            // Click Admin menu section
            WebElement adminSection = menuNav.findElement(By.xpath("div[2]"));
            WebElement adminMenu = adminSection.findElement(By.xpath("kd-nav-item/span"));
            showElementStatus("Admin menu", adminMenu);
            adminMenu.click();

            // Click User Management menu
            WebElement userMngMenu = adminSection.findElement(By.xpath("div/kd-nav-item[5]"));
            showElementStatus("User Management menu", userMngMenu);
            userMngMenu.click();
            WebElement contentCard = mainContentBox.findElement(By.xpath("div/div/div/div/div/kd-content-card/div"));
            WebElement userTable = contentCard.findElement(By.xpath("div/div[1]/kd-content/table"));
            showElementStatus("Content Card", contentCard);

//            // Click Add user button
//            WebElement addUserBtn = contentCard.findElement(By.xpath("div/h1/kd-title/button"));
//            showElementStatus("Add User button", addUserBtn);
//            addUserBtn.click();
//            waitSec(1);
//            WebElement createUserDialog = driver.findElement(By.xpath("/html/body//md-dialog[@aria-label='AddUser']/md-dialog-content"));
//            waitForElement(createUserDialog);
//
//            // Add user in dialog
//            WebElement addUserUserName = createUserDialog.findElement(By.name("name"));
//            WebElement addUserPassword = createUserDialog.findElement(By.name("newpassword"));
//            WebElement addUserConfirm = createUserDialog.findElement(By.name("confirmpassword"));
//            WebElement addUserEmail = createUserDialog.findElement(By.name("email"));
//            WebElement addUserGroup = createUserDialog.findElement(By.xpath("//md-select"));
//            WebElement addUserDisplay = createUserDialog.findElement(By.name("description"));
//            WebElement addUserSave = createUserDialog.findElement(By.xpath("md-dialog-actions/button[1]"));
//            WebElement addUserCancel = createUserDialog.findElement(By.xpath("md-dialog-actions/button[2]"));
//            String autoUser = "autoAdmin" + runs;
//            addUserUserName.sendKeys(autoUser);
//            addUserPassword.sendKeys("Admin111");
//            addUserConfirm.sendKeys("Admin111");
//            addUserEmail.sendKeys(autoUser + "@hpetest.com");
//            addUserDisplay.sendKeys("Automation Admin " + runs);
//            waitSec(1);
//            addUserSave.click();
//            waitSec(1);

            // Delete user
            WebElement newUserLine = userTable.findElement(By.xpath("tbody/tr[4]"));
            WebElement newUserAction = newUserLine.findElement(By.xpath("td[6]/md-menu/button"));
            newUserAction.click();
            waitSec(0.5);
            WebElement newUserMenu = driver.findElement(By.xpath("/html/body/div[contains(@class,'md-open-menu-container') and @aria-hidden='false']"));
            showElementStatus("new User Menu", newUserMenu);
            WebElement newUserDelete = newUserMenu.findElement(By.xpath("md-menu-content/md-menu-item[1]"));
            newUserDelete.click();
            waitSec(0.5);
            WebElement delUserDialog = driver.findElement(By.xpath("//md-dialog[@aria-label='Delete a user']"));
            showElementStatus("Delete a User dialog", delUserDialog);

            WebElement delUserBtn = delUserDialog.findElement(By.xpath("//button[2]"));
            delUserBtn.click();
            waitSec(4);

            // Logout
            WebElement adminBtn = driver.findElement(By.xpath("/html/body/kd-chrome/md-toolbar/div/kd-actionbar/div/div[2]/md-menu/button"));
            adminBtn.click();
            waitSec(0.5);
            WebElement toolbarAdminMenu = driver.findElement(By.xpath("/html/body/div[contains(@class,'md-open-menu-container') and @aria-hidden='false']"));
            showElementStatus("ADMIN Menu", toolbarAdminMenu);
            WebElement logoutBtn = toolbarAdminMenu.findElement(By.xpath("md-menu-content/md-menu-item[2]"));
            logoutBtn.click();
            waitSec(1);

//        Set<Cookie> allCookies = driver.manage().getCookies();
//        for (Cookie loadedCookie : allCookies) {
//            System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
//        }
        } catch (Exception e) {
            driver.quit();
            getDuration("END");
            log.warning("ERROR run: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        driver.quit();
        getDuration("END");

    }

    public static void main(String[] args) throws Exception {
        SimpleFlow simpleFlow = new SimpleFlow();

        for (int i=0; i<100000; i++) {
            runs++;
            try {
                simpleFlow.run();
                succeed++;
            } catch (Exception e) {
                failed++;
            }
            showSummary();
        }

    }

    private static void showSummary() {
        System.out.println("#############################");
        System.out.println("  Runs:     " + runs );
        System.out.println("  Succeed:  " + succeed );
        System.out.println("  Failed:   " + failed );
        System.out.println("#############################");
        System.out.println();
    }

    private void showElementStatus(String elementName, WebElement webElement) {
        if( webElement == null ) {
            System.out.println( elementName + "is null" );
        } else {
            if( webElement.isDisplayed() ) {
                System.out.println( "[" + elementName + "] is visible" );
            } else {
                System.out.println( "[" + elementName + "] is invisible" );
            }
        }
    }

    private void waitSec(double sec) {
        try {
//            System.out.println("Wait " + sec + " seconds ...");
            Thread.sleep((long) (sec * 1000));
        } catch (InterruptedException  e) {

        }
    }

    private boolean waitForElement(WebElement webElement) {
        int timeout=60;

        for (int i=0; i<timeout; i++) {
            if ( webElement == null || !webElement.isDisplayed() ) {
                waitSec(1);
            } else {
                return true;
            }
        }

        System.out.println("Wait for " + timeout + " seconds timeout!");
        return false;
    }
}

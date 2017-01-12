package my.com.myElement;

import my.com.myElement.util.GetWebDriver;
import my.com.myElement.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Wen Jian on 1/12/2017.
 */
public class MyWebElement {
    private By locator;
    private String name;
    protected WebElement we;

    protected WebDriverWait wait = new WebDriverWait(GetWebDriver.currentDriver(), Util.WAIT_FOR_ELEMENT_TIMEOUT);

    public MyWebElement(By locator) {
        this.locator = locator;
        this.we = GetWebDriver.currentDriver().findElement(locator);
        this.name = this.getClass().getName();
    }

    public MyWebElement(By locator, String elementName) {
        this.locator = locator;
        this.we = GetWebDriver.currentDriver().findElement(locator);
        this.name = elementName;
    }


    public MyWebElement(MyWebElement parent, By locator) {
        this.we = parent.getWe().findElement(locator);
        this.name = parent.getName() + "_" + this.getClass().getName();
    }

    public MyWebElement(MyWebElement parent, By locator, String elementName) {
        this.name = elementName;
        this.we = parent.getWe().findElement(locator);
    }


    public void setLocator(By locator) {
        this.locator = locator;
    }

    public void setName(String elementName) {
        this.name = elementName;
    }

    public WebElement getWe() {
        return we;
    }

    public String getName() {
        return name;
    }

    public MyWebElement load() {
        this.we = GetWebDriver.currentDriver().findElement(this.locator);

        if( name == null )
            this.name = this.getClass().getName();

        return this;
    }

    public boolean loaded() {
        return this.we != null;
    }

    public void click() {
        wait.until(ExpectedConditions.elementToBeClickable(we));
        this.we.click();
    }

    public boolean waitForVisible() {
        wait.until(ExpectedConditions.visibilityOf(we));
        return true;
    }

}

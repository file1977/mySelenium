package my.com.myElement.elements;

import my.com.myElement.MyWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;

/**
 * Created by wenjia on 1/12/2017.
 */
public class Textbox extends MyWebElement{
    public Textbox(By locator) {
        super(locator);
    }

    public Textbox(By locator, String textboxName) {
        super(locator, textboxName);
    }

    public Textbox(MyWebElement parent, By locator) {
        super(parent, locator);
    }

    public Textbox(MyWebElement parent, By locator, String textboxName) {
        super(parent, locator, textboxName);
    }

}

package hu.genesys.homework.bold360.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class OnlineHtmlEditorTest {

    private final static String ONLINE_HTML_EDITOR_PAGE_URL = "https://onlinehtmleditor.dev/";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        driver.get(ONLINE_HTML_EDITOR_PAGE_URL);
    }

    @Test
    public void testTypeFormattedText() {
        typeTextWithFormat();

        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ckeditor-4-output-button"))).click();

        String actualText = driver.findElement(By.cssSelector("#ckeditor-4-output > div > div.CodeMirror-scroll > div.CodeMirror-sizer > div > div > div > div.CodeMirror-code > div:nth-child(1) > pre > span"))
                .getText();
        Assertions.assertEquals("<p><strong>Automation</strong>&nbsp;<u>Test</u> Example</p>", actualText);
    }

    private void typeTextWithFormat() {
        Actions actions = new Actions(driver);

        WebElement bodyInIframe = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.className("cke_wysiwyg_frame")))
                .findElement(By.className("cke_editable_themed"));
        bodyInIframe.click();

        actions.keyDown(Keys.CONTROL).sendKeys("b").keyUp(Keys.CONTROL).sendKeys(bodyInIframe, "Automation").build().perform();
        actions.keyDown(Keys.CONTROL).sendKeys("b").keyUp(Keys.CONTROL).build().perform();
        bodyInIframe.sendKeys(" ");
        actions.keyDown(Keys.CONTROL).sendKeys("u").keyUp(Keys.CONTROL).build().perform();
        bodyInIframe.sendKeys("Test");
        actions.keyDown(Keys.CONTROL).sendKeys("u").keyUp(Keys.CONTROL).build().perform();
        bodyInIframe.sendKeys(" Example");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

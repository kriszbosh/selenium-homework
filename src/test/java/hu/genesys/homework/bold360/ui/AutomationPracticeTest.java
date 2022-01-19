package hu.genesys.homework.bold360.ui;

import hu.genesys.homework.bold360.ui.page.RegistrationPage;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AutomationPracticeTest {

    private final static String AUTOMATION_PRACTICE_INDEX_PAGE_URL = "http://automationpractice.com/index.php";

    private WebDriver driver;
    private RegistrationPage registrationPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(AUTOMATION_PRACTICE_INDEX_PAGE_URL);
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    public void testCreateAccount() {
        goToRegistrationForm();
        registrationPage.createAccount();
        Assertions.assertTrue(driver.findElement(By.cssSelector("a.logout")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector("a.account")).isDisplayed());
    }

    @Test
    public void testMandatoryFieldsErrorMessage() {
        goToRegistrationForm();
        registrationPage.clearPrefilledMandatoryFields();
        registrationPage.registerButton.click();
        checkErrorMessages();
    }

    @Test
    public void testScrollDownToFooter() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        Long screenHeight = (Long) javascriptExecutor.executeScript("return window.innerHeight");
        long pointWhereFooterStart = calculateFooterTop(javascriptExecutor);
        Long actualBottomPoint = screenHeight;

        while (actualBottomPoint < pointWhereFooterStart) {
            javascriptExecutor.executeScript("window.scrollBy(0, 100)");
            actualBottomPoint += 100L;
        }
    }

    private void goToRegistrationForm() {
        String email = MockNeat.threadLocal().emails().domain("test.io").get();
        driver.findElement(By.cssSelector("#header > div.nav > div > div > nav > div.header_user_info > a")).click();
        driver.findElement(By.id("email_create")).sendKeys(email);
        driver.findElement(By.id("SubmitCreate")).click();
    }

    private void checkErrorMessages() {
        Assertions.assertTrue(driver.findElement(By.className("alert-danger")).isDisplayed());
        final List<String> expectedErrorMessages = List.of("You must register at least one phone number.", "lastname is required.",
                "firstname is required.", "email is required.", "passwd is required.", "id_country is required.",
                "alias is required.", "address1 is required.", "city is required.", "Country cannot be loaded with address->id_country",
                "Country is invalid");

        final List<String> actualErrorMessages = driver.findElements(By.cssSelector("#center_column > div > ol > li"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        for (int i = 0; i < actualErrorMessages.size(); i++) {
            Assertions.assertEquals(expectedErrorMessages.get(i), actualErrorMessages.get(i));
        }
    }

    private long calculateFooterTop(JavascriptExecutor javascriptExecutor) {
        Long totalHeightOfPage = (Long) javascriptExecutor.executeScript("return document.body.offsetHeight");
        Long heightOfFooter = (Long) javascriptExecutor.executeScript("return document.getElementsByClassName('footer-container')[0].offsetHeight");
        return totalHeightOfPage - heightOfFooter;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

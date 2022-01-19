package hu.genesys.homework.bold360.ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage {

    @FindBy(id = "id_gender1")
    public WebElement maleRadioButton;

    @FindBy(id = "customer_firstname")
    public WebElement firstNameInput;

    @FindBy(id = "customer_lastname")
    public WebElement lastNameInput;

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(id = "passwd")
    public WebElement passwordInput;

    @FindBy(id = "days")
    public WebElement daysDropdown;

    @FindBy(id = "months")
    public WebElement monthsDropdown;

    @FindBy(id = "years")
    public WebElement yearsDropdown;

    @FindBy(id = "id_state")
    public WebElement stateDropdown;

    @FindBy(id = "id_country")
    public WebElement countryDropdown;

    @FindBy(id = "company")
    public WebElement companyInput;

    @FindBy(id = "address1")
    public WebElement addressInput;

    @FindBy(id = "city")
    public WebElement cityInput;

    @FindBy(id = "postcode")
    public WebElement postcodeInput;

    @FindBy(id = "phone_mobile")
    public WebElement mobileInput;

    @FindBy(id = "alias")
    public WebElement aliasInput;

    @FindBy(id = "submitAccount")
    public WebElement registerButton;

    public RegistrationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clearPrefilledMandatoryFields() {
        new Select(countryDropdown).selectByIndex(0);
        emailInput.clear();
        aliasInput.clear();
    }

    public void createAccount() {
        fillAllInputsAndSelects();
        registerButton.click();
    }

    private void fillAllInputsAndSelects() {
        maleRadioButton.click();
        firstNameInput.sendKeys("Balogh");
        lastNameInput.sendKeys("Krisztian");
        passwordInput.sendKeys("password");
        new Select(daysDropdown).selectByValue("1");
        new Select(monthsDropdown).selectByValue("11");
        new Select(yearsDropdown).selectByValue("1995");
        companyInput.sendKeys("Test Company");
        addressInput.sendKeys("Test street 1");
        cityInput.sendKeys("Test City");
        new Select(stateDropdown).selectByVisibleText("Alabama");
        postcodeInput.sendKeys("35242");
        mobileInput.sendKeys("+123456789");
        aliasInput.clear();
        aliasInput.sendKeys("Test address");
    }
}
package com.litvinova.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject {

    @FindBy(css = "div.r .wg-header__free-trial-button")
    private WebElement freeTrialButton;

    @FindBy(css = "button.modal-form-trial__submit")
    private WebElement submitEmailButton;

    @FindBy(css = ".modal-form-trial__input[name = email]")
    private WebElement inputField;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickTrialButton() {
        freeTrialButton.click();
    }

    public void enterEmail(String email) {
        inputField.clear();
        inputField.sendKeys(email);
    }

    public SurveyPage submitEmail(){
        submitEmailButton.click();
        return new SurveyPage(driver);
    }

    public boolean isInitialized() {
        return freeTrialButton.isDisplayed();
    }

}

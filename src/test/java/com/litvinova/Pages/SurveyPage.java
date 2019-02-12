package com.litvinova.Pages;

import com.litvinova.tools.ChoiceCreator;
import com.litvinova.tools.ExtendBufferImage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SurveyPage extends PageObject {

    @FindBy(css = "div.radio")
    private List<WebElement> questions;

    @FindBy(css = ".wg-btn--hollow.button")
    private WebElement emailResendButton;

    @FindBy(css = "body > iframe")
    private WebElement googleSurveyIframeOut;

    @FindBy(css = "iframe[ng-non-bindable]")
    private WebElement googleSurveyIframeIn;

    @FindBy(css = "span.RveJvd.snByac")
    private WebElement googleNoButton;

    @FindBy(css = "div.survey-success")
    private WebElement successSurveySubmit;

    @FindBy(css = "input[placeholder='Your comment']")
    private WebElement surveyComment;

    @FindBy(css = "button.js-survey-submit")
    private WebElement surveySubmitButton;

    @FindBy(css = "a[href = 'https://twitter.com/wrike'] svg")
    private WebElement twitterIcon;

    public SurveyPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return emailResendButton.isDisplayed();
    }

    public void fillInSurvey() {

        for (WebElement arg : questions) {
            List<WebElement> questionButtons = arg.findElements(By.cssSelector("button.switch__button"));
            int randomChoice = ChoiceCreator.getRandomChoice(questionButtons.size());
            questionButtons.get(randomChoice).click();
        }
        if (surveyComment.isDisplayed())
            surveyComment.sendKeys("depends on the situation");
    }

    public void killGoogleSurvey() {

        driver.switchTo().frame(googleSurveyIframeIn);
        googleNoButton.click();
        driver.switchTo().defaultContent();
    }

    public void clickEmailResendButton() {
        emailResendButton.click();
    }

    public boolean isSuccessSurveySubmit() {

        WebElement explicitWait = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOf(successSurveySubmit));
        return successSurveySubmit.isDisplayed();
    }

    public void submitSurvey() {
        surveySubmitButton.click();
    }

    public boolean isSuccessEmailResend(){

       WebDriverWait wait = (new WebDriverWait(driver, 5));
       return wait.until(ExpectedConditions.invisibilityOf(emailResendButton));
    }

    private String getNewHandle(String parentHandle) {

        Set<String> newHandles = driver.getWindowHandles();
        newHandles.remove(parentHandle);
        if (newHandles.iterator().hasNext())
            return newHandles.iterator().next();
        return null;
    }
    public boolean isCorrectTwitterUrl() {

        String expectedUrl = "https://twitter.com/wrike";
        String parentHandle = driver.getWindowHandle();

        twitterIcon.click();

        String handle = getNewHandle(parentHandle);
        if (handle != null && !handle.equals("")) {
            driver.switchTo().window(handle);
            if (expectedUrl.compareTo(driver.getCurrentUrl()) == 0)
                return true;
        }
        return false;
    }

    private void pageScrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    public boolean isCorrectTwitterIcon() throws IOException {

        pageScrollDown();

        // Get entire page screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Get the location of element on the page
        Point point = twitterIcon.getLocation();

        // Get width and height of the element
        int iconWidth = twitterIcon.getSize().getWidth();
        int iconHeight = twitterIcon.getSize().getHeight();

        // Get scrollY
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        int scrollY = Math.toIntExact((Long) executor.executeScript("return window.pageYOffset;"));

        // Crop the entire page screenshot to get only element screenshot
        ExtendBufferImage testImage = new ExtendBufferImage(fullImg.getSubimage(point.getX(), point.getY() -  scrollY,
                iconWidth, iconHeight));

        // Read image from class-path
        File classPathInput = new File(getClass().getResource("/screenshot.png").getFile());

        //Comparing images
        ExtendBufferImage realImage = new ExtendBufferImage(ImageIO.read(classPathInput));
        if (!testImage.looksLike(realImage))
            return false;

        return true;
    }
}

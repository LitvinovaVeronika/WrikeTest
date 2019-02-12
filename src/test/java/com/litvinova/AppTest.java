package com.litvinova;

import static org.junit.Assert.assertTrue;

import com.litvinova.Pages.HomePage;
import com.litvinova.Pages.SurveyPage;
import com.litvinova.tools.EmailCreator;

import org.junit.Test;

import java.io.IOException;

public class AppTest extends FunctionalTest
{
    @Test
    public void TestCase() throws IOException {

        driver.get("https://www.wrike.com");
        HomePage home = new HomePage(driver);
        assertTrue(home.isInitialized());

        home.clickTrialButton();

        String email = EmailCreator.getRandomEmail();
        home.enterEmail(email);

        SurveyPage surveyPage = home.submitEmail();
        assertTrue(surveyPage.isInitialized());

        surveyPage.killGoogleSurvey();
        surveyPage.fillInSurvey();

        surveyPage.submitSurvey();
        assertTrue(surveyPage.isSuccessSurveySubmit());

        surveyPage.clickEmailResendButton();
        assertTrue(surveyPage.isSuccessEmailResend());

        assertTrue(surveyPage.isCorrectTwitterIcon());
        assertTrue(surveyPage.isCorrectTwitterUrl());
    }

}

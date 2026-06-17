package com.threebrowsers.selenium.steps;

import com.aventstack.extentreports.ExtentTest;
import com.threebrowsers.selenium.pages.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends BaseSteps {
    private final LoginPage loginPage;

    public LoginSteps(WebDriver driver, String executionIdentifier, ExtentTest test) {
        super(driver, executionIdentifier, test);
        this.loginPage = new LoginPage(driver);
    }

    public void execute(String baseUrl) throws InterruptedException {
        loginPage.loadPage(baseUrl);
        Thread.sleep(1000);
        logStepWithScreenshot("loginPage_loaded", "Login page loaded and displayed");

        loginPage.enterUsername("zoaib@zoaibkhan.com");
        loginPage.enterPassword("testing123");
        loginPage.clickLogin();
        Thread.sleep(2000);

        logPassWithScreenshot("login_completed", "Login flow completed successfully in " + executionIdentifier);
    }
}
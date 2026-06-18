package com.threebrowsers.selenium.drivers;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class BaseDriver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public abstract WebDriver createDriver();

    protected void setupDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        WebDriver currentDriver = driverThreadLocal.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}
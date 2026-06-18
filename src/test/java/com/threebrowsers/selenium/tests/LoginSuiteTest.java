package com.threebrowsers.selenium.tests;

import annotations.browsers.chrome.ChromeDesktop;
import annotations.browsers.chrome.ChromeMobile;
import annotations.browsers.chrome.ChromeTablet;
import annotations.browsers.edge.EdgeDesktop;
import annotations.browsers.edge.EdgeMobile;
import annotations.browsers.edge.EdgeTablet;
import annotations.browsers.firefox.FirefoxDesktop;
import annotations.browsers.firefox.FirefoxMobile;
import annotations.browsers.firefox.FirefoxTablet;
import annotations.browsers.safari.SafariLocal;
import com.threebrowsers.selenium.drivers.BaseDriver;
import com.threebrowsers.selenium.drivers.DeviceProfile;
import com.threebrowsers.selenium.drivers.LocalDriverManager;
import com.threebrowsers.selenium.steps.LoginSteps;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginSuiteTest extends BaseTest {

    @Test
    @Order(1)
    @ChromeDesktop
    void testInChromeDesktop() {
        String browser = "chrome";
        DeviceProfile device = DeviceProfile.DESKTOP;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(2)
    @ChromeTablet
    void testInChromeTablet() {
        String browser = "chrome";
        DeviceProfile device = DeviceProfile.TABLET;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(3)
    @ChromeMobile
    void testInChromeMobile() {
        String browser = "chrome";
        DeviceProfile device = DeviceProfile.MOBILE;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(4)
    @EdgeDesktop
    void testInEdgeDesktop() {
        String browser = "edge";
        DeviceProfile device = DeviceProfile.DESKTOP;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(5)
    @EdgeTablet
    void testInEdgeTablet() {
        String browser = "edge";
        DeviceProfile device = DeviceProfile.TABLET;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(6)
    @EdgeMobile
    void testInEdgeMobile() {
        String browser = "edge";
        DeviceProfile device = DeviceProfile.MOBILE;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(7)
    @FirefoxDesktop
    void testInFirefoxDesktop() {
        String browser = "firefox";
        DeviceProfile device = DeviceProfile.DESKTOP;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(8)
    @FirefoxTablet
    void testInFirefoxTablet() {
        String browser = "firefox";
        DeviceProfile device = DeviceProfile.TABLET;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(9)
    @FirefoxMobile
    void testInFirefoxMobile() {
        String browser = "firefox";
        DeviceProfile device = DeviceProfile.MOBILE;
        executeTest(browser, device, new LocalDriverManager(browser, headlessLocal, device), (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }

    @Test
    @Order(10)
    @EnabledOnOs(OS.MAC)
    @DisplayName("Safari")
    @SafariLocal
    void testInSafari() {
        String browser = "safari";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new com.threebrowsers.selenium.drivers.mac.LocalDriverManagerMac(browser, false, device);

        executeTest(browser, device, driverManager, (driver, test, executionIdentifier) -> {
            new LoginSteps(driver, executionIdentifier, test).execute(baseUrlLocal);
        });
    }
}
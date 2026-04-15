package com.threebrowsers.selenium.tests;

import annotations.browsers.chrome.ChromeDesktop;
import annotations.browsers.chrome.ChromeTablet;
import annotations.browsers.chrome.ChromeMobile;
import annotations.browsers.edge.EdgeDesktop;
import annotations.browsers.edge.EdgeTablet;
import annotations.browsers.edge.EdgeMobile;
import annotations.browsers.firefox.FirefoxDesktop;
import annotations.browsers.firefox.FirefoxTablet;
import annotations.browsers.firefox.FirefoxMobile;
import annotations.browsers.safari.SafariLocalDesktop;
import annotations.browsers.safari.SafariLocalTablet;
import annotations.browsers.safari.SafariCloudDesktop;
import annotations.browsers.safari.SafariCloudTablet;
import com.threebrowsers.selenium.drivers.BaseDriver;
import com.threebrowsers.selenium.drivers.DeviceProfile;
import com.threebrowsers.selenium.drivers.LocalDriverManager;
import com.threebrowsers.selenium.drivers.RemoteDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossBrowserSuiteTest extends BaseTest {
    @Test
    @Order(1)
    @ChromeDesktop
    @DisplayName("Chrome Desktop")
    void testChromeDesktop() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager(currentBrowser, headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(2)
    @ChromeTablet
    @DisplayName("Chrome Tablet")
    void testChromeTablet() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("chrome", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(3)
    @ChromeMobile
    @DisplayName("Chrome Mobile")
    void testChromeMobile() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("chrome", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(4)
    @EdgeDesktop
    @DisplayName("Edge Desktop")
    void testEdgeDesktop() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(5)
    @EdgeTablet
    @DisplayName("Edge Tablet")
    void testEdgeTablet() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(6)
    @EdgeMobile
    @DisplayName("Edge Mobile")
    void testEdgeMobile() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(7)
    @FirefoxDesktop
    @DisplayName("Firefox Desktop")
    void testFirefoxDesktop() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(8)
    @FirefoxTablet
    @DisplayName("Firefox Tablet")
    void testFirefoxTablet() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(9)
    @FirefoxMobile
    @DisplayName("Firefox Mobile")
    void testFirefoxMobile() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(10)
    @SafariLocalDesktop
    @EnabledOnOs(OS.MAC)
    @DisplayName("Safari Desktop")
    void testSafariDesktop() throws InterruptedException {
        String currentBrowser = "safari";
        DeviceProfile device = DeviceProfile.DESKTOP;

        if (!isMacOS()) return;
        BaseDriver driverManager =
                new com.threebrowsers.selenium.drivers.mac.LocalDriverManagerMac(
                        "safari", false, device);

        executeTest(currentBrowser, device, driverManager);
    }


    @Test
    @Order(11)
    @SafariLocalTablet
    @EnabledOnOs(OS.MAC)
    @DisplayName("Safari Tablet")
    void testSafariTablet() throws InterruptedException {
        String currentBrowser = "safari";
        DeviceProfile device = DeviceProfile.TABLET;

        if (!isMacOS()) return;
        BaseDriver driverManager =
                new com.threebrowsers.selenium.drivers.mac.LocalDriverManagerMac(
                        "safari", false, device);

        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(12)
    @SafariCloudDesktop
    @DisplayName("Safari Cloud Desktop")
        //@Disabled("No Credentials")
    void testSafariCloudDesktop() throws InterruptedException {
        String currentBrowser = "safari cloud";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new RemoteDriverManager(device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(13)
    @SafariCloudTablet
    @DisplayName("Safari Cloud Tablet")
        //@Disabled("No Credentials")
    void testSafariCloudTablet() throws InterruptedException {
        String currentBrowser = "safari cloud";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new RemoteDriverManager(device);
        executeTest(currentBrowser, device, driverManager);
    }
}

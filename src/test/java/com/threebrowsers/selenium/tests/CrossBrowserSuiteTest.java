package com.threebrowsers.selenium.tests;

import annotations.browsers.*;
import annotations.devices.Desktop;
import annotations.devices.Mobile;
import annotations.devices.Tablet;
import com.threebrowsers.selenium.drivers.*;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossBrowserSuiteTest extends BaseTest {
    // -------------------------------------------------
    // Tests principales
    // -------------------------------------------------
    @Test
    @Order(1)
    @Chrome
    @Desktop
    @DisplayName("Chrome Desktop")
    void testChromeDesktop() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager(currentBrowser, headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(2)
    @Chrome
    @Tablet
    @DisplayName("Chrome Tablet")
    void testChromeTablet() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("chrome", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(3)
    @Chrome
    @Mobile
    @DisplayName("Chrome Mobile")
    void testChromeMobile() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("chrome", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(4)
    @Edge
    @Desktop
    @DisplayName("Edge Desktop")
    void testEdgeDesktop() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(5)
    @Edge
    @Tablet
    @DisplayName("Edge Tablet")
    void testEdgeTablet() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(6)
    @Edge
    @Mobile
    @DisplayName("Edge Mobile")
    void testEdgeMobile() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(7)
    @Firefox
    @Desktop
    @DisplayName("Firefox Desktop")
    void testFirefoxDesktop() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(8)
    @Firefox
    @Tablet
    @DisplayName("Firefox Tablet")
    void testFirefoxTablet() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(9)
    @Firefox
    @Mobile
    @DisplayName("Firefox Mobile")
    void testFirefoxMobile() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(10)
    @Safari
    @Desktop
    @DisplayName("Safari Desktop")
    void testSafariDesktop() throws InterruptedException {
        String currentBrowser = "safari";
        isMacOS();
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManagerMac("safari", false, device);
        executeTest(currentBrowser, device, driverManager);
    }


    @Test
    @Order(11)
    @Safari
    @Tablet
    @DisplayName("Safari Tablet")
    void testSafariTablet() throws InterruptedException {
        String currentBrowser = "safari";
        isMacOS();
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManagerMac("safari", false, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(12)
    @Remote
    @Desktop
    @DisplayName("Safari Cloud Desktop")
    @Disabled("No Credentials")
    void testSafariCloudDesktop() throws InterruptedException {
        String currentBrowser = "safari cloud";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new RemoteDriverManager(device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(13)
    @Remote
    @Tablet
    @DisplayName("Safari Cloud Tablet")
    @Disabled("No Credentials")
    void testSafariCloudTablet() throws InterruptedException {
        String currentBrowser = "safari cloud";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new RemoteDriverManager(device);
        executeTest(currentBrowser, device, driverManager);
    }
}

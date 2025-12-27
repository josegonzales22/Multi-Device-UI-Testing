package com.threebrowsers.selenium.tests;

import com.threebrowsers.selenium.drivers.*;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossBrowserSuiteTest extends BaseTest {
    // -------------------------------------------------
    // Tests principales
    // -------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("Chrome Desktop")
    void testChromeDesktop() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager(currentBrowser, headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(2)
    @DisplayName("Chrome Tablet")
    void testChromeTablet() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("chrome", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(3)
    @DisplayName("Chrome Mobile")
    void testChromeMobile() throws InterruptedException {
        String currentBrowser = "chrome";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("chrome", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(4)
    @DisplayName("Edge Desktop")
    void testEdgeDesktop() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(5)
    @DisplayName("Edge Tablet")
    void testEdgeTablet() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(6)
    @DisplayName("Edge Mobile")
    void testEdgeMobile() throws InterruptedException {
        String currentBrowser = "edge";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("edge", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(7)
    @DisplayName("Firefox Desktop")
    void testFirefoxDesktop() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.DESKTOP;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(8)
    @DisplayName("Firefox Tablet")
    void testFirefoxTablet() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(9)
    @DisplayName("Firefox Mobile")
    void testFirefoxMobile() throws InterruptedException {
        String currentBrowser = "firefox";
        DeviceProfile device = DeviceProfile.MOBILE;
        BaseDriver driverManager = new LocalDriverManager("firefox", headlessLocal, device);
        executeTest(currentBrowser, device, driverManager);
    }

    @Test
    @Order(10)
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
    @DisplayName("Safari Cloud Tablet")
    @Disabled("No Credentials")
    void testSafariCloudTablet() throws InterruptedException {
        String currentBrowser = "safari cloud";
        DeviceProfile device = DeviceProfile.TABLET;
        BaseDriver driverManager = new RemoteDriverManager(device);
        executeTest(currentBrowser, device, driverManager);
    }
}

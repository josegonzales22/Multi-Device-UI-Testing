package com.threebrowsers.selenium.tests;

import java.io.File;
import com.threebrowsers.selenium.drivers.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import com.threebrowsers.selenium.steps.StepsFlow;
import com.threebrowsers.selenium.utils.ConfigReader;
import com.threebrowsers.selenium.utils.FileUtil;
import com.threebrowsers.selenium.reports.ExtentReportManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.TestInfo;
import com.aventstack.extentreports.ExtentTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossBrowserSuiteTest extends BaseTest {

    private static ConfigReader localConfig;
    private static ConfigReader remoteConfig;
    private WebDriver driver;
    private String currentBrowser;

    @BeforeAll
    static void loadConfigs() {
        System.out.println("[INFO] Eliminando carpetas reports e images...");
        FileUtil.deleteFolder(new File("reports"));
        FileUtil.deleteFolder(new File("images"));

        localConfig = new ConfigReader("src/main/resources/local.properties");
        remoteConfig = new ConfigReader("src/main/resources/remote.properties");
        extent = ExtentReportManager.createInstance("CrossBrowserSuite");
        System.out.println("[INFO] Carpetas eliminadas y entorno iniciado.");
    }


    @BeforeEach
    void setupTest(TestInfo testInfo) {
        test = extent.createTest(testInfo.getDisplayName());
        System.out.println("[INFO] Iniciando test principal: " + testInfo.getDisplayName());
    }

    @AfterEach
    void cleanup() {
        if (driver != null) {
            driver.quit();
            System.out.println("[INFO] Navegador cerrado: " + currentBrowser);
        }
    }

    @AfterAll
    static void tearDown() {
        ExtentReportManager.closeReport();
        cleanFolder("images");
    }

    // -------------------------------------------------
    // Tests principales
    // -------------------------------------------------
    /**/
    @Test
    @Order(1)
    @DisplayName("Chrome local")
    void testInChrome() throws InterruptedException {
        runLocalTest("chrome");
    }
    /**/

    /**/
    @Test
    @Order(2)
    @DisplayName("Edge local")
    void testInEdge() throws InterruptedException {
        runLocalTest("edge");
    }
    /**/

    /**/
    @Test
    @Order(3)
    @DisplayName("Firefox local")
    void testInFirefox() throws InterruptedException {
        runLocalTest("firefox");
    }
    /**/

    /*/
    @Test
    @Order(4)
    @DisplayName("Safari local")
    void testInSafari() throws InterruptedException {
        runLocalTest("safari");
    }
    /**/
    
    /*/
    @Test
    @Order(5)
    @DisplayName("Safari remoto (VM)")
    void testInSafariVM() throws InterruptedException {
        runRemoteTest();
    }
    /**/

    // -------------------------------------------------
    // Métodos de ejecución
    // -------------------------------------------------
    private void runLocalTest(String browser) throws InterruptedException {
        boolean headless = Boolean.parseBoolean(localConfig.getOrDefault("headless", "false"));
        String baseUrl = localConfig.get("base.url");

        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");

        // Manejo de Safari: solo ejecuta en macOS
        if (browser.equalsIgnoreCase("safari") && !isMac) {
            System.out.println("[INFO] Safari solo se ejecuta en macOS. Test omitido.");
            assumeTrue(isMac, "Safari solo se ejecuta en macOS");
            return;
        }

        for (DeviceProfile device : DeviceProfile.values()) {
            currentBrowser = browser + "_" + device.name().toLowerCase();
            System.out.println("\n[INFO] Ejecutando en " + currentBrowser.toUpperCase());

            ExtentTest deviceTest = test.createNode("[" + device.name() + "] " + browser.toUpperCase());
            deviceTest.info("Iniciando pruebas en " + device.name());

            BaseDriver driverManager;

            if (browser.equalsIgnoreCase("safari")) {
                driverManager = new LocalDriverManagerMac(browser, headless, device);
            } else {
                driverManager = new LocalDriverManager(browser, headless, device);
            }

            try {
                driver = driverManager.createDriver();
                StepsFlow steps = new StepsFlow(driver, currentBrowser, deviceTest);
                steps.executeFlow(baseUrl);
            } catch (Exception e) {
                deviceTest.fail("[ERROR] Error en " + currentBrowser.toUpperCase() + ": " + e.getMessage());
                System.err.println("[ERROR] " + e.getMessage());
            } finally {
                if (driver != null) {
                    driver.quit();
                    System.out.println("[INFO] Finalizó ejecución en " + currentBrowser.toUpperCase());
                }
            }
        }
    }

    private void runRemoteTest() throws InterruptedException {
        currentBrowser = "safari_vm";
        BaseDriver driverManager = new RemoteDriverManager();

        try {
            driver = driverManager.createDriver();
            String baseUrl = remoteConfig.get("base.url");

            ExtentTest safariTest = test.createNode("[REMOTE] Safari VM");
            StepsFlow steps = new StepsFlow(driver, currentBrowser, safariTest);
            steps.executeFlow(baseUrl);
        } catch (Exception e) {
            test.fail("[ERROR] Error en Safari remoto (VM): " + e.getMessage());
            throw e;
        } finally {
            if (driver != null) driver.quit();
        }
    }
}

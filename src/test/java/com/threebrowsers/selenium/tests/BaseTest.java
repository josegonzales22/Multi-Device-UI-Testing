package com.threebrowsers.selenium.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.threebrowsers.selenium.drivers.BaseDriver;
import com.threebrowsers.selenium.drivers.DeviceProfile;
import com.threebrowsers.selenium.reports.ExtentReportManager;
import com.threebrowsers.selenium.steps.StepsFlow;
import com.threebrowsers.selenium.utils.ConfigReader;
import com.threebrowsers.selenium.utils.FileUtil;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected ExtentReports extent;
    protected ConfigReader localConfig;
    protected ConfigReader remoteConfig;

    protected ExtentTest test;
    protected WebDriver driver;

    protected Boolean headlessLocal;
    protected Boolean headlessRemote;

    protected String baseUrlLocal;
    protected String baseUrlRemote;

    private Map<String, ExtentTest> browserGroups = new HashMap<>();

    @BeforeAll
    void globalSetup() {
        System.out.println("[INFO] Limpiando carpetas y cargando configuraciones...");

        FileUtil.deleteFolder(new File("reports"));
        FileUtil.deleteFolder(new File("images"));

        localConfig = new ConfigReader("src/main/resources/local.properties");
        remoteConfig = new ConfigReader("src/main/resources/remote.properties");

        extent = ExtentReportManager.createInstance("CrossBrowserSuite");

        headlessLocal = Boolean.parseBoolean(localConfig.getOrDefault("headless", "false"));
        headlessRemote = Boolean.parseBoolean(remoteConfig.getOrDefault("headless", "false"));

        baseUrlLocal = localConfig.get("base.url");
        baseUrlRemote = remoteConfig.get("base.url");

        System.out.println("[INFO] Configuración global completada.");
    }

    @BeforeEach
    void setupTest(TestInfo info) {
        if (info.getTestMethod().isPresent() &&
                info.getTestMethod().get().isAnnotationPresent(Disabled.class)) {
            System.out.println("[INFO] Test deshabilitado: " + info.getDisplayName());
        }
    }

    @AfterEach
    void tearDownTest() {
        if (driver != null) {
            driver.quit();
            System.out.println("[INFO] Driver cerrado.");
        }
    }

    @AfterAll
    void globalTearDown() {
        ExtentReportManager.closeReport();
    }

    void isMacOS() {
        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        assumeTrue(isMac, "Safari solo se ejecuta en macOS");
    }

    private ExtentTest getOrCreateBrowserGroup(String browserKey) {
        return browserGroups.computeIfAbsent(browserKey,
                key -> extent.createTest(key.toUpperCase()));
    }

    void executeTest(String currentBrowser, DeviceProfile device, BaseDriver driverManager) {
        try {
            // Crear el grupo SOLO SI SE EJECUTA EL TEST
            ExtentTest browserGroup = getOrCreateBrowserGroup(currentBrowser.toLowerCase());

            // Crear nodo del test
            test = browserGroup.createNode("[" + device.name() + "] " + currentBrowser.toUpperCase());

            driver = driverManager.createDriver();

            StepsFlow steps = new StepsFlow(
                    driver,
                    currentBrowser.toUpperCase() + "_" + device.name().toUpperCase(),
                    test
            );

            steps.executeFlow(baseUrlLocal);

        } catch (Exception e) {
            test.fail("[ERROR] " + e.getMessage());
            System.err.println("[ERROR] " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("[INFO] Finalizó ejecución en " + currentBrowser.toUpperCase());
            }
        }
    }
}

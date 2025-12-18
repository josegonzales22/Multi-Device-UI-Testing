package com.threebrowsers.selenium.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class LocalDriverManagerMac extends LocalDriverManager {

    private final DeviceProfile device;

    public LocalDriverManagerMac(String browser, boolean headless, DeviceProfile device) {
        super(browser, headless, device);
        this.device = device;
    }

    @Override
    public WebDriver createDriver() {
        if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
            throw new IllegalStateException("[ERROR] Este driver solo es válido en macOS.");
        }

        if (super.headless) {
            System.err.println("[WARN] Safari NO soporta headless. Ignorando parámetro.");
        }

        SafariOptions safariOptions = new SafariOptions();
        WebDriver driver = new SafariDriver(safariOptions);

        System.out.println("[INFO] SafariDriver iniciado en macOS.");

        // Aplicar resolución si se pasa DeviceProfile
        if (device != null) {
            try {
                String[] res = device.getResolution().split(",");
                int width = Integer.parseInt(res[0]);
                int height = Integer.parseInt(res[1]);
                driver.manage().window().setSize(new Dimension(width, height));
                System.out.println("[INFO] Resolución aplicada a Safari: " + device.getResolution());
            } catch (Exception e) {
                System.err.println("[WARN] No se pudo aplicar resolución en Safari: " + e.getMessage());
            }
        }

        setupDriver(driver);
        return driver;
    }
}

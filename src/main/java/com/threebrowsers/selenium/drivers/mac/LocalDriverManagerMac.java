package com.threebrowsers.selenium.drivers.mac;

import com.threebrowsers.selenium.drivers.DeviceProfile;
import com.threebrowsers.selenium.drivers.LocalDriverManager;
import com.threebrowsers.selenium.utils.Logs;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
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
            Logs.warning("SafariDriver is only valid on macOS.");
        }

        if (super.headless) {
            Logs.warning("Safari does not support headless browsing. Ignoring parameter.");
        }

        SafariOptions safariOptions = new SafariOptions();
        WebDriver driver = new SafariDriver(safariOptions);

        Logs.info("SafariDriver launched on macOS.");

        // Aplicar resolución si se pasa DeviceProfile
        if (device != null) {
            try {
                String[] res = device.getResolution().split(",");
                int width = Integer.parseInt(res[0]);
                int height = Integer.parseInt(res[1]);
                driver.manage().window().setSize(new Dimension(width, height));
                Logs.info("Resolution applied to Safari: " + device.getResolution());
            } catch (Exception e) {
                Logs.error("Resolution could not be applied in Safari: " + e.getMessage());
            }
        }

        setupDriver(driver);
        return driver;
    }
}

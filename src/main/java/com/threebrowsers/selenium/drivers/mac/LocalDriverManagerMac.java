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

    public LocalDriverManagerMac(String browser, boolean headless) {
        this(browser, headless, DeviceProfile.DESKTOP);
    }

    public LocalDriverManagerMac(String browser, boolean headless, DeviceProfile device) {
        super(browser, headless, device);
        this.device = device;
    }

    @Override
    public WebDriver createDriver() {
        if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
            Logs.warning("SafariDriver is only valid on macOS. Current OS: " + System.getProperty("os.name"));
        }

        if (super.headless) {
            Logs.warning("Safari does not support headless browsing native flags. Ignoring headless parameter...");
        }

        SafariOptions safariOptions = new SafariOptions();
        WebDriver localDriver = new SafariDriver(safariOptions);

        Logs.info("SafariDriver launched on macOS using profile: " + (device != null ? device.name() : "NONE"));

        if (device != null) {
            try {
                if (device == DeviceProfile.DESKTOP) {
                    localDriver.manage().window().maximize();
                } else {
                    localDriver.manage().window().setSize(new Dimension(device.getWidth(), device.getHeight()));
                    Logs.info("Resolution applied to Safari: " + device.getResolutionString());
                }
            } catch (Exception e) {
                Logs.error("Resolution could not be applied in Safari: " + e.getMessage());
            }
        }

        setupDriver(localDriver);
        return localDriver;
    }
}
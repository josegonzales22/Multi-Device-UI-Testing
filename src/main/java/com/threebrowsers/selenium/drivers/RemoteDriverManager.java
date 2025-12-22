package com.threebrowsers.selenium.drivers;

import com.threebrowsers.selenium.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class RemoteDriverManager extends BaseDriver {

    private final DeviceProfile device;

    public RemoteDriverManager() {
        this.device = DeviceProfile.DESKTOP;
    }

    public RemoteDriverManager(DeviceProfile device) {
        this.device = device;
    }

    @Override
    public WebDriver createDriver() {
        ConfigReader config = new ConfigReader("src/main/resources/remote.properties");

        String remoteUrl = config.get("remote.url");
        String browser = config.getOrDefault("browser", "chrome");
        String user = config.getOrDefault("remote.user", "");
        String key = config.getOrDefault("remote.key", "");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);

        try {
            // ==================================================
            // 🔹 LAMBDATEST
            // ==================================================
            if (remoteUrl.contains("lambdatest")) {

                caps.setCapability(
                        "browserVersion",
                        config.getOrDefault("browser.version", "latest")
                );

                caps.setCapability(
                        "platformName",
                        config.getOrDefault("platform.name", "macOS Ventura")
                );

                Map<String, Object> ltOptions = new HashMap<>();
                ltOptions.put("user", user);
                ltOptions.put("accessKey", key);
                ltOptions.put("project", config.getOrDefault("project.name", "LambdaTest Project"));
                ltOptions.put("build", config.getOrDefault("build.name", "Build 1"));
                ltOptions.put("name", config.getOrDefault("test.name", "Remote Test"));

                String lambdaResolution = normalizeResolutionForLambda(device.getResolution());
                ltOptions.put("resolution", lambdaResolution);
                ltOptions.put("userAgent", device.getUserAgent());

                // Logs
                ltOptions.put("video", true);
                ltOptions.put("console", true);
                ltOptions.put("network", true);

                caps.setCapability("LT:Options", ltOptions);

                System.out.println(
                        "[INFO] LambdaTest → " + browser +
                        " | " + device.name() +
                        " | " + device.getResolution()
                );
            }
            // ==================================================
            // 🔹 BROWSERSTACK
            // ==================================================
            else if (remoteUrl.contains("browserstack")) {

                Map<String, Object> bsOptions = new HashMap<>();
                bsOptions.put("userName", user);
                bsOptions.put("accessKey", key);
                bsOptions.put("projectName", config.getOrDefault("project.name", "BrowserStack Demo"));
                bsOptions.put("resolution", device.getResolution());
                bsOptions.put("userAgent", device.getUserAgent());

                caps.setCapability("bstack:options", bsOptions);
            }
            // ==================================================
            // 🔹 GRID LOCAL / VM
            // ==================================================
            else {
                System.out.println("[INFO] Ejecutando en Grid local o VM: " + remoteUrl);
            }

            driver = new RemoteWebDriver(new URL(remoteUrl), caps);

        } catch (MalformedURLException e) {
            throw new RuntimeException("URL remota inválida: " + remoteUrl, e);
        }

        setupDriver(driver);
        return driver;
    }
    
    private String normalizeResolutionForLambda(String resolution) {
        if (resolution == null) return null;
        return resolution.replace(",", "x");
    }
}

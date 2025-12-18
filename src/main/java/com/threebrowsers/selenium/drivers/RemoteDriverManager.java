package com.threebrowsers.selenium.drivers;

import com.threebrowsers.selenium.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class RemoteDriverManager extends BaseDriver {

    private final DeviceProfile device;

    public RemoteDriverManager() {
        this.device = DeviceProfile.DESKTOP; // valor por defecto
    }

    public RemoteDriverManager(DeviceProfile device) {
        this.device = device;
    }

    @Override
    public WebDriver createDriver() {
        ConfigReader config = new ConfigReader("src/main/resources/remote.properties");

        String remoteUrl = config.get("remote.url"); // obligatorio
        String browser = config.getOrDefault("browser", "chrome");
        String user = config.getOrDefault("remote.user", "");
        String key = config.getOrDefault("remote.key", "");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);

        try {
            if (!user.isEmpty() && !key.isEmpty()) {
                // Configuración para servicios como BrowserStack o SauceLabs
                Map<String, Object> serviceOptions = new HashMap<>();
                serviceOptions.put("userName", user);
                serviceOptions.put("accessKey", key);
                serviceOptions.put("projectName", config.getOrDefault("project.name", "Selenium Tests"));
                serviceOptions.put("os", config.getOrDefault("os", "ANY"));
                serviceOptions.put("osVersion", config.getOrDefault("os.version", ""));

                // Detectar tipo de dispositivo
                switch (device) {
                    case MOBILE -> {
                        serviceOptions.put("deviceName", config.getOrDefault("device.mobile", "iPhone 14"));
                        serviceOptions.put("realMobile", true);
                        serviceOptions.put("browserName", browser);
                        System.out.println("[INFO] Ejecutando en dispositivo móvil remoto: " + serviceOptions.get("deviceName"));
                    }
                    case TABLET -> {
                        serviceOptions.put("deviceName", config.getOrDefault("device.tablet", "iPad 10th"));
                        serviceOptions.put("realMobile", true);
                        serviceOptions.put("browserName", browser);
                        System.out.println("[INFO] Ejecutando en tablet remota: " + serviceOptions.get("deviceName"));
                    }
                    case DESKTOP -> {
                        // Desktop (usar sistema operativo remoto configurado)
                        serviceOptions.put("resolution", config.getOrDefault("resolution", "1920x1080"));
                        System.out.println("[INFO] Ejecutando en desktop remoto.");
                    }
                }

                caps.setCapability("bstack:options", serviceOptions);
            } else {
                // Selenium Grid local o VM
                System.out.println("[INFO] Ejecutando en Grid local o VM: " + remoteUrl);
            }

            driver = new RemoteWebDriver(new URL(remoteUrl), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL remota inválida: " + remoteUrl, e);
        }

        setupDriver(driver);
        return driver;
    }
}

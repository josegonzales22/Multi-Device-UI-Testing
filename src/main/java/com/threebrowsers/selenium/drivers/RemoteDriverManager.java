package com.threebrowsers.selenium.drivers;

import com.threebrowsers.selenium.utils.ConfigReader;
import com.threebrowsers.selenium.utils.Logs;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
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
    @SuppressWarnings("unchecked")
    public WebDriver createDriver() {
        WebDriver localDriver;
        ConfigReader config = new ConfigReader("src/main/resources/remote.properties");

        Map<String, Object> rawCapabilitiesMap = new HashMap<>();

        for (String key : config.getProperties().stringPropertyNames()) {
            if (key.startsWith("capabilities.")) {
                String value = config.get(key);
                String fullPath = key.substring("capabilities.".length());

                buildDynamicCapabilities(rawCapabilitiesMap, fullPath, convertValue(value));
            }
        }

        injectDeviceProfileIfApplicable(rawCapabilitiesMap);

        MutableCapabilities caps = new MutableCapabilities();
        rawCapabilitiesMap.forEach(caps::setCapability);

        String remoteUrl = config.get("remote.url");

        try {
            if (remoteUrl != null && !remoteUrl.trim().isEmpty()) {
                localDriver = new RemoteWebDriver(new URL(remoteUrl), caps);
            } else {
                localDriver = new RemoteWebDriver(caps);
            }
        } catch (Exception e) {
            throw new RuntimeException("[CI/CD REMOTE DRIVER ERROR] Could not initialize remote session: " + e.getMessage(), e);
        }

        if (device == DeviceProfile.DESKTOP) {
            Logs.info("Desktop profile detected. Maximizing remote window...");
            localDriver.manage().window().maximize();
        } else {
            Logs.info("Mobile/Tablet profile detected. Setting window dimensions to: " + device.getResolutionString());
            localDriver.manage().window().setSize(new org.openqa.selenium.Dimension(device.getWidth(), device.getHeight()));
        }

        setupDriver(localDriver);
        return localDriver;
    }

    @SuppressWarnings("unchecked")
    private void buildDynamicCapabilities(Map<String, Object> currentMap, String path, Object value) {
        String normalizedPath = path.replaceAll("(?i)_options", ":options");
        String[] parts = normalizedPath.split("\\.");

        if (parts.length == 1) {
            currentMap.put(parts[0], value);
            return;
        }

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            currentMap = (Map<String, Object>) currentMap.computeIfAbsent(part, k -> new HashMap<String, Object>());
        }

        currentMap.put(parts[parts.length - 1], value);
    }

    @SuppressWarnings("unchecked")
    private void injectDeviceProfileIfApplicable(Map<String, Object> capsMap) {
        if (device == null) return;

        for (Map.Entry<String, Object> entry : capsMap.entrySet()) {
            String capName = entry.getKey();
            Object capValue = entry.getValue();

            if (capValue instanceof Map) {
                Map<String, Object> providerOptions = (Map<String, Object>) capValue;

                String currentRes = device.getResolutionString();
                String res = capName.toUpperCase().contains("LT") && currentRes != null
                        ? currentRes.replace(",", "x")
                        : currentRes;

                providerOptions.putIfAbsent("resolution", res);
                providerOptions.putIfAbsent("userAgent", device.getUserAgent());
            }
        }
    }

    private Object convertValue(String value) {
        if (value == null) return null;
        if (value.equalsIgnoreCase("true")) return true;
        if (value.equalsIgnoreCase("false")) return false;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}
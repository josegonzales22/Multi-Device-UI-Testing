package com.threebrowsers.selenium.drivers;

import com.threebrowsers.selenium.utils.Logs;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LocalDriverManager extends BaseDriver {

    private final String browser;
    protected final boolean headless;
    private final DeviceProfile device;

    public LocalDriverManager(String browser, boolean headless, DeviceProfile device) {
        this.browser = browser.toLowerCase();
        this.headless = headless;
        this.device = device;
    }

    @Override
    public WebDriver createDriver() {
        switch (browser) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                java.util.Map<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");

                if (device != null) {
                    if (device.name().equalsIgnoreCase("DESKTOP")) {
                        chromeOptions.addArguments("--start-maximized");
                    } else {
                        chromeOptions.addArguments("--user-agent=" + device.getUserAgent());
                        chromeOptions.addArguments("--window-size=" + device.getResolution().replace("x", ","));
                    }
                }

                if (headless) chromeOptions.addArguments("--headless=new", "--disable-gpu");

                driver = new ChromeDriver(chromeOptions);
                Logs.info("Chrome lanzado con perfil: " + device.name());
            }

            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                java.util.Map<String, Object> edgePrefs = new java.util.HashMap<>();
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                edgePrefs.put("profile.password_manager_leak_detection", false);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);

                if (device != null) {
                    if (device.name().equalsIgnoreCase("DESKTOP")) {
                        edgeOptions.addArguments("--start-maximized");
                    } else {
                        edgeOptions.addArguments("--user-agent=" + device.getUserAgent());
                        edgeOptions.addArguments("--window-size=" + device.getResolution().replace("x", ","));
                    }
                }

                if (headless) edgeOptions.addArguments("--headless=new", "--disable-gpu");

                driver = new EdgeDriver(edgeOptions);
                Logs.info("Edge lanzado con perfil: " + device.name());
            }

            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("signon.rememberSignons", false);
                firefoxOptions.addPreference("signon.autofillForms", false);
                firefoxOptions.addPreference("signon.management.page.breach-alerts.enabled", false);
                firefoxOptions.addPreference("profile.password_manager_leak_detection", false);

                if (device != null) {
                    firefoxOptions.addPreference("general.useragent.override", device.getUserAgent());
                    if (headless) {
                        String[] res = device.getResolution().split("x");
                        firefoxOptions.addArguments("--width=" + res[0], "--height=" + res[1]);
                    }
                }

                if (headless) firefoxOptions.addArguments("--headless");

                driver = new FirefoxDriver(firefoxOptions);

                if (device != null && !headless) {
                    if (device.name().equalsIgnoreCase("DESKTOP")) {
                        driver.manage().window().maximize();
                    } else {
                        String[] res = device.getResolution().split("x");
                        driver.manage().window().setSize(new Dimension(Integer.parseInt(res[0]), Integer.parseInt(res[1])));
                    }
                }
                Logs.info("Firefox lanzado con perfil: " + device.name());
            }
            default -> throw new IllegalArgumentException("[ERROR] Navegador no soportado: " + browser);
        }

        setupDriver(driver);
        return driver;
    }
}
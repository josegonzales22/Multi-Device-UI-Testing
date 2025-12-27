package com.threebrowsers.selenium.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URL;

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
        String os = System.getProperty("os.name").toLowerCase();
        boolean isMac = os.contains("mac");
        switch (browser) {
            case "chrome" -> {
                if (isMac) {
                    WebDriverManager.chromedriver()
                            .setup();
                } else {
                    WebDriverManager.chromedriver()
                            .browserVersion("latest")
                            .setup();
                }
                ChromeOptions chromeOptions = new ChromeOptions();
                java.util.Map<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");

                if (device != null) {
                    chromeOptions.addArguments("--user-agent=" + device.getUserAgent());
                    String resolution = device.getResolution().replace("x", ",");
                    chromeOptions.addArguments("--window-size=" + resolution);
                }

                if (headless) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu");
                }

                driver = new ChromeDriver(chromeOptions);
                System.out.println("[INFO] Chrome lanzado con perfil: " + device.name());
            }

            case "edge" -> {
                try {
                    URL driverUrl = new URL("https://msedgedriver.microsoft.com/");
                    if (isMac) {
                        WebDriverManager.edgedriver()
                                .driverRepositoryUrl(driverUrl)
                                .setup();
                    } else {
                        WebDriverManager.edgedriver()
                                .browserVersion("latest")
                                .driverRepositoryUrl(driverUrl)
                                .setup();
                    }
                } catch (MalformedURLException e) {
                    throw new RuntimeException("[ERROR] URL mal formada para el repositorio del Edge driver.", e);
                }

                EdgeOptions edgeOptions = new EdgeOptions();
                java.util.Map<String, Object> edgePrefs = new java.util.HashMap<>();
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                edgePrefs.put("profile.password_manager_leak_detection", false);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);

                if (device != null) {
                    edgeOptions.addArguments("--user-agent=" + device.getUserAgent());
                    String resolution = device.getResolution().replace("x", ",");
                    edgeOptions.addArguments("--window-size=" + resolution);
                }

                if (headless) {
                    edgeOptions.addArguments("--headless=new", "--disable-gpu");
                }

                driver = new EdgeDriver(edgeOptions);
                System.out.println("[INFO] Edge lanzado con perfil: " + device.name());
            }

            case "firefox" -> {
                if (isMac) {
                    WebDriverManager.firefoxdriver().setup();
                } else {
                    WebDriverManager.firefoxdriver().browserVersion("latest").setup();
                }
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("signon.rememberSignons", false);
                firefoxOptions.addPreference("signon.autofillForms", false);
                firefoxOptions.addPreference("signon.management.page.breach-alerts.enabled", false);
                firefoxOptions.addPreference("profile.password_manager_leak_detection", false);

                if (device != null) {
                    firefoxOptions.addPreference("general.useragent.override", device.getUserAgent());

                    if (headless) {
                        try {
                            String[] resolution = device.getResolution().split("x");
                            if (resolution.length == 2) {
                                firefoxOptions.addArguments("--width=" + resolution[0]);
                                firefoxOptions.addArguments("--height=" + resolution[1]);
                            } else {
                                System.err.println("[WARN] Resolución inválida para " + device.name() +
                                        ": " + device.getResolution());
                            }
                        } catch (Exception e) {
                            System.err.println("[WARN] No se pudo aplicar resolución headless en Firefox: " + e.getMessage());
                        }
                    }
                }

                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(firefoxOptions);

                if (device != null && !headless) {
                    try {
                        String[] resolution = device.getResolution().split("x");
                        int width = Integer.parseInt(resolution[0]);
                        int height = Integer.parseInt(resolution[1]);
                        driver.manage().window().setSize(new Dimension(width, height));
                        System.out.println("[INFO] Resolución aplicada a Firefox: " + device.getResolution());
                    } catch (Exception e) {
                        System.err.println("[WARN] No se pudo aplicar la resolución para Firefox: " + e.getMessage());
                    }
                }

                System.out.println("[INFO] Firefox lanzado con perfil: " + device.name());
            }
            case "safari" -> {
                throw new IllegalStateException("[ERROR] Safari solo está disponible en macOS.");
            }
            default -> throw new IllegalArgumentException("[ERROR] Navegador no soportado: " + browser);
        }

        setupDriver(driver);
        return driver;
    }
}

package com.threebrowsers.selenium.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Valida si los elementos visibles están dentro del viewport actual.
 * Calcula el % basado en el área total visible frente al área fuera del viewport.
 * Permite margen y tolerancia configurables. Marca FAIL si supera el límite, pero continúa el flujo.
 */
public class ResponsiveValidator {

    private static final AtomicInteger screenshotCounter = new AtomicInteger(1);

    // 🔧 margen permitido de desborde (px)
    private static final int MARGIN_TOLERANCE = 8;

    // 🔧 porcentaje máximo permitido de área fuera del viewport (ej. 5 = 5%)
    private static final double PERCENT_TOLERANCE = 5.0;

    // ⏱️ tiempo para que se rendericen los bordes antes del screenshot
    private static final int RENDER_DELAY_MS = 200;

    public static boolean validateViewportIntegrity(WebDriver driver, String browserName, ExtentTest test, String stepName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> highlightedElements = new ArrayList<>();

        try {
            Long viewportWidth = ((Number) js.executeScript("return window.innerWidth;")).longValue();
            Long viewportHeight = ((Number) js.executeScript("return window.innerHeight;")).longValue();
            Long scrollY = ((Number) js.executeScript("return window.scrollY;")).longValue();
            Long scrollX = ((Number) js.executeScript("return window.scrollX;")).longValue();

            List<WebElement> allElements = driver.findElements(By.xpath("//*"));
            double totalVisibleArea = 0.0;
            double totalOutOfBoundsArea = 0.0;

            String highlightScript =
                    "try {" +
                    "arguments[0].setAttribute('data-prev-style', arguments[0].getAttribute('style') || '');" +
                    "arguments[0].style.outline='3px solid red';" +
                    "arguments[0].style.outlineOffset='2px';" +
                    "} catch(e) {}";

            for (WebElement el : allElements) {
                try {
                    if (!el.isDisplayed()) continue;

                    String visibility = el.getCssValue("visibility");
                    String display = el.getCssValue("display");
                    if ("hidden".equals(visibility) || "none".equals(display)) continue;

                    Dimension size = el.getSize();
                    if (size.getWidth() == 0 || size.getHeight() == 0) continue;

                    Point loc = el.getLocation();
                    int scrollXInt = scrollX != null ? scrollX.intValue() : 0;
                    int scrollYInt = scrollY != null ? scrollY.intValue() : 0;
                    int viewportWidthInt = viewportWidth != null ? viewportWidth.intValue() : 0;
                    int viewportHeightInt = viewportHeight != null ? viewportHeight.intValue() : 0;

                    int left = loc.getX() - scrollXInt;
                    int top = loc.getY() - scrollYInt;
                    int right = left + size.getWidth();
                    int bottom = top + size.getHeight();

                    int width = size.getWidth();
                    int height = size.getHeight();
                    double elementArea = width * height;
                    totalVisibleArea += elementArea;

                    // Calcular área fuera del viewport (si existe)
                    int overflowLeft = Math.max(0, -left - MARGIN_TOLERANCE);
                    int overflowTop = Math.max(0, -top - MARGIN_TOLERANCE);
                    int overflowRight = Math.max(0, right - (viewportWidthInt + MARGIN_TOLERANCE));
                    int overflowBottom = Math.max(0, bottom - (viewportHeightInt + MARGIN_TOLERANCE));

                    boolean outOfBounds = (overflowLeft > 0 || overflowTop > 0 || overflowRight > 0 || overflowBottom > 0);

                    if (outOfBounds) {
                        js.executeScript(highlightScript, el);
                        highlightedElements.add(el);

                        // Estimación de área fuera del viewport (rectangular, aproximada)
                        int overflowWidth = overflowLeft + overflowRight;
                        int overflowHeight = overflowTop + overflowBottom;
                        double overflowArea = Math.min(elementArea, (overflowWidth > 0 ? overflowWidth * height : 0) +
                                (overflowHeight > 0 ? overflowHeight * width : 0));
                        totalOutOfBoundsArea += Math.max(1, overflowArea);
                    }

                } catch (StaleElementReferenceException ignored) {
                } catch (Exception ignored) {
                }
            }

            double percentOut = totalVisibleArea > 0
                    ? (totalOutOfBoundsArea * 100.0 / totalVisibleArea)
                    : 0.0;

            try { Thread.sleep(RENDER_DELAY_MS); } catch (InterruptedException ignored) {}

            String screenshotPath = takeScreenshot(driver, browserName);
            String fileName = new File(screenshotPath).getName();

            if (percentOut == 0) {
                test.log(Status.PASS,
                        String.format("✅ [%s] Página totalmente responsive en %s", stepName, browserName));
                return true;
            }

            if (percentOut <= PERCENT_TOLERANCE) {
                test.log(Status.PASS,
                        String.format("✅ [%s] Responsive dentro del límite (%.2f%% área fuera, tolerancia %.2f%%) - %s",
                                stepName, percentOut, PERCENT_TOLERANCE, fileName))
                    .addScreenCaptureFromPath(screenshotPath);
                return true;
            }

            test.log(Status.FAIL,
                    String.format("❌ [%s] No responsive: %.2f%% del área fuera del viewport (tolerancia %.2f%%) - %s",
                            stepName, percentOut, PERCENT_TOLERANCE, fileName))
                .addScreenCaptureFromPath(screenshotPath);
            return false;

        } catch (Exception e) {
            test.log(Status.WARNING, "⚠️ Error validando responsive (" + stepName + "): " + e.getMessage());
            return false;

        } finally {
            // 🔄 Restaurar estilos originales
            String rollbackScript =
                    "try {" +
                    "var prev = arguments[0].getAttribute('data-prev-style');" +
                    "if (prev !== null) { arguments[0].setAttribute('style', prev); }" +
                    "else { arguments[0].removeAttribute('style'); }" +
                    "arguments[0].removeAttribute('data-prev-style');" +
                    "} catch(e) {}";

            for (WebElement el : highlightedElements) {
                try {
                    js.executeScript(rollbackScript, el);
                } catch (Exception ignored) {}
            }
        }
    }

    private static String takeScreenshot(WebDriver driver, String browserName) {
        try {
            File baseDir = new File("reports/images");
            if (!baseDir.exists()) baseDir.mkdir();

            File browserDir = new File(baseDir, browserName);
            if (!browserDir.exists()) browserDir.mkdir();

            int number = screenshotCounter.getAndIncrement();
            String filename = String.format("responsive_check_%02d.png", number);

            File screenshotFile = new File(browserDir, filename);
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, screenshotFile);

            return screenshotFile.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }
}

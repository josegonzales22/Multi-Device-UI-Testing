package com.threebrowsers.selenium.steps;

import com.aventstack.extentreports.ExtentTest;
import com.threebrowsers.selenium.images.ScreenshotUtil;
import com.threebrowsers.selenium.pages.LoginPage;
import com.threebrowsers.selenium.pages.NavigatePage;
import org.openqa.selenium.WebDriver;

public class StepsFlow {

    private final WebDriver driver;
    private final String browserName;
    private final LoginPage loginPage;
    private final NavigatePage navigatePage;
    private final ExtentTest test;

    public StepsFlow(WebDriver driver, String browserName, ExtentTest test) {
        this.driver = driver;
        this.loginPage = new LoginPage(driver);
        this.navigatePage = new NavigatePage(driver);
        this.browserName = browserName;
        this.test = test;
    }

    public void executeFlow(String baseUrl) throws InterruptedException {
        String username = "zoaib@zoaibkhan.com";
        String psw = "testing123";
        ScreenshotUtil.resetCounter(browserName);

        test.info("Cargando página de login");
        loginPage.loadPage(baseUrl);
        Thread.sleep(1000);
        captureStep("loginPage_loaded", "Página de login cargada");

        test.info("Ingresando nombre de usuario");
        loginPage.enterUsername(username);
        Thread.sleep(1000);
        captureStep("username_entered", "Nombre de usuario ingresado");

        test.info("Ingresando contraseña");
        loginPage.enterPassword(psw);
        Thread.sleep(1000);
        captureStep("username_entered", "Nombre de usuario ingresado");

        test.info("Haciendo clic en login");
        loginPage.clickLogin();
        Thread.sleep(2000);
        captureStep("login_clicked", "Botón login clickeado");

        test.info("Cerrando menu");
        navigatePage.clickMenu();
        Thread.sleep(2000);
        captureStep("menu_closed", "Botón menú cerrado");

        test.info("Ingresando a página de componentes");
        navigatePage.goToComponentsPage();
        Thread.sleep(2000);
        captureStep("components_page", "Pantalla de componentes");

        test.info("Ingresando a página de formulario");
        navigatePage.goToFormsPage();
        Thread.sleep(2000);
        captureStep("forms_page", "Pantalla de formulario");

        test.info("Ingresando a página de contenido");
        navigatePage.goToContentPage();
        Thread.sleep(2000);
        captureStep("content_page", "Pantalla de contenido");

        test.pass("Flujo completado exitosamente en " + browserName.toUpperCase());
    }

    private void captureStep(String stepName, String description) {
        String path = ScreenshotUtil.takeScreenshot(driver, browserName, stepName);
        if (path != null) {
            try {
                test.addScreenCaptureFromPath(path, description);
            } catch (Exception e) {
                test.warning("No se pudo agregar la imagen al reporte: " + e.getMessage());
            }
        }
    }
}

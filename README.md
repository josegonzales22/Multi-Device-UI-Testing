# 📱💻 Multi-Device UI Testing – Automation Template

Framework avanzado para **pruebas funcionales automatizadas** con ejecución **multinavegador** y **multi-dispositivo**,
permitiendo validar un mismo flujo en tres tamaños de interfaz por navegador: **Desktop**, **Tablet** y **Mobile**.  
Incluye soporte para ejecución **local y remota**, así como generación automática de **reportes HTML** con dashboard.

Este proyecto está construido sobre una arquitectura sólida utilizando **Selenium WebDriver + JUnit 5 + Maven**,
soportando ejecución en:

- **Chrome**
- **Edge**
- **Firefox**
- **Safari** *(solo local en macOS o remoto vía RemoteWebDriver)*
- **Remote WebDriver** (Selenium Grid, Selenoid, BrowserStack, LambdaTest, etc.)

## 📦 Características Principales

- ✔️ Ejecución multinavegador + multi-dispositivo (Desktop, Tablet, Mobile)
- ✔️ Tres tamaños de ventana predefinidos para pruebas UI responsivas
- ✔️ Flujo funcional unificado para todos los navegadores y dispositivos
- ✔️ Ejecuta todo con un solo comando: `mvn clean test`
- ✔️ Manejo automático de drivers con **WebDriverManager**
- ✔️ Reportes HTML avanzados con **ExtentReports 5**
- ✔️ Arquitectura modular, escalable y fácil de extender
- ✔️ Soporte completo para **RemoteWebDriver**
- ✔️ Totalmente compatible con CI/CD (Windows, Linux, macOS)
- ✔️ Perfiles Maven que habilitan Safari únicamente en macOS

## 🧩 Tecnologías Usadas

| Tecnología         | Versión | Uso                             |
|--------------------|---------|---------------------------------|
| Selenium WebDriver | 4.25.0  | Automatización web              |
| JUnit Jupiter      | 5.11.0  | Estructura y ejecución de tests |
| WebDriverManager   | 5.9.2   | Gestión automática de drivers   |
| ExtentReports      | 5.1.1   | Reportes HTML                   |
| Jackson Databind   | 2.18.0  | Lectura de configuración        |
| Apache Commons IO  | 2.16.1  | Utilidades de archivos          |
| Maven              | –       | Build y gestión de dependencias |

## ▶️ Ejecución del Proyecto

### 🔹 Comando principal

```bash
mvn clean test
```

### 🔹 Comando por grupos

Por ejemplo:

```bash
mvn clean test -Dgroups="desktop"
```

### 🔹 Estructura de ejecución multi-dispositivo

El test suite ejecuta cada navegador en los siguientes perfiles:

- 🖥️ **Desktop**
- 📱 **Tablet**
- 📳 **Mobile**

Cada ejecución se registra como una instancia independiente en los reportes.

## 🌐 Navegadores Soportados

### Ejecución Local

| Navegador | Local | Notas                     |
|-----------|-------|---------------------------|
| Chrome    | ✔️    | Windows, Linux, macOS     |
| Edge      | ✔️    | Windows, Linux, macOS     |
| Firefox   | ✔️    | Windows, Linux, macOS     |
| Safari    | ✔️    | Solo macOS (SafariDriver) |

### Ejecución en CI/CD

| Navegador | CI/CD Local | Notas                            |
|-----------|-------------|----------------------------------|
| Chrome    | ✔️          | Soporte completo                 |
| Edge      | ✔️          | Soporte completo                 |
| Firefox   | ✔️          | Soporte completo                 |
| Safari    | ❌           | No soportado localmente en CI/CD |

> **Safari sí puede ejecutarse en CI/CD mediante RemoteWebDriver.**

### RemoteWebDriver

Compatible con proveedores y entornos remotos como:

- Selenium Grid
- Selenoid / Moon
- BrowserStack
- LambdaTest
- SauceLabs

## 📄 Reportes HTML (ExtentReports)

Los reportes se generan automáticamente en:

```
/reports/ExecutionReport_MultiDevice_<timestamp>.html
```

Incluyen:

- Dashboard general
- Resultados por navegador
- Resultados por dispositivo (Desktop / Tablet / Mobile)
- Capturas de pantalla por step
- Trazabilidad de errores y detalles técnicos

---

## ⚙️ Configuración Destacada del POM

- Build con Java 17
- Surefire configurado para ejecutar el suite multi-dispositivo
- Codificación UTF-8 para ambientes CI
- Perfiles Maven:
    - **windows** → excluye Safari
    - **mac** → habilita Safari
- Dependencias estables y actualizadas

## ⭐ Conclusión

**Multi-Device UI Testing** es un template moderno, robusto y escalable para ejecutar pruebas automatizadas
multiplataforma, combinando navegadores y tamaños de interfaz de forma eficiente y totalmente integrable en pipelines
CI/CD.

Ideal para:

- QA Automation
- Pruebas responsivas
- Validación UI en múltiples dispositivos
- Regresiones automatizadas
- Equipos con necesidades de testing cross-browser + multi-dispositivo

## Licencia

Este proyecto utiliza la [Licencia MIT](https://opensource.org/licenses/MIT).

## Disclaimer

La aplicación web utilizada en los ejemplos de este
proyecto [angular-dashboard-lime.vercel.app](https://angular-dashboard-lime.vercel.app) pertenece
a [Zoaib Khan](https://www.youtube.com/@ZoaibKhan). Se utiliza exclusivamente con fines educativos, demostrativos y para
prácticas de automatización.
package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver create() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,900");
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }

        if ("yandex".equals(browser)) {
            String binary = System.getProperty("yandex.binary");
            if (binary == null || binary.isBlank()) {
                throw new IllegalArgumentException(
                        "Для запуска в Яндекс Браузере укажи -Dyandex.binary=/path/to/yandex-browser"
                );
            }
            options.setBinary(binary);

            String driver = System.getProperty("yandex.driver");
            if (driver != null && !driver.isBlank()) {
                System.setProperty("webdriver.chrome.driver", driver);
            }
        } else if (!"chrome".equals(browser)) {
            throw new IllegalArgumentException("Поддерживаются browser=chrome и browser=yandex");
        }

        return new ChromeDriver(options);
    }
}

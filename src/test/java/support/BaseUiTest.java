package support;

import config.WebDriverFactory;
import io.qameta.allure.Allure;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public abstract class BaseUiTest {

    protected WebDriver driver;

    @Rule
    public final TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(
                        "Скриншот при ошибке",
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        ".png"
                );
                Allure.addAttachment("URL", driver.getCurrentUrl());
            }
        }

        @Override
        protected void finished(Description description) {
            if (driver != null) {
                driver.quit();
            }
        }
    };

    @Before
    public void createDriver() {
        driver = WebDriverFactory.create();
    }
}

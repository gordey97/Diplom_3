package page;

import config.TestConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.WAIT_SECONDS));
    }

    @Step("Дождаться отображения элемента {locator}")
    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Нажать на элемент {locator}")
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @Step("Ввести значение в элемент {locator}")
    protected void type(By locator, String value) {
        WebElement input = visible(locator);
        input.clear();
        input.sendKeys(value);
    }

    @Step("Проверить отображение элемента {locator}")
    protected boolean isVisible(By locator) {
        return visible(locator).isDisplayed();
    }

    @Step("Дождаться URL, содержащего {fragment}")
    protected void waitForUrl(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    @Step("Открыть страницу {url}")
    protected void open(String url) {
        driver.get(url);
    }
}

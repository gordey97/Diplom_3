package page;

import config.TestConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    private final By loginLink =
            By.xpath("//a[@href='/login' and normalize-space()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу восстановления пароля")
    public ForgotPasswordPage openForgotPasswordPage() {
        open(TestConfig.FORGOT_PASSWORD_URL);
        return this;
    }

    @Step("Нажать «Войти» в форме восстановления пароля")
    public void clickLoginLink() {
        click(loginLink);
    }
}

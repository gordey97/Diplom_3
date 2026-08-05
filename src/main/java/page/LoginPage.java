package page;

import config.TestConfig;
import io.qameta.allure.Step;
import model.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailInput =
            By.xpath("//label[normalize-space()='Email']/parent::*//input");
    private final By passwordInput =
            By.xpath("//label[normalize-space()='Пароль']/parent::*//input");
    private final By loginButton = By.xpath("//button[normalize-space()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу входа")
    public LoginPage openLoginPage() {
        open(TestConfig.LOGIN_URL);
        return this;
    }

    @Step("Войти как {user.email}")
    public void login(TestUser user) {
        type(emailInput, user.getEmail());
        type(passwordInput, user.getPassword());
        click(loginButton);
    }
}

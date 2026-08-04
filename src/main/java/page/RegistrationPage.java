package page;

import config.TestConfig;
import io.qameta.allure.Step;
import model.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    private final By nameInput =
            By.xpath("//label[normalize-space()='Имя']/parent::*//input");
    private final By emailInput =
            By.xpath("//label[normalize-space()='Email']/parent::*//input");
    private final By passwordInput =
            By.xpath("//label[normalize-space()='Пароль']/parent::*//input");
    private final By registerButton =
            By.xpath("//button[normalize-space()='Зарегистрироваться']");
    private final By loginLink =
            By.xpath("//a[@href='/login' and normalize-space()='Войти']");
    private final By invalidPasswordError =
            By.xpath("//p[normalize-space()='Некорректный пароль']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу регистрации")
    public RegistrationPage openRegistrationPage() {
        open(TestConfig.REGISTER_URL);
        return this;
    }

    @Step("Заполнить форму регистрации")
    public void fillRegistrationForm(TestUser user) {
        type(nameInput, user.getName());
        type(emailInput, user.getEmail());
        type(passwordInput, user.getPassword());
    }

    @Step("Нажать «Зарегистрироваться»")
    public void submit() {
        click(registerButton);
    }

    @Step("Нажать «Войти» в форме регистрации")
    public void clickLoginLink() {
        click(loginLink);
    }

    @Step("Проверить сообщение «Некорректный пароль»")
    public boolean isInvalidPasswordErrorVisible() {
        return isVisible(invalidPasswordError);
    }

    @Step("Дождаться перехода на страницу входа")
    public void waitForLoginPage() {
        waitForUrl("/login");
    }
}

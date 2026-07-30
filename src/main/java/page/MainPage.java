package page;

import config.TestConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private final By loginButton = By.xpath("//button[normalize-space()='Войти в аккаунт']");
    private final By accountLink = By.cssSelector("a[href='/account']");
    private final By orderButton = By.xpath("//button[normalize-space()='Оформить заказ']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public MainPage openMainPage() {
        open(TestConfig.BASE_URL);
        return this;
    }

    @Step("Нажать «Войти в аккаунт»")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Нажать «Личный Кабинет»")
    public void clickAccountLink() {
        click(accountLink);
    }

    @Step("Перейти в раздел конструктора «{name}»")
    public void selectConstructorSection(String name) {
        By tab = tab(name);
        click(tab);
        wait.until(currentDriver ->
                currentDriver.findElement(tab)
                        .getDomAttribute("class")
                        .contains("tab_tab_type_current")
        );
    }

    @Step("Проверить, что выбран раздел «{name}»")
    public boolean isConstructorSectionSelected(String name) {
        return visible(tab(name))
                .getDomAttribute("class")
                .contains("tab_tab_type_current");
    }

    @Step("Проверить отображение кнопки «Оформить заказ»")
    public boolean isOrderButtonVisible() {
        return isVisible(orderButton);
    }

    private By tab(String name) {
        return By.xpath(
                "//div[contains(@class,'tab_tab') and .//span[normalize-space()='" + name + "']]"
        );
    }
}

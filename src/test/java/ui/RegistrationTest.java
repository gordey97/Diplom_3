package ui;

import client.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import model.TestUser;
import org.junit.After;
import org.junit.Test;
import page.RegistrationPage;
import support.BaseUiTest;

import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Регистрация")
public class RegistrationTest extends BaseUiTest {

    private final UserApiClient userApiClient = new UserApiClient();
    private TestUser createdUser;
    private String accessToken;

    @After
    @Step("Удалить созданного пользователя")
    public void deleteCreatedUser() {
        if (createdUser != null && accessToken == null) {
            try {
                accessToken = userApiClient.login(createdUser);
            } catch (RuntimeException ignored) {
                // Пользователь не был создан — удалять нечего.
            }
        }
        userApiClient.delete(accessToken);
    }

    @Test
    @Story("Успешная регистрация")
    @Description("Пользователь с валидными уникальными данными регистрируется и попадает на страницу входа")
    public void validUserCanRegister() {
        createdUser = TestUser.random();
        RegistrationPage registrationPage =
                new RegistrationPage(driver).openRegistrationPage();

        registrationPage.fillRegistrationForm(createdUser);
        registrationPage.submit();
        registrationPage.waitForLoginPage();

        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Story("Валидация пароля")
    @Description("При пароле из пяти символов показывается сообщение «Некорректный пароль»")
    public void passwordShorterThanSixCharactersShowsError() {
        TestUser user = TestUser.random();
        TestUser userWithShortPassword =
                new TestUser(user.getEmail(), "12345", user.getName());
        RegistrationPage registrationPage =
                new RegistrationPage(driver).openRegistrationPage();

        registrationPage.fillRegistrationForm(userWithShortPassword);
        registrationPage.submit();

        assertTrue(registrationPage.isInvalidPasswordErrorVisible());
    }
}

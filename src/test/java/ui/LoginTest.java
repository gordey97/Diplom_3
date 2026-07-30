package ui;

import client.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.TestUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.ForgotPasswordPage;
import page.LoginPage;
import page.MainPage;
import page.RegistrationPage;
import support.BaseUiTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@Epic("Stellar Burgers")
@Feature("Вход")
public class LoginTest extends BaseUiTest {

    private final LoginEntryPoint entryPoint;
    private final UserApiClient userApiClient = new UserApiClient();
    private TestUser user;
    private String accessToken;

    public LoginTest(LoginEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Parameterized.Parameters(name = "Вход: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {LoginEntryPoint.MAIN_LOGIN_BUTTON},
                {LoginEntryPoint.PERSONAL_ACCOUNT},
                {LoginEntryPoint.REGISTRATION_FORM},
                {LoginEntryPoint.FORGOT_PASSWORD_FORM}
        });
    }

    @Before
    public void createUser() {
        user = TestUser.random();
        accessToken = userApiClient.register(user);
    }

    @After
    public void deleteUser() {
        userApiClient.delete(accessToken);
    }

    @Test
    @Story("Вход из разных точек приложения")
    @Description("Существующий пользователь входит через выбранную точку входа")
    public void existingUserCanLoginFromEveryRequiredEntryPoint() {
        openLoginForm(entryPoint);

        new LoginPage(driver).login(user);

        assertTrue(new MainPage(driver).isOrderButtonVisible());
    }

    private void openLoginForm(LoginEntryPoint point) {
        switch (point) {
            case MAIN_LOGIN_BUTTON:
                new MainPage(driver).openMainPage().clickLoginButton();
                break;
            case PERSONAL_ACCOUNT:
                new MainPage(driver).openMainPage().clickAccountLink();
                break;
            case REGISTRATION_FORM:
                new RegistrationPage(driver).openRegistrationPage().clickLoginLink();
                break;
            case FORGOT_PASSWORD_FORM:
                new ForgotPasswordPage(driver).openForgotPasswordPage().clickLoginLink();
                break;
            default:
                throw new IllegalArgumentException("Неизвестная точка входа: " + point);
        }
    }

    public enum LoginEntryPoint {
        MAIN_LOGIN_BUTTON("кнопка «Войти в аккаунт»"),
        PERSONAL_ACCOUNT("ссылка «Личный Кабинет»"),
        REGISTRATION_FORM("форма регистрации"),
        FORGOT_PASSWORD_FORM("форма восстановления пароля");

        private final String title;

        LoginEntryPoint(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

package config;

public final class TestConfig {

    public static final String BASE_URL = "https://stellarburgers.education-services.ru";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "/forgot-password";
    public static final int WAIT_SECONDS = 15;

    private TestConfig() {
    }
}

package model;

import java.util.UUID;

public class TestUser {

    private final String email;
    private final String password;
    private final String name;

    public TestUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static TestUser random() {
        String suffix = UUID.randomUUID().toString().replace("-", "");
        return new TestUser(
                "diplom_" + suffix + "@example.com",
                "Password123",
                "Тестовый пользователь"
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}

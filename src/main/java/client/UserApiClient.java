package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import config.TestConfig;
import io.qameta.allure.Step;
import model.Credentials;
import model.TestUser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserApiClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Step("Зарегистрировать тестового пользователя через API: {user.email}")
    public String register(TestUser user) {
        String serializedUser = gson.toJson(user);
        JsonObject response = send("/api/auth/register", "POST", serializedUser, null, 200);
        return response.get("accessToken").getAsString();
    }

    @Step("Выполнить вход тестового пользователя через API: {user.email}")
    public String login(TestUser user) {
        Credentials credentials = new Credentials(user.getEmail(), user.getPassword());
        String serializedCredentials = gson.toJson(credentials);
        JsonObject response = send(
                "/api/auth/login",
                "POST",
                serializedCredentials,
                null,
                200
        );
        return response.get("accessToken").getAsString();
    }

    @Step("Удалить тестового пользователя через API")
    public void delete(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            return;
        }
        send("/api/auth/user", "DELETE", null, accessToken, 202);
    }

    private JsonObject send(String path, String method, String body, String token, int expectedStatus) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(TestConfig.BASE_URL + path))
                .header("Content-Type", "application/json");
        if (token != null) {
            builder.header("Authorization", token);
        }

        if ("DELETE".equals(method)) {
            builder.DELETE();
        } else {
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        }

        try {
            HttpResponse<String> response = httpClient.send(
                    builder.build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            if (response.statusCode() != expectedStatus) {
                throw new IllegalStateException(
                        "API вернул " + response.statusCode() + " вместо " + expectedStatus
                                + ": " + response.body()
                );
            }
            return gson.fromJson(response.body(), JsonObject.class);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка обращения к API Stellar Burgers", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Запрос к API был прерван", e);
        }
    }
}

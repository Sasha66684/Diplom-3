package users;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import java.net.HttpURLConnection;

public class UserChecks {

    @Step("Проверка, успешно ли удален пользователь (check User deleted successfully)")
    public void deleteSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .and()
                .body("success", Matchers.equalTo(true));
    }
}

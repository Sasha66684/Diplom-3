package users;

import service.Endpoints;
import service.Specifications;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserProperties {

    @Step("Удалить пользователя (delete User)")
    public ValidatableResponse deleteExistingUser(String accessToken) {
        return given()
                .filter(new AllureRestAssured())
                .spec(Specifications.requestSpecification())
                .log()
                .all()
                .header("Authorization", accessToken)
                .when()
                .delete(Endpoints.PATCH_USER)
                .then().log().all();
    }

    @Step("Создать пользователя (create User)")
    public ValidatableResponse createNewUser(User user) {
        return given()
                .spec(Specifications.requestSpecification())
                .filter(new AllureRestAssured())
                .log()
                .all()
                .body(user)
                .when()
                .post(Endpoints.AUTH_PATH + "/register")
                .then()
                .log()
                .all();
    }
}


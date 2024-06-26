import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import users.User;
import users.UserChecks;
import users.UserProperties;
import static service.Endpoints.BROWSER;


public class RegisterUserTest {
    String accessToken;
    User user;
    private final UserProperties userProperties = new UserProperties();
    private final UserChecks userChecks = new UserChecks();
    private WebDriver webDriver;

    @Before
    @DisplayName("Создать нового пользователя (create new User)")
    public void createUser() {
        user = User.getRandomUser();
        webDriver = WebDriverFactory.getWebDriver(BROWSER);
    }
    @Test
    @DisplayName("Регистрация пользователя (register User)")
    public void registerUser() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickOnLinkToAuthorize();
        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.enterName(user)
                .enterEmail(user)
                .enterPassword(user)
                .clickOnButtonToRegister();
        loginPage.checkRegisterSuccessfully();
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        accessToken = mainPage.getAccessTokenFromLocalStorage();
    }
    @Test
    @DisplayName("Регистрация пользователя с коротким паролем(register User with short password)")
    public void registerUserWithShortPassword() {
        String newPassword = user.getPassword().substring(0, 5);
        user.setPassword(newPassword);
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickOnLinkToAuthorize();
        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.enterName(user)
                .enterEmail(user)
                .enterPassword(user)
                .clickOnButtonToRegister()
                .checkErrorInvalidPassword();
    }
    @After
    @DisplayName("Закрытие браузера.Удаление пользователя, проверка удаление пользователя  (delete User)")
    public void tearDown() {
    webDriver.quit();
    if (accessToken != null) {
        ValidatableResponse response = userProperties.deleteExistingUser(accessToken);
        userChecks.deleteSuccessfully(response);
      }
   }
}

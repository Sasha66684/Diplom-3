import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RecoveryPasswordPage;
import pages.RegisterPage;
import users.User;
import users.UserChecks;
import users.UserProperties;



public class AuthorizationUserTest {

    String  accessToken;
    User user;
    private final UserProperties userProperties = new UserProperties();
    private final UserChecks userChecks = new UserChecks();

    WebDriver webDriver = WebDriverFactory.getWebDriver();

    @Before
    @DisplayName("Создать нового пользователя.(create new User)")
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userProperties.createNewUser(user);
        accessToken = User.getToken(response);

    }

    @Test
    @DisplayName("Авторизация пользователя с помощью кнопки на главной странице.(authorize User by button on mane page)")
    public void authorizeUserByButtonOnManePage() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully();
    }

    @Test
    @DisplayName("Авторизация пользователя из личного кабинета.(аuthorize User from personal account")
    public void authorizeUserFromPersonalAccount() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnPersonalAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully();
    }

    @Test
    @DisplayName("Авторизация пользователя на странице регистрации.(authorize User from register page)")
    public void authorizeUserFromRegisterPage() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickOnLinkToAuthorize();
        RegisterPage registerPage = new RegisterPage(webDriver);
        registerPage.clickOnLinkToEnterAccount();
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully();
    }

    @Test
    @DisplayName("Авторизация пользователя на странице восстановления пароля.(authorize User from password recovery page)")
    public void authorizeUserFromPasswordRecoveryPage() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.clickOnLinkToRecoverPassword();
        RecoveryPasswordPage recoveryPasswordPage = new RecoveryPasswordPage(webDriver);///
        recoveryPasswordPage.clickOnLinkToEnterAccount();
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully();
    }

    @After
      @DisplayName("Закрытие браузера.Удалить пользователя, проверка удаление пользователя  (delete User)")
      public void tearDown() {
      webDriver.quit();
      if (accessToken != null) {
        ValidatableResponse response = userProperties.deleteExistingUser(accessToken);
        userChecks.deleteSuccessfully(response);
    }
  }
}
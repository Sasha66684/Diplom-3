import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.PersonalAccount;
import users.User;
import users.UserChecks;
import users.UserProperties;



public class NavigationTest {
    String accessToken;
    User user;
    private final UserProperties userProperties = new UserProperties();
    private final UserChecks userChecks = new UserChecks();

   WebDriver webDriver = WebDriverFactory.getWebDriver();

    @Before
    @DisplayName("Создать нового пользователя (create new User)")
    public void createUser() {
        user = User.getRandomUser();
        ValidatableResponse response = userProperties.createNewUser(user);
        accessToken = User.getToken(response);

    }

    @Test
    @DisplayName("Перейти в личный кабинет (go to personal account)")
    public void goToPersonalAccount() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully()
                .clickOnPersonalAccount();
        PersonalAccount personalAccount = new PersonalAccount(webDriver);
        personalAccount.checkSwitchToAccount();
    }

    @Test
    @DisplayName("Перейдите на главную страницу, нажав на логотип (go to main page by click on logo)")
    public void goToMainPageByClickLogo() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully()
                .clickOnPersonalAccount();
        PersonalAccount personalAccount = new PersonalAccount(webDriver);
        personalAccount.checkSwitchToAccount();
        mainPage.clickOnLogo().
                checkSwitchToMainPage();
    }

    @Test
    @DisplayName("Перейдите на главную страницу, нажав на конструктор (go to main page by click on constructor)")
    public void goToMainPageByClickConstructor() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully()
                .clickOnPersonalAccount();
        PersonalAccount personalAccount = new PersonalAccount(webDriver);
        personalAccount.checkSwitchToAccount();
        mainPage.clickOnConstructor()
                .checkSwitchToMainPage();
    }

    @Test
    @DisplayName("Выход из учетной записи (exit from account)")
    public void exitFromAccount() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnButtonEnterToAccount();
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.enterEmail(user)
                .enterPassword(user)
                .clickOnButtonEnter();
        mainPage.checkLoginSuccessfully()
                .clickOnPersonalAccount();
        PersonalAccount personalAccount = new PersonalAccount(webDriver);
        personalAccount.checkSwitchToAccount()
                .exitFromAccount();
        loginPage.checkLogoutSuccessfully();
    }

@Test
@DisplayName("Переключение на секцию Соусы (switching between burger sections)")
public void switchingToSauceSection() {
    MainPage mainPage = new MainPage(webDriver);
    mainPage.open()
            .clickOnSauce()
            .checkSauceIsSelected();

}
    @Test
    @DisplayName("Переключение на секцию Начинки  (switching between burger sections)")
    public void switchingToFillingSection() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnFilling()
                .checkFillingIsSelected();

    }
    @Test
    @DisplayName("Переключение на секцию Булки (switching between burger sections)")
    public void BreadsSection() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.open()
                .clickOnBreads()
                .checkIsBreads();
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

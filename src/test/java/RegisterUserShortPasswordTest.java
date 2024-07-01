import driver.WebDriverFactory;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import users.User;




@RunWith(Parameterized.class)
public class RegisterUserShortPasswordTest {
    User user;
    private final int passLength;

   WebDriver webDriver = WebDriverFactory.getWebDriver();

    public RegisterUserShortPasswordTest(int passLength) {
        this.passLength = passLength;
    }



    @Before
    @DisplayName("Создать нового пользователя (create new User)")
    public void createUser() {
        user = User.getRandomUser();
       // webDriver = WebDriverFactory.getWebDriver(BROWSER);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {4},
                {5}
        };
    }
    @Test
    @DisplayName("Регистрация пользователя с коротким паролем (register User with short password)")
    public void registerUserWithShortPassword() {
        // укорачиваю пароль
        String newPassword = user.getPassword().substring(0, passLength);
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
    public void tearDown() {
        webDriver.quit();
    }
}
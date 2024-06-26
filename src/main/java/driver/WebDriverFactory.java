package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    public static WebDriver getWebDriver(String browserType) {
        if (browserType.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().clearDriverCache().clearResolutionCache().setup();//
            return new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("yandex")) {
            System.setProperty("webdriver.chrome.driver", "/Users/sasha/WebDriver/bin/yandexdriver");
            return new ChromeDriver();

        } else {
            WebDriverManager.chromedriver();
            return new ChromeDriver();
        }
    }
}
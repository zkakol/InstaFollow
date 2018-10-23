package utils;

import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class InstagramUtil {

    private static ChromeDriver browser;

    public InstagramUtil() {
        File file;
        if (System.getProperty("os.name").toUpperCase().startsWith("WINDOWS")) {
            file = new File("resources/main/chromedriver.exe");
        } else {
            file = new File("resources/main/chromedriver");
        }

        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        //def chromeOptions = new ChromeOptions().addArguments("--headless")
        ChromeOptions chromeOptions = new ChromeOptions();
        browser = new ChromeDriver(chromeOptions);
    }

    public static boolean login(User u) {
        String username = u.getUsername();
        String password = u.getPassword();

        browser.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
        WebElement userInput = browser.findElement(By.name("username"));
        WebElement passInput = browser.findElement(By.name("password"));
        userInput.sendKeys(username);
        passInput.sendKeys(password);

        WebElement loginButton = browser.findElementByXPath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[3]/button");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(browser, 30);
        //TODO: change to check the status of the AJAX login rather than a simple timeout function
        //wait for the AJAX login
        try {
            Thread.sleep(2000);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        wait.until(expectation);

        if (browser.findElementsByXPath("//*[@id=\"slfErrorAlert\"]").size() > 0) {
            System.out.println("Cannot Login!");
            browser.close();
            return false;
        }


        if (browser.findElementsByXPath("//button[contains(.,'Not Now')]").size() > 0) {
            browser.findElementByXPath("//button[contains(.,'Not Now')]").click();
        }

        return true;
    }

    private static ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
        @Override
        public Boolean apply(WebDriver webDriver) {
            return ((JavascriptExecutor) browser).executeScript("return document.readyState").toString().toUpperCase().equals("COMPLETE");
        }

    };
}

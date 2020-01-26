import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeoutException;

/**
 * Created by Mike on 26.01.2020.
 */
public class TestBaseClass {

    WebDriver driver;
    @Parameters({ "browser" })
    @BeforeTest
    public void prepareEnv(String browser) throws Exception {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Mike\\Downloads\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mike\\Downloads\\chromedriver.exe");
        if(browser.equals("Firefox"))
            driver = new FirefoxDriver();
        else if(browser.equals("Chrome"))
            driver = new ChromeDriver();
        else if(browser.equals("IE"))
            driver = new InternetExplorerDriver();
        else
            throw new Exception("Unknown browser: " + browser);

    }

    public WebDriver getWebDriver(){
        return this.driver;
    }

    protected void waitForPageLoad(long timeout) throws TimeoutException, InterruptedException {
        long end = System.currentTimeMillis() + timeout;

        while (System.currentTimeMillis() < end) {

            if (String.valueOf(
                    ((JavascriptExecutor) driver)
                            .executeScript("return document.readyState"))
                    .equals("complete")) {
                break;
            }
            Thread.sleep(1000);
        }
        if(System.currentTimeMillis() > end)
            throw new TimeoutException("Web page didn't load in expected time of " + timeout + " miliseconds");
    }
    @AfterTest
    public void closebrowser(){
        System.out.println("Cleaning up after tests");
        driver.close();
        driver.quit();
    }
}

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

/**
 * Created by Mike on 26.01.2020.
 */
public class TestListener implements ITestListener {

    private WebDriver driver;

    private void takeScreenshot(ITestResult iTestResult){
        this.driver = ((TestBaseClass)iTestResult.getInstance()).getWebDriver();
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("c:\\tmp\\" + iTestResult.getName() + "_" + System.currentTimeMillis() + "_screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {
        takeScreenshot(iTestResult);
    }

    public void onTestFailure(ITestResult iTestResult) {
        takeScreenshot(iTestResult);
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }
}
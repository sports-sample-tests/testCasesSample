import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Mike on 26.01.2020.
 */
public class SportsDirectPageObject {
    /*
    This class describes and maps web elements used in tests and wraps user actions possible to do in corresponding methods.
     */

    private WebDriver driver;
    public SportsDirectPageObject(WebDriver driver){
        this.driver = driver;
    }

    // Locators:
    private By searchBox = By.cssSelector("#txtSearch");
    private By searchLink = By.cssSelector("#cmdSearch > span:nth-child(1)");
    private By menuWrapper = By.cssSelector("#topMenuWrapper");
    private By advertisement = By.cssSelector(".advertPopup > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)");
    private By results = By.cssSelector("#productlistcontainer");
    private By searchResultsLabel = By.cssSelector("#lblCategoryHeader");

    private WebElement getWebElement(By locator){
        return driver.findElement(locator);
    }

    public void loadWebPage(){
        driver.get("https://lv.sportsdirect.com/");
    }
    public WebElement getMenuWrapper(){
        return driver.findElement(menuWrapper);
    }
    public void searchItem(String item) throws InterruptedException {
        getWebElement(searchBox).sendKeys(item);
        getWebElement(searchLink).click();
        waitToGetResults(60000);
    }

    public void waitToGetResults(long timeout) throws InterruptedException {
        long time = 0;
        while(time < timeout){
            Thread.sleep(1000);
            time+=1000;
            try{
                if(driver.findElement(searchResultsLabel).isDisplayed())
                    break;
            } catch (NoSuchElementException ex){
                System.out.println("Search results label wasn't found yet after " + time + "miliseconds, trying again...");
            }
        }
    }
    public WebElement getContainerWithResults(){
        return driver.findElement(results);
    }
    public boolean isElementDisplayedInOtherWebelement(WebElement in, By elem){
        return in.findElement(elem).isDisplayed();
    }
    public int getNumberOfItemsInProductContainerFromSearchResults(){
        return getContainerWithResults().findElements(By.cssSelector(".ProductImageList")).size();
    }
    public void closeAdvertisement(){
        driver.findElement(advertisement).click();
    }
}

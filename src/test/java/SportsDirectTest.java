import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mike on 26.01.2020.
 */
public class SportsDirectTest extends TestBaseClass {
    private SportsDirectPageObject pageUnderTest;
    @BeforeTest
    public void setup() throws TimeoutException, InterruptedException {
        pageUnderTest = new SportsDirectPageObject(driver);
        pageUnderTest.loadWebPage();
        waitForPageLoad(60000);
        pageUnderTest.closeAdvertisement();
        driver.manage().window().maximize();
    }

    @Test
    public void checkBasicElementsOnMainWebPage(){
        List<String> listOfExpectedLinks = new ArrayList<String>();
        listOfExpectedLinks.add("Mens");
        listOfExpectedLinks.add("Ladies");
        listOfExpectedLinks.add("Kids");
        listOfExpectedLinks.add("Football");
        listOfExpectedLinks.add("Running");
        listOfExpectedLinks.add("Fitness & Training");
        listOfExpectedLinks.add("Accessories");
        listOfExpectedLinks.add("Sports");
        listOfExpectedLinks.add("Brands");
        listOfExpectedLinks.add("Sale");
        listOfExpectedLinks.add("Deals");
        for (String expectedLink : listOfExpectedLinks) {
            WebElement item = pageUnderTest.getMenuWrapper().findElement(By.linkText(expectedLink));
            assertThat(item.isDisplayed(), equalTo(Boolean.TRUE));
            assertThat(item.isEnabled(), equalTo(Boolean.TRUE));
        }
        assertThat(driver.getTitle(), equalTo("SportsDirect.com – The UK’s No 1 Sports Retailer"));
    }

    @Test(dataProvider = "data-for-search-engine-tests")
    public void checkIfSearchEngineReturnsData(String searchItem) throws TimeoutException, InterruptedException {
        pageUnderTest.searchItem(searchItem);
        waitForPageLoad(60000);
        assertThat(pageUnderTest.isElementDisplayedInOtherWebelement(pageUnderTest.getContainerWithResults(), By.cssSelector(".ProductImageList")), equalTo(Boolean.TRUE));
        assertThat(pageUnderTest.getNumberOfItemsInProductContainerFromSearchResults(), not(0));
    }

    @DataProvider(name = "data-for-search-engine-tests")
    public Object[][] getTestDataForSearchEngine(){
        return new Object[][] {{"All Shoes"},{"Mens shoes"},{"Women shoes"},{"Nike shoes"}, {"Adidas shoes"}, {"Puma shoes"}};
    }
}

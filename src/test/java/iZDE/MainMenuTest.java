package iZDE;

import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.utils.ConfigReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("iZDE Web")
@Feature("Главное меню")
public class MainMenuTest extends BaseTest {

    private String getFullUrl(Endpoints endpoint) {
        return ConfigReader.getValue("baseURL") + endpoint.getEndpoint();
    }

    @BeforeMethod
    public void loginOnce() {
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.SIGNIN.getEndpoint());

        LoginEntity entity = randomUtils.validLoginEntity();
        getLoginPage().fillUpLoginForm(entity);
    }

    @Test
    public void testClickStartButton() {
        getMainMenuPage().clickStartButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.SEARCH), By.xpath("//div[@class='_contentBackground_qv3mn_6']"),
                "Блок поисковика");

        browserHelper.goBack();
        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickSearchButton() {
        getMainMenuPage().clickBurgerMenu();
        getMainMenuPage().clickSearchButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.SEARCH),
                By.xpath("//div[@class='_contentBackground_qv3mn_6']"),
                "Блок поисковика");

        browserHelper.goBack();
        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickTravellingButton() {
        getMainMenuPage().clickBurgerMenu();
        getMainMenuPage().clickTravellingButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.TRIPS),
                By.xpath("//div[@class='_trips_pp0r2_1']"),
                "Блок путешествий");

        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickSupportButton() {
        getMainMenuPage().clickBurgerMenu();
        getMainMenuPage().clickSupportButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.SUPPORT),
                By.xpath("//div[@class='_container_1nnn2_8']"),
                "Блок поддержки");

        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickProfileButton() {
        getMainMenuPage().clickProfileButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.PROFILE),
                By.xpath("//div[@class='_wrapper_1yb73_1']"),
                "Блок профиля");

        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickCellarSearchButton() {
        getMainMenuPage().clickCellarSearchButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.SEARCH),
                By.xpath("//div[@class='_contentBackground_qv3mn_6']"),
                "Блок профиля");

        browserHelper.goBack();
        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickCellarTravellingButton() {
        getMainMenuPage().clickCellarTravellingButton();

        webElementActions.verifyPage(getFullUrl(Endpoints.TRIPS),
                By.xpath("//div[@class='_trips_pp0r2_1']"),
                "Блок путешествий");

        browserHelper.goBack();
        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickPlayMarketButton() {
        getMainMenuPage().clickCellarPlayMarketButton();

        browserHelper.switchToNewTab();

        String expectedUrl = "https://play.google.com/store/apps/details?id=com.izde.izdeuserandroid";
        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedUrl,
                "URL открытой вкладки не соответствует ожидаемому. Ожидалось: " + expectedUrl + ", но было: " + currentUrl);

        browserHelper.switchToParentWindowAndCloseChildren();
        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickAppStoreButton() {
        getMainMenuPage().clickCellarAppStoreButton();

        browserHelper.switchToNewTab();

        String expectedUrl = "https://apps.apple.com/kg/app/izde/id6498629576";
        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedUrl,
                "URL открытой вкладки не соответствует ожидаемому. Ожидалось: " + expectedUrl + ", но было: " + currentUrl);

        browserHelper.switchToParentWindowAndCloseChildren();
        webElementActions.assertBaseUrlIsCurrent();
    }

    @Test
    public void testClickVendorButton() {
        getMainMenuPage().clickCellarVendorButton();

        browserHelper.switchToNewTab();

        String expectedUrl = "https://vendor.izde.online/sign-in/";
        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, expectedUrl,
                "URL открытой вкладки не соответствует ожидаемому. Ожидалось: " + expectedUrl + ", но было: " + currentUrl);


        browserHelper.goBack();
        webElementActions.assertBaseUrlIsCurrent();
    }

}

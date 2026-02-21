package iZDE.ProfileTest;

import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.utils.ConfigReader;
import iZDE.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.izde.drivers.DriverManager.getDriver;

@Epic("iZDE Web")
@Feature("Профиль")
public class DashboardTest extends BaseTest {

    @BeforeMethod
    public void loginOnce() {
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.PROFILE.getEndpoint());

        LoginEntity entity = randomUtils.validLoginEntity();
        getLoginPage().fillUpLoginForm(entity);
        getMainMenuPage().clickProfileButton();
    }

    @Test
    public void testClickExitBtn() {
        getDashboardProfilePage().clickExitBtn();
        getDashboardProfilePage().clickExitBtn2();

        String expectedBaseUrl = ConfigReader.getValue("baseURL");
        wait.until(ExpectedConditions.urlToBe(expectedBaseUrl));

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedBaseUrl, "URL не соответствует базовому URL после выхода.");
    }
}

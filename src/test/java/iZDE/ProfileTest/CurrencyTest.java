package iZDE.ProfileTest;

import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.utils.ConfigReader;
import iZDE.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("iZDE Web")
@Feature("Валюта")
public class CurrencyTest extends BaseTest {

    @BeforeClass
    public void loginOnce() {
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.SIGNIN.getEndpoint());

        LoginEntity entity = randomUtils.validLoginEntity();
        getLoginPage().fillUpLoginForm(entity);
    }

    private void changeCurrencyIfNeeded(WebElement currencyBtn, String expectedCurrency) {
        String initialCurrencyText = getCurrencyPage().getCurrentCurrencyElement().getText();

        if (!initialCurrencyText.equals(expectedCurrency)) {
            getCurrencyPage().clickCurrencyIfNotSelected(currencyBtn);
            getCurrencyPage().clickSave();
            browserHelper.waitForElementTextToBe(getCurrencyPage().getCurrentCurrencyElement(), expectedCurrency, 10);
        }
    }

    @Test(priority = 1)
    public void currencyChangeToEurTest() {
        getMainMenuPage().clickProfileButton();
        getDashboardProfilePage().clickCurrencyBtn();

        changeCurrencyIfNeeded(getCurrencyPage().eurBtn, "Евро\nEUR");

        getMainMenuPage().clickBurgerMenu();
        getMainMenuPage().clickSearchButton();

        String actualDisplayedText = String.valueOf(getSearchPage().getEurText());
        Assert.assertTrue(actualDisplayedText.contains("€"), "Отображение валюты в поиске некорректно!");
    }

    @Test(priority = 2)
    public void currencyChangeToUsdTest() {
        getMainMenuPage().clickProfileButton();
        getDashboardProfilePage().clickCurrencyBtn();
        getDashboardProfilePage().clickCurrencyBtn();

        changeCurrencyIfNeeded(getCurrencyPage().usdBtn, "Доллар США\nUSD");

        getMainMenuPage().clickBurgerMenu();
        getMainMenuPage().clickSearchButton();

        String actualDisplayedText = String.valueOf(getSearchPage().getUsdText());
        Assert.assertTrue(actualDisplayedText.contains("$"), "Отображение валюты в поиске некорректно!");
    }

    @Test(priority = 3)
    public void currencyChangeToKgsTest() {
        getMainMenuPage().clickProfileButton();
        getDashboardProfilePage().clickCurrencyBtn();
        getDashboardProfilePage().clickCurrencyBtn();

        changeCurrencyIfNeeded(getCurrencyPage().kgsBtn, "Кыргызский сом\nKGS");

        getMainMenuPage().clickBurgerMenu();
        getMainMenuPage().clickSearchButton();

        String actualDisplayedText = String.valueOf(getSearchPage().getKgsText());
        Assert.assertTrue(actualDisplayedText.contains("с"), "Отображение валюты в поиске некорректно!");
    }
}

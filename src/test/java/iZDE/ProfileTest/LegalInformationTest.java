package iZDE.ProfileTest;

import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.utils.ConfigReader;
import iZDE.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import java.time.Duration;

@Epic("iZDE Web")
@Feature("Юридическая информация")
public class LegalInformationTest extends BaseTest {

    private WebDriverWait wait;

    @BeforeClass
    public void loginOnce() {
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.SIGNIN.getEndpoint());

        LoginEntity entity = randomUtils.validLoginEntity();
        getLoginPage().fillUpLoginForm(entity);
        getMainMenuPage().clickProfileButton();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testUserAgreementButton() {
        getLegalInformationPage().clickUserAgreementButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='rpv-core__text-layer-text' and text()='ПОЛЬЗОВАТЕЛЬСКОЕ СОГЛАШЕНИЕ']")));

        String actualText = getLegalInformationPage().getUserAgreementText();
        Assert.assertEquals(actualText, "ПОЛЬЗОВАТЕЛЬСКОЕ СОГЛАШЕНИЕ", "Текст пользовательского соглашения некорректен!");
    }

    @Test
    public void testPrivacyPolicyButton() {
        getLegalInformationPage().clickPrivacyPolicyButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='rpv-core__text-layer-text' and text()='ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ']")));

        String actualText = getLegalInformationPage().getPrivacyPolicyText();
        Assert.assertEquals(actualText, "ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ", "Текст политики конфиденциальности некорректен!");
    }

    @Test
    public void testPublicOfferButton() {
        getLegalInformationPage().clickPublicOfferButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='rpv-core__text-layer-text' and text()='ПУБЛИЧНАЯ ОФЕРТА']")));

        String actualText = getLegalInformationPage().getPublicOfferText();
        Assert.assertEquals(actualText, "ПУБЛИЧНАЯ ОФЕРТА", "Текст публичной оферты некорректен!");
    }
}

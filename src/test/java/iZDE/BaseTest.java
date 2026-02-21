package iZDE;

import com.izde.drivers.DriverManager;
import com.izde.entities.iZDE.*;
import com.izde.helpers.AlertHelper;
import com.izde.helpers.BrowserHelper;
import com.izde.helpers.IframeHelper;
import com.izde.helpers.WebElementActions;
import com.izde.listener.ScreenshotListener;
import com.izde.pages.iZDE.*;
import com.izde.pages.iZDE.Profile.*;
import com.izde.utils.iZDE.RandomUtils;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.time.Duration;

@Listeners({AllureTestNg.class, ScreenshotListener.class})

public class BaseTest {
    protected WebDriver driver;
    protected RandomUtils randomUtils;
    protected WebElementActions webElementActions;
    protected WebDriverWait wait;

    protected AlertHelper alertHelper;
    protected BrowserHelper browserHelper;
    protected IframeHelper iframeHelper;

    private LoginEntity loginEntity;
    private RegisterEntity registerEntity;
    private ChangePasswordEntity resetPasswordEntity;
    private ChangePasswordEntity changePasswordEntity;
    private ChangeEmailEntity changeEmailEntity;
    private ResetNumberEntity resetNumberEntity;

    private RegisterPage registerPage;
    private LoginPage loginPage;
    private ResetPasswordPage resetPasswordPage;
    private MainMenuPage mainMenuPage;
    private SearchPage searchPage;
    private CurrencyPage currencyPage;
    private DashboardProfilePage dashboardProfilePage;
    private PrivateProfilePage privateProfilePage;
    private LegalInformationPage legalInformationPage;
    private ChangeEmailPage changeEmailPage;
    private ChangePasswordPage changePasswordPage;

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        driver = DriverManager.getDriver();
        randomUtils = new RandomUtils();
        webElementActions = new WebElementActions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        alertHelper = new AlertHelper(driver);
        browserHelper = new BrowserHelper(driver);
        iframeHelper = new IframeHelper(driver);
    }

    protected LoginEntity getLoginEntity() {
        if (loginEntity == null) loginEntity = new LoginEntity();
        return loginEntity;
    }

    protected RegisterEntity getRegisterEntity() {
        if (registerEntity == null) registerEntity = new RegisterEntity();
        return registerEntity;
    }

    protected ChangePasswordEntity getResetPasswordEntity() {
        if (resetPasswordEntity == null) resetPasswordEntity = new ChangePasswordEntity();
        return resetPasswordEntity;
    }

    protected ChangePasswordEntity getChangePasswordEntity() {
        if (changePasswordEntity == null) changePasswordEntity = new ChangePasswordEntity();
        return changePasswordEntity;
    }

    protected ChangeEmailEntity getChangeEmailEntity() {
        if (changeEmailEntity == null) changeEmailEntity = new ChangeEmailEntity();
        return changeEmailEntity;
    }

    protected ResetNumberEntity getResetNumberEntity() {
        if (resetNumberEntity == null) resetNumberEntity = new ResetNumberEntity();
        return resetNumberEntity;
    }

    protected RegisterPage getRegisterPage() {
        if (registerPage == null) registerPage = new RegisterPage();
        return registerPage;
    }

    protected LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage();
        return loginPage;
    }

    protected ResetPasswordPage getResetPasswordPage() {
        if (resetPasswordPage == null) resetPasswordPage = new ResetPasswordPage();
        return resetPasswordPage;
    }

    protected MainMenuPage getMainMenuPage() {
        if (mainMenuPage == null) mainMenuPage = new MainMenuPage();
        return mainMenuPage;
    }

    protected SearchPage getSearchPage() {
        if (searchPage == null) searchPage = new SearchPage();
        return searchPage;
    }

    protected CurrencyPage getCurrencyPage() {
        if (currencyPage == null) currencyPage = new CurrencyPage();
        return currencyPage;
    }

    protected DashboardProfilePage getDashboardProfilePage() {
        if (dashboardProfilePage == null) dashboardProfilePage = new DashboardProfilePage();
        return dashboardProfilePage;
    }

    protected PrivateProfilePage getPrivateProfilePage() {
        if (privateProfilePage == null) privateProfilePage = new PrivateProfilePage();
        return privateProfilePage;
    }

    protected LegalInformationPage getLegalInformationPage() {
        if (legalInformationPage == null) legalInformationPage = new LegalInformationPage();
        return legalInformationPage;
    }

    protected ChangeEmailPage getChangeEmailPage() {
        if (changeEmailPage == null) changeEmailPage = new ChangeEmailPage();
        return changeEmailPage;
    }

    protected ChangePasswordPage getChangePasswordPage() {
        if (changePasswordPage == null) changePasswordPage = new ChangePasswordPage();
        return changePasswordPage;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        DriverManager.closeDriver();
    }
}

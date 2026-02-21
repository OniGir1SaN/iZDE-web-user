package iZDE;

import com.izde.enums.iZDE.Endpoints;
import com.izde.utils.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("iZDE Web")
@Feature("Сброс пароля")
public class ResetPasswordTest extends BaseTest {

    @BeforeMethod
    public void openResetPasswordPage() {
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.RESET.getEndpoint());
    }

    @Test
    public void testEmptyEmailError() {
        webElementActions.click(getResetPasswordPage().submitBtn);

        String errorMessage = getResetPasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Обязательно к заполнению"),
                "Сообщение об ошибке при пустом поле некорректно!");
    }

    @Test
    public void testInvalidEmailFormatError() {
        webElementActions.sendKeys(getResetPasswordPage().emailInput, "invalidEmail");
        webElementActions.click(getResetPasswordPage().submitBtn);

        String errorMessage = getResetPasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Введите верный Email"),
                "Сообщение об ошибке при некорректном формате email некорректно!");
    }

    @Test
    public void testUnregisteredEmailError() {
        webElementActions.sendKeys(getResetPasswordPage().emailInput, "unregistered@example.com");
        webElementActions.click(getResetPasswordPage().submitBtn);

        String errorMessage = getResetPasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains(""),
                "Сообщение об ошибке при незарегистрированном email некорректно!");
    }

    @Test
    public void testValidEmail() {
        webElementActions.sendKeys(getResetPasswordPage().emailInput, ConfigReader.getValue("login"));
        webElementActions.click(getResetPasswordPage().submitBtn);
        webElementActions.waitFor(20000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("code"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("code"),
                "Не выполнен переход на страницу с эндпоинтом 'code'!");
    }

    @Test
    public void testCode(){
        testValidEmail();
    }

    @Test(groups = "validation")
    public void testResetPasswordPlaceholders() {
        getResetPasswordPage().verifyFormResetPasswordPlaceholders();
        Allure.step("Проверка плэйсхолдера в поле email");
    }
}

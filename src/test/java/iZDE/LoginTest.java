package iZDE;

import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.listener.ScreenshotListener;
import com.izde.utils.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("iZDE Web")
@Feature("Авторизация")
@Listeners({AllureTestNg.class, ScreenshotListener.class})
public class LoginTest extends BaseTest {

    @BeforeMethod
    public void openLoginPage() {
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.SIGNIN.getEndpoint());
        Allure.step("Открыта страница авторизации");
    }

    private void submitFormAndVerifyError(LoginEntity entity, String expectedErrorMessage) {
        getLoginPage().fillUpLoginForm(entity);
        webElementActions.click(getLoginPage().submitBtn);

        String errorMessage = getLoginPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage),
                "Ожидаемая ошибка: " + expectedErrorMessage + ", получено: " + errorMessage);
        Allure.step("Проверка ошибки: " + expectedErrorMessage);
    }

    private void submitFormAndVerifyError2(LoginEntity entity, String expectedErrorMessage) {
        getLoginPage().fillUpLoginForm(entity);
        webElementActions.click(getLoginPage().submitBtn);

        String errorMessage = getLoginPage().getErrorMessage2();
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage),
                "Ожидаемая ошибка: " + expectedErrorMessage + ", получено: " + errorMessage);
        Allure.step("Проверка ошибки: " + expectedErrorMessage);
    }

    // ==================== UI / Текст ====================

    @Test(groups = "validation")
    public void testLoginPlaceholders() {
        getLoginPage().verifyEmailAndPasswordPlaceholders();
        Allure.step("Проверка плейсхолдеров полей email и пароля");
    }

    @Test
    public void testTextLogin1() {
        String expectedText = "Готовы к новым приключениям?";
        Assert.assertTrue(getLoginPage().isTextLogin1Correct(expectedText),
                "Текст для Login1 не соответствует ожидаемому тексту.");
    }

    @Test
    public void testTextLogin2() {
        String expectedText = "Войдите и начнем!";
        Assert.assertTrue(getLoginPage().isTextLogin2Correct(expectedText),
                "Текст для Login2 не соответствует ожидаемому тексту.");
    }

    @Test
    public void testSubmitButtonIsDisplayed() {
        Assert.assertTrue(getLoginPage().submitBtn.isDisplayed(),
                "Кнопка 'Войти' не отображается на странице");
        Allure.step("Кнопка 'Войти' отображается");
    }

    @Test
    public void testPasswordFieldIsMasked() {
        String inputType = getLoginPage().passwordInput.getAttribute("type");
        Assert.assertEquals(inputType, "password",
                "Поле пароля не замаскировано (type != password)");
        Allure.step("Поле пароля замаскировано");
    }

    @Test
    public void testEmailFieldTypeIsEmail() {
        String inputType = getLoginPage().emailInput.getAttribute("type");
        Assert.assertEquals(inputType, "email",
                "Тип поля email некорректен");
        Allure.step("Тип поля email корректен");
    }

    // ==================== Успешный логин ====================

    @Test(groups = "success")
    public void testSuccessfulLogin() {
        LoginEntity entity = randomUtils.validLoginEntity();
        getLoginPage().fillUpLoginForm(entity);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(ConfigReader.getValue("baseURL")));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, ConfigReader.getValue("baseURL"),
                "После успешного логина не произошёл редирект на главную страницу. URL: " + currentUrl);
        Allure.step("Успешный логин, редирект на главную страницу");
    }

    // ==================== Пустые поля ====================

    @Test(groups = "validation")
    public void testLoginWithEmptyFields() {
        LoginEntity entity = new LoginEntity();
        entity.setEmail("");
        entity.setPassword("");
        submitFormAndVerifyError(entity, "Обязательно к заполнению");
    }

    @Test(groups = "validation")
    public void testLoginWithEmptyEmail() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("");
        submitFormAndVerifyError(entity, "Обязательно к заполнению");
    }

    @Test(groups = "validation")
    public void testLoginWithEmptyPassword() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setPassword("");
        submitFormAndVerifyError(entity, "Обязательно к заполнению");
    }

    // ==================== Валидация email ====================

    @Test(groups = "validation")
    public void testLoginWithInvalidEmailFormat() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("invalidemail");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    @Test(groups = "validation")
    public void testLoginWithEmailWithoutDomain() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("user@");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    @Test(groups = "validation")
    public void testLoginWithEmailWithoutAt() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("userdomain.com");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    @Test(groups = "validation")
    public void testLoginWithEmailWithSpaces() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("user @domain.com");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    @Test(groups = "validation")
    public void testLoginWithEmailOnlyAtSymbol() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("@");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    @Test(groups = "validation")
    public void testLoginWithEmailDoubleDots() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("user@domain..com");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    @Test(groups = "validation")
    public void testLoginWithEmailSpecialChars() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("user!#$%@domain.com");
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    // ==================== Валидация пароля ====================

    @Test(groups = "validation")
    public void testLoginWithShortPassword() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setPassword("short");
        submitFormAndVerifyError2(entity, "Пользователь с таким email не найден");
    }

    @Test(groups = "validation")
    public void testLoginWithPasswordOnlySpaces() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setPassword("        ");
        submitFormAndVerifyError2(entity, "Пользователь с таким email не найден");
    }

    @Test(groups = "validation")
    public void testLoginWithPasswordOneChar() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setPassword("A");
        submitFormAndVerifyError2(entity, "Пользователь с таким email не найден");
    }

    // ==================== Неверные учётные данные ====================

    @Test(groups = "validation")
    public void testLoginWithInvalidCredentials() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("invalidemail@example.com");
        entity.setPassword("InvalidPassword123!");
        submitFormAndVerifyError2(entity, "Пользователь с таким email не найден");
    }

    @Test(groups = "validation")
    public void testLoginWithValidEmailWrongPassword() {
        LoginEntity entity = new LoginEntity();
        entity.setEmail(ConfigReader.getValue("login"));
        entity.setPassword("WrongPassword123!");
        submitFormAndVerifyError2(entity, "не правильный логин или пароль");
    }

    @Test(groups = "validation")
    public void testLoginWithNonExistentEmail() {
        LoginEntity entity = new LoginEntity();
        entity.setEmail("nonexistent_user_12345@fakeemail.com");
        entity.setPassword("ValidPassword123!");
        submitFormAndVerifyError2(entity, "Пользователь с таким email не найден");
    }

    // ==================== Навигация ====================

    @Test
    public void testNavigateToRegistrationPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signUpLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, 'sign-up')]")));
        signUpLink.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(Endpoints.SIGNUP.getEndpoint()),
                "Не произошёл переход на страницу регистрации. URL: " + currentUrl);
        Allure.step("Переход на страницу регистрации");
    }

    @Test
    public void testNavigateToResetPasswordPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement resetLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, 'reset-password')]")));
        resetLink.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("reset-password"),
                "Не произошёл переход на страницу сброса пароля. URL: " + currentUrl);
        Allure.step("Переход на страницу сброса пароля");
    }
}

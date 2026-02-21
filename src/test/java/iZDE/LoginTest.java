package iZDE;

import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.listener.ScreenshotListener;
import com.izde.utils.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

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

    @Test(groups = "validation")
    public void testLoginPlaceholders() {
        getLoginPage().verifyEmailAndPasswordPlaceholders();
        Allure.step("Проверка плейсхолдеров полей email и пароля");
    }

    // Тесты для поля "Email"
    @Test(groups = "validation")
    public void testLoginWithEmptyEmail() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail(""); // Пустой email
        submitFormAndVerifyError(entity, "Обязательно к заполнению");
    }

    @Test(groups = "validation")
    public void testLoginWithInvalidEmailFormat() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("invalidemail"); // Неверный формат email
        submitFormAndVerifyError(entity, "Введите верный Email");
    }

    // Тесты для поля "Пароль"
    @Test(groups = "validation")
    public void testLoginWithEmptyPassword() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setPassword(""); // Пустой пароль
        submitFormAndVerifyError(entity, "Обязательно к заполнению");
    }

    @Test(groups = "validation")
    public void testLoginWithShortPassword() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setPassword("short"); // Короткий пароль
        submitFormAndVerifyError2(entity,"не правильный логин или пароль");
    }

    // Тесты для комбинации email и пароля
    @Test(groups = "validation")
    public void testLoginWithInvalidCredentials() {
        LoginEntity entity = randomUtils.generateRandomLoginEntity();
        entity.setEmail("invalidemail@example.com");
        entity.setPassword("InvalidPassword123!");
        submitFormAndVerifyError2(entity, "не правильный логин или пароль");
    }

    @Test(groups = "validation")
    public void testLoginWithEmptyFields() {
        LoginEntity entity = new LoginEntity(); // Поля email и пароль пустые
        entity.setEmail("");
        entity.setPassword("");
        submitFormAndVerifyError(entity, "Обязательно к заполнению");
    }

    @Test
    public void testTextLogin1() {
        String expectedText3 = "Готовы к новым приключениям?";

        Assert.assertTrue(getLoginPage().isTextLogin1Correct(expectedText3),
                "Текст для Login1 не соответствует ожидаемому тексту.");
    }

    @Test
    public void testTextLogin2() {
        String expectedText4 = "Войдите и начнем!";

        Assert.assertTrue(getLoginPage().isTextLogin2Correct(expectedText4),
                "Текст для Login2 не соответствует ожидаемому тексту.");
    }
}

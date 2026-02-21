package iZDE.ProfileTest;

import com.izde.entities.iZDE.ChangePasswordEntity;
import com.izde.entities.iZDE.LoginEntity;
import com.izde.enums.iZDE.Endpoints;
import com.izde.utils.ConfigReader;
import iZDE.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("iZDE Web")
@Feature("Смена пароля")
public class ChangePasswordTest extends BaseTest {

    private String currentPassword = ConfigReader.getValue("password");

    @BeforeMethod
    public void loginOnce() {
        LoginEntity loginEntity = randomUtils.validLoginEntity();
        loginEntity.setPassword(currentPassword);
        browserHelper.open(ConfigReader.getValue("baseURL") + Endpoints.SIGNIN.getEndpoint());
        getLoginPage().fillUpLoginForm(loginEntity);
        getMainMenuPage().clickProfileButton();
        getPrivateProfilePage().clickChangePassword();
    }

    @Test(groups = "validation")
    public void testChangePasswordWithEmptyFields() {
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword("");
        entity.setRepeatNewPassword("");

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Обязательно к заполнению"),
                "Ожидаемая ошибка: Обязательно к заполнению, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordTooShort() {
        String shortPassword = "short";
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword(shortPassword);
        entity.setRepeatNewPassword(shortPassword);

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароль должен содержать не менее 8 символов"),
                "Ожидаемая ошибка: Пароль должен содержать не менее 8 символов, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordOnlyLetters() {
        String passwordOnlyLetters = "passwordonly";
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword(passwordOnlyLetters);
        entity.setRepeatNewPassword(passwordOnlyLetters);

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароль должен содержать хотя бы одну цифру"),
                "Ожидаемая ошибка: Пароль должен содержать хотя бы одну цифру, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordOnlyDigits() {
        String passwordOnlyDigits = "12345678";
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword(passwordOnlyDigits);
        entity.setRepeatNewPassword(passwordOnlyDigits);

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароль должен содержать хотя бы одну букву латиницы"),
                "Ожидаемая ошибка: Пароль должен содержать хотя бы одну букву латиницы, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordWithoutSpecialChars() {
        String passwordWithoutSpecialChars = "Password123";
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword(passwordWithoutSpecialChars);
        entity.setRepeatNewPassword(passwordWithoutSpecialChars);

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароль должен содержать хотя бы один специальный символ: !@#$%^&*(),.?\":{}|<>"),
                "Ожидаемая ошибка: Пароль должен содержать хотя бы один специальный символ, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordWithSpaces() {
        String passwordWithSpaces = "Password 123!";
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword(passwordWithSpaces);
        entity.setRepeatNewPassword(passwordWithSpaces);

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароль не должен содержать пробелы"),
                "Ожидаемая ошибка: Пароль не должен содержать пробелы, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testChangePasswordWithEmptyRepeatPassword() {
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword("NewPassword123!");
        entity.setRepeatNewPassword("");

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Обязательно к заполнению"),
                "Ожидаемая ошибка: Обязательно к заполнению, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordMismatch() {
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword("NewPassword123!");
        entity.setRepeatNewPassword("AnotherPassword123!");

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароли должны совпадать"),
                "Ожидаемая ошибка: Пароли должны совпадать, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testNewPasswordNoUppercase() {
        String passwordWithoutUppercase = "password123!";
        ChangePasswordEntity entity = new ChangePasswordEntity();
        entity.setCurrentPassword(currentPassword);
        entity.setNewPassword(passwordWithoutUppercase);
        entity.setRepeatNewPassword(passwordWithoutUppercase);

        getChangePasswordPage().fillUpChangePasswordForm(entity);

        String errorMessage = getChangePasswordPage().getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Пароль должен содержать хотя бы одну заглавную букву латиницы"),
                "Ожидаемая ошибка: Пароль должен содержать хотя бы одну заглавную букву латиницы, получено: " + errorMessage);
    }

    @Test(groups = "validation")
    public void testChangePasswordPlaceholders() {
        getChangePasswordPage().verifyFormChangePasswordPlaceholders();
        Allure.step("Проверка плейсхолдеров полей Введите текущий пароль, Введите новый пароль, Повторите пароль");
    }
}

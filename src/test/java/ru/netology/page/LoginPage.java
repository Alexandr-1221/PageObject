package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement fieldLogin = $("[data-test-id=login] input");
    private SelenideElement fieldPassword = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage valid(DataHelper.AuthInfo info) {
        fieldLogin.setValue(info.getLogin());
        fieldPassword.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

}

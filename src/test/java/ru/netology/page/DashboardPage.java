package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement buttonCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private SelenideElement buttonCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    private SelenideElement notification = $("[data-test-id=error-notification]").$(withText("Ошибка"));
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage transferToTheCard(int card) {
        if (card == 1) {
            buttonCard1.click();
        }
        else if (card == 2) {
            buttonCard2.click();
        }
        return new TransferPage();
    }

    private int findBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getCardBalance(String id) {
        val text = $("[data-test-id='" + id + "']").text();
        return findBalance(text);
    }

    public SelenideElement getNotificationVisible() {
        return notification.shouldBe(visible);
    }

}

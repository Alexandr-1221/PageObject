package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.valid(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void asserting() {
        val dashboardPage = new DashboardPage();
        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.cardFirst().getCardId());
        int secondCardBalance = dashboardPage.getCardBalance(DataHelper.cardSecond().getCardId());
        if (firstCardBalance != secondCardBalance) {
            int average = (firstCardBalance - secondCardBalance) / 2;
            if (firstCardBalance < secondCardBalance) {
                val transferPage = dashboardPage.transferToTheCard(1);
                transferPage.transfer(Integer.toString(average), DataHelper.cardSecond().getCardNumber());
            }
            else {
                val transferPage = dashboardPage.transferToTheCard(2);
                transferPage.transfer(Integer.toString(average), DataHelper.cardFirst().getCardNumber());
            }
        }
    }

    @Test
    void shouldTransferToTheFirstCard() {
        val dashboardPage = new DashboardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance(DataHelper.cardFirst().getCardId()) + 1;
        int expectedSecondCardBalance = dashboardPage.getCardBalance(DataHelper.cardSecond().getCardId()) - 1;
        val transferPage = dashboardPage.transferToTheCard(1);
        transferPage.transfer("1", DataHelper.cardSecond().getCardNumber());
        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.cardFirst().getCardId());
        int secondCardBalance = dashboardPage.getCardBalance(DataHelper.cardSecond().getCardId());
        assertEquals(expectedFirstCardBalance, firstCardBalance);
        assertEquals(expectedSecondCardBalance, secondCardBalance);
    }

    @Test
    void shouldTransferToTheSecondCard() {
        val dashboardPage = new DashboardPage();
        int expectedFirstCardBalance = 0;
        int expectedSecondCardBalance = 20000;
        val transferPage = dashboardPage.transferToTheCard(2);
        transferPage.transfer("10000", DataHelper.cardFirst().getCardNumber());
        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.cardFirst().getCardId());
        int secondCardBalance = dashboardPage.getCardBalance(DataHelper.cardSecond().getCardId());
        assertEquals(expectedFirstCardBalance, firstCardBalance);
        assertEquals(expectedSecondCardBalance, secondCardBalance);
    }

    @Test
    void shouldTransferOverLimit() {
        val dashboardPage = new DashboardPage();
        val transferPage = dashboardPage.transferToTheCard(2);
        transferPage.transfer("10001", DataHelper.cardFirst().getCardNumber());
        val dashboardPageWithNotification = new DashboardPage();
        dashboardPageWithNotification.getNotificationVisible();
    }
}

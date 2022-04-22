package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;

public class TestWithFaker {

    private RegistrationInfo info;

    @BeforeEach
    void SetUpAll() {
        info = DataGenerator
                .Registration
                .generateInfo("ru");
    }

    @Test
    void shouldSendForm() {
        String date = getDate(3, "dd.MM.yyyy");

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//span[@data-test-id='city']//input").val(info.getCity());
        $x("//span[@data-test-id='name']//input").val(info.getName());
        $x("//span[@data-test-id='phone']//input").val(info.getPhone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//button[contains(., 'Запланировать')]").click();
        $x("//*[contains(text(),'Встреча успешно запланирована на')][contains(.,'" + date + "')]")
                .should(Condition.visible, Duration.ofSeconds(15));



        $x("//button[contains(., 'Запланировать')]").click();
        $x("//*[contains(text(),'Необходимо подтверждение')]")
                .should(Condition.visible, Duration.ofSeconds(15));



        $x("//button[contains(., 'Перепланировать')]").click();
        $x("//*[contains(text(),'Встреча успешно запланирована на')][contains(.,'" + date + "')]")
                .should(Condition.visible, Duration.ofSeconds(15));
    }


    private String getDate(Integer plusDays, String formatArg) {
        LocalDate date = LocalDate.now().plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatArg);
        return date.format(formatter);
    }
}

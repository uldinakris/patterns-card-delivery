package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @Test
    public void shouldSendForm() {
        LocalDate date = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateForCal = date.format(formatter);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type='text']").val("Оренбург");
        $x("//span[contains(text(), 'Фамилия')]/ancestor::span[2]//input").val("Иванов Сергей");
        $$x("//input[@type='tel']").get(1).val("+79856432174");
        $x("//label[@data-test-id='agreement']").click();
        $(withText("Забронировать")).click();

        $x("//*[contains(text(),'Встреча успешно забронирована на')][contains(.,'" + dateForCal + "')]")
                .should(Condition.visible, Duration.ofSeconds(15));
    }
}

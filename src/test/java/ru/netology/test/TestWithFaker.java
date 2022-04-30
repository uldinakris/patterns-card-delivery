package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.test.DateUtil.getDate;
import static ru.netology.test.DateUtil.getTime;

public class TestWithFaker {

    private RegistrationInfo info;

    @BeforeEach
    void setUpAll() {
        info = DataGenerator
                .Registration
                .generateInfo("ru");
    }

    @Test
    void shouldSendForm() {
        String date = getDate(3, "dd.MM.yyyy");
        String time = getTime(40);

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

        $x("//span[@data-test-id='date']//input").click();

        ElementsCollection nextDateCellCollection = $$x("//td[@data-day='"+time+"']");
        if (nextDateCellCollection.size() != 0) {
            nextDateCellCollection.get(0).click();
        } else {
            $x("//div[contains(@class, 'calendar__arrow')][@data-step='1']").click();
            $x("//td[@data-day='"+time+"']").click();
        }

        $x("//button[contains(., 'Перепланировать')]").click();
        $x("//*[contains(text(),'Встреча успешно запланирована на')][contains(.,'" + date + "')]")
                .should(Condition.visible, Duration.ofSeconds(15));
    }
}

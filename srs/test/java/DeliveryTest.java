import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");

        String deliveryDate = LocalDate.now().plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A", Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + deliveryDate))
                .shouldBe(Condition.visible);
    }
}
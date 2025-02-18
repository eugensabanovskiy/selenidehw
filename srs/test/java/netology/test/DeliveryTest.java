import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
      
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
    @Test
    void shouldSelectCityFromDropdown() {
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item__control").findBy(Condition.text("Москва")).click();
    }
    @Test
    void shouldSelectDateFromCalendar() {
        $("[data-test-id=date] input").click();
        LocalDate targetDate = LocalDate.now().plusDays(7);
        String targetDay = String.valueOf(targetDate.getDayOfMonth());
        $$(".calendar__day").findBy(Condition.text(targetDay)).click();
    }
}
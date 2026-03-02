package tpo_lab1.domain;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import tpo_lab1.domain.actions.EnterAction;
import tpo_lab1.domain.actions.ObserveAction;
import tpo_lab1.domain.actions.TouchAction;
import tpo_lab1.domain.model.*;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Доменная модель")
@Story("Сценарий и правила домена")
@Link(name = "Требование: доменная модель", url = "https://example.com/req/domain")
@Severity(SeverityLevel.CRITICAL)
class DomainModelTest {

    @Test
    @Story("Счастливый путь сценария — корректные состояния")
    void scenario_happyPath_statesCorrect() {
        Person arthur = new Person("Артур");
        Person man = new Person("Человек");
        man.makeTwoHeaded();

        Chair chair = new Chair();
        chair.setOccupied(true);

        ControlPanel panel = new ControlPanel();

        Scene scene = new Scene(arthur, man, chair, panel);

        man.pickTeethOfRightHeadWithLeftHand();
        man.smileLeftHead();

        Scenario scenario = new Scenario()
                .add(new EnterAction())
                .add(new ObserveAction())
                .add(new TouchAction());

        scenario.run(scene);

        assertTrue(scene.getArthur().isInRoom());
        assertTrue(scene.getArthur().isShocked());
        assertTrue(scene.getArthur().isJawDropped());

        assertTrue(scene.getChair().isOccupied());
        assertTrue(scene.getControlPanel().isLegsOnPanel());

        assertTrue(scene.getStrangeMan().getRightHead().isBusy());
        assertTrue(scene.getStrangeMan().getLeftHead().isSmiling());

        assertTrue(scene.getWeirdThingsCount() >= 2);
    }

    @Test
    @Story("Правило: нельзя чистить зубы без левой руки")
    @Severity(SeverityLevel.BLOCKER)
    void domainRule_cannotPickTeethWithoutLeftHand() {
        Person man = new Person("Человек");
        man.makeTwoHeaded();
        man.setHasLeftHand(false);

        assertThrows(DomainException.class, man::pickTeethOfRightHeadWithLeftHand);
    }

    @Test
    @Story("Правило: действия двухголового только для двухголового")
    void domainRule_cannotUseTwoHeadedActionsIfNotTwoHeaded() {
        Person man = new Person("Человек");

        assertThrows(DomainException.class, man::smileLeftHead);
        assertThrows(DomainException.class, man::pickTeethOfRightHeadWithLeftHand);
    }

    @Test
    @Story("Персона с пустым именем — исключение")
    void personWithEmptyName_shouldThrow() {
        assertThrows(DomainException.class,
                () -> new Person(""));
    }

}
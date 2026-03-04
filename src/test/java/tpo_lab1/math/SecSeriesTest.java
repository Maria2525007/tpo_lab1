package tpo_lab1.math;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Математика")
@Story("Функция sec — ряд и граничные случаи")
@Link(name = "Требование: sec(x)", url = "https://se.ifmo.ru/courses/testing")
@Severity(SeverityLevel.CRITICAL)
class SecSeriesTest {

    private static final double DELTA = 1e-6;
    private static final double RANDOM_DELTA = 1e-4;

    @Test
    @Story("Значение в нуле")
    void testZero() {
        assertEquals(1.0, SecSeries.sec(0.0), DELTA);
    }

    @Test
    @Story("Значение в π")
    void testPi() {
        assertEquals(-1.0, SecSeries.sec(Math.PI), DELTA);
    }

    @Test
    @Story("Значение в π/3")
    void testPiOverThree() {
        double expected = 1 / Math.cos(Math.PI / 3);
        assertEquals(expected, SecSeries.sec(Math.PI / 3), DELTA);
    }

    @Test
    @Story("Разрыв в π/2")
    @Severity(SeverityLevel.BLOCKER)
    void testNearDiscontinuity() {
        assertThrows(ArithmeticException.class,
                () -> SecSeries.sec(Math.PI / 2));
    }

    @Test
    @Story("Обработка NaN")
    void testNaN() {
        assertTrue(Double.isNaN(SecSeries.sec(Double.NaN)));
    }

    @Test
    @Story("Обработка бесконечности")
    void testInfinity() {
        assertThrows(ArithmeticException.class,
                () -> SecSeries.sec(Double.POSITIVE_INFINITY));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2.0, -1.0, -0.5, 0.5, 1.0, 2.0})
    @Story("Параметризованные значения")
    void testMultipleValues(double x) {
        double expected = 1 / Math.cos(x);
        assertEquals(expected, SecSeries.sec(x), DELTA);
    }

    @Test
    @Story("Сравнение с Math.cos (случайные точки)")
    void randomTestAgainstMathCos() {
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            double x = -5 + 10 * random.nextDouble();

            if (Math.abs(Math.cos(x)) < 1e-8) {
                continue;
            }

            double expected = 1 / Math.cos(x);
            double actual = SecSeries.sec(x);

            assertEquals(expected, actual, RANDOM_DELTA);
        }
    }
}
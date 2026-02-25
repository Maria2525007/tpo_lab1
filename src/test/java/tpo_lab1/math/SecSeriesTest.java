package tpo_lab1.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SecSeriesTest {

    private static final double DELTA = 1e-6;

    @Test
    void testZero() {
        assertEquals(1.0, SecSeries.sec(0.0), DELTA);
    }

    @Test
    void testPi() {
        assertEquals(-1.0, SecSeries.sec(Math.PI), DELTA);
    }

    @Test
    void testPiOverThree() {
        double expected = 1 / Math.cos(Math.PI / 3);
        assertEquals(expected, SecSeries.sec(Math.PI / 3), DELTA);
    }

    @Test
    void testNearDiscontinuity() {
        assertThrows(ArithmeticException.class,
                () -> SecSeries.sec(Math.PI / 2));
    }

    @Test
    void testNaN() {
        assertTrue(Double.isNaN(SecSeries.sec(Double.NaN)));
    }

    @Test
    void testInfinity() {
        assertThrows(ArithmeticException.class,
                () -> SecSeries.sec(Double.POSITIVE_INFINITY));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-2.0, -1.0, -0.5, 0.5, 1.0, 2.0})
    void testMultipleValues(double x) {
        double expected = 1 / Math.cos(x);
        assertEquals(expected, SecSeries.sec(x), DELTA);
    }

    @Test
    void randomTestAgainstMathCos() {
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            double x = -5 + 10 * random.nextDouble();

            if (Math.abs(Math.cos(x)) < 1e-8) {
                continue; // пропускаем точки разрыва
            }

            double expected = 1 / Math.cos(x);
            double actual = SecSeries.sec(x);

            assertEquals(expected, actual, 1e-4);
        }
    }
}
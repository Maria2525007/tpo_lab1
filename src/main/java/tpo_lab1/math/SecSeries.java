package tpo_lab1.math;

public class SecSeries {

    private static final double EPS = 1e-10;
    private static final int MAX_ITER = 100;

    public static double sec(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }

        if (Double.isInfinite(x)) {
            throw new ArithmeticException("sec(x) undefined for infinite x");
        }

        double cos = cosSeries(x);

        if (Math.abs(cos) < EPS) {
            throw new ArithmeticException("sec(x) undefined when cos(x) = 0");
        }

        return 1.0 / cos;
    }

    private static double cosSeries(double x) {
        // Приводим x к диапазону [-π, π] для лучшей сходимости
        x = x % (2 * Math.PI);
        if (x > Math.PI) {
            x -= 2 * Math.PI;
        }
        if (x < -Math.PI) {
            x += 2 * Math.PI;
        }

        double term = 1.0;
        double sum = 1.0;

        for (int k = 1; k < MAX_ITER; k++) {
            term *= -1 * x * x / ((2 * k - 1) * (2 * k));
            sum += term;

            if (Math.abs(term) < EPS) {
                break;
            }
        }

        return sum;
    }
}
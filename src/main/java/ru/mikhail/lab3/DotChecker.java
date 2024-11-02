package ru.mikhail.lab3;

public class DotChecker {


    public static boolean checkDot(float x, float y, float r) {

        return checkFirstQuarter(x, y, r) || checkSecondQuarter() ||
                checkThirdQuarter(x, y, r) || checkFourthQuarter(x, y, r);
    }

    // Первая четверть (треугольник, масштаб R/2)
    static boolean checkFirstQuarter(float x, float y, float r) {
        if (x > 0 && y > 0) {
            // Треугольник: y <= -x + R/2
            return (y <= (r / 2) - x / 2);

        }
        return false;
    }


    static boolean checkSecondQuarter() {
        // Вторая четверть (нет области)
        return false;
    }


    static boolean checkThirdQuarter(float x, float y, float r) {
        // Третья четверть (прямоугольник)
        if (x <= 0 && y <= 0) {
            // Прямоугольник: x >= -R/2 и y >= -R
            return (x >= (-r / 2) && y >= -r);
        }
        return false;
    }

    static boolean checkFourthQuarter(float x, float y, float r) {
        // Четвёртая четверть (четверть окружности радиуса R)
        if (x >= 0 && y <= 0) {
            // Условие: x^2 + y^2 <= R^2
            return (x * x + y * y <= r * r);
        }
        return false;
    }


}

package ru.mikhail.lab3;

public class DotChecker {


    public static boolean checkDot(float x, float y, float r) {

        return checkFirstQuarter(x, y, r) || checkSecondQuarter() ||
                checkThirdQuarter(x, y, r) || checkFourthQuarter(x, y, r);
    }


    static boolean checkFirstQuarter(float x, float y, float r) {
        if (x > 0 && y > 0) {
            // Треугольник со сторонами R/2
            return (y <= (-x +r/2));

        }
        return false;
    }


    static boolean checkSecondQuarter() {

        return false;
    }


    static boolean checkThirdQuarter(float x, float y, float r) {
        if (x <= 0 && y <= 0) {
            // Квадрат
            return (x >= -r && y >= -r);
        }
        return false;
    }

    static boolean checkFourthQuarter(float x, float y, float r) {
        if (x >= 0 && y <= 0) {
            // Условие: x^2 + y^2 <= R^2
            return (x * x + y * y <= r * r);
        }
        return false;
    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Pablo JS dos Santos
 */
public strictfp class Calculator {
    private double value;
    private RoundingMode roundingMode;
    private int decimals;
    private boolean autoRound;
    private boolean nanAsZero;

    /**
     * Creates a new ExMath with 2 decimals and half up rounding mode. autoRound = true
     */
    public Calculator() {
        this(0, RoundingMode.HALF_UP);
    }

    public Calculator(double value) {
        this(value, RoundingMode.HALF_UP);
    }

    public Calculator(double value, RoundingMode roundingMode) {
        this(value, roundingMode, 2, true);
    }

    public Calculator(double value, RoundingMode roundingMode, int decimals) {
        this(value, roundingMode, decimals, true);
    }

    public Calculator(double value, RoundingMode roundingMode, int decimals, boolean autoRound) {
        this(value, roundingMode, decimals, autoRound, false);
    }

    public Calculator(double value, RoundingMode roundingMode, int decimals, boolean autoRound, boolean nanAsZero) {
        this.value = value;
        this.roundingMode = roundingMode;
        this.decimals = decimals;
        this.autoRound = autoRound;
        this.nanAsZero = nanAsZero;

        if (nanAsZero && Double.isNaN(value)) {
            this.value = 0;
        }
    }

    public boolean isNaN() {
        return Double.isNaN(this.value);
    }

    public boolean isNanAsZero() {
        return nanAsZero;
    }

    public void setNanAsZero(boolean nanAsZero) {
        this.nanAsZero = nanAsZero;
    }

    public Double getValue(Double defaultValue) {
        if (Calculator.isInvalid(this.value)) {
            return defaultValue;
        }

        return this.value;
    }

    public double getValue() {
        return value;
    }

    public double getValue(int decimals, RoundingMode roundingMode) {
        return Calculator.round(this.value, decimals, roundingMode);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public boolean isAutoRound() {
        return autoRound;
    }

    public void setAutoRound(boolean autoRound) {
        this.autoRound = autoRound;
    }

    public Calculator plus(Calculator math) {
        return this.plus(math.getValue());
    }

    public Calculator plus(double value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        BigDecimal a = BigDecimal.valueOf(this.value);
        BigDecimal b = BigDecimal.valueOf(value);

        BigDecimal c = a.add(b);
        double result = c.doubleValue();

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, roundingMode, decimals, autoRound, this.nanAsZero);
    }

    public Calculator power(Calculator math) {
        return this.power((int) math.getValue());
    }

    public Calculator power(int value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        BigDecimal a = BigDecimal.valueOf(this.value);
        double result = a.pow(value, MathContext.UNLIMITED).doubleValue();

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, roundingMode, decimals, autoRound, this.nanAsZero);
    }

    public Calculator power(double value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        double result = Math.pow(this.value, value);

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, roundingMode, decimals, autoRound, this.nanAsZero);
    }

    public Calculator restDivision(double value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        double result = this.value % value;

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, roundingMode, decimals, autoRound, this.nanAsZero);
    }

    public Calculator minus(Calculator math) {
        return this.minus(math.getValue());
    }

    public Calculator minus(double value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        BigDecimal a = BigDecimal.valueOf(this.value);
        BigDecimal b = BigDecimal.valueOf(value);

        BigDecimal c = a.subtract(b);
        double result = c.doubleValue();

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, roundingMode, decimals, autoRound, this.nanAsZero);
    }

    public Calculator multiply(Calculator math) {
        return this.multiply(math.getValue());
    }

    public Calculator multiply(double value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        BigDecimal a = BigDecimal.valueOf(this.value);
        BigDecimal b = BigDecimal.valueOf(value);

        BigDecimal c = a.multiply(b);
        double result = c.doubleValue();

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, roundingMode, decimals, autoRound, this.nanAsZero);
    }

    public Calculator divide(Calculator math) {
        return this.divide(math.getValue());
    }

    public Calculator divide(double value) {
        if (this.nanAsZero && Double.isNaN(value)) {
            value = 0;
        }

        if (isInvalid(value) || isInvalid(this.value)) {
            return new Calculator(Double.NaN, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
        }

        BigDecimal a = BigDecimal.valueOf(this.value);
        BigDecimal b = BigDecimal.valueOf(value);

        BigDecimal c = a.divide(b, this.decimals,
                this.roundingMode == RoundingMode.UNNECESSARY ? RoundingMode.DOWN : this.roundingMode);

        double result = c.doubleValue();

        if (this.autoRound) {
            result = Calculator.round(result, this.decimals, this.roundingMode);
        }

        return new Calculator(result, this.roundingMode, this.decimals, this.autoRound, this.nanAsZero);
    }

    @Override
    public String toString() {
        return Calculator.toString(this.value);
    }

    public void round(int decimals, RoundingMode roundingMode) {
        this.value = Calculator.round(this.value, decimals, roundingMode);
    }

    public int getIntValue() {
        return getIntValue(this.value);
    }

    public static int getIntValue(double value) {
        return (int) value;
    }

    public static boolean isInvalid(double value) {
        return Double.isNaN(value) || Double.isInfinite(value);
    }

    public static double round(double rounding, int decimals, RoundingMode roundingMode) {
        if (isInvalid(rounding)) {
            return Double.NaN;
        }

        if (roundingMode == RoundingMode.UNNECESSARY) {
            roundingMode = RoundingMode.DOWN;
        }

        BigDecimal b = BigDecimal.valueOf(rounding);
        b = b.setScale(decimals, roundingMode);

        return b.doubleValue();
    }

    public static boolean isInRange(double rangeStart, double rangeEnd, double value) {
        if (value < rangeStart) {
            return false;
        }

        return value <= rangeEnd;
    }

    public static double getClosest(double target, double... options) {
        if (isInvalid(target)) {
            return Double.NaN;
        }

        double closest = Double.NaN;
        double gap = Double.NaN;

        for (double option : options) {
            double gap1 = target - option;
            double gap2 = option - target;

            double minGap = module(Math.min(gap1, gap2));

            if (Double.isNaN(gap) || minGap < gap) {
                closest = option;
                gap = minGap;
            }
        }

        return closest;
    }

    public static double getFarther(double target, double... options) {
        if (isInvalid(target)) {
            return Double.NaN;
        }

        double farther = Double.NaN;
        double gap = Double.NaN;

        for (double option : options) {
            double gap1 = target - option;
            double gap2 = option - target;

            double maxGap = module(Math.max(gap1, gap2));

            if (Double.isNaN(gap) || maxGap > gap) {
                farther = option;
                gap = maxGap;
            }
        }

        return farther;
    }

    public static double module(double value) {
        return value < 0 ? -value : value;
    }

    /**
     * If d is NaN returns 0 else returns d
     * Double.isNaN(d) ? 0 : d;
     */
    public static double notNaN(double d) {
        return isInvalid(d) ? 0 : d;
    }

    /**
     * If value is null or NaN, returns the defaultValue, else returns value
     */
    public static Double nullOrNaN(Double value, Double defaultValue) {
        return value == null || isInvalid(value) ? defaultValue : value;
    }

    public static int decimalsCount(double value) {
        if (isInvalid(value)) {
            return 0;
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.scale();
    }

    public static double getDecimalsValue(double value) {
        int count = decimalsCount(value);
        Calculator math = new Calculator(value, RoundingMode.DOWN, count);
        return math.minus((int) value).getValue();
    }

    public static DecimalFormat getPreferredFormater(double value, int minDecimals) {
        int decimalsCount = Calculator.decimalsCount(value);
        if (decimalsCount == 1) {
            double decimals = Calculator.getDecimalsValue(value);
            if (decimals == 0) {
                decimalsCount = 0;
            }
        }

        int decimals = Math.max(minDecimals, decimalsCount);

        StringBuilder builder = new StringBuilder("0");
        for (int i = 0; i < decimals; i++) {
            if (builder.indexOf(".") == -1) {
                builder.append(".");
            }

            builder.append("0");
        }

        return new DecimalFormat(builder.toString());
    }

    public static String toString(float value) {
        return toString(Double.parseDouble(Float.toString(value)));
    }

    public static String toString(double value) {
        if (isInvalid(value)) {
            return Double.toString(value);
        }

        DecimalFormat formater = getPreferredFormater(value, 0);
        return formater.format(value);
    }

    public static String toString(double value, String format) {
        return new DecimalFormat(format).format(value);
    }

    public static double plus(double... values) {
        Calculator math = new Calculator(0, RoundingMode.DOWN, 2);

        for (double value : values) {
            math = math.plus(value);
        }

        return math.getValue();
    }

    public static double plus(int decimals, RoundingMode mode, double... values) {
        Calculator math = new Calculator(0, mode, decimals);

        for (double value : values) {
            math = math.plus(value);
        }

        return math.getValue();
    }

    public static double toDouble(float value) {
        return Double.parseDouble(Float.toString(value));
    }

    public static Calculator set(double value) {
        return new Calculator(value, RoundingMode.HALF_DOWN, 5, false, true);
    }
}

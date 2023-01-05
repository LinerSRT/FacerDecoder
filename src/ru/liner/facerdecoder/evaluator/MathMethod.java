package ru.liner.facerdecoder.evaluator;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import ru.liner.facerdecoder.anroidwrapper.*;
import ru.liner.facerdecoder.utils.MathUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 02.01.2023, понедельник
 **/
public enum MathMethod {
    ROUND(new Function("round") {
        @Override
        public double apply(double... doubles) {
            return Math.round(doubles[0]);
        }
    }),
    FLOOR(new Function("floor") {
        @Override
        public double apply(double... doubles) {
            return Math.floor(doubles[0]);
        }
    }),
    PAD(new Function("pad", 2) {
        @Override
        public double apply(double... doubles) {
            if (doubles.length < 2)
                return 0f;
            double value = doubles[0];
            double digits = doubles[1];
            return Double.parseDouble(String.format("%0"+digits+"d", value));
        }
    }),
    SIN(new Function("sin") {
        @Override
        public double apply(double... doubles) {
            return Math.sin(doubles[0]);
        }
    }),
    COS(new Function("cos") {
        @Override
        public double apply(double... doubles) {
            return Math.cos(doubles[0]);
        }
    }),
    RAND(new Function("rand", 2) {
        @Override
        public double apply(double... doubles) {
            if (doubles.length < 2)
                return 0f;
            double upper = Math.max(doubles[1], doubles[0]);
            double lower = Math.min(doubles[1], doubles[0]);
            return Math.round((Math.random() * Math.abs(upper - lower)) + lower);
        }
    }),
    RAND_STATE(new Function("stRand", 2) {
        @Override
        public double apply(double... doubles) {
            if (doubles.length < 2)
                return 0f;
            double upper = Math.max(doubles[1], doubles[0]);
            double lower = Math.min(doubles[1], doubles[0]);
            return Math.round((Math.random() * Math.abs(upper - lower)) + lower);
        }
    }),
    RAND_WAKE(new Function("wakeRand", 2) {
        @Override
        public double apply(double... doubles) {
            if (doubles.length < 2)
                return 0f;
            double upper = Math.max(doubles[1], doubles[0]);
            double lower = Math.min(doubles[1], doubles[0]);
            return Math.round((Math.random() * Math.abs(upper - lower)) + lower);
        }
    }),
    RAD(new Function("rad") {
        @Override
        public double apply(double... doubles) {
            return Math.toRadians(doubles[0]);
        }
    }),
    DEG(new Function("deg") {
        @Override
        public double apply(double... doubles) {
            return Math.toDegrees(doubles[0]);
        }
    }),
    CLAMP(new Function("clamp", 3) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            double value = doubles[0];
            double min = doubles[1];
            double max = doubles[2];
            return Math.max(min, Math.min(max, value));
        }
    }),
    ORBITX(new Function("orbitX", 2) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 2)
                return 0f;
            return Math.cos(doubles[0]) * doubles[1];
        }
    }),
    ORBITY(new Function("orbitY", 2) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 2)
                return 0f;
            return Math.sin(doubles[0]) * doubles[1];
        }
    }),
    INTERP_LINEAR(new Function("interpLinear", 3) {
        private final Interpolator interpolator = new LinearInterpolator();

        @Override
        public double apply(double... doubles) {
            return calculateInterpolation(interpolator, doubles);
        }
    }),
    INTERP_ACCEL(new Function("interpAccel", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            return calculateInterpolation(new AccelerateInterpolator((float) doubles[3]), doubles);
        }
    }),
    INTERP_DECEL(new Function("interpDecel", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            return calculateInterpolation(new DecelerateInterpolator((float) doubles[3]), doubles);
        }
    }),
    INTERP_ACCELDECEL(new Function("interpAccelDecel", 3) {
        private final Interpolator interpolator = new AccelerateDecelerateInterpolator();

        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            return calculateInterpolation(interpolator, doubles);
        }
    }),
    INTERP_ANTICIPATE(new Function("interpAnticipate", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            return calculateInterpolation(new AccelerateInterpolator((float) doubles[3]), doubles);
        }
    }),
    INTERP_OVERSHOOT(new Function("interpOvershoot", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            return calculateInterpolation(new OvershootInterpolator((float) doubles[3]), doubles);
        }
    }),
    INTERP_ANTICIPATEOVERSHOOT(new Function("interpAnticipateOvershoot", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            return calculateInterpolation(new AnticipateOvershootInterpolator((float) doubles[3]), doubles);
        }
    }),
    INTERP_BOUNCE(new Function("interpBounce", 3) {
        private final Interpolator interpolator = new BounceInterpolator();

        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            return calculateInterpolation(interpolator, doubles);
        }
    }),
    INTERP_CYCLE(new Function("interpCycle", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            return calculateInterpolation(new CycleInterpolator((float) doubles[3]), doubles);
        }
    }),
    INTERP_FASTOUTLINEARIN(new Function("interpFastOutLinearIn", 3) {
        private final Interpolator interpolator = new FastOutLinearInInterpolator();

        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            return calculateInterpolation(interpolator, doubles);
        }
    }),
    INTERP_FASTOUTSLOWIN(new Function("interpFastOutSlowIn", 3) {
        private final Interpolator interpolator = new FastOutSlowInInterpolator();

        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            return calculateInterpolation(interpolator, doubles);
        }
    }),
    INTERP_LINEAROUTSLOWIN(new Function("interpLinearOutSlowIn", 3) {
        private final Interpolator interpolator = new LinearOutSlowInInterpolator();

        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            return calculateInterpolation(interpolator, doubles);
        }
    }),
    HEAVISIDE(new Function("heaviside") {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 1)
                return 0f;
            return heaviside(doubles[0]);
        }
    }),
    BOXCAR(new Function("boxcar", 3) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 3)
                return 0f;
            double current = doubles[0];
            double beginning = doubles[1];
            double end = doubles[2];
            return heaviside(current - beginning) - heaviside(current - end);
        }
    }),
    SQUAREWAVE(new Function("squareWave", 4) {
        @Override
        public double apply(double... doubles) {
            if (doubles == null || doubles.length < 4)
                return 0f;
            double current = doubles[0];
            double amplitude = doubles[1];
            double period = doubles[2];
            double xOffset = doubles[3];
            return Math.signum(Math.sin(((Math.PI * 2) * (current - xOffset)) / period)) * amplitude;
        }
    }),
    GYRO_X(new Function("gyroX") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    GYRO_Y(new Function("gyroY") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    GYRO_ACCEL_X(new Function("gyroRawX") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    GYRO_ACCEL_Y(new Function("gyroRawY") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    ACCEL_X(new Function("accelerometerX") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    ACCEL_Y(new Function("accelerometerY") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    ACCEL_ACCEL_X(new Function("accelerometerRawX") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    }),
    ACCEL_ACCEL_Y(new Function("accelerometerRawY") {
        @Override
        public double apply(double... doubles) {
            return 0;
        }
    });
    private final String name;
    private final Function function;

    MathMethod(Function function) {
        this.function = function;
        this.name = function.getName();
    }

    public String getName() {
        return name;
    }

    public static String eval(String text) {
        if (text.contains("VAR")){
            Pattern pattern = Pattern.compile("#VAR_([0-9]*)#");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()){
                text = text.replace(matcher.group(), "0");
            }
        }
        try {
            Expression expression = new ExpressionBuilder(text)
                    .variables("pi", "e")
                    .functions(MathEvaluator.functionList)
                    .build()
                    .setVariable("pi", Math.PI)
                    .setVariable("e", Math.E);
            String result = MathEvaluator.numberFormat.format(expression.evaluate());
            if (result.endsWith(".0"))
                return result.substring(0, result.length() - 2);
            return result;
        } catch (Exception e) {

            return "0";
        }
    }

    public static List<MathMethod> mathMethodList() {
        return Arrays.asList(MathMethod.values());
    }

    public static List<Function> functionList() {
        List<Function> functionList = new ArrayList<>();
        for (MathMethod mathMethod : mathMethodList())
            functionList.add(mathMethod.function);
        return functionList;
    }

    private static double calculateInterpolation(Interpolator interpolator, double... doubles) {
        if (interpolator == null || doubles == null || doubles.length < 3)
            return 0f;
        double current = doubles[0];
        double min = Math.min(doubles[1], doubles[2]);
        double max = Math.max(doubles[1], doubles[2]);
        float interpolatedValue = 0.0f;
        double range = max - min;
        if (Double.doubleToRawLongBits(range) != 0)
            interpolatedValue = (float) MathUtils.clamp((current - min) / range, 0d, 1d);
        return interpolator.getInterpolation(interpolatedValue);
    }

    private static double heaviside(double value) {
        return .5d * (1d - Math.signum(value));
    }
}

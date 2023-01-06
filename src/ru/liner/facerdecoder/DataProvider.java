package ru.liner.facerdecoder;

import java.lang.reflect.Field;

@SuppressWarnings({"unchecked", "unused", "FieldMayBeFinal"})
public class DataProvider {
    @FieldProvider
    private boolean is24HourFormat = true;

    @FieldProvider
    private boolean isCharging = false;

    @FieldProvider
    private boolean isLowPowerMode = false;

    @FieldProvider
    private boolean isScreenRound = true;

    @FieldProvider
    private String unitSystem = "METRIC";

    @FieldProvider
    private String deviceName = "Undefined";

    @FieldProvider
    private String deviceManufacturer = "Undefined";

    @FieldProvider
    private int wifiLevel = 4;

    @FieldProvider
    private int watchfaceShownCount = 26;

    @FieldProvider
    private String weatherUnit = "C";

    @FieldProvider
    private String weatherLocation = "Los Angeles";

    @FieldProvider
    private int todayMaxTemp = 35;

    @FieldProvider
    private int todayMinTemp = 11;

    @FieldProvider
    private int todayCurrentTemp = 33;

    @FieldProvider
    private int currentWeatherConditionIcon = 3;

    @FieldProvider
    private String currentWeatherConditionText = "Fair";

    @FieldProvider
    private int currentHumidityNumber = 40;

    @FieldProvider
    private int stepsCount = 40;

    @FieldProvider
    private int heartRate = 67;

    @FieldProvider
    private int standValue = 5;

    @FieldProvider
    private int standGoalValue = 12;

    @FieldProvider
    private float caloriesValue = 153.99f;

    @FieldProvider
    private float caloriesGoalValue = 300f;

    @FieldProvider
    private int exerciseMinutes = 12;

    @FieldProvider
    private int exerciseGoalMinutes = 30;


    private static boolean isLoggable = false;

    public <T> T get(String key, T defaultValue) {
        long startTime = System.nanoTime();
        for (Field field : getClass().getDeclaredFields()) {
            FieldProvider annotation = field.getAnnotation(FieldProvider.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    if (key.equals(field.getName())) {
                        if (isLoggable)
                            System.out.println("DataProvider: took " + ((System.nanoTime() - startTime) / 1000000f) + "ms to get " + key);
                        return (T) field.get(this);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (isLoggable)
            System.out.println("DataProvider: took " + ((System.nanoTime() - startTime) / 1000000f) + "ms to get " + key);
        return defaultValue;
    }

    public <T> void set(String key, T value) {
        for (Field field : getClass().getDeclaredFields()) {
            FieldProvider annotation = field.getAnnotation(FieldProvider.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    if (key.equals(field.getName()))
                        field.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

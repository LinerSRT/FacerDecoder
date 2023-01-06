package ru.liner.facerdecoder;

public interface Script<I,O> {
    DataProvider getDataProvider();
    void setSettings(DataProvider dataProvider);
    boolean shouldEvaluate(I value);
    O evaluate(I value);
    void update(long currentTimeMillis);
}

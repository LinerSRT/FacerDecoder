package ru.liner.facerdecoder;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ScriptEngine implements Script<String, String> {
    private DataProvider dataProvider;
    private final List<Script<String, String>> evaluatorStack;


    public ScriptEngine() {
        this.evaluatorStack = new ArrayList<>();
        this.dataProvider = new DataProvider();
    }

    @Override
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setSettings(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }


    public ScriptEngine addEvaluator(Script<String, String> evaluator) {
        evaluator.setSettings(getDataProvider());
        this.evaluatorStack.add(evaluator);
        return this;
    }

    public ScriptEngine removeEvaluator(Script<String, String> evaluator) {
        this.evaluatorStack.remove(evaluator);
        return this;
    }

    public <E extends Script<String, String>> E getEvaluator(Class<E> evaluatorClass){
        for(Script<String, String> evaluator: evaluatorStack)
            if(evaluator.getClass().equals(evaluatorClass))
                return (E) evaluator;
        throw new RuntimeException("Cannot allocate specified "+evaluatorClass.getSimpleName()+" in evaluator stack");
    }

    @Override
    public boolean shouldEvaluate(String value) {
        for (Script<String, String> stringStringScript : evaluatorStack)
            if (stringStringScript.shouldEvaluate(value.trim()))
                return true;
        return false;
    }

    @Override
    public String evaluate(String value) {
        if (shouldEvaluate(value)) {
            String evaluationResult = value.trim();
            for (Script<String, String> evaluator : evaluatorStack)
                evaluationResult = evaluator.evaluate(evaluationResult);
            return evaluationResult;
        }
        return value;
    }

    @Override
    public void update(long currentTimeMillis) {
        for (Script<String, String> evaluator : evaluatorStack)
            evaluator.update(currentTimeMillis);
    }
}

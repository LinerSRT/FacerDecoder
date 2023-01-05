package ru.liner.facerdecoder.evaluator;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public class Condition {
    public final String firstValue;
    public final Operator operator;
    public final String secondValue;
    public final String trueValue;
    public final String falseValue;
    public boolean result;

    Condition(String firstValue, Operator operator, String secondValue, String trueValue, String falseValue) {
        this.firstValue = firstValue;
        this.operator = operator;
        this.secondValue = secondValue;
        this.trueValue = trueValue;
        this.falseValue = falseValue;
        this.result = operator.eval(firstValue, secondValue);
    }


    public String result(Condition condition){
        boolean eval = operator == Operator.OR ?  condition.result || result : result && condition.result;
        return eval ? trueValue : falseValue;
    }

    public String result(){
        return result ? trueValue : falseValue;
    }
}
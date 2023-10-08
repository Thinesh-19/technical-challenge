package com.workflow86.technicalchallenge.model;

public class Condition {
    private String condition;
    private Boolean then;
    private ElseIfCondition elseIfCondition;
    private ElseCondition elseCondition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getThen() {
        return then;
    }

    public void setThen(Boolean then) {
        this.then = then;
    }

    public ElseIfCondition getElseIfCondition() {
        return elseIfCondition;
    }

    public void setElseIfCondition(ElseIfCondition elseIfCondition) {
        this.elseIfCondition = elseIfCondition;
    }

    public ElseCondition getElseCondition() {
        return elseCondition;
    }

    public void setElseCondition(ElseCondition elseCondition) {
        this.elseCondition = elseCondition;
    }
}

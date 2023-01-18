package com.uptbal.calculator.model.ast;


public class Parenthesis extends Ast {
    public Ast value;

    public Parenthesis(Ast v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return " Parenthesis(" + value.toString() + ") ";
    }
}

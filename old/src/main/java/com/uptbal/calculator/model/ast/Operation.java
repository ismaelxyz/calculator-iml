package com.uptbal.calculator.model.ast;


public class Operation extends Ast {
    public enum Operator {
        Min,
        Add,
        Mul,
        Div,
        Pow
    }

    public Operator operator;
    public Ast left;
    public Ast right;
    
    public Operation() {  }

    public Operator getOperador() {
        return this.operator;
    }
    
    public Ast getIzquierda() {
        return this.left;
    }
    
    public Ast getDerecha() {
        return this.right;
    }

    public void setOperator(Operator o) {
        this.operator = o;
    }
    public void setIzquierda(Ast i) {
        this.left = i;
    }
    public void setDerecha(Ast d) {
        this.right = d;
    }
    
    @Override
    public String toString() {
        var op = switch (operator) {
            case Add -> "+";
            case Min -> "-";
            case Mul -> "*";
            case Div -> "/";
            case Pow -> "^";
        };
        
        return " Operation(" + left.toString() + " " + op + " " + right.toString() + ") ";
    }
}
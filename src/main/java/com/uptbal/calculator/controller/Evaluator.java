package com.uptbal.calculator.controller;

import com.uptbal.calculator.model.Parser;
import com.uptbal.calculator.model.ast.*;
import com.uptbal.calculator.model.ast.Operation.Operator;

public class Evaluator {
    Ast nodes;

    public Evaluator(String source) {
        Parser p = new Parser(source);
        nodes = p.evalSyntax();
    }

    public Double evaluate() {
        return eval(nodes);
    }

    private Double eval(Ast nodo) {
               
        if (nodo.getClass() == Decimal.class) {
            return ((Decimal) nodo).value;
        
        } else if (nodo.getClass() == Parenthesis.class) {
            return eval(((Parenthesis) nodo).value);
        
        } else if (nodo.getClass() == Operation.class) {
            var operation = (Operation) nodo;
            Double left = eval(operation.left), right = eval(operation.right);
            
            return switch (operation.operator) {
                case Add -> left +  right;
                case Min -> left -  right;
                case Mul -> left * right;
                case Div -> left / right;
                case Pow -> Math.pow(left, right);
            };
            
        }

        return null;
    }
}
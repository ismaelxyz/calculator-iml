package org.imlcalculator.controller;

import org.imlcalculator.model.Token;
import org.imlcalculator.model.Token.TokenType;
import org.imlcalculator.model.ast.*;
import org.imlcalculator.model.ast.Operation.Operator;

public class Parser {
    Lexer lexer;
    Token current;
    Token ahead;

    public Parser(String source) {
        this.lexer = new Lexer(source);        
        
        advance();
        advance();
    }

    public Ast evalSyntax() {
        return expresion(0);
    }

    private void advance() {
        if (current == null || ahead.type != TokenType.Eof) {
            current = ahead;
            ahead = lexer.nextToken();
        }
    }

    private Ast parenthesis() {
        advance();
        var parenthesis = new Parenthesis(expresion(0));
            
        if (ahead.type == TokenType.ParenthesisClose) {
            advance();
            return  parenthesis;
        }
        
        return null;
    }

    private int getPrecedence(TokenType type) {
        return type == TokenType.Add || type == TokenType.Min ? 1
        : type == TokenType.Mul || type == TokenType.Div ? 2
        : type == TokenType.Pow ? 3
        : type == TokenType.ParenthesisOpen ? 4 : 0;
    }
    
    private Ast expresion(int precedence) {
        Double decimal = null;
        Ast res = null;
        
        if (current.type == TokenType.Error
            || ahead.type == TokenType.Error) {
            return null;
        }
        
        if ((current.type == TokenType.Add 
            || current.type == TokenType.Min) 
            && ahead.type == TokenType.Decimal) {
                
            decimal = Double.parseDouble(ahead.value);
            if (current.type == TokenType.Min) {
                decimal = -decimal;
            }
            
            advance();
            
        } else if (current.type == TokenType.ParenthesisOpen) {
            res = parenthesis();
            if (res == null)  return res;
        }
        
        if (current.type == TokenType.Decimal && res == null) {
            if (decimal == null) {
                decimal = Double.parseDouble(current.value);
            }
            
            res = new Decimal(decimal);
        }
        
        while (precedence < getPrecedence(ahead.type)) {
		    advance();
            var operation = new Operation();
            operation.setIzquierda(res);
            
            if (current.type == TokenType.ParenthesisOpen) {
                operation.setOperator(Operator.Mul);   
                operation.setDerecha(parenthesis());
                
            } else {
                operation.setOperator(
                    current.type == TokenType.Pow ? Operator.Pow
                    : current.type == TokenType.Mul ? Operator.Mul
                    : current.type == TokenType.Div ? Operator.Div
                    : current.type == TokenType.Add ? Operator.Add
                    : Operator.Min
                );
                var cprecedence = getPrecedence(current.type);
                advance();
                operation.setDerecha(expresion(cprecedence));
                
            }
            
            res = operation;
	   }
              
        return res;
    }
    
}
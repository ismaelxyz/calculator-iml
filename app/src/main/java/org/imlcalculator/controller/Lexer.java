package org.imlcalculator.controller;

import org.imlcalculator.model.Token;
import org.imlcalculator.model.Token.TokenType;

public class Lexer {
    String source;
    Integer position;

    public Lexer(String f) {
        this.source = f;
        this.position = 0;
    }

    public Token nextToken() {
        String value = "";
        while (first() == ' ') advance();
        
        Character firstChar = this.advance();
        
        TokenType tipo = switch (firstChar) {
        case '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
            value = this.readNumber(firstChar);
            yield TokenType.Decimal;
        }
        case '(' -> TokenType.ParenthesisOpen;
        case ')' -> TokenType.ParenthesisClose;
        // Corchete [ ]
        case '-' -> TokenType.Min;
        case '+' -> TokenType.Add;
        case '*' -> TokenType.Mul;
        case '/' -> TokenType.Div;
        case '^' -> TokenType.Pow;
        case '\0' -> TokenType.Eof;
        default  -> TokenType.Error;
        };

        return new Token(tipo, value);
    }

    private Character advance() {
        return this.position < this.source.length() ?
            this.source.charAt(this.position++) : '\0';
    }

    private Character first() {
        return this.position < this.source.length() ? 
            this.source.charAt(this.position) : '\0';
    }

    private String readInteger() {
        String ret = "";

        while ((int) '0' <= (int) this.first()
            && (int) this.first() <= (int) '9') ret += this.advance();
        
        return ret;

    }

    private String readNumber(Character firstChar) {
        String ret = firstChar.toString();

        if (firstChar != '.') ret += this.readInteger();

        if (this.first() == '.' && firstChar != '.') {
            this.advance();
            ret += ".";
        }

        if (this.first() == 'E') {
            this.advance();
            ret += "e";

            if (this.first() == '-' || this.first() == '+') {
                ret += this.advance();
            }
        }

        ret += this.readInteger();
        
        if (ret.compareTo(".") == 0) ret = "0";
        return ret;
    }
}

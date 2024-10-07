package org.imlcalculator.model;


public class Token {
    public enum TokenType {
        // Decimal,
        Decimal,
        Pow,
        ParenthesisOpen,
        ParenthesisClose,
        // Brackets [ ]
        Min,
        Add,
        Mul,
        Div,
    
        Eof,
        Error
    };

    public TokenType type;
    // Only when "type" is TokenTipo.Decimal
    public String value;

    public Token(TokenType t, String v) {
        this.type = t;
        this.value = v;
    }
}

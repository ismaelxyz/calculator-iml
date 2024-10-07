package org.imlcalculator.model.ast;

public class Decimal extends Ast {
    public Double value;
    
    public Decimal(Double v) {
        this.value = v;
    }
    
    @Override
    public String toString() {
        return " Decimal(" + this.value + ") ";
    }
}

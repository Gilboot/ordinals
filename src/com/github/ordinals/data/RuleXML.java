package com.github.ordinals.data;


/**
 * A object representing a rule with its attributes as they are obtained from a rules XML file
 */
public class RuleXML {
    private final int precedence;
    private final String type;
    private final int value;
    private final String fullname;
    private final int remainder;
    private final int modulus;

    public RuleXML(
            int precedence,
            String type,
            int value,
            String fullname,
            int remainder,
            int modulus
    ) {
        this.precedence = precedence;
        this.type = type;
        this.value = value;
        this.remainder = remainder;
        this.modulus = modulus;
        this.fullname = fullname;
    }

    public int getPrecedence() {
        return precedence;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getFullname() {
        return fullname;
    }

    public int getRemainder() {
        return remainder;
    }

    public int getModulus() {
        return modulus;
    }

    @Override
    public String toString() {
        return "RuleXML{" +
                "precedence=" + precedence +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", fullname='" + fullname + '\'' +
                ", remainder=" + remainder +
                ", modulus=" + modulus +
                '}';
    }
}

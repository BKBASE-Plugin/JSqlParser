/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2013 JSQLParser
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package net.sf.jsqlparser.expression;

import net.sf.jsqlparser.parser.ASTNodeAccessImpl;

/**
 * A string as in 'example_string'
 */
public class StringValue extends ASTNodeAccessImpl implements Expression {


    private final String expressionType = "string_value";

    @Override
    public String getExpressionType() {
        return expressionType;
    }

    private String value = "";
    private String escapedValue;

    public StringValue(String escapedValue) {
        // romoving "'" at the start and at the end
        if (escapedValue.startsWith("'") && escapedValue.endsWith("'")
                || escapedValue.startsWith("\"") && escapedValue.endsWith("\"")) {
            value = escapedValue.substring(1, escapedValue.length() - 1);
        } else {
            value = escapedValue;
        }
        this.escapedValue = escapedValue;
    }

    public String getValue() {
        return value;
    }

    public String getNotExcapedValue() {
        StringBuilder buffer = new StringBuilder(value);
        int index = 0;
        int deletesNum = 0;
        String escapedQuote = String.format("%c%c", escapedValue.charAt(0), escapedValue.charAt(0));
        while ((index = value.indexOf(escapedQuote, index)) != -1) {
            buffer.deleteCharAt(index - deletesNum);
            index += 2;
            deletesNum++;
        }
        return buffer.toString();
    }

    public void setValue(String string) {
        value = string;
    }

    public String getEscapedValue() {
        return escapedValue;
    }

    public void setEscapedValue(String escapedValue) {
        this.escapedValue = escapedValue;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return escapedValue;
    }
}

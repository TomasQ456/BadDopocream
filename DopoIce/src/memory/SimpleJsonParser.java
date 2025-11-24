package memory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal JSON parser capable of handling the subset required by the level descriptors.
 */
final class SimpleJsonParser {

    private final String source;
    private int index;

    private SimpleJsonParser(String source) {
        this.source = source;
    }

    static Object parse(String json) {
        SimpleJsonParser parser = new SimpleJsonParser(json);
        Object value = parser.parseValue();
        parser.skipWhitespace();
        if (!parser.isEnd()) {
            throw parser.error("Unexpected trailing content");
        }
        return value;
    }

    private Object parseValue() {
        skipWhitespace();
        if (isEnd()) {
            throw error("Unexpected end of input");
        }
        char ch = source.charAt(index);
        if (ch == '"') {
            return parseString();
        }
        if (ch == '{') {
            return parseObject();
        }
        if (ch == '[') {
            return parseArray();
        }
        if (ch == 't') {
            return parseLiteral("true", Boolean.TRUE);
        }
        if (ch == 'f') {
            return parseLiteral("false", Boolean.FALSE);
        }
        if (ch == 'n') {
            return parseLiteral("null", null);
        }
        return parseNumber();
    }

    private Map<String, Object> parseObject() {
        expect('{');
        Map<String, Object> result = new LinkedHashMap<>();
        skipWhitespace();
        if (peek('}')) {
            index++;
            return result;
        }
        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            expect(':');
            Object value = parseValue();
            result.put(key, value);
            skipWhitespace();
            if (peek('}')) {
                index++;
                return result;
            }
            expect(',');
        }
    }

    private List<Object> parseArray() {
        expect('[');
        List<Object> values = new ArrayList<>();
        skipWhitespace();
        if (peek(']')) {
            index++;
            return values;
        }
        while (true) {
            values.add(parseValue());
            skipWhitespace();
            if (peek(']')) {
                index++;
                return values;
            }
            expect(',');
        }
    }

    private String parseString() {
        expect('"');
        StringBuilder builder = new StringBuilder();
        while (!isEnd()) {
            char ch = source.charAt(index++);
            if (ch == '"') {
                return builder.toString();
            }
            if (ch == '\\') {
                if (isEnd()) {
                    throw error("Unterminated escape sequence");
                }
                char escape = source.charAt(index++);
                switch (escape) {
                    case '"':
                    case '\\':
                    case '/':
                        builder.append(escape);
                        break;
                    case 'b':
                        builder.append('\b');
                        break;
                    case 'f':
                        builder.append('\f');
                        break;
                    case 'n':
                        builder.append('\n');
                        break;
                    case 'r':
                        builder.append('\r');
                        break;
                    case 't':
                        builder.append('\t');
                        break;
                    case 'u':
                        builder.append(parseUnicode());
                        break;
                    default:
                        throw error("Unsupported escape \\" + escape + "\"");
                }
            } else {
                builder.append(ch);
            }
        }
        throw error("Unterminated string literal");
    }

    private char parseUnicode() {
        if (index + 4 > source.length()) {
            throw error("Incomplete unicode escape");
        }
        String hex = source.substring(index, index + 4);
        index += 4;
        return (char) Integer.parseInt(hex, 16);
    }

    private Object parseNumber() {
        int start = index;
        if (peek('-')) {
            index++;
        }
        while (!isEnd() && Character.isDigit(source.charAt(index))) {
            index++;
        }
        if (!isEnd() && source.charAt(index) == '.') {
            index++;
            while (!isEnd() && Character.isDigit(source.charAt(index))) {
                index++;
            }
        }
        if (!isEnd() && (source.charAt(index) == 'e' || source.charAt(index) == 'E')) {
            index++;
            if (!isEnd() && (source.charAt(index) == '+' || source.charAt(index) == '-')) {
                index++;
            }
            while (!isEnd() && Character.isDigit(source.charAt(index))) {
                index++;
            }
        }
        String token = source.substring(start, index);
        if (token.indexOf('.') >= 0 || token.indexOf('e') >= 0 || token.indexOf('E') >= 0) {
            return Double.parseDouble(token);
        }
        return Long.parseLong(token);
    }

    private Object parseLiteral(String literal, Object value) {
        if (source.startsWith(literal, index)) {
            index += literal.length();
            return value;
        }
        throw error("Unexpected value, expected " + literal);
    }

    private void skipWhitespace() {
        while (!isEnd()) {
            char ch = source.charAt(index);
            if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t') {
                index++;
            } else {
                break;
            }
        }
    }

    private void expect(char expected) {
        skipWhitespace();
        if (isEnd() || source.charAt(index) != expected) {
            throw error("Expected '" + expected + "'");
        }
        index++;
    }

    private boolean peek(char candidate) {
        skipWhitespace();
        return !isEnd() && source.charAt(index) == candidate;
    }

    private boolean isEnd() {
        return index >= source.length();
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException(message + " at position " + index);
    }
}

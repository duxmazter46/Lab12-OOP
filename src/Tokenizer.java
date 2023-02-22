import java.util.NoSuchElementException;
import static java.lang.Character.*;

public class Tokenizer {
    private String src, next;
    private int pos;
    public Tokenizer(String src) throws SyntaxError {
        this.src = src;
        pos = 0;
        computeNext();
    }
    public boolean hasNextToken()
    { 
        return next != null;
    }
    public String peek() {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
        return next;
    }
    public String consume() throws SyntaxError {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
        String result = next;
        computeNext();
        return result;
    }

    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }

    public void consume(String s) throws SyntaxError {
        if (peek(s)) consume();
        else  throw new SyntaxError(s + " expected");
    }


    private void computeNext() throws SyntaxError {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && isWhitespace(src.charAt(pos))){
            pos++;  // ignore whitespace
        }

        if (pos == src.length()) {
            next = null;
            return;
        }  // no more tokens

        char c = src.charAt(pos);
        if (isDigit(c) || c == 'x'||c == 'y'|c == 'z') {  // start of number, x ,y ,z
            s.append(c);
            for (pos++; pos < src.length() && isDigit(src.charAt(pos)); pos++){
                s.append(src.charAt(pos));
            }

        }
        else if (c == '+' || c == '-'||c == '*' ||c == '/' ||c == '%' ||c == '(' ||c == ')') {
            s.append(c);
            pos++;
        }
        else {
            throw new SyntaxError("unknown character: " + c);
        }
        next = s.toString();
    }
}

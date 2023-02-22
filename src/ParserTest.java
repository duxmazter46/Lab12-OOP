import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthButtonUI;
import java.util.HashMap;
import java.util.Map;

public class ParserTest {
    private Tokenizer tkz;
    private Parser p;

    @Test
    public void testEval() throws SyntaxError, EvalError {
        tkz = new Tokenizer("2*4+6");
        p = new Parser(tkz);
        Map<String, Integer> bindings = new HashMap<>();
        int expected = 14;
        int actual = p.parse().eval(bindings);
        assertEquals(expected, actual);

        tkz = new Tokenizer("2+4*6");
        p = new Parser(tkz);
        expected = 26;
        actual = p.parse().eval(bindings);
        assertEquals(expected, actual);

    }

    @Test
    public void testPrettyPrint() throws SyntaxError {
        tkz = new Tokenizer("2*4+6");
        p = new Parser(tkz);
        StringBuilder sb = new StringBuilder();
        String expected = "((2*4)+6)";
        p.parse().prettyPrint(sb);
        String actual = sb.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testPrettyPrint2() throws SyntaxError {
        tkz = new Tokenizer("2+4*6");
        p = new Parser(tkz);
        StringBuilder sb = new StringBuilder();
        String expected = "(2+(4*6))";
        p.parse().prettyPrint(sb);
        String actual = sb.toString();
        assertEquals(expected, actual);

    }

    @Test
    public void test9() throws SyntaxError, EvalError {
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("y", 4);
        bindings.put("z", 6);
        Expr e = new Parser(new Tokenizer("x+y*z")).parse();
        int expected = 26;
        int actual = e.eval(bindings);
        assertEquals(expected, actual);
    }

    @Test
    public void test8() throws SyntaxError, EvalError {
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("y", 4);
        bindings.put("z", 0);
        try{
        Expr e = new Parser(new Tokenizer("x-y/z")).parse();
        int actual = e.eval(bindings);
        } catch (EvalError e) {
            assertEquals(e.getMessage(), "Evaluation error , cannot / or % by zero");
        }
    }

    @Test
    public void testModZero() throws SyntaxError, EvalError {
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("z", 0);
        try{
            Expr e = new Parser(new Tokenizer("x%z")).parse();
            int actual = e.eval(bindings);
        } catch (EvalError e) {
            assertEquals(e.getMessage(), "Evaluation error , cannot / or % by zero");
        }
    }

    @Test
    public void test10() throws SyntaxError, EvalError {
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("y", 4);
        bindings.put("z", 6);
        Expr e = new Parser(new Tokenizer("x*y+z")).parse();
        int expected = 14;
        int actual = e.eval(bindings);
        assertEquals(expected, actual);
    }

    @Test
    public void test11() throws SyntaxError {
        StringBuilder s = new StringBuilder();
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("y", 4);
        bindings.put("z", 6);
        Expr e = new Parser(new Tokenizer("x*y+z")).parse();
        e.prettyPrint(s);
        String expected = "((x*y)+z)";
        String actual = s.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void test12() throws SyntaxError {
        StringBuilder s = new StringBuilder();
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("y", 4);
        bindings.put("z", 6);
        Expr e = new Parser(new Tokenizer("x+y*z")).parse();
        e.prettyPrint(s);
        String expected = "(x+(y*z))";
        String actual = s.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void test13() throws SyntaxError {
        StringBuilder s = new StringBuilder();
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 2);
        bindings.put("y", 4);
        bindings.put("z", 6);
        Expr e = new Parser(new Tokenizer("(x+y)*z")).parse();
        e.prettyPrint(s);
        String expected = "((x+y)*z)";
        String actual = s.toString();
        assertEquals(expected, actual);
    }
}

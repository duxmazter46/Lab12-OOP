public class newParser {
    private Tokenizer tkz;
    private static final ArithExprFactory factory = ArithExprFactory.getInstance();

    public newParser(Tokenizer tkz) {
        this.tkz = tkz;
    }

    public Expr parse() throws SyntaxError {
        Expr n = parseE();
        if (tkz.hasNextToken()) {
            throw new SyntaxError("leftover token");
        }
        return n;
    }

    private Expr parseE() throws SyntaxError {
        Expr e = parseT();
        while (tkz.peek("+") || tkz.peek("-")) {
            if (tkz.peek("+")) {
                tkz.consume();
                e = factory.createExpr("BinaryArithExpr", e, "+", parseT());
            } else if (tkz.peek("-")) {
                tkz.consume();
                e = factory.createExpr("BinaryArithExpr", e, "-", parseT());
            }
        }
        return e;
    }

    private Expr parseT() throws SyntaxError {
        Expr e = parseF();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            if (tkz.peek("*")) {
                tkz.consume();
                e = factory.createExpr("BinaryArithExpr", e, "*", parseF());
            } else if (tkz.peek("/")) {
                tkz.consume();
                e = factory.createExpr("BinaryArithExpr", e, "/", parseF());
            } else if (tkz.peek("%")) {
                tkz.consume();
                e = factory.createExpr("BinaryArithExpr", e, "%", parseF());
            }
        }
        return e;
    }

    private Expr parseF() throws SyntaxError {
        if (Parser.isNumber(tkz.peek())) {
            return factory.createExpr("IntLit", Integer.parseInt(tkz.consume()));
        } else if (Parser.isVar(tkz.peek())) {
            return factory.createExpr("Variable", tkz.consume());
        } else {
            tkz.consume("(");
            Expr e = parseE();
            tkz.consume(")");
            return e;
        }
    }
}

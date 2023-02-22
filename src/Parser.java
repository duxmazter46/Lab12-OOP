public class Parser {
    private Tokenizer tkz;
    public Parser(Tokenizer tkz) {
        this.tkz = tkz;
    }
    public Expr parse() throws SyntaxError {
        Expr n = parseE();
        if (tkz.hasNextToken()){
            throw new SyntaxError("leftover token");
        }
        return n;
    }
    private Expr parseE() throws SyntaxError {
        Expr e = parseT();
        while (tkz.peek("+")||tkz.peek("-")) {
            if(tkz.peek("+")){
                tkz.consume();
                e = new BinaryArithExpr(e,"+",parseT());
            }else if(tkz.peek("-")){
                tkz.consume();
                e = new BinaryArithExpr(e,"-",parseT());
            }
        }
        return e;
    }
    private Expr parseT() throws SyntaxError {
        Expr e = parseF();
        while(tkz.peek("*")||tkz.peek("/")||tkz.peek("%")){
            if (tkz.peek("*")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"*",parseT());
            }
            else if (tkz.peek("/")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"/",parseT());

            }
            else if(tkz.peek("%")) {
                tkz.consume();
                e = new BinaryArithExpr(e,"/",parseT());
            }
        }

        return e;
    }
    private Expr parseF() throws SyntaxError {
        if (isNumber(tkz.peek())) {
            return new IntLit(Integer.parseInt(tkz.consume()));
        } else if (isVar(tkz.peek())) {
            return new Variable(tkz.consume());
        } else {
            tkz.consume("(");
            Expr e = parseE();
            tkz.consume(")");
            return e;
        }
    }
    public static boolean isNumber(String string) {
        if(string == null || string.equals("")) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

        //This section is used by chatGPT to generate
    public static boolean isVar(String s) {
        // check if the string is a valid variable name
        // you can implement this method according to your variable naming conventions
        // for example, you can check if the string starts with a letter and only contains letters, numbers and underscores
        return s.matches("[a-zA-Z][a-zA-Z0-9_]*");


    }



}

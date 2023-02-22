import java.util.Map;

class IntLit implements Expr {
    private int val;
    public IntLit(int val) {
        this.val = val;
    }
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return val;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(val);
    }
}
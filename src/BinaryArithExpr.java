import java.util.Map;

class BinaryArithExpr implements Expr {
    private Expr left, right;
    private String op;
    public BinaryArithExpr(
            Expr left, String op, Expr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
    public int eval(Map<String, Integer> bindings) throws EvalError {
        int lv = left.eval(bindings);
        int rv = right.eval(bindings);
        int result = 0;
        try {
            if (op.equals("+")) result = lv + rv;
            if (op.equals("-")) result = -rv;
            if (op.equals("*")) result = lv * rv;
            if (op.equals("/")) result = lv / rv;
            if (op.equals("%")) result = lv % rv;
        }catch (Exception e) {
            throw new EvalError("Evaluation error , cannot / or % by zero");
        }
        return result;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }
}
public class ArithExprFactory {
    private static final ArithExprFactory instance = new ArithExprFactory();

    private ArithExprFactory() {
        // private constructor to prevent direct instantiation
    }

    public static ArithExprFactory getInstance() {
        return instance;
    }

    private IntLit createIntLit(int val) {
        return new IntLit(val);
    }

    private Variable createVariable(String name) {
        return new Variable(name);
    }

    private BinaryArithExpr createBinaryArithExpr(Expr left, String op, Expr right) {
        return new BinaryArithExpr(left, op, right);
    }

    public Expr createExpr(String type, Object... args) {
        if (type.equals("IntLit")) {
            return createIntLit((int) args[0]);
        } else if (type.equals("Variable")) {
            return createVariable((String) args[0]);
        } else if (type.equals("BinaryArithExpr")) {
            return createBinaryArithExpr((Expr) args[0], (String) args[1], (Expr) args[2]);
        } else {
            throw new IllegalArgumentException("Unknown expression type: " + type);
        }
    }
}

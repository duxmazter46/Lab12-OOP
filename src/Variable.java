import java.util.Map;

class Variable implements Expr {
    private String name;
    public Variable(String name) {
        this.name = name;
    }
    public int eval(
            Map<String, Integer> bindings) throws EvalError {
        if (bindings.containsKey(name))
            return bindings.get(name);
        throw new EvalError(
                "undefined variable: " + name);
    }
    public void prettyPrint(
            StringBuilder s) {
        s.append(name);
    }
}
package jlox.app;

import java.util.List;
public class LoxFunction implements LoxCallable {
  private final Stmt.Function declaration;
  private final Environment closure;
  LoxFunction(Stmt.Function declaration, Environment closure) {
    this.closure = closure;
    this.declaration = declaration;
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment environment = new Environment(closure);
    for (int i = 0; i < declaration.params.size(); i++) {
      // pub the arguments into the environment
      environment.define(declaration.params.get(i).lexeme, arguments.get(i));
    }

    // The return statement is implemented as an exception, unwind the stack until the call function.
    try {
      interpreter.executeBlock(declaration.body, environment);
    } catch (Return returnValue) {
      return returnValue.value;
    }
    return null;
  }

  @Override
  public String toString() {
    return "<fn " + declaration.name.lexeme + ">";
  }

  @Override
  public int arity() {
    return declaration.params.size();
  }
}

package jlox.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

// This program generates the AST classes for the expressions in Lox.
// for example, it generates the following classes:
/*
package jlox.app;

abstract class Expr {
  static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    final Expr left;
    final Token operator;
    final Expr right;
  }

  // Other expressions...
}
*/

public class GenerateAst {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <output directory>");
      System.exit(64);
    }
    String outputDir = args[0];
    defineAst(outputDir, "Expr", Arrays.asList(
      "Binary   : Expr left, Token operator, Expr right",
      "Call     : Expr callee, Token paren, List<Expr> arguments",
      "Get      : Expr object, Token name",
      "Assign   : Token name, Expr value",
      "Grouping : Expr expression",
      "Literal  : Object value",
      "Logical  : Expr left, Token operator, Expr right",
      "Set      : Expr object, Token name, Expr value",
      "This     : Token keyword",
      "Unary    : Token operator, Expr right",
      "Variable : Token name"
    ));
    /*
     * There is no place in the grammar where both an expression and a statement are allowed.
     * The operands of, say, + are always expressions, never statements. The body of a while
     * loop is always a statement.
     * Since the two syntaxes are disjoint, we don’t need a single base class that they all
     * inherit from. Splitting expressions and statements into separate class hierarchies
     * enables the Java compiler to help us find dumb mistakes like passing a statement to
     * a Java method that expects an expression.
     */
    defineAst(outputDir, "Stmt", Arrays.asList(
      "Block      : List<Stmt> statements",
      "Class      : Token name, List<Stmt.Function> methods",
      "Expression : Expr expression",
      "Function   : Token name, List<Token> params, List<Stmt> body",
      "If         : Expr condition, Stmt thenBranch, Stmt elseBranch",
      "Print      : Expr expression",
      "Var        : Token name, Expr initializer",
      "Return     : Token keyword, Expr value",
      "While      : Expr condition, Stmt body"
    ));
  }

  private static void defineAst(String outputDir, String baseName,
      List<String> types) throws IOException {
    var path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);

    writer.println("package jlox.app;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");

    defineVisitor(writer, baseName, types);

    for (var type : types) {
      var className = type.split(":")[0].trim();
      var fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }

    writer.println();
    writer.println("  abstract <R> R accept(Visitor<R> visitor);");

    writer.println("}");
    writer.close();
  }

  private static void defineVisitor(PrintWriter writer, String baseName,
      List<String> types) {
    writer.println("  interface Visitor<R> {");

    for (var type : types) {
      var typeName = type.split(":")[0].trim();
      writer.println("    R visit" + typeName + baseName + "(" + typeName +
          " " + baseName.toLowerCase() + ");");
    }

    writer.println("  }");
  }

  private static void defineType(PrintWriter writer, String baseName,
      String className, String fieldList) {
    writer.println("  static class " + className + " extends " + baseName +
        " {");

    // Constructor.
    writer.println("    " + className + "(" + fieldList + ") {");

    // store parameters in fields
    String[] fields = fieldList.split(", ");
    for (var field : fields) {
      var name = field.split(" ")[1];
      writer.println("      this." + name + " = " + name + ";");
    }

    writer.println("    }");

    writer.println();
    writer.println("    @Override");
    writer.println("    <R> R accept(Visitor<R> visitor) {");
    writer.println("      return visitor.visit" + className + baseName + "(this);");
    writer.println("    }");

    // Fields
    writer.println();
    for (var field : fields) {
      writer.println("    final " + field + ";");
    }

    writer.println("  }");
  }
}

package jlox.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

// This program generates the AST classes for the expressions in Lox.
// for example, it generates the following classes:
/*
package com.craftinginterpreters.lox;

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
    defineAst(outputDir, "Expr",
        Arrays.asList("Binary   : Expr left, Token operator, Expr right",
            "Grouping : Expr expression",
            "Literal  : Object value",
            "Unary    : Token operator, Expr right"));
  }

  private static void defineAst(String outputDir, String baseName,
      List<String> types) throws IOException {
    var path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");

    writer.println("package jlox.app;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");

    for (var type : types) {
      var className = type.split(":")[0].trim();
      var fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }

    writer.println("}");
    writer.close();
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

    // Fields
    writer.println();
    for (var field : fields) {
      writer.println("    final " + field + ";");
    }

    writer.println("  }");
  }
}
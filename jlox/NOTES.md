# Jlox

The call "operator" has higher precedence than any other operator, then the unary operator.

## Resolver

After the parser produces the syntax tree, but before the interpreter starts executing it, we’ll do a single walk over the tree to resolve all of the variables it contains. Additional passes between parsing and execution are common. If Lox had static types, we could slide a type checker in there. Optimizations are often implemented in separate passes like this too. Basically, any work that doesn’t rely on state that’s only available at runtime can be done in this way.

## This

For lox, we generally hew to java-ish style, it will refer to the object that the method is **accessed** from.

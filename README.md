# Lox language

My implementation of lox language.
Jlox is a tree walker interpreter written in java.
Rlox is a bytecode/jit interpreter written in rust.
Still under active development.

## Language grammar:
+ program        → declaration* EOF ;
+ declaration    → varDecl
                 | funDecl
                 | statement ;

+ funDecl        → "fun" function ;

+ function       → IDENTIFIER "(" parameters? ")" block ;

+ parameters     → IDENTIFIER ( "," IDENTIFIER )* ;

+ varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;

+ statement      → exprStmt
                 | printStmt
                 | block
                 | whileStmt
                 | forStmt
                 | returnStmt
                 | ifStmt;

+ returnStmt     → "return" expression? ";" ;

+ forStmt        → "for" "(" ( varDecl | exprStmt | ";" ) expression? ";" expression? ")" statement ;

+ whileStmt      → "while" "(" expression ")" statement ;

+ ifStmt         → "if" "(" expression ")" statement
                 | ( "else" statement )? ;

+ block          → "{" declaration* "}" ;

+ exprStmt       → expression ";" ;

+ printStmt      → "print" expression ";" ;

+ expression     → assignment ;

+ assignment     → IDENTIFIER "=" assignment
                 | logic_or;

+ logic_or       → logic_and ( "or" logic_and )* ;

+ logic_and      → equality ( "and" equality )* ;

+ equality       → comparison ( ( "!=" | "==" ) comparison )* ;

+ comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;

+ term           → factor ( ( "-" | "+" ) factor )* ;

+ factor         → unary ( ( "/" | "*" ) unary )* ;

+ unary          → ( "!" | "-" ) unary | call ;

+ call           → primary ( "(" arguments? ")" )* ;

+ arguments      → expression ( "," expression )* ;

+ primary        → NUMBER | STRING | "true" | "false" | "nil"
                 | "(" expression ")"
                 | IDENTIFIER ;

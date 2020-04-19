grammar Sql;
//https://github.com/antlr/grammars-v4/blob/master/sql/sqlite/SQLite.g4

select
 : K_SELECT ( K_ALL )? result_column ( ',' result_column )*
   ( K_FROM table ( ',' table )* )?
   ( K_WHERE expr )?
 ;

table
 : ( database_name '.' )? table_name ( K_AS? table_alias )?
 ;

result_column
 : (table_name '.')? '*'
 | (table_name '.')? column_name ( K_AS? column_alias )?
 ;

expr
 : literal_value #expr_literal
 | ( ( database_name '.' )? table_name '.' )? column_name #expr_column
 | K_NOT expr #expr_not
 | expr comparator expr #expr_filter
 | '(' expr ')' #expr_parenthesis
 | expr binary expr #expr_binary
 ;

 comparator
 : ( '<' | '<=' | '>' | '>=' | '==' | '=' | '!=' | '<>' | K_IN | K_LIKE | K_REGEXP )
 ;

 binary
 : (K_AND | K_OR)
 ;

literal_value
 : NUMERIC_LITERAL
 | DATE_TIME
 | DATE
 | TIME
 | STRING_LITERAL
 ;

column_alias
 : IDENTIFIER
 | STRING_LITERAL
 ;

database_name
 : any_name
 ;

table_name
 : any_name
 ;

table_alias
 : any_name
 ;

column_name
 : any_name
 ;

any_name
 : IDENTIFIER
 | K_AS
 | STRING_LITERAL
 | '(' any_name ')'
 ;

SCOL : ';';
DOT : '.';
TWO_DOTS : ':';
OPEN_PAR : '(';
CLOSE_PAR : ')';
COMMA : ',';
ASSIGN : '=';
STAR : '*';
PLUS : '+';
MINUS : '-';
TILDE : '~';
PIPE2 : '||';
DIV : '/';
MOD : '%';
LT2 : '<<';
GT2 : '>>';
AMP : '&';
PIPE : '|';
LT : '<';
LT_EQ : '<=';
GT : '>';
GT_EQ : '>=';
EQ : '==';
NOT_EQ1 : '!=';
NOT_EQ2 : '<>';


K_AND : A N D;
K_OR : O R;
K_NOT : N O T;
K_FROM : F R O M;
K_SELECT : S E L E C T;
K_WHERE : W H E R E;
K_AS : A S;
K_IN : I N;
K_LIKE : L I K E;
K_REGEXP : R E G E X P;


IDENTIFIER
 : '"' (~'"' | '""')* '"'
 | '`' (~'`' | '``')* '`'
 | '[' ~']'* ']'
 | [a-zA-Z_] [a-zA-Z_0-9]* // TODO check: needs more chars in set
 ;

NUMERIC_LITERAL
 : DIGIT+ ( '.' DIGIT* )? ( E [-+]? DIGIT+ )?
 | '.' DIGIT+ ( E [-+]? DIGIT+ )?
 ;

DATE
 : QUOTE FOUR_DIGITS MINUS TWO_DIGITS MINUS TWO_DIGITS QUOTE
 ;

TIME
 : QUOTE TWO_DIGITS TWO_DOTS TWO_DIGITS TWO_DOTS TWO_DIGITS QUOTE
 ;

DATE_TIME
 : QUOTE FOUR_DIGITS MINUS TWO_DIGITS MINUS TWO_DIGITS SPACES TWO_DIGITS TWO_DOTS TWO_DIGITS TWO_DOTS TWO_DIGITS QUOTE
 ;

FOUR_DIGITS
 : DIGIT DIGIT DIGIT DIGIT
 ;

TWO_DIGITS
 : DIGIT DIGIT
 ;

QUOTE
 : ('\'' | '"')
 ;

BIND_PARAMETER
 : '?' DIGIT*
 | [:@$] IDENTIFIER
 ;

STRING_LITERAL
: '\'' ( ~'\'' | '\'\'' )* '\''
 ;

SPACES
 : [ \u000B\t\r\n] -> channel(HIDDEN)
 ;

UNEXPECTED_CHAR
 : .
 ;

fragment DIGIT : [0-9];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];


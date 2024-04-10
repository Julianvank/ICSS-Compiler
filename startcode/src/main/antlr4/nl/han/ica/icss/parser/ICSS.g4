grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
stylesheet: (variableAssignment* stylerule+) EOF;



stylerule : (selector OPEN_BRACE (ifClause | declaration+ ) CLOSE_BRACE) ;
ifClause : IF BOX_BRACKET_OPEN variableReference BOX_BRACKET_CLOSE OPEN_BRACE expression+ CLOSE_BRACE;

selector : (idSelector | classSelector | tagSelector);
declaration : property COLON expression SEMICOLON;

variableAssignment : variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;
variableReference : CAPITAL_IDENT;


property : LOWER_IDENT;
expression : literal
            | expression MUL expression+
            | expression MIN expression+
            | expression PLUS expression+;

literal : colorLiteral | pixelLiteral | percentageLiteral | boolLiteral | scalarLiteral | variableReference;

idSelector : ID_IDENT;
classSelector : CLASS_IDENT;
tagSelector : LOWER_IDENT;

colorLiteral : COLOR;
pixelLiteral : PIXELSIZE;
percentageLiteral : PERCENTAGE;
boolLiteral : TRUE | FALSE;
scalarLiteral : SCALAR;
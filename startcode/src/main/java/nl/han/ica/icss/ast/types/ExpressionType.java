package nl.han.ica.icss.ast.types;

public enum ExpressionType {
    PIXEL(5, 6),
    PERCENTAGE(1, 2),
    COLOR (0, 0),
    SCALAR (3, 4),
    UNDEFINED(100, 99),
    BOOL(0,0);


    private final int inSymbol;
    private final int topSymbol;
    ExpressionType(int inSymbol, int topSymbol) {
        this.inSymbol = inSymbol;
        this.topSymbol = topSymbol;
    }

    public int getInSymbol(){return inSymbol;}

    public int getTopSymbol(){return topSymbol;}

}

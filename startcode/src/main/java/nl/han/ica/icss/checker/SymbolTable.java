package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.UnderflowException;
import nl.han.ica.icss.ast.Expression;

import java.util.HashMap;

public class SymbolTable {
    private HANStack<HashMap<String, Expression>> symbolTable;

    public SymbolTable(){
        symbolTable = new HANStack<>();
    }

    public SymbolTable(HANStack<HashMap<String, Expression>> symbolTable){
        this.symbolTable = symbolTable;
    }

    public HANStack<HashMap<String, Expression>> getSymbolTable() {
        return symbolTable;
    }

    public void pushScope() {
        symbolTable.push(new HashMap<>());
    }

    public void popScope() {
        try {
            symbolTable.pop();
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Expression> getTop(){
        try {
            return symbolTable.peek();
        } catch (UnderflowException e) {
            throw new RuntimeException(e);
        }
    }
}

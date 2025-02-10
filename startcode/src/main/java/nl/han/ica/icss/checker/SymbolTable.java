package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.LinkedList;
import nl.han.ica.datastructures.LinkedListIterator;
import nl.han.ica.datastructures.UnderflowException;
import nl.han.ica.icss.ast.Expression;

import java.util.HashMap;

public class SymbolTable {

    private LinkedList<HashMap<String, Expression>> symbolTable;

    public SymbolTable() {
        symbolTable = new LinkedList<>();

    }

    public LinkedList<HashMap<String, Expression>> getSymbolTable() {
        return symbolTable;
    }

    public void pushScope() {
        symbolTable.addFirst(new HashMap<>());
    }

    public void popScope() {
        symbolTable.removeFirst();
    }

    public Expression findSymbol(String name) {
            LinkedListIterator<HashMap<String, Expression>> itr = symbolTable.zeroth();
            while(itr.isValid()){
                if(itr.retrieve() != null && itr.retrieve().containsKey(name)){
                    return itr.retrieve().get(name);
                }
                itr.advance();
            }
            return null;
    }

    public void addSymbol(String name, Expression expression) {
        symbolTable.getFirst().put(name, expression);
    }

}

package nl.han.ica.icss.transforms.NodeEvaluator;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.IfClause;

import java.util.ArrayList;
import java.util.List;

public class StyleRuleEvaluator extends NodeEvaluatorBase{
    public StyleRuleEvaluator(SymbolTable table) {
        super(table);
    }

    @Override
    public ASTNode evaluate(ASTNode node) {
        /*
            if stylerule/children contains IfClause
            stylerule.body.add(ifClause.getBody)
                ifClause:
                    if true then body
                    if false and elseClause != null;
                        moveUp symbolTableScope for else body
                        then body
         */
        System.out.println(table.toString());
        return node;
    }
}

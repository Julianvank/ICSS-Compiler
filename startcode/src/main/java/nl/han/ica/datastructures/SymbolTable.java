package nl.han.ica.datastructures;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;

import java.util.HashMap;

public class SymbolTable {

    private final TreeNavigator<ASTNode, HashMap<String, Expression>> treeNavigator;
    private TreeNode<ASTNode, HashMap<String, Expression>> rootNode;

    public SymbolTable() {
        treeNavigator = new TreeNavigator<>();

    }

    public HashMap<String, Expression> getSymbolTable() {
        return treeNavigator.current.content;
    }

    public void pushScope(ASTNode key) {

        if (treeNavigator.current == null) {
            treeNavigator.current = new TreeNode<>(new HashMap<>());
            rootNode = treeNavigator.current;
            return;
        }

        TreeNode<ASTNode, HashMap<String, Expression>> newNode = new TreeNode<>(new HashMap<>());
        newNode.parent = treeNavigator.current;

        treeNavigator.current.children.put(key, newNode);

        treeNavigator.current = newNode;
    }

    public void findScope(ASTNode key){
        if (treeNavigator.current == null) {
            return;
        }
        if(treeNavigator.current.children.containsKey(key)){
            treeNavigator.current = treeNavigator.current.children.get(key);
        }
    }

    public void popScope() {
        if (treeNavigator.current == null) {
            return;
        }

        treeNavigator.current = treeNavigator.current.parent;
    }

    public Expression findSymbol(String name) {
           for(TreeNavigator<ASTNode, HashMap<String, Expression>> nav = new TreeNavigator<>(treeNavigator.current);
               nav.isValid(); nav.moveUp()){
               if(nav.current == null) continue;
               Expression expression = nav.current.content.get(name);
               if(expression != null){return expression;}
           }
           return null;
    }

    public void addSymbol(String name, Expression expression) {
        if (!treeNavigator.isValid()) {
            expression.setError("Node is creating great stress in the symbol table.");
            return;
        }
        treeNavigator.current.content.put(name, expression);
    }

    @Override
    public String toString(){
//        StringBuilder string = new StringBuilder();
        return toString(rootNode);
    }

    private String toString(TreeNode<ASTNode, HashMap<String, Expression>> node){
        //print root
            //print first child
                //print childs childs
        //stringBuilder.add(node.toString)
        //get children
        // for each child
            //stringBuilder.add(toString(childNode))

        StringBuilder builder = new StringBuilder();
//        for (var entry : node.content.)
        node.content.forEach((K, V) -> builder.append(K).append("=").append(V).append("\n"));

        for(TreeNode<ASTNode, HashMap<String, Expression>> childNode : node.children.values()){
            builder.append("\t").append(toString(childNode));
        }

        return builder.append("\n").toString();
    }

    private static class TreeNode<K, V>{
        public V content;
        public TreeNode<K, V> parent;
        public HashMap<K, TreeNode<K, V>> children;

        public TreeNode(V content){
            this.content = content;
            children = new HashMap<>();
        }

//        @Override
//        public String toString(){
//            return content.toString();
//        }
    }

    private static class TreeNavigator<K, V>{
        TreeNode<K, V> current;
        private TreeNode<K, V> root;

        public TreeNavigator() {
        }

        TreeNavigator(TreeNode<K, V> current){
            this.current = current;
        }

        public void moveUp(){
            current = current.parent;
        }

        public boolean isValid(){
            return current != null;
        }
    }
}

package code;

import java.util.*;

//e3mel instance mel class ba3dein .call
public class LLAPSearch extends GenericSearch {

       


    public static String solve(String initalState, String strategy, Boolean visualize) {
        LLAPSearch LLAPSearch = new LLAPSearch();
        State rootState = new State(initalState);
        //System.out.println(rootState.getUnitPriceEnergy());
        Node root = new Node(rootState, null, ActionEnum.root, 0);
        Queue<Node> Tree = new LinkedList<>();
        Stack<Node> TreeStack = new Stack<>();
        Tree.add(root);
        TreeStack.push(root);
        String result = "";
        switch (strategy) {
            case "BF":

                result = LLAPSearch.BF(Tree,visualize);
                break;

            case "DF":

                result = LLAPSearch.DF(TreeStack,visualize);
                break;
            case "ID":

                result = LLAPSearch.ID(Tree,visualize);
                break;

            case "UC":

                result = LLAPSearch.UC(Tree,visualize);
                break;

            case "GR1":

                result = LLAPSearch.GR1(Tree,visualize);
                break;

             case "GR2":

                result = LLAPSearch.GR2(Tree,visualize);
                break;

            case "AS1":

                result = LLAPSearch.AS1(Tree,visualize);
                break;

            case "AS2":

                result = LLAPSearch.AS2(Tree,visualize);
                break;
        }


       

        return result;

    }
}
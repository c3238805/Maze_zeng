/*  
    /============================\
    |  SENG2230 Assignment 2     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */

public class Node {
    
    private boolean isVisited;
    private int cell_openness;
    private int nodeIndex;
    private String NodeDisplay;
    private int nodeRow;
    private int nodeCollum;

    public Node(int Nodeindex){
        this.isVisited = false;
        cell_openness = 0;
        nodeIndex = Nodeindex; //node index will auto increase depends on the number of Nodes.
        NodeDisplay = "  ";
        nodeRow = 0;
        nodeCollum = 0;
    }

    public Node(){
        
    }

    public void setCell_openness(int index){

        cell_openness = index;
    }

    public int getCell_openness(){

        return cell_openness;
    }
    //--------------------------------------
    public void setNodeRow(int row){
        nodeRow = row;
    }
    public int getNodeRow(){
        return nodeRow;
    }
    // --------------------------------------
    public void setNodeCollum(int collum) {
        nodeCollum = collum;
    }
    public int getNodeCollum() {
        return nodeCollum;
    }

    // --------------------------------------
    public void setVisited(boolean b){
        this.isVisited = b;
    }

    public boolean isVisited(){
        return isVisited;
    }
    // --------------------------------------
    public String getNodeIndex(){
        return String.valueOf(this.nodeIndex);
        
    }
    // --------------------------------------
    public void setNodeDisplay(String NodeDisplayString){
        NodeDisplay = NodeDisplayString;
    }
    public String getNodeDisplay(){
        return NodeDisplay;
    }


    // --------------------------------------
    public String getNodeGraphRight(){
        // this method is to get each node's horizontal graph
        String graphR = "";

        if(cell_openness == 0){
            graphR = getNodeDisplay()+"|";   // Both closed
        }else if(cell_openness == 1){
            graphR = getNodeDisplay() + " ";   // Right only open
        } else if (cell_openness == 2) {
            graphR = getNodeDisplay() + "|";    // Down only open
        } else if (cell_openness == 3) {
            graphR = getNodeDisplay() + " ";    // both open
        }
        

        return graphR;
    }

    public String getNodeGraphBottom() {
        // this method is to get each node's vertical graph
        String graphB = "";

        if (cell_openness == 0) {
            graphB ="--|"; // Both closed
        }else if (cell_openness == 1) {
            graphB = "--|"; // Right only open
        } else if (cell_openness == 2) {
            graphB = "  |"; // Down only open
        } else if (cell_openness == 3) {
            graphB = "  |"; // both open
        }

        return graphB;
    }


}

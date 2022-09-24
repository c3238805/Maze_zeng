import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/*  
    /============================\
    |  COMP2230 Assignment 2     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */

public class MazeGenerator {
    private static Node[] possiableNextNodes;
    private static Node[][] allNodes;
    private static int rows;
    private static int collum;
    private static Stack<Node> pathTracking;

    public static void main(String[] args) {

        pathTracking = new Stack<Node>();   //initial a stack for maze path track
        try {

            rows = 10;// Integer.parseInt(args[0]); // user input for number of rows
            collum = 10;// Integer.parseInt(args[1]); // user input for number of collum
            String datString = "data.dat";// args[2]; // user set output maze file

            File file = new File(datString);
            file.createNewFile();
            FileOutputStream oFile = new FileOutputStream(file, false);

            // generate the maze nodes
            generateMazeNode(rows, collum);

            // generate the maze node by using walkthough method
            Random_Walk(allNodes);

            // input the maze graph data into the file just created.
            oFile.write((printMazeString(rows, collum)).getBytes());

            oFile.write((printMazeGraph(rows, collum)).getBytes());
            
            oFile.close();
            
            // oFile.write(("hello").getBytes());

        } catch (Exception e) {

            System.out.println(e);

            // TODO: handle exception
        }

    }

    public static void Random_Walk(Node[][] allNodes) {

        Node currentNode = new Node();
        // randomly pick a start Node
        Random randomPick = new Random();

        // set the random Node display name as "S-"
        Node nextNode = allNodes[randomPick.nextInt(rows)][randomPick.nextInt(collum)];

        nextNode.setNodeDisplay("S ");
        nextNode.setVisited(true); // set start Node boolean isVisited = true

        pathTracking.push(nextNode); // push current node into stack
        currentNode = pathTracking.peek();
        

        while (!checkComplete(allNodes)){
            Node temp = getRandomNextMove(currentNode);

            if(temp == null && !checkComplete(allNodes) && pathTracking.size() > 0){
                pathTracking.pop();
                currentNode = pathTracking.peek();

            }
            else {
                temp.setVisited(true);
                pathTracking.push(temp);
                
                // update current node with coresponding walls
                if(temp.getNodeRow() > currentNode.getNodeRow()){
                    // this mean pointer move down.
                    // remove wall between current node and temp node
                    currentNode.setCell_openness(updatebottomWall(currentNode.getCell_openness()));

                } else if (temp.getNodeRow() < currentNode.getNodeRow()) {
                    // this mean pointer move up.
                    temp.setCell_openness(updatebottomWall(temp.getCell_openness()));
                    

                } else if(temp.getNodeCollum() < currentNode.getNodeCollum()){
                    // this mean pointer move left.
                    temp.setCell_openness(updateSideWall(temp.getCell_openness()));

                } else if (temp.getNodeCollum() > currentNode.getNodeCollum()) {
                    // this mean pointer move right.
                    currentNode.setCell_openness(updateSideWall(currentNode.getCell_openness()));
                }
                currentNode = temp;
                
            }
            
        }
        
        currentNode.setNodeDisplay("F ");
        //

    }

    public static Node getRandomNextMove(Node currentNode) {

        int counter = 0;
        possiableNextNodes = new Node[4];   // one node can possiable have 4 ways to move

        // first search all possiable Node to visit next and store all into an array
        for (Node[] n : allNodes) {
            for (Node foundNode : n) {
                if ((foundNode.getNodeRow() == currentNode.getNodeRow() + 1)
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum())
                        && foundNode.isVisited() == false ) {

                    possiableNextNodes[counter++] = foundNode;
                    
                }
                else if ((foundNode.getNodeRow() == currentNode.getNodeRow())
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum() + 1) && foundNode
                                .isVisited() == false) {

                    possiableNextNodes[counter++] = foundNode;
                    
                }
                else if ((foundNode.getNodeRow() == currentNode.getNodeRow())
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum() - 1) && foundNode
                                .isVisited() == false) {

                    possiableNextNodes[counter++] = foundNode;
                    
                }
                else if ((foundNode.getNodeRow() == currentNode.getNodeRow() - 1)
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum() ) && foundNode
                                .isVisited() == false) {

                    possiableNextNodes[counter++] = foundNode;
                    
                    
                }

            }
        }

        int arrayRange = possiableNextNodes.length;
        //clean the null node
        for(Node n: possiableNextNodes){
            if(n == null){
                arrayRange--;
            }
        }

        if(arrayRange == 0 ){
            return null;
        }
        Node [] tempNode = new Node[arrayRange];
        for(int i = 0;i<tempNode.length;i++){
            tempNode [i] =  possiableNextNodes[i];
        }
        possiableNextNodes = tempNode;
        int rnd = new Random().nextInt(possiableNextNodes.length);
        return possiableNextNodes[rnd];

    }

    public static void generateMazeNode(int rows, int collum) {
        int nodeNameAuto = 1; // Node name start from 1
        allNodes = new Node[rows][collum];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < collum; j++) {
                allNodes[i][j] = new Node(nodeNameAuto++); // initial a new Node with isVisisted = false
                allNodes[i][j].setNodeRow(i);
                allNodes[i][j].setNodeCollum(j);
            }
        }

    }

    public static boolean checkComplete(Node[][] allNodes){

        boolean isComplete = true;

        for(Node[] nodes: allNodes){
            for(Node n: nodes){
                if(!n.isVisited()){
                    isComplete = false;
                    break;
                }
            }
        }

        return isComplete;

    }

    public static int updateSideWall(int cell_openness){
        int updatedCell_openness = cell_openness;
        
        switch (cell_openness) {
            case 0:
                // Both closed, when updating side wall , it becomes right only open
                updatedCell_openness = 1;
                break;
            case 1:
                // Right only open, there is no need for any updating on this case
                break;
            case 2:
                // Down only open, when updating side wall, it becomes both open
                updatedCell_openness = 3;
                break;
            case 3:
                // both open
                break;
        }

        return updatedCell_openness;
    }

    public static int updatebottomWall(int cell_openness) {
        int updatedCell_openness = cell_openness;

        switch (cell_openness) {
            case 0:
                // Both closed, when updating bottom wall , it becomes down only open
                updatedCell_openness = 2;
                break;
            case 1:
                // Right only open, when updating bottom wall , it becomes both open
                updatedCell_openness = 3;
                break;
            case 2:
                // Down only open, no need to change
                break;
            case 3:
                // both open, no need to change
                break;

        }

        return updatedCell_openness;
    }


    public static String printMazeGraph(int rows, int collum) {
        String mazeString = "-";

        for (int i = 0; i < allNodes.length; i++) {

            if (i != 0) {
                mazeString += "\n";
            } 
            else if (i == 0 ) {
                for (int rowsCount = 0; rowsCount < rows; rowsCount++) {
                    mazeString += "---";
                }

                mazeString += "\n";
            }

            for (int j = 0; j < allNodes[i].length; j++) {
                if (j == 0) {
                    mazeString += "|";
                }
                mazeString += allNodes[i][j].getNodeGraphRight();
            }
            
            mazeString += "\n";
//
            if(i == allNodes.length-1){
                mazeString += "-";
                for (int rowsCount = 0; rowsCount < rows; rowsCount++) {
                    mazeString += "---";
                }

                mazeString += "\n";
            }else {
                for (int j = 0; j < allNodes[i].length; j++) {
                    if (j == 0) {
                        mazeString += "|";
                    }
                    mazeString += allNodes[i][j].getNodeGraphBottom();
                }
            }
            
        }

        return mazeString;
    }


    public static String printMazeString(int rows, int collum) {
        String mazeString = rows+","+ collum + ":";
        String startNode = ""; String endNode = "";
        String cell_openness_list = "";
        //search start node and end node
        for(Node[] node : allNodes){
            for(Node n:node){
                cell_openness_list += n.getCell_openness();
                if(n.getNodeDisplay().equals("S ")){
                    startNode = n.getNodeIndex();
                }
                else if (n.getNodeDisplay().equals("F ")) {
                    endNode = n.getNodeIndex();
                }
            }
        }

        mazeString += startNode + ":" + endNode + ":" + cell_openness_list + "\n";

      
        

        return mazeString + "\n";
    }


}
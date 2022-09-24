import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;

public class MazeSolverBFS {
    private static String mazeData = "";
    private static Node[] possiableNextNodes;
    private static Node[][] allNodes;
    private static int rows;
    private static int collum;
    private static String startNode;
    private static String finishNode;
    private static Stack<Node> pathTracking ;


    public static void main(String[] args){
        pathTracking = new Stack<>();

        try {
            File file = new File("data.dat");
            Scanner reader = new Scanner(file);
            //scann the first line to get the input of the maze
            mazeData = reader.nextLine();
            System.out.println(mazeData);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
                
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // first copy the maze into each variable
        String [] split = mazeData.split(":");
        String [] row_collum = split[0].split(",") ;

        rows = Integer.parseInt(row_collum[0]);
        collum = Integer.parseInt(row_collum[1]);

        generateMazeNode(rows, collum); //initial the maze

        startNode = split[1];
        finishNode = split[2];


        String cell_openness_list = split[3];
        String [] cell_opennes = new String[cell_openness_list.length()];

        int beginIndex = 0;
        int endIndex = 1;
        for(int i=0; i < cell_openness_list.length();i++){

            cell_opennes[i] = cell_openness_list.substring(beginIndex++, endIndex++);
        }
        
        //update the maze with user input data
        UpdateMaze(cell_opennes);

        //start solving the maze
        solution();

        for(Node n:pathTracking){
            System.out.println(n.getNodeIndex());
        }

    }

    public static void solution(){
        Node currentNode = new Node();
        
        //first find the startNode push to the stack
        for(Node [] node: allNodes){
            for(Node n : node ){
                if(n.getNodeDisplay().equals("S ")){
                    pathTracking.push(n);
                    n.setVisited(true); 
                    currentNode = n;
                    break;
                }
            }
        }


        Node temp = getPossiableNextMove(currentNode);

        while (true) {
            temp = getPossiableNextMove(currentNode);

            if (temp == null ) {
                
                pathTracking.pop();
                currentNode = pathTracking.peek();

            }else if(temp.getNodeDisplay().equals("F ")){
                //reach the final Node
                temp.setVisited(true);
                pathTracking.push(temp);
                currentNode = temp;
                break;
            }
            else {
                temp.setVisited(true);
                pathTracking.push(temp);
                currentNode = temp;
            }

        }

    }

    public static Node getPossiableNextMove(Node currentNode) {

        int counter = 0;
        possiableNextNodes = new Node[4]; // one node can possiable have 4 ways to move

        // first search all possiable Node to visit next and store all into an array
        for (Node[] n : allNodes) {
            for (Node foundNode : n) {
                if ((foundNode.getNodeRow() == currentNode.getNodeRow() + 1)
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum())
                        && foundNode.isVisited() == false && (currentNode.getCell_openness()==2 || currentNode
                                .getCell_openness() == 3) ) {

                    possiableNextNodes[counter++] = foundNode;

                } else if ((foundNode.getNodeRow() == currentNode.getNodeRow())
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum() + 1) && foundNode
                                .isVisited() == false 
                        && (currentNode.getCell_openness() == 1 || currentNode
                                .getCell_openness() == 3)) {

                    possiableNextNodes[counter++] = foundNode;

                } else if ((foundNode.getNodeRow() == currentNode.getNodeRow())
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum() - 1) && foundNode
                                .isVisited() == false 
                        && (foundNode.getCell_openness() == 1 || foundNode
                                .getCell_openness() == 3)) {

                    possiableNextNodes[counter++] = foundNode;

                } else if ((foundNode.getNodeRow() == currentNode.getNodeRow() - 1)
                        && (foundNode.getNodeCollum() == currentNode.getNodeCollum()) && foundNode
                                .isVisited() == false 
                        && (foundNode.getCell_openness() == 2 || foundNode
                                .getCell_openness() == 3)) {

                    possiableNextNodes[counter++] = foundNode;

                }

            }
        }

        int arrayRange = possiableNextNodes.length;
        // clean the null node
        for (Node n : possiableNextNodes) {
            if (n == null) {
                arrayRange--;
            }
        }

        if (arrayRange == 0) {
            return null;
        }
        Node[] tempNode = new Node[arrayRange];
        for (int i = 0; i < tempNode.length; i++) {
            tempNode[i] = possiableNextNodes[i];
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

    public static void UpdateMaze(String [] cell_opennes){
        int StringCounter = 0;

        for (Node[] node : allNodes) {
            for (Node n : node) {
                n.setCell_openness(Integer.parseInt(cell_opennes[StringCounter++]));
                if(n.getNodeIndex().equals(startNode)){
                    //update the node's display name
                    n.setNodeDisplay("S ");
                }else if(n.getNodeIndex().equals(finishNode)){
                    n.setNodeDisplay("F ");
                }
            
            }
        }
    }

    public static boolean checkComplete(Node[][] allNodes) {

        boolean isComplete = true;

        for (Node[] nodes : allNodes) {
            for (Node n : nodes) {
                if (!n.isVisited()) {
                    isComplete = false;
                    break;
                }
            }
        }

        return isComplete;

    }

}

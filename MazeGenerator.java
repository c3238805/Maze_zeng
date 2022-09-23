/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
    
public class MazeGenerator{




    public static void main(String[] args) {
        
        try {
            
            int rows = Integer.parseInt(args[0]);   // user input for number of rows
            int collum = Integer.parseInt(args[1]); // user input for number of collum
            String datString = args[2]; // user set output maze file


        } catch (Exception e) {

            System.out.println(e);
            
            // TODO: handle exception
        }
        

    }

}
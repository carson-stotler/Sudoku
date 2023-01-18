/***
 * @author Carson Stotler
 * @version 0.1
 * Date of Creation: Oct. 2, 2022
 * Last Date Modified: Oct. 7, 2022
 * Assignment: HW 3
 * TEST CLASS -- Using Recursion to solve a Sudoku Puzzle
 */
 public class Test {
    public static void main(String[] args){
        try{
            Board puzzle = new Board("sudoku.txt");
            System.out.println();
            System.out.println("Starting Puzzle:");
            System.out.println(puzzle);
            puzzle.solve();
            System.out.println("Solved Puzzle:");
            System.out.println(puzzle);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        
    }
}
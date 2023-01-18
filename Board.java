/***
 * @author Carson Stotler
 * @version 0.1
 * Date of Creation: Oct. 2, 2022
 * Last Date Modified: Oct. 7, 2022
 * Assignment: HW 3
 * Using Recursion to solve a Sudoku Puzzle
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class Board {
    // data members
    private ArrayList<ArrayList<Integer>> board;
    private ArrayList<Integer> availableNumbers;
    private final int EMPTY = 0;
    private int count = 0;

    /***
     * 1-arg Constuctor
     * @param filename String representing the filename
     * Initializes 2d ArrayList, make all cells EMPTY, initializes availableNumbers ArrayList, and calls readBoard
     */
    public Board (String filename){
        this.board = new ArrayList<ArrayList<Integer>>();

        for ( int j = 0; j < 9; j++ ){
            ArrayList<Integer> rows = new ArrayList<Integer>(9);
            for (int i = 0; i < 9; i++ ){
                rows.add(EMPTY);
            }
            board.add(rows);
        }
        
        this.availableNumbers = new ArrayList<Integer>();
        for (int k = 0; k < 9; k++ ){
            availableNumbers.add(9);
        }
        
        readBoard(filename);
    }

    /***
     * Method to read board as a file and will catch File Not Found exception and throws IllegalArgumentException.
     * @param filename for the file of the previous layout
     * Uses isAvailable and checkMove to ensure all input is correct 
     * no return
     */
    private void readBoard(String filename) throws IllegalArgumentException {
        File f = new File(filename);
        int rows = 0;
        Integer element = 0;
       
        try{
            Scanner fileScanner = new Scanner(f);
            
            while(fileScanner.hasNextLine()) {
                ArrayList<Integer> line = new ArrayList<Integer>(9);
                
                String[] str = fileScanner.nextLine().split("\\s");

                for (int i = 0; i < str.length; i++ ){
                    element = Integer.parseInt(str[i]);
                    line.add(element);

                    
                }
                board.set(rows, line);
                rows++;
                    
            }
            // use isAvailable and checkMove
            for (int m = 0; m < board.size(); m++ ){
                for (int n = 0; n < board.get(m).size(); n++){
                    Integer ex = board.get(m).get(n);
                    if ( ex != 0 ) {
                        if ( isAvailable(ex) ){
                            if ( checkMove(m, n) ){
                                Integer test = availableNumbers.get(ex-1);
                                availableNumbers.set(ex-1, --test);
                            }
                            else {
                                throw new IllegalArgumentException("Failed at Row: " + m + " and Column: " + n);
                            } 
                        } else {
                            throw new IllegalArgumentException("Failed at Row: " + m + " and Column: " + n);
                        } 
                    } 
                }
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not Found.");
            System.exit(0);
        }
        catch (IllegalArgumentException e){
            System.out.println("Failed at Row: " + rows + " and Column: ");
        }
    }

    /***
     * Method to test if digit is available from availabeNumbers ArrayList.
     * @param digit int of the digit being tested
     * @return boolean - true if test > 0, false if not
     */
    private boolean isAvailable(int digit){
        Integer test = availableNumbers.get(digit-1);
        if ( test > 0 ){
            return true;
        } else {
            return false;
        }
    }

    /***
     * Method to test if all digits in availabeNumbers ArrayList is 0.
     * no param
     * @return boolean - true if all of availableNumbers = 0, false if one does not equal 0
     */
    private boolean noNumbersLeft(){
        boolean noNums = false;
        for (int i : availableNumbers ){
            if ( availableNumbers.get(i) > 0 ){
                return false;
            } 
        }
        return true;
    }

    /***
     * Method to test if digit represented by row and col are legal in the moves of Sudoku.
     * @param row int of the row being tested
     * @param col int of the col being tested
     * @return boolean - true if all tests pass, false if even one fails
     */
    private boolean checkMove(int row, int col){

        Integer num = board.get(row).get(col);
        if ( num == 0 ){
            return true;
        }
        // check rows
        for ( int i = 0; i < board.get(row).size(); i++ ){
            if ( board.get(row).get(i) == num && (i != col) ){
                return false;
            } 
        }
        // check cols
        for ( int j = 0; j < 9; j++ ){
            if ( board.get(j).get(col) == num && (j != row) ){
                return false;
            } 
        } 
        // check blocks
        int r = row - row%3;
        int c = col - col%3;
        for ( int m = r; m < r + 3; m++ ){
            for ( int n = c; n < c + 3; n++ ){
                if ( board.get(m).get(n) == num && (m != row) && (n != col)){
                    return false;
                } 
            }
        }
        
        return true;
        
    }

    /***
     * Method that uses recursion and test the input of row and col for each cell of the puzzle.
     * @param row int of the row being tested
     * @param col int of the col being tested
     * @return boolean - true if noNumbersLeft() or gone through whole arrayList, false if something fails
     */
    public boolean solve(int row, int col) {
        count++; 

        // base case 
        if ( noNumbersLeft() || row == 9 ){
            System.out.println("Recursive Calls: " + count);
            return true;
        }
        if ( col == 9 ){
            return solve(row+1, 0); 
        }
        if ( board.get(row).get(col) != EMPTY ){
            return solve(row, col+1);
        }
            
        for ( int i = 0; i < 9 ; i++ ){
            board.get(row).set(col, i+1);
            if ( isAvailable(i+1) && checkMove(row, col) ){
                if ( solve( row, col ) ) {
                    Integer test = availableNumbers.get(i);
                    availableNumbers.set(i, --test);
                    return true;
                } else {
                    board.get(row).set(col, EMPTY);
                }
            } else {
                board.get(row).set(col, EMPTY);
            }     
        } 
        return false;
    }

    /***
     * Method that calls its helper method but starts the recursive chain
     * no param
     * @return boolean - true if solve(0,0) returns true, false if it returns false
     */
    public boolean solve(){
        return solve(0,0);
        
    }
 
    /***
     * Method toString to return a formatted String of board layout
     * no parameters
     * @return String of formatted board layout 
     */
    @Override
    public String toString(){
        String x = "";
        for (int i = 0; i < board.size(); i++ ){
            for (int j = 0; j < board.get(i).size(); j++){
                x += board.get(i).get(j) + " ";
            }
            x += "\n";
        }
        return x;
    }

}
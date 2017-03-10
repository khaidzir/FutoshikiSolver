/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikisolver;

import static futoshikisolver.Element.GREATER_HOR;
import static futoshikisolver.Element.GREATER_VER;
import static futoshikisolver.Element.LESSTHAN_HOR;
import static futoshikisolver.Element.LESSTHAN_VER;
import java.util.PriorityQueue;

/**
 *
 * @author khaidzir
 */
public class BnBFutoshiki extends AlgorithmFutoshiki {

    PriorityQueue<BnBElement> queue;
    
    public BnBFutoshiki(Element[][] board) {
        super(board);
        queue = new PriorityQueue<>(new BnBComparator());
    }

//    @Override
//    public boolean solve() {
//        found = false;
//        BnBElement be = new BnBElement(puzzle);
//        be.calculate();
//        found = be.solution;
//        if (be.possible) queue.add(be);
//        while(!queue.isEmpty() && !found) {
//            BnBElement b = queue.poll();
//            for(int a : b.possibleValues.get(b.rowmin/2).get(b.colmin/2)) {
//                BnBElement newBE = new BnBElement(b.puzzle);
//                newBE.puzzle[b.rowmin][b.colmin].el = a;
//                newBE.calculate();
//                if (newBE.possible && cekOperator(newBE.puzzle, b.rowmin, b.colmin)) {
//                    if (newBE.solution) {
//                        puzzle = newBE.puzzle;
//                        found = true;
//                    } else {
//                        queue.add(newBE);
//                    }
//                }
//            }
//        }
//        
//        return found;
//    }
    
    @Override
    public boolean solve() {
        found = false;
        BnBElement be = new BnBElement(puzzle);
        be.calculate();
        found = be.solution;
        if (be.possible) queue.add(be);
        while(!queue.isEmpty() && !found) {
            BnBElement b = queue.poll();
            for(int a : b.possibleValues.get(b.rowmin/2).get(b.colmin/2)) {
                BnBElement newBE = new BnBElement();
                newBE.duplicate(b);
                newBE.insert(a);
                if (newBE.possible && cekOperator(newBE.puzzle, b.rowmin, b.colmin)) {
                    if (newBE.solution) {
                        puzzle = newBE.puzzle;
                        found = true;
                    } else {
                        queue.add(newBE);
                    }
                }
            }
        }
        
        return found;
    }
    
    private boolean cekOperator(Element[][] board, int row, int col) {
        int val = board[row][col].el;
        if (row > 0) {
            if ( board[row-1][col].el == GREATER_VER && board[row-2][col].el != 0                    
                    && val > board[row-2][col].el ) return false;
            if ( board[row-1][col].el == LESSTHAN_VER && board[row-2][col].el != 0                    
                    && val < board[row-2][col].el ) return false;
        }
        if (row < size-1) {
            if ( board[row+1][col].el == GREATER_VER && board[row+2][col].el != 0                    
                    && val < board[row+2][col].el ) return false;
            if ( board[row+1][col].el == LESSTHAN_VER && board[row+2][col].el != 0     
                    && val > board[row+2][col].el ) return false;
        }
        if (col > 0) {
            if ( board[row][col-1].el == GREATER_HOR && board[row][col-2].el != 0
                    && val > board[row][col-2].el ) return false;
            if ( board[row][col-1].el == LESSTHAN_HOR && board[row][col-2].el != 0
                    && val < board[row][col-2].el ) return false;
        }
        if (col < size-1) {
            if ( board[row][col+1].el == GREATER_HOR && board[row][col+2].el != 0
                    && val < board[row][col+2].el) return false;
            if ( board[row][col+1].el == LESSTHAN_HOR && board[row][col+2].el != 0
                    && val > board[row][col+2].el) return false;
        }
        
        return true;
    }
    
}

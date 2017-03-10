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

/**
 *
 * @author khaidzir
 */
public class BacktrackFutoshiki extends AlgorithmFutoshiki {

    int currRow, currCol;
    
    public BacktrackFutoshiki(Element[][] board) {
        super(board);
    }
    
    @Override
    public boolean solve() {
        resetCheckBoard();
        found = false;
        boolean end = false;
        currRow = 0;
        currCol = -2;
        
        move();
        do {
            if (currRow > size-1) { //Selesai
                end = true;
                found = true;
            }   
            else {
                increment();
                if (puzzle[currRow][currCol].el < N) {
                    while(!cek()) {
                        increment();
                        if (puzzle[currRow][currCol].el == N) break;
                    }
                }
                if (puzzle[currRow][currCol].el == N) {
                    if(!cek()) {
                        backtrack();
                        if (currRow < 0) end = true;    //Tidak ada solusi
                    } else move();
                } else move();              
            }
        } while(!end);
        
        return found;
    }
    
    private boolean cek2() {
        if (!rowCheck[currRow/2][puzzle[currRow][currCol].el-1]
                && !colCheck[currCol/2][puzzle[currRow][currCol].el-1]) {
            boolean b = cekOperator();
            if (!b) return false;
            rowCheck[currRow/2][puzzle[currRow][currCol].el-1] = true;
            colCheck[currCol/2][puzzle[currRow][currCol].el-1] = true;
            return true;
        } else return false;        
    }
    
    private boolean cek() {
        // Cek baris
        for(int i=0; i<currCol; i+=2) {
            if (puzzle[currRow][i].el == puzzle[currRow][currCol].el) return false;
        }
        for(int i=currCol+2; i<size; i++) {
            if (puzzle[currRow][i].el == puzzle[currRow][currCol].el) return false;
        }
        
        //Cek kolom
        for(int i=0; i<currRow; i++) {
            if (puzzle[i][currCol].el == puzzle[currRow][currCol].el) return false;
        }
        for(int i=currRow+2; i<size; i++) {
            if (puzzle[i][currCol].el == puzzle[currRow][currCol].el) return false;
        }
        
        return cekOperator();
    }
    
    private boolean cekOperator() {
        // Cek vertikal
        if (currRow > 0) {
            switch(puzzle[currRow-1][currCol].el) {
                case GREATER_VER :
                    if (puzzle[currRow-2][currCol].el < puzzle[currRow][currCol].el)
                        return false;
                    break;
                case LESSTHAN_VER :
                    if (puzzle[currRow-2][currCol].el > puzzle[currRow][currCol].el)
                        return false;
                    break;
                default : break;
            }
        }
        
        //Cek horizontal
        if (currCol > 0) {
            switch(puzzle[currRow][currCol-1].el) {
                case GREATER_HOR :
                    if (puzzle[currRow][currCol-2].el < puzzle[currRow][currCol].el)
                        return false;
                    break;
                case LESSTHAN_HOR :
                    if (puzzle[currRow][currCol-2].el > puzzle[currRow][currCol].el)
                        return false;
                    break;
                default : break;
            }
            return true;
        } else return true;
    }
    
    private void backtrack() {
        rowCheck[currRow/2][puzzle[currRow][currCol].el-1] = false;
        colCheck[currCol/2][puzzle[currRow][currCol].el-1] = false;
        puzzle[currRow][currCol].el = 0;        
        boolean stop = false;
        do {
            if (currCol == 0) {
                currRow -= 2;
                currCol = size-1;
            } else {
                currCol -= 2;
            }
            
            if (currRow < 0) stop = true;
            else {
                if (puzzle[currRow][currCol].vul) {
                    if (puzzle[currRow][currCol].el == N) {
                        rowCheck[currRow/2][puzzle[currRow][currCol].el-1] = false;
                        colCheck[currCol/2][puzzle[currRow][currCol].el-1] = false;
                        puzzle[currRow][currCol].el = 0;
                    } else {
                        rowCheck[currRow/2][puzzle[currRow][currCol].el-1] = false;
                        colCheck[currCol/2][puzzle[currRow][currCol].el-1] = false;
                        stop = true;
                    }
                }
            }
        } while(!stop);
    }
    
    private void move() {
        boolean stop;
        do {
            if (currCol == size-1) {
                currRow += 2;
                currCol = 0;
            } else {
                currCol += 2;
            }
            
            if (currRow > size-1) stop = true;
            else stop = puzzle[currRow][currCol].vul;
            
        } while(!stop);
    }
    
    private void increment() {
        puzzle[currRow][currCol].el++;
    }
}

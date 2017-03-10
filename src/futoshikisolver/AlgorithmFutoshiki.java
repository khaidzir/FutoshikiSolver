/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikisolver;

/**
 *
 * @author khaidzir
 */
public abstract class AlgorithmFutoshiki {    
    protected Element[][] puzzle;
    protected boolean [][] rowCheck, colCheck;
    protected int N, size;
    protected boolean found;
    
    /* Constructor */
    public AlgorithmFutoshiki(Element[][] board) {
        puzzle = new Element[board.length][board[0].length];
        for(int i=0; i<puzzle.length; i++) {
            for(int j=0; j<puzzle[i].length; j++) {
                puzzle[i][j] = new Element(board[i][j].el, board[i][j].vul);
            }
        }
        
        N = (board.length+1)/2;
        size = board.length;
        rowCheck = new boolean[N][N];
        colCheck = new boolean[N][N];
        resetCheckBoard();
        found = false;
    }
    
    final void resetCheckBoard() {
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                rowCheck[i][j] = false;
                colCheck[i][j] = false;
            }
        }
        
        for(int i=0; i<size; i+=2) {
            for(int j=0; j<size; j+=2) {
                if (!puzzle[i][j].vul) {
                    rowCheck[i/2][puzzle[i][j].el-1] = true;
                    colCheck[j/2][puzzle[i][j].el-1] = true;
                }
            }
        }
    }
    
    public abstract boolean solve();
    
    // Getter
    public Element[][] getPuzzle() {
        return puzzle;
    }
    
    // Setter
    public void setPuzzle(Element[][] board) {
        puzzle = new Element[board.length][board[0].length];
        for(int i=0; i<puzzle.length; i++) {
            for(int j=0; j<puzzle[i].length; j++) {
                puzzle[i][j] = new Element(board[i][j].el, board[i][j].vul);
            }
        }
        
        N = (board.length+1)/2;
        size = board.length;
        rowCheck = new boolean[N][N];
        colCheck = new boolean[N][N];
        resetCheckBoard();
    }
}

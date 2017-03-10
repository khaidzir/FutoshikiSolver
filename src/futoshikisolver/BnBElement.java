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
import static futoshikisolver.Element.NO_OPERATOR;
import java.util.ArrayList;

/**
 *
 * @author khaidzir
 */
public class BnBElement {
    int size, N;
    
    public boolean possible, solution;
    public Element[][] puzzle;
    public ArrayList<ArrayList<ArrayList<Integer>>> possibleValues;
    public int rowmin, colmin, heuval;
    
    public BnBElement() {
        
    }
    
    public BnBElement(Element[][] board) {
        init(board);
    }
    
    public void calculate() {
        calculatePossibilities();
    }
    
    public void duplicate(BnBElement other) {
        size = other.puzzle.length;
        N = (size+1)/2;
        possible = other.possible;
        solution = other.solution;
        rowmin = other.rowmin;
        colmin = other.colmin;
        heuval = other.heuval;
        puzzle = new Element[size][size];
        for(int i=0; i<other.puzzle.length; i++) {
            for(int j=0; j<other.puzzle[i].length; j++) {
                puzzle[i][j] = new Element(other.puzzle[i][j].el, other.puzzle[i][j].vul);
            }
        }
        possibleValues = new ArrayList<>();
        for(int i=0; i<other.possibleValues.size(); i++) {
            possibleValues.add(new ArrayList<>());
            for(int j=0; j<other.possibleValues.get(i).size(); j++) {
                possibleValues.get(i).add(new ArrayList<>(other.possibleValues.get(i).get(j)));
            }
        }
    }
    
    /* Menambahkan nilai val pada puzzle[rowmin,colmin], val harus valid */
    public void insert(int val) {
        
        puzzle[rowmin][colmin].el = val;
        heuval -= possibleValues.get(rowmin/2).get(colmin/2).size();
        possibleValues.get(rowmin/2).get(colmin/2).clear();
        // Update possible values
        int tempsz;        
        for(int i=0; i<size; i+=2) {
            // Cek baris
            if (possibleValues.get(rowmin/2).get(i/2).remove(Integer.valueOf(val))) {
                heuval--;
                tempsz = possibleValues.get(rowmin/2).get(i/2).size();
                if (tempsz == 0) {
                    possible = false;
                    break;
                }
            }
            // Cek kolom
            if (possibleValues.get(i/2).get(colmin/2).remove(Integer.valueOf(val))) {
                heuval--;
                tempsz = possibleValues.get(i/2).get(colmin/2).size();
                if (tempsz == 0) {
                    possible = false;
                    break;
                }
            }
        }
        if (!possible) return;
        //Cari rowmin & colmin yang baru
        int valmin = N+1;
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                tempsz = possibleValues.get(i).get(j).size();
                if (tempsz > 0 && tempsz < valmin) {
                    valmin = tempsz;
                    rowmin = i*2;
                    colmin = j*2;
                }
            }
        }
        if (heuval == 0 && possible) solution = true;
    }
    
    private void init(Element[][] board) {
        size = board.length;
        N = (board.length+1)/2;
        puzzle = new Element[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                puzzle[i][j] = new Element(board[i][j].el, board[i][j].vul);
            }
        }
        possible = true;
        solution = false;
    }
    
    private void calculatePossibilities() {
        possibleValues = new ArrayList<>();
        ArrayList<Integer> p = new ArrayList<>();
        for(int i=1; i<=N; i++) p.add(i);
        
        int minPosVal = N+1;
        int temp;
        heuval = 0;
        for(int i=0; i<size&&possible; i+=2) {
            possibleValues.add(new ArrayList<>());
            for(int j=0; j<size&&possible; j+=2) {
                if (puzzle[i][j].el != 0) {
                    possibleValues.get(i/2).add(new ArrayList<>());
                } else {
                    possibleValues.get(i/2).add(new ArrayList<>(p));
                    cekSel(i,j);                    
//                    System.out.println(possibleValues.get(i/2).get(j/2));
                    temp = possibleValues.get(i/2).get(j/2).size();
                    if (temp == 0)
                        possible = false;
                    else {
                        heuval += temp;
                        if (temp < minPosVal) {
                            minPosVal = temp;
                            rowmin = i;
                            colmin = j;
                        }
                    }
                }
            }
        }
//        System.out.println("Heuristik = " + heuval);
        if (heuval == 0 && possible) solution = true;
    }
    
    private void cekSel(int row, int col) {
        cekOperator(row, col);
        cekUnikAngka(row, col);
    }
    
    private void cekOperator(int row, int col) {
        int greath=0, lessh=0, greatv=0, lessv=0;
        int cek, temp;
        /* Cek horizontal */
        // cek ke kiri
        if (col > 0) {
            cek = puzzle[row][col-1].el;
            temp = 0;
            if (cek != NO_OPERATOR) {
                temp++;
                for(int i=col-3; i>0; i-=2) {
                    if (puzzle[row][i].el == cek) temp++;
                    else break;
                }
                if (cek == LESSTHAN_HOR) greath += temp;
                else if (cek == GREATER_HOR) lessh += temp;
            }
        }
        // cek ke kanan
        if (col < size-1) {
            cek = puzzle[row][col+1].el;
            temp = 0;
            if (cek != NO_OPERATOR) {
                temp++;
                for(int i=col+3; i<size-1; i+=2) {
                    if (puzzle[row][i].el == cek) temp++;
                    else break;
                }
                if (cek == LESSTHAN_HOR) lessh += temp;
                else if (cek == GREATER_HOR) greath += temp;
            }            
        }
        
        /* Cek vertikal */
        // cek ke atas
        if (row > 0) {
            cek = puzzle[col][row-1].el;
            temp = 0;
            if (cek != NO_OPERATOR) {
                temp++;
                for(int i=row-3; i>0; i-=2) {
                    if (puzzle[i][col].el == cek) temp++;
                    else break;
                }
                if (cek == LESSTHAN_VER) greatv += temp;
                else if (cek == GREATER_VER) lessv += temp;
            }
        }
        // cek ke bawah
        if (row < size-1) {
            cek = puzzle[col][row+1].el;
            temp = 0;
            if (cek != NO_OPERATOR) {
                temp++;
                for(int i=row+3; i<size-1; i+=2) {
                    if (puzzle[i][col].el == cek) temp++;
                    else break;
                }
                if (cek == LESSTHAN_VER) lessv += temp;
                else if (cek == GREATER_VER) greatv += temp;
            }            
        }
        
        int great = Math.max(greath, greatv);
        int less = Math.max(lessh, lessv);
        temp = 1;
        for(int i=0; i<great; i++) {
            possibleValues.get(row/2).get(col/2).remove(Integer.valueOf(temp));
            temp++;
        }
        temp = N;
        for(int i=0; i<less; i++) {
            possibleValues.get(row/2).get(col/2).remove(Integer.valueOf(temp));
            temp--;
        }
    }
    
    private void cekUnikAngka(int row, int col) {
       if (puzzle[row][col].el != 0) return;
        for(int i=0; i<size; i+=2) {
            if (puzzle[row][i].el != 0)  {              
//                System.out.println("\nSebelum : " + possibleValues.get(row/2).get(col/2));
                possibleValues.get(row/2).get(col/2).remove(Integer.valueOf(puzzle[row][i].el));
//                System.out.println("Sesudah : " + possibleValues.get(row/2).get(col/2));
            }
            if (puzzle[i][col].el != 0)
                possibleValues.get(row/2).get(col/2).remove(Integer.valueOf(puzzle[i][col].el));
        } 
    }
    
}

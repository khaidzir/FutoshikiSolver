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
import java.util.ArrayList;

/**
 *
 * @author khaidzir
 */
public class BruteForceFutoshiki extends AlgorithmFutoshiki{
    
    private boolean found;
    private ArrayList<ArrayList<Element[]>> possibilities;
    
    public BruteForceFutoshiki(Element[][] board) {
        super(board);
    }
    
    @Override
    public boolean solve() {
        generatePossibilities();
        found = false;
        coba(0);
        return found;
    }
    
    private void coba(int level) {
        if (level == size-1) {
            for(int i=0; i<possibilities.get(level/2).size()&&!found; i++) {
                puzzle[level] = possibilities.get(level/2).get(i);
                found = check();
            }
        } else {
            for(int i=0; i<possibilities.get(level/2).size()&&!found; i++) {
                puzzle[level] = possibilities.get(level/2).get(i);
                coba(level+2);
            }
        }
    }
    
    private boolean check() {
        // Cek unik angka kolom
        for(int i=0; i<size; i+=2) {
            for(int j=0; j<size; j+=2) {
                for(int k=j+2; k<size; k+=2) {
                    if (puzzle[j][i].el == puzzle[k][i].el) return false;
                }
            }
        }
        
        // Cek operator horizontal
        for(int i=0; i<puzzle.length; i+=2) {
            for(int j=1; j<puzzle[i].length; j+=2) {
                if (puzzle[i][j].el == GREATER_HOR) {
                    if (puzzle[i][j-1].el < puzzle[i][j+1].el) return false;
                } else if (puzzle[i][j].el == LESSTHAN_HOR) {
                    if (puzzle[i][j-1].el > puzzle[i][j+1].el) return false;
                }  
            }
        }
        
        // Cek operator vertikal
        for(int i=1; i<puzzle.length; i+=2) {
            for(int j=0; j<puzzle[i].length; j+=2) {
                if (puzzle[i][j].el == GREATER_VER) {
                    if (puzzle[i-1][j].el < puzzle[i+1][j].el) return false;
                } else if (puzzle[i][j].el == LESSTHAN_VER) {
                    if (puzzle[i-1][j].el > puzzle[i+1][j].el) return false;
                }  
            }
        }
        
        return true;
    }
    
    public void generatePossibilities() {
        possibilities = new ArrayList<>();
        for(int i=0; i<puzzle.length; i+=2) {
            ArrayList<Element> unique = uniqueNumber(puzzle[i]);
            possibilities.add(heapAlgorithm(puzzle[i], unique));
        }
    }
    
    // Mengembalikan nilai-nilai element yang mungkin untuk diisi
    // VulIdx menjadi indeks-indeks yang terisi angka (invul) pada row
    private ArrayList<Element> uniqueNumber(Element [] row) {
        int n = (row.length+1)/2;
        boolean [] possible = new boolean[n];
        for(int i=0; i<n; i++) {
            possible[i] = true;
        }
        for(int i=0; i<row.length; i+=2) {
            if (!row[i].vul) {
                possible[row[i].el-1] = false;
            }
        }
        
        ArrayList<Element> ret = new ArrayList<>();
        for(int i=0; i<n; i++) {
            if (possible[i]) {
                ret.add(new Element(i+1, true));
            }
        }
        return ret;
    }
    
    /* Permutation using heap algorithm */
    public ArrayList<ArrayList<Integer>> heapAlgorithmInt(ArrayList<Integer> arr) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        int n = arr.size();
        int [] c = new int[n];
        for(int i=0; i<n; i++) c[i] = 0;
        int i = 0;
        ret.add(new ArrayList<>(arr));
        int temp, j;
        while (i < n) {
            if (c[i] < i) {
                ArrayList<Integer> newArr = new ArrayList<>(ret.get(ret.size()-1));
                j = (i%2) * c[i];
                //swap
                temp = newArr.get(j);
                newArr.set(j, newArr.get(i));
                newArr.set(i, temp);
                ret.add(newArr);
                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }
        
        return ret;
    }
    
    /*
        Row adalah row asli dari puzzle
        idx adalah indeks-indeks (angka) pada row yang tidak dapat diisi
        arr adalah elemen-elemen yang mungkin untuk diisikan pada row
    */
    public ArrayList<Element[]> heapAlgorithm(Element[] row, ArrayList<Element> arr) {
        ArrayList<Element[]> ret = new ArrayList<>();
        int n = arr.size();
        ArrayList<Element> tempArr = new ArrayList<>(arr);
        
        Element [] nArr = new Element[row.length];
        int i = 0, j = 0;
        for(int k=0; k<nArr.length; k++) {
            if (!row[k].vul) {
                nArr[k] = new Element(row[k].el, row[k].vul);
            } else {
                nArr[k] = new Element(tempArr.get(i).el, true);
                i++;
            }
        }
        ret.add(nArr);
        
        int [] c = new int[n];
        for(i=0; i<n; i++) c[i] = 0;
        Element temp;
        i = 0;
        while (i < n) {
            if (c[i] < i) {
                j = (i%2) * c[i];
                //swap
                temp = tempArr.get(j);
                tempArr.set(j, tempArr.get(i));
                tempArr.set(i, temp);
                
                Element [] newArr = new Element[row.length];
                int a = 0;
                for(int k=0; k<newArr.length; k++) {
                    if (!row[k].vul) {
                        newArr[k] = new Element(row[k].el, row[k].vul);
                    } else {
                        newArr[k] = new Element(tempArr.get(a).el, true);
                        a++;
                    }
                }
                ret.add(newArr);
                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }
        
        return ret;
    }
    
    Element[] convIntToEl(ArrayList<Integer> arr) {
        Element [] ret = new Element[arr.size()];
        for(int i=0; i<arr.size(); i++) {
            ret[i] = new Element(arr.get(i), true);
        }
        return ret;
    }
    
    public void printArray(int[] arr) {
        for(int a : arr) System.out.print(a + " ");
        System.out.println("");
    }

    
    
}

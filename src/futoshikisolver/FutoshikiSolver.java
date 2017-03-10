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
public class FutoshikiSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length < 3 || args.length > 5) {
            printUsage();
            return;
        }
        
        boolean bruteforce=false, backtrack=false, bnb=false;
        String file = "";
        
        int i=0;
        boolean alg =  false;
        do {
            if (args[i].equals("-bf")) {
                bruteforce = true;
                alg = true;
            }
            
            if (args[i].equals("-bt")) {
                backtrack = true;
                alg = true;
            }
            
            if (args[i].equals("-bn")) {
                bnb = true;
                alg = true;
            }
            
            if (args[i].equals("-f")) {
                if (i == args.length-1) {
                    printUsage();
                    return;
                }
                
                i++;
                file = args[i];
            }
            
            i++;
        } while(i < args.length);
        
        if (file.length() == 0) {
            printUsage();
            return;
        }
        if (!alg) {
            printUsage();
            return;
        }
        
//        String path = "C:/Users/khaidzir/Desktop/futoshiki5.txt";
//        Element[][] tes = Tools.readPuzzle(path);
        
        Element[][] tes = Tools.readPuzzle(file);
        
        System.out.println("Puzzle :");
        System.out.println(Tools.stringPuzzle(tes));
        
        long start, end;
        double exec;
        
        // Bruteforce
        if (bruteforce) {
            BruteForceFutoshiki bff = new BruteForceFutoshiki(tes);
            start = System.nanoTime();
            boolean bf = bff.solve();
            end = System.nanoTime();
            exec = (end-start)/1000000f;
            System.out.println("Hasil bruteforce :");
            if (bf) System.out.println(Tools.stringPuzzle(bff.getPuzzle()));
            else System.out.println("Solusi tidak ada");
            System.out.println("Waktu eksekusi : " + String.format("%.3f", exec) + " ms");
        }
        
        // Backtrack
        if (backtrack) {
            System.out.println("\nHasil backtrack :");
            BacktrackFutoshiki btf = new BacktrackFutoshiki(tes);
            start = System.nanoTime();
            boolean bt = btf.solve();
            end = System.nanoTime();
            exec = (end-start)/1000000f;
            if (bt) System.out.println(Tools.stringPuzzle(btf.getPuzzle()));
            else System.out.println("Solusi tidak ada");
            System.out.println("Waktu eksekusi : " + String.format("%.3f", exec) + " ms");
        }
        
        // BnB
        if (bnb) {
            BnBFutoshiki bnf = new BnBFutoshiki(tes);
            start = System.nanoTime();
            boolean bn = bnf.solve();
            end = System.nanoTime();
            exec = (end-start)/1000000f;
            System.out.println("\nHasil branch and bound :");
            if (bn) System.out.println(Tools.stringPuzzle(bnf.getPuzzle()));
            else System.out.println("Solusi tidak ada");
            System.out.println("Waktu eksekusi : " + String.format("%.3f", exec) + " ms");
        }
        
    }
    
    public static void printUsage() {
        System.out.println("Argumen : -f FILE (-bf|-bt|-bn)");
    }
    
}

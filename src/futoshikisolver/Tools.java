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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khaidzir
 */
public class Tools {
    
    public static Element[][] readPuzzle(String path) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            line = br.readLine();
            if (line == null) return null;
            int size = Integer.parseInt(line)*2-1;
            Element [][] ret = new Element[size][size];
            int i = 0, el;
            while( (line = br.readLine()) != null ) {
                String[] arrS = line.split("\\s+");
                for(int j=0; j<arrS.length; j++) {
                    switch (arrS[j]) {
                        case "<":
                            el = LESSTHAN_HOR;
                            break;
                        case ">":
                            el = GREATER_HOR;
                            break;
                        case "^":
                            el = LESSTHAN_VER;
                            break;
                        case "V":
                            el = GREATER_VER;
                            break;
                        case ".":
                            el = NO_OPERATOR;
                            break;
                        default:
                            el = Integer.parseInt(arrS[j]);
                            break;
                    }
                    ret[i][j] = new Element(el, el==0);
                }
                i++;
            }
            return ret;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException e) {
            return null;
        }
        
    }
    
    public static String stringPuzzle(Element[][] puzzle) {
        StringBuilder sb = new StringBuilder();
        for(Element[] arr : puzzle) {
            for(Element e : arr) {
                switch(e.el) {
                    case LESSTHAN_HOR :
                        sb.append("<");
                        break;
                    case GREATER_HOR :
                        sb.append(">");
                        break;
                    case LESSTHAN_VER :
                        sb.append("^");
                        break;
                    case GREATER_VER :
                        sb.append("V");
                        break;
                    case NO_OPERATOR :
                        sb.append(".");
                        break;
                    case 0 :
                        sb.append("0");
                        break;
                    default :
                        sb.append(e.el);
                        break;
                }
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
    
}

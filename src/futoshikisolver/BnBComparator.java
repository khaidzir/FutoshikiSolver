/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikisolver;

import java.util.Comparator;

/**
 *
 * @author khaidzir
 */
public class BnBComparator implements Comparator<BnBElement> {

    @Override
    public int compare(BnBElement o1, BnBElement o2) {
        return o1.heuval - o2.heuval;
    }
    
}

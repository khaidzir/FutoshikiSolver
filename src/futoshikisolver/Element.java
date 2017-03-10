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
public class Element {
    public static final int GREATER_VER = -1, LESSTHAN_VER = -2, 
            GREATER_HOR = -3, LESSTHAN_HOR = -4, NO_OPERATOR = -5;
    public int el;
    public boolean vul;     //vulnerability : false jika tidak bisa diganti
    public Element(int val, boolean v) {
        el = val;
        vul = v;
    }
}

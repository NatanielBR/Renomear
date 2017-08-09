/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statics;

/**
 *
 * @author nataniel
 */
public class Numeral {

    public static String processarN(String cont, int i) {
        if (cont.startsWith("0")) {
            StringBuilder bstr = new StringBuilder();
            char[] arr = cont.toCharArray();
            int num = Integer.parseInt(Character.toString(arr[arr.length - 1]));
            num += i;
            if (num < 10) {
                arr[arr.length - 1] = String.valueOf(num).charAt(0);
                bstr.append(arr);
            } else {
                for (int j = 0; j < arr.length - 2; j++) {
                    char c = arr[j];
                    bstr.append(c);
                }
                bstr.append(num);
            }
            cont = bstr.toString();
        } else {
            cont = Integer.toString(Integer.parseInt(cont) + i);
        }
        return cont;
    }
    public static boolean validar(String cont){
        return cont.matches("/s[0-9]*");
    }
}

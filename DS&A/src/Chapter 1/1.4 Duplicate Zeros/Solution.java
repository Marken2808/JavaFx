import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {


//    Runtime: 4 ms
//    Memory Usage: 53.3 MB
    public static void duplicateZeros(int[] arr) {

        System.out.println(Arrays.toString(arr));

        String str = "";

        for(int i=0; i<arr.length; i++) {
            str += String.valueOf(arr[i]);
        }

        System.out.println(str);
        System.out.println(str.length());

        String newstr = str.replace("0","00");
        System.out.println(newstr.length());

        String[] strarr = newstr.split("");
        System.out.println(Arrays.asList(strarr));

        String[] temp = Arrays.copyOfRange(strarr, 0, strarr.length - (newstr.length() - str.length()));
        for (int i = 0; i < temp.length ; i++) {
            arr[i] = Integer.valueOf(temp[i]);
        }
        System.out.println(Arrays.toString(arr));

    }

    public static void main (String[] args) {
        duplicateZeros(new int[] {1,0,2,3,0,4,5,0});
    }

}




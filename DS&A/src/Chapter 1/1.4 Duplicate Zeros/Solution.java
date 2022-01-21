import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {


//    Runtime: 24 ms
//    Memory Usage: 40.6 MB
//    public static void duplicateZeros(int[] arr) {
//        String str = "";
//
//        for(int i=0; i<arr.length; i++) {
//            str += String.valueOf(arr[i]);
//        }
//
//        System.out.println(str);
//        System.out.println(str.length());
//
//        String newstr = str.replace("0","00");
//        System.out.println(newstr.length());
//
//        String[] strarr = newstr.split("");
//        System.out.println(Arrays.asList(strarr));
//
//        String[] temp = Arrays.copyOfRange(strarr, 0, strarr.length - (newstr.length() - str.length()));
//        for (int i = 0; i < temp.length ; i++) {
//            arr[i] = Integer.valueOf(temp[i]);
//        }
//        System.out.println(Arrays.toString(arr));
//    }

//    Runtime: 0 ms
//    Memory Usage: 39.4 MB
    public static void duplicateZeros(int[] arr) {
        int[] shifted = new int[arr.length];
        for (int i = 0, j = 0; i < arr.length && j < arr.length; i++) {
            if (arr[i] != 0) {
                shifted[j++] = arr[i];
//                shifted[j] = arr[i];
//                j++;
            } else {
                j += 2;
            }
            System.out.println("i="+i+" & j="+j);
            System.out.println(Arrays.toString(shifted));
        }
        System.arraycopy(shifted, 0, arr, 0, arr.length);
    }

    public static void main (String[] args) {
        duplicateZeros(new int[] {1,0,2,3,0,4,5,0});
    }

}




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {


//    Runtime: 4 ms
//    Memory Usage: 53.3 MB
    public static void duplicateZeros(int[] arr) {

        int insert = 0;
        int count = 0;
        String str = "";

        for(int i=0; i<arr.length-count; i++) {

            if(arr[i]==0){
                count++;
            }

             String.valueOf(arr[i]).replaceAll("0","00");

            System.out.print(str);

//            for(int j=0; i<str.length()-1; j++){
//                arr[j] = Integer.valueOf(str.charAt(i));
//            }

//            System.out.print(Arrays.toString(arr));
        }
    }

    public static void main (String[] args) {
        duplicateZeros(new int[] {1,0,2,3,0,4,5,0});
    }

}




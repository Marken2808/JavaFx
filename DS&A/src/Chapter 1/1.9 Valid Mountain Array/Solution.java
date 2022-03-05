import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class Solution {

////    Runtime: 3 ms
////    Memory Usage: 54.7 MB
//    public static int findPerkIndex(int[] arr){
//        int maxAtIndex = 0;
//        int maxAtValue = 0;
//        for(int i=0; i< arr.length; i++){
//            if(arr[i] > maxAtValue) {
//                maxAtValue = arr[i];
//                maxAtIndex = i;
//            }
//        }
//        return maxAtIndex;
//    }
//    public static boolean validMountainArray(int[] arr) {
//        int perk = findPerkIndex(arr);
//        if(arr.length>2 && perk!=(arr.length-1) && perk!=0){
//            for(int i=0; i<perk-1; i++){
//                if(arr[i]>=arr[i+1] ) return false;
//            }
//            for (int i=perk; i<arr.length-1; i++){
//                if(arr[i]<=arr[i+1]) return false;
//            }
//            return true;
//        }
//        return false;
//    }

//    Runtime: 2 ms
//    Memory Usage: 54.2 MB
    public static boolean validMountainArray(int[] arr) {
        if(arr.length < 3) return false;
        int l = 0;
        int r = arr.length - 1;
        while(l + 1 < arr.length - 1 && arr[l] < arr[l + 1]) l++;
        while(r - 1 > 0 && arr[r] < arr[r - 1]) r--;
        return l == r;
    }

    public static void main(String[] args) {
        validMountainArray(new int[]{3,5,5});
    }
}
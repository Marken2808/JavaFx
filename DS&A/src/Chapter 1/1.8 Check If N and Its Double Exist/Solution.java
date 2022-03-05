import java.util.Arrays;

class Solution {

//    Runtime: 2 ms
//    Memory Usage: 44.3 MB
    public static boolean checkIfExist(int[] arr) {
        for (int i=0; i< arr.length; i++){
            for (int j=i+1; j< arr.length; j++){
                if(arr[i]==2*arr[j] || (arr[i] == arr[j]/2 && arr[j]%2==0))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkIfExist(new int[]{7,1,14,11}));
    }
}
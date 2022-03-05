import java.util.Arrays;
import java.util.HashSet;

class Solution {

////    Runtime: 2 ms
////    Memory Usage: 44.3 MB
//    public static boolean checkIfExist(int[] arr) {
//        for (int i=0; i< arr.length; i++){
//            for (int j=i+1; j< arr.length; j++){
//                if(arr[i]==2*arr[j] || (arr[i] == arr[j]/2 && arr[j]%2==0))
//                    return true;
//            }
//        }
//        return false;
//    }


////    Runtime: 2 ms
////    Memory Usage: 44.3 MB
//    public static boolean checkIfExist(int[] arr) {
//        HashSet<Integer> set = new HashSet<>();
//        for(int a : arr) {
//            if(set.contains(a*2) || (a%2 == 0 && set.contains(a/2))) return true;
//            set.add(a);
//        }
//        return false;
//    }

//    ------------------Binary Search ----------------
//    Runtime: 2 ms
//    Memory Usage: 41.7 MB
    public static boolean checkIfExist(int[] arr) {
        Arrays.sort(arr);
        for(int i=0;i<arr.length;i++){
            if(binarySearch(arr,arr[i],i)){
                return true;
            }
        }
        return false;
    }
    public static boolean binarySearch(int[] arr,int target,int index){
        int s=0;
        int e=arr.length-1;
        while(s<=e){
            int mid=s+(e-s)/2;
            if(arr[mid]*2==target && index!=mid){
                return true;
            }
            if(arr[mid]*2>target){
                e=mid-1;
            }else{
                s=mid+1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkIfExist(new int[]{7,1,14,11}));
    }
}
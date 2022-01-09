import java.util.Arrays;

class Solution {


//    Runtime: 4 ms
//    Memory Usage: 53.3 MB
    public static int[] sortedSquares(int[] nums) {

        int[] squares = new int[nums.length];
        for(int i=0; i<nums.length; i++) {
            squares[i] = (int)(Math.pow(nums[i], 2));
        }
        Arrays.sort(squares);
        return squares;

    }

    public static void main (String[] args) {
        sortedSquares(new int[] {-7,-3,2,3,11});
    }

}




class Solution {

////    Runtime: 3 ms
////    Memory Usage: 53.7 MB
//    public static int findMaxConsecutiveOnes(int[] nums) {
//
//        int count = 0;
//        int max = 0;
//
//        for(int i=0; i<nums.length; i++) {
//            if(nums[i]==1) {
//                count++;
//            } else {
//                count = 0;
//            }
//
//            if (count>max) {
//                max = count;
//            }
//        }
//        System.out.println(max);
//        return max;
//    }

//    Runtime: 2 ms
//    Memory Usage: 40.7 MB
    public static int findMaxConsecutiveOnes(int[] nums) {

        int maxHere = 0, max = 0;
        for (int n : nums)
            max = Math.max(max, maxHere = n == 0 ? 0 : maxHere + 1);

        System.out.println(max);
        return max;
    }

    public static void main (String[] args) {
        findMaxConsecutiveOnes(new int[]{1,0,1,1,0,1});
    }
}




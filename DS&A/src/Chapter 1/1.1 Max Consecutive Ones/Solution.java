class Solution {

//    Runtime: 3 ms
//    Memory Usage: 53.7 MB
    public static int findMaxConsecutiveOnes(int[] nums) {

        int count = 0;
        int max = 0;

        for(int i=0; i<nums.length; i++) {
            if(nums[i]==1) {
                count++;
            } else {
                count = 0;
            }

            if (count>max) {
                max = count;
            }
        }
        return max;
    }

    public static void main (String[] args) {
        findMaxConsecutiveOnes(new int[]{});
    }

}




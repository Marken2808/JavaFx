class Solution {

//    Runtime: 3 ms
//    Memory Usage: 41.4 MB
    public static int findNumbers(int[] nums) {

        int count = 0;
        for (int num : nums) {
            if(String.valueOf(num).length() % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main (String[] args) {
        findNumbers(new int[] {555,901,482,1771});
    }

}




import java.util.Arrays;

class Solution {

    public static int removeElement(int[] nums, int val) {
        int a = 0;
        for(int i=0; i< nums.length; i++) {
            if(nums[i] != val){
                nums[a++] = nums[i];
            }
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{3,2,2,3,8,1,4,5,6}, 3));
    }
}
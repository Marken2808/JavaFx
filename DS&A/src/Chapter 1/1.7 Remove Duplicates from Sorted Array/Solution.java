import java.util.Arrays;

class Solution {

    public static int removeDuplicates(int[] nums) {
        int a = 0;
        for (int num : nums)
            if (a == 0 || num != nums[a-1])
                nums[a++] = num;

        System.out.println(Arrays.toString(nums));
        return a;
    }

    public static void main(String[] args) {
        removeDuplicates(new int[]{1,1,2});
    }
}
import java.util.Arrays;

class Solution {

    public static int[] clone(int[] nums, int length) {
        int[] clone = new int[length];
        for (int i=0; i < nums.length; i++) {
            if (nums[i] != 0){
                clone[i] = nums[i];
            }
        }
        return clone;
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {


        int[] nums1clone = clone(nums1,m);
        int[] nums2clone = clone(nums2,n);

        nums1 = new int[m+n];

//        System.out.println(Arrays.toString(clone(nums2,n)));
        System.arraycopy(nums2clone,0,nums1,m,n);
//        System.out.println(Arrays.toString(clone(nums1,m)));
        System.arraycopy(nums1clone,0,nums1,0,m);

        Arrays.sort(nums1);
//        nums1 = new int[m+n];
//        System.arraycopy(merge,0,nums1,0,merge.length);
        System.out.println(Arrays.toString(nums1));
    }

    public static void main(String[] args) {
        merge( new int[]{1,2,3,0,0,0}, 3, new int[]{2,5,6}, 3);
    }
}
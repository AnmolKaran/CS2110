package cs2110;

import java.util.Arrays;

public class LoopInvariants {

    /**
     * Returns the second-largest element of the given `nums` array. Requires that `nums.length >=
     * 2` and `nums` contains distinct elements.
     */
    static int secondLargest(int[] nums) {
        assert nums.length >= 2; // defensive programming

        // TODO 1: Use the loop invariant that you developed on the Discussion 3 handout to
        //  implement this method in a single array pass.

        int i =2;
        int max;
        int secondMax;
        if (nums[0]> nums[1]) {
            max = nums[0];
            secondMax = nums[1];
        }else{
            max = nums[1];
            secondMax = nums[0];
        }

        while (i < nums.length){
            if (nums[i] > max){
                secondMax = max;
                max = nums[i];
            }
            else if(nums[i] > secondMax){
                    secondMax = nums[i];

            }
            i++;
        }
        return secondMax;
        //throw new UnsupportedOperationException();
    }

    /**
     * Returns whether the entries of `nums` form a *mountain*, meaning there is some index `i` such
     * that `nums[..i]` is non-decreasing and `nums[i..]` is non-increasing. Requires that
     * nums.length > 0`.
     */
    static boolean isMountain(int[] nums) {
        assert nums.length > 0; // defensive programming

        // TODO 2: Use the loop invariant that you developed on the Discussion 3 handout to
        //  implement this method in a single array pass.
        /*
         * Loop Invariant: nums[0..i] has is increasing if isIncreasing == true
         * and decreasing if isIncreasing == false. It has not yet reached a peak
         */
        boolean isIncreasing = true; //nums[1] >=nums[0];
        if (nums.length==1){
            return true;
        }
        for (int i = 1;i<nums.length; i ++){
            if (nums[i]<=nums[i-1]){
                if (isIncreasing == true){
                    isIncreasing = false;
                    return true;
                }
            }
        }
        return false;
       // throw new UnsupportedOperationException();
    }

    /**
     * Swaps the entries `nums[x]` and `nums[y]`. Requires that `0 <= x < nums.length` and `0 <= y <
     * nums.length`.
     */
    static void swap(int[] nums, int x, int y) {
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }

    /**
     * Rearranges the elements of `nums` about the given `pivot` so that `nums[..i) < pivot`,
     * `nums[i..j) == pivot` and `nums[j..] > pivot` for some indices `i,j`. Requires that
     * `nums.length > 0`.
     */
    static void partition3Way(int[] nums, int pivot) {
        assert nums.length > 0; // defensive programming

        // TODO 3: Use the loop invariant that you developed on the Discussion 3 handout to
        //  implement this method in a single array pass.
        throw new UnsupportedOperationException();
    }
}

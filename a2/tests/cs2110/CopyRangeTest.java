package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CopyRangeTest {

    @DisplayName("WHEN srcStart falls within the range of src's indices and the length is a number that fits within the range from srcStart to src.length, THEN " +
            "The subarray is transferred to dst successfully.")
    @Test
    void testBasicTransfer( ){
        int[] src = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] dst = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        boolean result = ArrayUtilities.copyRange(src,2, dst,4,3);
        assertTrue(result);
        int[] expectedDst = {9, 8, 7, 6, 3, 4, 5, 2, 1};
        assertArrayEquals(expectedDst,dst);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, src);

    }

    @DisplayName("WHEN src is the same size as dst and length is the same as src.length, THEN the entire src array is transferred")
    @Test
    void testEntireArrayTransfer(){
        int[] src = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] dst = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expectedDst = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        boolean result = ArrayUtilities.copyRange(src,0, dst,0,9);
        assertTrue(result);
        assertArrayEquals(expectedDst,dst);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, src);

    }

    @DisplayName("WHEN length is passed as 0, THEN the dst array remains the same and returns true")
    @Test
    void testZeroLength(){
        int[] src = {1, 2, 3, 4, 5};
        int[] dst = {9, 8, 7, 6, 5};
        boolean result = ArrayUtilities.copyRange(src,2,dst,4,0);
        assertTrue(result);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, src);
        assertArrayEquals(new int[]{9,8,7,6,5}, dst);
    }


    @DisplayName("WHEN the src array and the dst array are the same array object with different srcStart and dstStart, THEN the code should return the array with the subarray beginning from dstStart, without any overlap ")
    @Test
    void testSameArray(){
        int[] myArr = {1, 5, 7,2,4, 7,2,4};
        int[] expectedArr = {1, 5,7, 2, 7, 2, 4, 4};
        boolean result = ArrayUtilities.copyRange(myArr,2,myArr,4,3);
        assertTrue(result);
        assertArrayEquals(expectedArr,myArr);
    }


    @DisplayName("WHEN length is negative, THEN the function should return false")
    @Test
    void testNegativeLength(){
        int[] src = {1, 5, 7, 2, 4, 7, 2,4};
        int[] dst = {5, 5, 3, 3, 4, 6, 1, 14};
        boolean result = ArrayUtilities.copyRange(src,2,dst,4,-3);
        assertFalse(result);

        assertArrayEquals(new int[]{5, 5, 3, 3, 4, 6, 1, 14},dst);
        assertArrayEquals(new int[]{1, 5, 7, 2, 4, 7, 2,4}, src);

    }

    @DisplayName("WHEN dstStart + length is greater than the dst's length, THEN the method should return false")
    @Test
    void testLengthTooLongDst(){
        int[] src = {1,5,7,2,4,7,2,4};
        int[] dst = {5,5,3,3,4,6,1,14};
        boolean result = ArrayUtilities.copyRange(src,2,dst,6,6);
        assertFalse(result);
        assertArrayEquals(new int[]{1,5,7,2,4,7,2,4}, src);
        assertArrayEquals(new int[]{5,5,3,3,4,6,1,14}, dst);
    }


    @DisplayName("WHEN srcStart + length is greater than the src's length, THEN the method should return false")
    @Test
    void testLengthTooLongSrc(){
        int[] src = {1,5,7,2,4,7,2,4};
        int[] dst = {5,5,3,3,4,6,1,14};
        boolean result = ArrayUtilities.copyRange(src,6,dst,3,6);
        assertFalse(result);
        assertArrayEquals(new int[]{1,5,7,2,4,7,2,4},src);
        assertArrayEquals(new int[]{5,5,3,3,4,6,1,14},dst);
    }



    @DisplayName("WHEN both src and dst are empty but length is passed as 0, THEN the function should return true")
    @Test
    void testBothArraysEmpty(){
        int[] src = {};
        int[] dst = {};
        boolean result = ArrayUtilities.copyRange(new int[]{},0,new int[]{},0,0);
        assertTrue(result);
        assertArrayEquals(new int[]{}, dst);
        assertArrayEquals(new int[]{}, src);
    }

    @DisplayName("WHEN src is empty and length > 0, THEN return false")
    @Test
    void testSrcEmptyNonZeroLength(){
        int[] src = {};
        int[] dst = {1,2,3};
        boolean result = ArrayUtilities.copyRange(src,0, dst, 0, 1);
        assertFalse(result);
        assertArrayEquals(new int[]{1,2,3},dst);
        assertArrayEquals(new int[]{},src);
    }

    @DisplayName("WHEN srcSTart is negative, THEN return false")
    @Test
    void TestNegativeSrcStart() {
        int[] src = {1, 5, 7, 2, 4, 7, 2, 4};
        int[] dst = {5, 5, 3, 3, 4, 6, 1, 14};
        boolean result = ArrayUtilities.copyRange(src, -1, dst, 3, 3);
        assertFalse(result);
        assertArrayEquals(new int[]{1, 5, 7, 2, 4, 7, 2, 4}, src);
        assertArrayEquals(new int[]{5, 5, 3, 3, 4, 6, 1, 14}, dst);

    }

    @DisplayName("WHEN dstStart is negative, THEN return false")
    @Test
    void TestNegativeDstStart() {
        int[] src = {1, 5, 7, 2, 4, 7, 2, 4};
        int[] dst = {5, 5, 3, 3, 4, 6, 1, 14};
        boolean result = ArrayUtilities.copyRange(src, 2, dst, -1, 3);
        assertFalse(result);
        assertArrayEquals(new int[]{1, 5, 7, 2, 4, 7, 2, 4},src);
        assertArrayEquals(new int[]{5, 5, 3, 3, 4, 6, 1, 14},dst);
    }

    @DisplayName("WHEN dst is empty and length > 0, THEN return false")
    @Test
    void testDstEmptyNonZeroLength() {
        int[] src = {1, 2, 3};
        int[] dst = {};
        boolean result = ArrayUtilities.copyRange(src, 0, dst, 0, 1);
        assertFalse(result);
        assertArrayEquals(new int[]{1, 2, 3},src);
        assertArrayEquals(new int[]{},dst);
    }

    @DisplayName("WHEN dst is empty and length is 0, THEN return true")
    @Test
    void testDstEmptyZeroLength() {
        int[] src = {1, 2, 3};
        int[] dst = {};
        boolean result = ArrayUtilities.copyRange(src, 0, dst, 0, 0);
        assertTrue(result);
        assertArrayEquals(new int[]{}, dst);
        assertArrayEquals(new int[]{1,2,3}, src);
    }

    @DisplayName("WHEN src is empty and length is 0, THEN return true")
    @Test
    void testSrcEmptyZeroLength() {
        int[] dst = {1, 2, 3};
        int[] src = {};
        boolean result = ArrayUtilities.copyRange(src, 0, dst, 0, 0);
        assertTrue(result);
        assertArrayEquals(new int[]{}, src);
        assertArrayEquals(new int[]{1,2,3}, dst);
    }
    
}

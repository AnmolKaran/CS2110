package cs2110;

/**
 * Contains utility methods for copying an array range to another array range (potentially within
 * the same array).
 */
public class ArrayUtilities {



    /**
     * Returns 'true' if the subarray of length 'length' from array 'src' starting at index 'srcStart' is
     * successfully transferred to array 'dst', starting at index 'dstStart', otherwise
     * returns 'false', including cases when 'length' < 0, or when values 'length', 'srcStart',
     * and/or 'dstStart' would result in an ArrayIndexOutOfBoundsException. This could occur when
     * 'length' is larger than the subarray from 'srcStart' to the end of 'src' or when
     * 'length' is larger than the subarray from 'dstStart' to the end of 'dst'. ArrayIndexOutOfBoundsException
     * could also occur when 'srcStart' or 'dstStart' are negative or greater than the length of their
     * corresponding arrays.
     * Requires that 'src!= null' and 'dst != null'.
     *
    */
    static boolean copyRange(int[] src, int srcStart, int[] dst, int dstStart, int length) {


        //Initial checks

        if (length == 0){//should always return true, even if other args aren't valid
            return true;
        }
        if (length <0 || srcStart < 0  || dstStart < 0){
            return false;
        }

        if ((srcStart + length > src.length) || (dstStart + length > dst.length)){ //checking ifthe bounds
            //the requested copy range exceeds the length of src or dst
            return false;
        }
        if ((srcStart >= src.length) || dstStart >= dst.length){ //checking if, since length is not 0,
            //srcStart or dstStart is larger than the array's range of indices

            return false;
        }

        if (src == dst && dstStart > srcStart) {//Special case for if src and dst are same array and there is rightward overlapping shift
            //in this case, work backwards and add from end to front
            /*
             * Loop Invariant: The subarray src[srcStart + i+ 1..srcStart +length -1]
             * has been copied to dst[dstStart+i+1..dstStart+length-1].
             * This means the last 'length - 1 - i' elements have been copied.
             */
            for (int i = length - 1; i >= 0; i--) {
                dst[dstStart + i] = src[srcStart + i];
            }
        }else{//regular case, standard forward copy
            /*
             * Loop Invariant: The subarray src[srcStart..srcStart+ i- 1] has
             * been copied to dst[dstStart..dstStart+ i- 1].
             * This means the first 'i' elements have been copied.
             */
            for (int i = 0; i < length; i++) {
                dst[dstStart + i] = src[srcStart + i];
            }
        }


        return true;
        //throw new UnsupportedOperationException();
    }


    /**Returns `true` if the rectangular sub-array of size `height`x`width` from array `src`,
     * starting at index `(srcI, srcJ)`, is successfully transferred to array `dst`, starting at
     * index `(dstI, dstJ)`. Otherwise, returns `false`. This method returns `false` for the following:any
     * parameter values that would result in an `ArrayIndexOutOfBoundsException` or a
     * `NullPointerException`.This could occur when 'height' or 'width' is negative, any of
     * `srcI`, `srcJ`, `dstI`, or `dstJ` are negative, the source row range (`srcI` to `srcI
     * + height - 1`) is longer than the bounds of 'src', or the destination row range ('dstI' to
     * 'dstI + height - 1') is longer than the bounds of 'dst'. The function also returns false when,
     * for any row within the source region, the column range (`srcJ` to `srcJ + width - 1`) is longer
     * than the bounds of that row, or when, for any row within the destination region, the column range
     * (`srcJ` to `srcJ + width - 1`) is longer than the bounds of that row.
     * Requires that any row that would be accessed in 'src' or 'dst' is not null.
     * Requires that no inner row array is null.
     * Requires that no inner array is aliased
    */
    static boolean copy2DRange(int[][] src, int srcI, int srcJ, int[][] dst, int dstI, int dstJ,
            int height, int width) {


        //Initial checks
        if (height == 0 || width == 0){ //should always return true, even if other args aren't valid
            return true;
        }

        if (height <0 || width <0){
            return false;
        }
        if (srcI <0 || dstI <0 || srcJ <0 || dstJ <0){
            return false;
        }

        if (srcI + height > src.length || dstI + height > dst.length){
            return false;
        }


        /*
          * Loop Invariant: All rows in 'src[srcI..srcI+ k -1] are not null and have a length greater
          * than or equal to 'srcJ + width'.
          * Similarly all rows in 'dst[dstI..dstI + k-1]' are not null and have a length greater than
          * or equal to 'dstJ + width'.
         */
        for (int k = 0; k <height; k++) {

            if (srcJ + width > src[srcI + k].length) {
                return false;
            }
            if (dstJ + width > dst[dstI + k].length){
                return false;
            }
        }




        /*
          *Outer Loop Invariant: The first 'i' rows of 'dst' subarray ('dst[dstI..dstI+i-1]') have
          * been correctly copied from corresponding rows of 'src'
         */
        for (int i = 0; i < height; i ++){
            int currSrcVertInd = srcI + i; //index of the source row that loop is currently on
            int currDstVertInd = dstI + i; //index of the dst row that loop is currently on
            int[] srcRow = src[currSrcVertInd]; // array of row that loop is currently on from src array
            int[] dstRow = dst[currDstVertInd]; // array of row that loop is currently on from dst array

            if (srcRow == dstRow && dstJ > srcJ) { //if src==dst and a subarray dst is rightwards overlapping with that of src
                /*
                 * Inner Loop Invariant: The suffix of the current 'dst'
                 * row subarray ('dstRow[dstJ+j+1..dstJ+width-1]') has been
                 * updated with the values from the corresponding 'src' row subarray.
                 */
                for (int j = width - 1; j >= 0; j--) {
                    dstRow[dstJ + j] = srcRow[srcJ + j];
                }
            }else{
                /*
                 * Inner Loop Invariant: The first 'j' elements of the
                 * current 'dst' row subarray ('dstRow[dstJ..dstJ+j-1]') have
                 * been updated with values from the corresponding 'src' row subarray.
                 */
                for (int j = 0; j < width; j++) {
                    dstRow[dstJ + j] = srcRow[srcJ + j];
                }
            }

        }
        return true;
        //throw new UnsupportedOperationException();
    }
}

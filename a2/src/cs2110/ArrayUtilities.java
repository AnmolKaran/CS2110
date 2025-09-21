package cs2110;

/**
 * Contains utility methods for copying an array range to another array range (potentially within
 * the same array).
 */
public class ArrayUtilities {



    /**
     * Returns 'true' if the subarray of length 'length' from array 'src' starting at index 'srcStart' is
     * successfully transferred to array 'dst', starting at index 'dstStart'. Otherwise, it
     * returns 'false'.
     * The method returns 'false' if any of the following conditions would cause an
     * ArrayIndexOutOfBoundsException:
     * 1. 'length', 'srcStart', or 'dstStart' are negative.
     * 2. The copy range ('srcStart' to 'srcStart + length') exceeds the bounds of 'src'.
     * 3. The destination range ('dstStart' to 'dstStart + length') exceeds the bounds of 'dst'.
     * Requires that 'src' and 'dst' are not null.
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


    /**
     * Returns `true` if the rectangular sub-array of size `height`x`width` from array `src`,
     * starting at index `(srcI, srcJ)`, is successfully transferred to array `dst`, starting at
     * index `(dstI, dstJ)`. Otherwise, returns `false`.
     * This method returns `false` if any parameter values would result in an
     * ArrayIndexOutOfBoundsException, such as:
     * 1. When 'height' or 'width' are negative.
     * 2. When any of the start indices ('srcI', 'srcJ', 'dstI', 'dstJ') are negative.
     * 3. When the source row range (`srcI` to `srcI + height`) exceeds the bounds of `src`.
     * 4. When the destination row range (`dstI` to `dstI + height`) exceeds the bounds of `dst`.
     * 5. When, for any row in the source region, the column range (`srcJ` to `srcJ + width`)
     * exceeds the bounds of that row.
     * 6. When, for any row in the destination region, the column range (`dstJ` to `dstJ + width`)
     * exceeds the bounds of that row.
     * Requires that 'src', 'dst', and all relevant inner rows are not null.
     * Requires that no inner array is aliased, meaning no two rows point to the same array.
    */
    static boolean copy2DRange(int[][] src, int srcI, int srcJ, int[][] dst, int dstI, int dstJ,
            int height, int width) {


        //Initial checks


        if (height <0 || width <0){
            return false;
        }
        if (srcI <0 || dstI <0 || srcJ <0 || dstJ <0){
            return false;
        }

        if (srcI + height > src.length || dstI + height > dst.length){
            return false;
        }
        if (height == 0 || width == 0){ //should always return true, even if other args aren't valid
            return true;
        }

        /*
          * Loop Invariant: All rows in 'src[srcI..srcI+ k -1] have a length greater
          * than or equal to 'srcJ + width'.
          * Similarly all rows in 'dst[dstI..dstI + k-1]' have a length greater than
          * or equal to 'dstJ + width'.
         */
        for (int k = 0; k <height; k++) {

            if (srcJ+width > src[srcI + k].length) {
                return false;
            }
            if (dstJ +width > dst[dstI + k].length){
                return false;
            }
        }


        if (src == dst && dstI > srcI){//if 'src' and 'dst' are the same 2d arrays and there is a vertical overlap
            //in that dstI is larger than srcI, then rows must be copied from bottom to top to avoid overwriting 'src' data.
            /*
             * Outer Loop Invariant: The rows in range dst[dstI + i + 1..dstI +height- 1]
             * have been correctly copied from the corresponding rows in the range
             * src[srcI+ i+ 1..srcI+ height- 1].
             */
            for (int i = height- 1; i >= 0; i--) {
                int currSrcVertInd = srcI + i; //index of the source row that loop is currently on
                int currDstVertInd = dstI + i; //index of the dst row that loop is currently on
                int[] srcRow = src[currSrcVertInd]; // array of row that loop is currently on from src array
                int[] dstRow = dst[currDstVertInd]; // array of row that loop is currently on from dst array

                if (srcRow == dstRow && dstJ > srcJ) {
                    /*
                     * Inner Loop Invariant: The the range of 'dst' 'dstRow[dstJ+j+1..dstJ+width-1]'
                     * has been updated with the values from the corresponding 'src' row subarray.
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
        }else{//regular case: non-vertical overlap


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
        }

        return true;
        //throw new UnsupportedOperationException();
    }
}

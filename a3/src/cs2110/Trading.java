package cs2110;

import java.util.Random;

/**
 * Contains methods for computing the optimal achievable profit of a stock transaction based on its
 * price history in a given time window.
 */
public class Trading {

    /**
     * Represents a stock transaction in which a share is purchased at the `purchaseTime` and sold
     * at the `sellTime`. Requires that `purchaseTime < sellTime`.
     */
    record BuySellTransaction(int purchaseTime, int sellTime) {
    }

    /**
     * Returns the profit earned through the given `BuySellTransaction t` for the given `prices`
     * array.
     */
    static int profit(int[] prices, BuySellTransaction t) {
        return prices[t.sellTime()] - prices[t.purchaseTime];
    }

    /**
     * Returns the *index* of the maximum value in `prices(i..]`. Requires that `0 <= i <
     * prices.length-1`.
     */
    static int argmaxTail(int[] prices, int i) {

        if (i + 1 >= prices.length - 1) {//if tail only contains one element
            return i + 1;
        }
        int max = prices[i+1];
        int loc = i+1;
        int j = i +2;
        /*
         *Loop invariant: 'max' >= every value in 'prices[i+1..j)', prices[loc] = max
         */
        while (j<prices.length){ //line 7
            if (prices[j] >max){
                max = prices[j];
                loc = j;
            }
            j++;
        }
        return loc;

        //throw new UnsupportedOperationException();
    }

    /**
     * Returns a BuySellTransaction with the maximum achievable profit for the given `prices`
     * window.
     */
    static BuySellTransaction optimalTransaction1(int[] prices) {


        if (prices.length < 2) { //if prices only has one element
            return new BuySellTransaction(0, 0);
        }
        BuySellTransaction opt; //line 2
        int i = 0;
        int initMaxPriceInd = argmaxTail(prices,i);
        opt = new BuySellTransaction(i,initMaxPriceInd);
        i ++;
        /*
         * Loop invariant: opt references a `Transaction` among all those with `purchaseTime` in
         * `prices[0..i)` with the maximum achievable profit.
         *
         */
         while (i< prices.length-1) { //line 7
             int maxPriceInd = argmaxTail(prices,i);
             BuySellTransaction currTransaction = new BuySellTransaction(i, maxPriceInd);
             if (profit(prices, currTransaction) > profit(prices, opt)){
                opt = currTransaction;
             }
             i++;

         }
         return opt;
         //throw new UnsupportedOperationException();
    }

    /**
     * Returns a BuySellTransaction with the maximum achievable profit for the given `prices`
     * window.
     */
    static BuySellTransaction optimalTransaction2(int[] prices) {


        BuySellTransaction opt;

        if (prices.length < 2) { //if prices only has one element
            return new BuySellTransaction(0, 0);
        }
        int minPriceInd = 0;
        int i = 1;
        opt = new BuySellTransaction(minPriceInd,i);
        //i++;
        /*
           *Loop Invariant: 'opt' references a 'Transaction' among all those with 'sellTime'
           * in 'prices[1..prices.length-1] with the maximum achievable profit.
           * 'minPriceIndex' is the index of the minimum price in 'prices[0..i-1]'
         */
        while (i < prices.length){

            BuySellTransaction currTransaction = new BuySellTransaction(minPriceInd, i);
            if (profit(prices,currTransaction) > profit(prices,opt)){
                opt = currTransaction;
            }
            if (prices[i] < prices[minPriceInd]){
                minPriceInd = i;
            }
            i++;
        }
        return opt;
        //throw new UnsupportedOperationException();
    }

    public static void main(String[] args){
        int max = 125;
        int min = 75;

        /*
         * Loop Invariant: The execution times for `optimalTransaction1` and `optimalTransaction2`
         * have been calculated and printed for all array sizes in {100000, 200000 ..., i - 100000}.
         */
        for (int i = 100000; i <= 1000000; i+=100000){
            int[] prices = new int[i];
            Random random = new Random();
            for (int j = 0; j <i; j ++){
                prices[j] = random.nextInt(50)+75;
            }

            long startTime1 = System.nanoTime();
            optimalTransaction1(prices);
            long endTime1 = System.nanoTime();
            long elapsedTimeMs1 = (endTime1 - startTime1)/ 1000000;
            double startTime2 = System.nanoTime();
            optimalTransaction2(prices);
            long endTime2 = System.nanoTime();
            double elapsedTimeMs2 = (endTime2 - startTime2)/ 1e6;
            System.out.println("Prices of size " +i +": ");
            System.out.println("OptimalTransaction1 time: " + elapsedTimeMs1 + " ms");
            System.out.println("OptimalTransaction2 time: " + elapsedTimeMs2+ " ms");
            System.out.println();


        }
    }

}

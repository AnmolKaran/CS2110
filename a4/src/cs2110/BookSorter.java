package cs2110;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookSorter {
    /**
     * Parse the file of book data
     * @param filename - input filename relative to current working directory
     * @precondition - input file exists and has:
     *    - a header row "Title, Author" and 1 or more rows of data
     *    - data rows include a title followed by a comma followed by an author
     *    - title and author do not contain commas
     *    - no lines besides header and data
     * @return an array with one string array for each book
     *    - a book is represented as an array whose first element is title
     *      and whose second element is author
     */
    public static Book[] parseBookList(String filename) {
        File inputFile = new File(filename).getAbsoluteFile();
        System.out.println(inputFile.getAbsolutePath());
        try {
            Scanner lineCounter = new Scanner(inputFile);
            int numLines;
            for (numLines = 0; lineCounter.hasNextLine(); numLines++) {
                lineCounter.nextLine();
            }
            lineCounter.close();

            Scanner dataScanner = new Scanner(inputFile);
            dataScanner.nextLine(); // ignore header row
            Book[] ret = new Book[numLines-1];
            for (int i = 0; dataScanner.hasNextLine(); i++) {
                String line = dataScanner.nextLine();
                String[] parts = line.split(",");
                ret[i] = new Book(parts[0], parts[1]);
            }
            dataScanner.close();
            return ret;
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return new Book[] {};
    }

    /**
     * Data class representing a book
     * @param title - the name of the book
     * @param author - string representing the full name(s) of the author(s) of the book
     */
    record Book(String title, String author) {
        /**
         * @return a String representing this Book
         */
        public String toString() {
            // this looks nicer than the default implementation of Record.toString
            return title() + " by " + author();
        }
    }

    /**Provides either the title or author from a 'Book' object to allow for comparison. 'book' represents
     * the 'Book' object to process, and 'ordering' represents an integer which determines how to sort the 'Book':
     * 1 for title, 2 for author.
     * Requires that 'book' != null and 'ordering' is either 1 or 2.
     * Returns the title or author of the 'book' as a String.
     */
    private static String bookToString(Book book, int ordering) {
        assert book !=null;
        assert book.title() != null;
        assert book.author() != null;
        assert ordering == 1 || ordering == 2;
        if (ordering == 1) {
            return book.title();
        } else {
            return book.author();
        }
    }

    /**
     *
     * Recursively finds the index of the alphabetically first (case insensitive) book by title or author,
     * depending on whether 'ordering' is 1 or 2. In each call, it compares the 'Book' at the 'start' index
     * witht the 'Book' at the 'firstSoFar' index. If the current book is "smaller" (as determined by the ordering),
     * its index 'start' is passed as the new 'firstSoFar' to the next recursive call. This continues until the
     * end of the array is reached, which is when the final 'firstSoFar' index is returned.
     *
     * Requires that 'book' is not null, no element within 'books' is null, no book within 'books' contains null
     * strings for title or author, 'ordering' is either 1 (sort by title) or 2 (sort by author),
     * '0 <= start < books.length', and '0 <= firstSoFar < books.length'.
     * Returns the index of the alphabetically lowest element in the array from 'start' to the end.

     */
    private static int selectFirst(Book[] books, int start, int firstSoFar, int ordering) {
        assert books != null;
        if (start >= books.length){
            return firstSoFar;
        }
        if (bookToString(books[start],ordering).compareToIgnoreCase(bookToString(books[firstSoFar],ordering))<0){//line 4
            return selectFirst(books,start +1,start,ordering);
        }
        else {
            return selectFirst(books, start + 1, firstSoFar, ordering);
        }



    }

    /**
     * Swaps in place two elements in array of 'Book' objects. 'a' represents the array in which the elements must
     * be swapped. 'x' is the index in 'a' of the first element to swap and 'y' is the index in 'a' of the second
     * element to swap.
     */
    private static void swap(Book[]a,int x, int y){
        assert a != null;
        Book temp = a[x];
        a[x] = a [y];
        a[y] = temp;
    }

    /**
     *
     * Helper function of selectionSort() that recursively sorts an array of 'Book' objects with selection sort.
     * At each step, it finds the minimum element in 'books[startInd..books.length)', which is the unsorted portion of the array
     * It then swaps the minimum element with the element at 'startInd', which is its correct sorted position.
     * 'books' represents the array of 'Book' objects to be sorted, 'startInd' represents the starting index of
     * the unsorted portion of the 'books' array, and 'ordering' is an integer representing the sorting criteria:
     *   - A value of 1 means to sort by title
     *   - A value of 2 means to sort by author
     * Requires that 'books' is not null, no element within 'books' is null, no book within 'books' contains null
     * strings for title or author, 'ordering' is either 1 (sort by title) or 2 (sort by author), or
     * '0 <= startInd <= books.length'
     * Returns the sorted array of books.
     */
    private static Book[] selectionSortHelper(Book[] books, int startInd, int ordering){
        assert books != null;
        if (startInd >= books.length){
            return books;
        }
        int lowestInd = selectFirst(books,startInd, startInd, ordering);
        swap(books,startInd,lowestInd);
        return selectionSortHelper(books,startInd +1, ordering);
    }
    /**
     * Sorts an array of 'Book' objects using selection sort algorithm, either by title or author, through calling
     * 'selectionSortHelper()'. 'books' represents the array of 'Book' objects to be sorted. The array is modified in place
     * and 'ordering' is an integer representing the sorting criteria:
     *   - A value of 1 means to sort by title
     *   - A value of 2 means to sort by author
     */
    public static void selectionSort(Book[] books, int ordering) {
        // assert that ordering is 1 or 2?
        assert books != null;
        books = selectionSortHelper(books,0,ordering);

    }

    //public static selectionSortHelper(Book[] books, int )

    public static void main(String[] args) {
        // Edit this filename to use a different data set
        String filename = "data/books.csv";

        Book[] books = parseBookList(filename);
        System.out.println("=== Before Sorting ===");
        for(int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
        selectionSort(books, 1);

        System.out.println();
        System.out.println("=== After Sorting ===");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
    }
}
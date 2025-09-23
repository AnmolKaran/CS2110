package cs2110;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs2110.BookSorter.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Smoke test suite for Selection Sort
 *
 * The smoke tests ensure that your code runs and works as expected for a few simple cases.
 * The tests provided here are the ones that will run on gradescope every time you
 * upload your code.
 *
 * As a reminder, smoke tests are not a complete test suite. Passing all smoke tests does not
 * mean that your code has entirely correct behavior. We will run additional hidden autograder
 * tests on your code after the homework submission deadline to further verify its correctness.
 *
 * You should add more tests to this file to gain a greater level of confidence in your code!
 */
public class SelectionSortSmokeTest {

    @DisplayName("WHEN array is reverse sorted, THEN it is fully sorted")
    @Test
    void testReverseSorted() {
        Book[] input = new Book[]{
                new Book("Zebra", "Author"),
                new Book("Monkey", "Author"),
                new Book("Apple", "Author")
        };

        Book[] expected = new Book[]{
                new Book("Apple", "Author"),
                new Book("Monkey", "Author"),
                new Book("Zebra", "Author")
        };

        BookSorter.selectionSort(input, 1);
        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN books have long titles, THEN sorting completes correctly")
    @Test
    void testVeryLongTitles() {
        String longTitle = "A".repeat(1000);
        String longAuthor = "B".repeat(2000);

        Book[] input = new Book[]{
                new Book(longTitle + "x", longAuthor + "z"),
                new Book(longTitle + "a", longAuthor + "y"),
        };

        BookSorter.selectionSort(input, 1);

        Book[] expected = new Book[]{
                new Book(longTitle + "a", longAuthor + "y"),
                new Book(longTitle + "x", longAuthor + "z"),
        };

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN titles differ only by case, THEN sorting by title is case-insensitive")
    @Test
    void testCaseInsensitiveTitleSort() {
        Book[] input = new Book[]{
                new Book("apple", "Author"),
                new Book("Banana", "Author"),
                new Book("Apple", "Author")
        };

        BookSorter.selectionSort(input, 1);

        Book[] expected = new Book[]{
                new Book("apple", "Author"),
                new Book("Apple", "Author"),
                new Book("Banana", "Author")
        };

        // Or, the order of 'apple' and 'Apple' may vary but both must be before 'Banana'
        assertTrue(input[0].title().equalsIgnoreCase("apple"));
        assertTrue(input[1].title().equalsIgnoreCase("apple"));
        assertTrue(input[2].title().equalsIgnoreCase("banana"));
    }

    @DisplayName("WHEN there is one book with empty title and author, THEN sort does nothing")
    @Test
    void testSingleBookEmptyFields() {
        Book[] input = new Book[]{ new Book("", "") };
        Book[] expected = new Book[]{ new Book("", "") };
        BookSorter.selectionSort(input, 1);
        assertArrayEquals(expected, input);
        BookSorter.selectionSort(input, 2);
        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN all books are identical, THEN sorting preserves order")
    @Test
    void testAllIdenticalBooks() {
        Book[] input = new Book[]{
                new Book("Same Title", "Same Author"),
                new Book("Same Title", "Same Author"),
                new Book("Same Title", "Same Author")
        };

        Book[] expected = input.clone();
        BookSorter.selectionSort(input, 1);
        assertArrayEquals(expected, input);
        BookSorter.selectionSort(input, 2);
        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN authors have special characters, THEN sorting by author works")
    @Test
    void testAuthorsWithSpecialCharacters() {
        Book[] input = new Book[]{
                new Book("Technology Ethics", "123 Numbers"),
                new Book("Coding 101", "!Exclaim Author"),
                new Book("Internet Policy", "#Hashtag Human"),
                new Book("Programming", "Zed Last")
        };

        BookSorter.selectionSort(input, 2);

        Book[] expected = new Book[]{
                new Book("Coding 101", "!Exclaim Author"),
                new Book("Internet Policy", "#Hashtag Human"),
                new Book("Technology Ethics", "123 Numbers"),
                new Book("Programming", "Zed Last")
        };

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN titles have special characters, THEN sorting by title works")
    @Test
    void testTitlesWithSpecialCharacters() {
        Book[] input = new Book[]{
                new Book("!Artificial Intelligence", "Rob Brown"),
                new Book("123 Algorithms", "Ann Numbers"),
                new Book("#Ethics", "Charlie Symbol"),
                new Book("Zebra Guide", "Zoe Zebra")
        };

        BookSorter.selectionSort(input, 1);

        Book[] expected = new Book[]{
                new Book("!Artificial Intelligence", "Rob Brown"),
                new Book("#Ethics", "Charlie Symbol"),
                new Book("123 Algorithms", "Ann Numbers"),
                new Book("Zebra Guide", "Zoe Zebra")
        };

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN this list of books has one element, THEN sorting with ordering 1"
            + "yields the same list")
    @Test
    void testEmptyOrdering1() {
        Book[] input = new Book[] {new Book("Race After Technology", "Ruha Benjamin")};

        BookSorter.selectionSort(input, 1);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        Book[] expected = new Book[] {new Book("Race After Technology", "Ruha Benjamin")};
        assertArrayEquals(actual, expected);
    }

    @DisplayName("WHEN this list of books has one element, THEN sorting with ordering 2"
            + "yields the same list")
    @Test
    void testEmptyOrdering2() {
        Book[] input = new Book[] {new Book("Race After Technology", "Ruha Benjamin")};

        BookSorter.selectionSort(input, 2);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        Book[] expected = new Book[] {new Book("Race After Technology", "Ruha Benjamin")};
        assertArrayEquals(actual, expected);
    }

    @DisplayName("WHEN this list of multiple books is already sorted, THEN sorting with ordering 1 "
            + "yields the same list")
    @Test
    void testAlreadySortedTitle() {
        Book[] input = new Book[] {
                new Book("Invisible Women", "Caroline Criado Perez"),
                new Book("Race After Technology", "Ruha Benjamin"),
                new Book("Weapons of Math Destruction", "Cathy O'Neil")
        };

        BookSorter.selectionSort(input, 1);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        Book[] expected = new Book[] {
                new Book("Invisible Women", "Caroline Criado Perez"),
                new Book("Race After Technology", "Ruha Benjamin"),
                new Book("Weapons of Math Destruction", "Cathy O'Neil")
        };
        assertArrayEquals(actual, expected);
    }


    @DisplayName("WHEN this list has duplicates, THEN the output list is in order for ordering 2")

    @Test
    void testDuplicatesAuthor() {
        Book[] input = new Book[] {
                new Book("Viral Justice", "Ruha Benjamin"),
                new Book("Invisible Women", "Caroline Criado Perez"),
                new Book("Race After Technology", "Ruha Benjamin"),
                new Book("Weapons of Math Destruction", "Cathy O'Neil"),
                new Book("Imagination: A Manifesto", "Ruha Benjamin"),
        };

        BookSorter.selectionSort(input, 2);

        // Input has been modified. We are now thinking of it as actual output.
        Book[] actual = input;

        // This tests a property of the output array rather than testing it for equality
        // with an expected array.
        for (int i = 0; i < input.length-1; i++) {
            // we want actual[i+1] to have author string greater than or equal to actual[i]
            // so the output should be negative or zero
            assertTrue(actual[i].author().compareToIgnoreCase(actual[i+1].author()) <= 0);
        }
    }

    // New test cases added below

    @DisplayName("WHEN the list of books is empty, THEN sorting does not cause an error")
    @Test
    void testSortEmptyArray() {
        Book[] input = new Book[]{};
        Book[] expected = new Book[]{};

        BookSorter.selectionSort(input, 1); // Test with ordering 1

        assertArrayEquals(expected, input);

        BookSorter.selectionSort(input, 2); // Test with ordering 2
        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN a book has an empty title, THEN it is sorted correctly by title (ordering 1)")
    @Test
    void testSortWithEmptyTitle() {
        Book[] input = new Book[]{
                new Book("Catcher in the Rye", "J.D. Salinger"),
                new Book("", "Anonymous"),
                new Book("Animal Farm", "George Orwell")
        };

        BookSorter.selectionSort(input, 1);

        Book[] expected = new Book[]{
                new Book("", "Anonymous"),
                new Book("Animal Farm", "George Orwell"),
                new Book("Catcher in the Rye", "J.D. Salinger")
        };

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN a book has an empty author, THEN it is sorted correctly by author (ordering 2)")
    @Test
    void testSortWithEmptyAuthor() {
        Book[] input = new Book[]{
                new Book("To Kill a Mockingbird", "Harper Lee"),
                new Book("The Great Gatsby", "F. Scott Fitzgerald"),
                new Book("1984", "")
        };

        BookSorter.selectionSort(input, 2);

        Book[] expected = new Book[]{
                new Book("1984", ""),
                new Book("The Great Gatsby", "F. Scott Fitzgerald"),
                new Book("To Kill a Mockingbird", "Harper Lee")
        };

        assertArrayEquals(expected, input);
    }
}
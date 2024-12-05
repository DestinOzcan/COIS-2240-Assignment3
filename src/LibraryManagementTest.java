import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryManagementTest {

    private Library library;
    private Member member1;
    private Book book1;
    private Transaction transaction;

    @Before
    public void setUp() {
        // Initialize objects before each test case
        library = new Library();
        member1 = new Member(1, "George");
        book1 = new Book(100, "Programming");
        transaction = Transaction.getTransaction(); // Singleton instance
    }

    @Test
    public void testBorrowReturn() {
        // Borrow the book and check if successful
        assertTrue(transaction.borrowBook(book1, member1)); // Book should be borrowed successfully
        assertFalse(book1.isAvailable()); // The book should no longer be available

        // Try borrowing the same book again (should fail)
        assertFalse(transaction.borrowBook(book1, member1)); // Should fail since the book is already borrowed

        // Return the book and check if successful
        assertTrue(transaction.returnBook(book1, member1)); // Book should be returned successfully
        assertTrue(book1.isAvailable()); // The book should now be available

        // Try returning the book again (should fail)
        assertFalse(transaction.returnBook(book1, member1)); // Should fail since the book is not borrowed anymore
    }
    
    @Test
    public void testBookId() {
        try {
            // Valid cases
            Book book1 = new Book(100, "Valid Book 1");
            assertEquals(100, book1.getId());

            Book book2 = new Book(999, "Valid Book 2");
            assertEquals(999, book2.getId());

            // Invalid cases (ID > 999)
            try {
                Book book3 = new Book(1000, "Invalid Book");
                fail("Exception should be thrown for ID greater than 999");
            } catch (Exception e) {
                assertEquals("Invalid Book ID. ID must be between 100 and 999.", e.getMessage());
            }

            // Invalid cases (ID < 100)
            try {
                Book book4 = new Book(99, "Invalid Book");
                fail("Exception should be thrown for ID less than 100");
            } catch (Exception e) {
                assertEquals("Invalid Book ID. ID must be between 100 and 999.", e.getMessage());
            }

        } catch (Exception e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }
}
public class Book {
    private int id;
    private String title;
    private boolean available;

    // Constructor
    public Book(int id, String title) throws Exception {
        if (!isValidId(id)) {
            throw new Exception("Invalid Book ID. ID must be between 100 and 999.");
        }
        this.id = id;
        this.title = title;
        this.available = true; // Initially, the book is available
    }

    // Method to check if a book ID is valid (between 100 and 999)
    public boolean isValidId(int id) {
        return id >= 100 && id <= 999;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    // Method to check if the book is available
    public boolean isAvailable() {
        return available;
    }

    // Method to mark the book as borrowed
    public void borrowBook() {
        if (available) {
            available = false; // Mark the book as not available
        }
    }

    // Method to mark the book as returned
    public void returnBook() {
        available = true; // Mark the book as available
    }
}

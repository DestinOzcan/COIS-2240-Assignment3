import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class Transaction {
    // Singleton instance
    private static Transaction instance;

    // Flag to prevent reflection-based instantiation
    private static boolean instanceCreated = false;

    // Private constructor to prevent instantiation
    private Transaction() {
        if (instanceCreated) {
            throw new RuntimeException("Reflection not allowed to create multiple instances of Singleton");
        }
        instanceCreated = true; // Mark the instance as created
    }

    // Public method to get the single instance of the Transaction class
    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }

    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book);
            return true;  // Successfully borrowed
        }
        return false;  // Book is not available
    }

    public boolean returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            return true;  // Successfully returned
        }
        return false;  // Book was not borrowed
    }

    // Get the current date and time in a readable format
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    // Method to save the transaction details to the file
    public void saveTransaction(String transactionDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(transactionDetails + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Method to display the transaction history
    public void displayTransactionHistory() {
        File file = new File("transactions.txt");
        if (!file.exists()) {
            System.out.println("No transaction history found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("=== Transaction History ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading transaction history: " + e.getMessage());
        }
    }
}

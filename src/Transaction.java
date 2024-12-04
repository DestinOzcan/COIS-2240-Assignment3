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

    // Private constructor to prevent instantiation
    private Transaction() {}

    // Public method to get the single instance of the Transaction class
    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }

    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book);
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails); // Save the transaction to file
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public void returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails); // Save the transaction to file
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
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

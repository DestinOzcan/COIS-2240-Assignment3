import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryManagement {
    private Library library = new Library();

    public static void main(String[] args) {
        new LibraryManagement().run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        Transaction transaction = Transaction.getTransaction(); // Singleton instance

        while (running) {
            System.out.println("===========================");
            System.out.println("Library Management System");
            System.out.println("1. Add Member");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Borrowed Books");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add Member code
                    break;

                case 2:
                    // Add Book with Exception Handling
                    try {
                        System.out.print("Enter book ID: ");
                        int id = scanner.nextInt();
                        System.out.print("Enter book title: ");
                        String title = scanner.next();
                        scanner.nextLine();

                        Book newBook = new Book(id, title); // This will throw an exception if ID is invalid
                        library.addBook(newBook);
                        System.out.println("Book added to library successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Borrow Book
                    System.out.println("\n--- Available Members ---");
                    for (Member member : library.getMembers()) {
                        System.out.println(member.getId() + ". " + member.getName());
                    }
                    
                    System.out.print("Enter member ID: ");
                    int memberId = scanner.nextInt();
                    
                    System.out.println("\n--- Available Books ---");
                    for (Book book : library.getBooks()) {
                        if (book.isAvailable())
                            System.out.println(book.getId() + ". " + book.getTitle());
                    }
                    
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    
                    scanner.nextLine();

                    Member member = library.findMemberById(memberId);
                    Book book = library.findBookById(bookId);

                    if (member != null && book != null) {
                        transaction.borrowBook(book, member);
                    } else {
                        System.out.println("Invalid member or book ID.");
                    }
                    break;

                case 4:
                    // Return Book
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextInt();
                    
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextInt();
                    
                    scanner.nextLine();

                    member = library.findMemberById(memberId);
                    book = library.findBookById(bookId);

                    if (member != null && book != null) {
                        transaction.returnBook(book, member);
                    } else {
                        System.out.println("Invalid member or book ID.");
                    }
                    break;

                case 5:
                    // View Borrowed Books
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextInt();
                    scanner.nextLine();

                    Member specificMember = library.findMemberById(memberId);
                    
                    if (specificMember != null) {
                        System.out.println("Books borrowed by " + specificMember.getName() + ":");
                        for (Book bk : specificMember.getBorrowedBooks()) {
                            System.out.println(" - " + bk.getTitle());
                        }
                    } else {
                        System.out.println("Invalid member ID.");
                    }
                    break;

                case 6:
                    // View Transaction History
                    transaction.displayTransactionHistory(); // Display the transaction history
                    break;

                case 7:
                    // Exit
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

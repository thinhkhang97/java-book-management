package sdet.test;

import sdet.io.BookFactory;
import sdet.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class BookManagement {

    public static String path;

    public static void menu() {
        System.out.println("*** Press 1 to create a book and save it");
        System.out.println("*** Press 2 to retrieve a book based on ISBN");
        System.out.println("*** Press 3 to retrieve book list base on author name");
        System.out.println("*** Press 4 to retrieve all Book those are sorted based on Book name/title");
        System.out.println("*** Press 5 to delete a book by ISBN");
        System.out.println("*** Press 6 to edit a book by ISBN");
        System.out.println("*** Press 0 to exit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> {
                Book book = createBook();
                addABook(book);
            }
            case 2 -> {
                Book foundBook = findBookByISBN();
                if (foundBook != null) {
                    System.out.println("Book information:");
                    System.out.println(foundBook.toString());
                } else {
                    System.out.println("Book not found");
                }
            }
            case 3 -> {
                List<Book> foundBooks = findBooksByAuthor();
                if (foundBooks != null && foundBooks.size() > 0) {
                    System.out.println("Book information:");
                    for (Book book : foundBooks) {
                        System.out.println(book.toString());
                    }
                } else {
                    System.out.println("Book not found");
                }
            }
            case 4 -> {
                List<Book> books = sortBooksByTitle();
                if (books != null && books.size() > 0) {
                    System.out.println("Sorted books:");
                    for (Book book : books) {
                        System.out.println(book.toString());
                    }
                } else {
                    System.out.println("No Book to sort");
                }
            }
            case 5 -> {
                deleteBookByISBN();
            }
            case 6 -> {
                editBookByISBN();
            }
            case 0 -> System.exit(0);
            default -> System.out.println("Not available option!!!");
        }
    }

    public static void main(String[] args) {
        File file = new File("books.txt");
        path = file.getAbsolutePath();

        System.out.println("Welcome to book management!!!");
        while (true) {
            menu();
        }
    }

    public static Book createBook() {
        System.out.println("Please input the information of book");
        Scanner scanner = new Scanner(System.in);

        System.out.println("ISBN:");
        String ISBN = scanner.nextLine();

        System.out.println("Title:");
        String title = scanner.nextLine();

        System.out.println("Author:");
        String author = scanner.nextLine();

        System.out.println("Publish Year:");
        String year = scanner.nextLine();

        System.out.printf("Created a book with ISBN %s, title %s, author %s, publish year %s%n", ISBN, title, author, year);

        return new Book(ISBN, title, parseInt(year), author);
    }

    public static void addABook(Book bookToAdd) {
        List<Book> books = fetchBookList();
        if (books == null) {
            return;
        }
        Book sameISBNBook = null;
        for (Book book : books) {
            if (book.getISBN().equals(bookToAdd.getISBN())) {
                sameISBNBook = book;
            }
        }
        if (sameISBNBook == null) {
            books.add(bookToAdd);
            saveBooks(books);
        } else {
            System.out.println("Could add the book: The book has duplicated ISBN");
        }
    }

    public static void saveBooks(List<Book> books) {
        BookFactory bookFactory = new BookFactory();
        try {
            bookFactory.saveBookList(books, path);
        } catch (IOException e) {
            System.out.println("COULDN'T SAVE BOOKS");
            e.printStackTrace();
        }
    }

    public static List<Book> fetchBookList() {
        BookFactory bookFactory = new BookFactory();
        try {
            return bookFactory.readBookList(path);
        } catch (IOException e) {
            System.out.println("COULDN'T READ BOOK DATA");
            e.printStackTrace();
        }
        return null;
    }

    public static Book findBookByISBN() {
        List<Book> books = fetchBookList();
        if (books == null) {
            return null;
        }

        System.out.println("Please input ISBN of book to find:");
        Scanner scanner = new Scanner(System.in);
        String ISBN = scanner.nextLine();

        Book foundBook = null;
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                foundBook = book;
            }
        }

        return foundBook;
    }

    public static List<Book> findBooksByAuthor() {
        List<Book> books = fetchBookList();
        if (books == null) {
            return null;
        }

        System.out.println("Please input author name of book to find:");
        Scanner scanner = new Scanner(System.in);
        String author = scanner.nextLine();

        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public static List<Book> sortBooksByTitle() {
        List<Book> books = fetchBookList();
        if (books == null) {
            return null;
        }

        books.sort((b1, b2) -> b1.getTitle().compareTo(b2.getTitle()));
        return books;
    }

    public static void deleteBookByISBN() {
        List<Book> books = fetchBookList();
        if (books == null) {
            System.out.println("No book to delete");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the ISBN to delete: ");
        String ISBN = scanner.nextLine();
        int foundBookIndex = -1;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                foundBookIndex = i;
            }
        }

        if (foundBookIndex < 0) {
            System.out.println("Book not found");
        } else {
            books.remove(foundBookIndex);
            saveBooks(books);
            System.out.println("Deleted book!");
        }
    }

    public static void editBookByISBN() {
        List<Book> books = fetchBookList();
        if (books == null) {
            System.out.println("No book to delete");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the ISBN to edit: ");
        String ISBN = scanner.nextLine();
        int foundBookIndex = -1;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                foundBookIndex = i;
            }
        }

        if (foundBookIndex < 0) {
            System.out.println("Book not found");
        } else {
            Book bookToEdit = books.get(foundBookIndex);
            System.out.println("Please input the information of book:");

            System.out.printf("Title (%s)%n", bookToEdit.getTitle());
            bookToEdit.setTitle(scanner.nextLine());

            System.out.printf("Author (%s)%n", bookToEdit.getAuthor());
            bookToEdit.setAuthor(scanner.nextLine());

            System.out.printf("Publish Year (%s)%n", bookToEdit.getYear());
            bookToEdit.setYear(parseInt(scanner.nextLine()));

            System.out.println("The information after edit");
            System.out.println(bookToEdit.toString());
            saveBooks(books);
        }
    }
}

package sdet.io;

import sdet.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookFactory {
    public void saveBookList(List<Book> books, String path) throws IOException {
        FileIO fileIO = new FileIO(path);
        BufferedWriter writer = fileIO.getWriter();
        for (Book book : books) {
            String dataLine = book.getISBN() + ";" + book.getTitle() + ";" + book.getYear() + ";" + book.getAuthor();
            writer.write(dataLine);
            writer.newLine();
        }
        fileIO.closeWriter();
    }

    public List<Book> readBookList(String path) throws IOException {
        FileIO fileIO = new FileIO(path);
        List<Book> books = new ArrayList<>();
        BufferedReader reader = fileIO.getReader();
        String dataLine = reader.readLine();
        while (dataLine != null) {
            String[] bookdata = dataLine.split(";");
            books.add(new Book(bookdata[0], bookdata[1], Integer.parseInt(bookdata[2]), bookdata[3]));
            dataLine = reader.readLine();
        }
        fileIO.closeReader();
        return books;
    }
}

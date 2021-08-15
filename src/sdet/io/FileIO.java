package sdet.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileIO {
    private String path;

    private BufferedWriter writer = null;

    private BufferedReader reader = null;

    private FileOutputStream fileOutputStream;

    private OutputStreamWriter outputStreamWriter;

    private FileInputStream fileInputStream;

    private InputStreamReader inputStreamReader;

    public FileIO(String path) {
        this.path = path;
    }

    private void initWriter() throws FileNotFoundException, UnsupportedEncodingException {
        this.fileOutputStream = new FileOutputStream(this.path);
        this.outputStreamWriter = new OutputStreamWriter(this.fileOutputStream, "UTF-8");
        this.writer = new BufferedWriter(this.outputStreamWriter);
    }

    private void initReader() throws FileNotFoundException {
        this.fileInputStream = new FileInputStream(this.path);
        this.inputStreamReader = new InputStreamReader(this.fileInputStream, StandardCharsets.UTF_8);
        this.reader = new BufferedReader(this.inputStreamReader);
    }

    public BufferedWriter getWriter() throws FileNotFoundException, UnsupportedEncodingException {
        if (this.writer == null) {
            this.initWriter();
        }
        return writer;
    }

    public BufferedReader getReader() throws FileNotFoundException {
        if (this.reader == null) {
            this.initReader();
        }
        return reader;
    }

    public void closeWriter() throws IOException {
        if (this.writer != null) {
            this.writer.close();
            this.outputStreamWriter.close();
            this.fileOutputStream.close();
        }
    }

    public void closeReader() throws IOException {
        if (this.reader != null) {
            this.reader.close();
            this.inputStreamReader.close();
            this.fileInputStream.close();
        }
    }

    public String getPath() {
        return path;
    }


}

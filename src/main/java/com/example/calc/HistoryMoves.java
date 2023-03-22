package com.example.calc;

import java.io.*;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HistoryMoves {

    private File file;
    private final String absolutePath;

    {
        file = new File("filename.txt");
        absolutePath = file.getAbsolutePath();
    }

    public void saveString(String result) throws IOException {

        try (FileOutputStream fileout = new FileOutputStream(absolutePath, true)) {
            fileout.write(result.getBytes());
            fileout.write('\n');
        } catch (FileNotFoundException ignored) {

        }

    }

    public void clearHistory() throws IOException {
        try (FileOutputStream fileout = new FileOutputStream(absolutePath, false)) {
            fileout.write("".getBytes());
        }
    }

    public ObservableList<String> getHistory() throws IOException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try(Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
                list.add(scan.nextLine());
            }
        } catch (IOException ignored) {
        }
        return list;
    }
}

package ru.vsu.cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private Scanner file;

    public FileReader(String path) {
        try {
            this.file = new Scanner(new File(path));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String readAll() {
        StringBuilder text = new StringBuilder();
        while (this.file.hasNext()) {
            text.append(this.file.nextLine());
        }
        return text.toString();
    }
}

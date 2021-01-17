package ru.vsu.cs;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String text;
        if (args.length == 0) {
            text = readLine();
        } else {
            FileReader fileReader = new FileReader(args[0]);
            text = fileReader.readAll();
        }

        if (text != null) {
            TextParser textParser = new TextParser(text);
            HashSet<String> emails = textParser.getEmails();

            if (emails.size() != 0) {
                printEmails(emails);
            } else {
                printNoEmails();
            }
        }
    }

    private static void printEmails(HashSet<String> emails) {
        System.out.print("Found email addresses: ");
        for (String email : emails) {
            System.out.print(email + " ");
        }
    }

    private static void printNoEmails() {
        System.out.println("No email addresses found");
    }

    private static String readLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text: ");
        return scanner.nextLine();
    }
}

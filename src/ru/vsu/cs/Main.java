package ru.vsu.cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String line;
        if (args.length == 0) {
            line = readLine();
        } else {
            line = readFile(args[0]);
        }

        if (line != null) {
            HashSet<String> emails = getEmails(line);

            if (isEmailsInLine(emails)) {
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

    private static String readFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }
    }

    private static boolean isEmailsInLine(HashSet<String> emails) {
        return emails.size() == 0;
    }

    private static HashSet<String> getEmails(String line) {
        HashSet<String> emails = new HashSet<>();
        HashSet<String> words = splitByWords(line);
        for (String word : words) {
            if (isEmail(word)) {
                emails.add(word);
            }
        }
        return emails;
    }

    private static HashSet<String> splitByWords(String line) {
        StringBuilder curWord = new StringBuilder();
        HashSet<String> words = new HashSet<>();
        char[] charsLine = line.toCharArray();
        char lastChar = ' ';
        for (char ch : charsLine) {
            if (isValidCharForEmail(ch)) {
                curWord.append(ch);
                if (ch == '.' && lastChar == ' ') {
                    curWord.deleteCharAt(curWord.length() - 1);
                }
            } else if (ch == ' ' && lastChar == '.' && curWord.length() > 0) {
                curWord.deleteCharAt(curWord.length() - 1);
            } else {
                words.add(curWord.toString());
                curWord = new StringBuilder();
            }
            lastChar = ch;
        }
        words.add(curWord.toString());
        return words;
    }

    private static boolean isValidCharForEmail(char ch) {
        return (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch == '-' || ch == '_'
                || ch == '@' || ch == '.');
    }

    private static boolean isEmail(String word) {
        char[] chars = word.toCharArray();
        int lenOfLocal = 0;
        int lenOfDomain = 0;
        char lastChar = ' ';
        boolean isWordWithAt = false;
        for (char ch : chars) {
            if (ch == '@') {
                if (lastChar == '.') {
                    return false;
                }
                if (isWordWithAt) {
                    return false;
                } else {
                    isWordWithAt = true;
                }
            } else if (ch == '.' && lastChar == '@') {
                return false;
            } else if (!isWordWithAt) {
                lenOfLocal++;
            } else {
                lenOfDomain++;
            }
            lastChar = ch;
        }
        return lenOfLocal > 0 && lenOfDomain > 0 && isWordWithAt;
    }
}

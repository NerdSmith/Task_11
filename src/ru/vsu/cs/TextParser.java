package ru.vsu.cs;

import java.util.HashSet;

public class TextParser {
    private final String text;

    public TextParser(String text) {
        this.text = text;
    }

    public HashSet<String> getEmails() {
        HashSet<String> emails = new HashSet<>();
        HashSet<String> words = splitByWords(this.text);
        for (String word : words) {
            if (isEmail(word)) {
                emails.add(word);
            }
        }
        return emails;
    }

    private HashSet<String> splitByWords(String text) {
        StringBuilder curWord = new StringBuilder();
        HashSet<String> words = new HashSet<>();
        char[] charsLine = text.toCharArray();
        char lastChar = ' ';
        for (char ch : charsLine) {
            if (isValidCharForEmail(ch)) {
                curWord.append(ch);
                if (ch == '.' && lastChar == ' ') {
                    curWord.deleteCharAt(curWord.length() - 1);
                }
            }
            else if (ch == ' ' && lastChar == '.' && curWord.length() > 0) {
                curWord.deleteCharAt(curWord.length() - 1);
            }
            else {
                words.add(curWord.toString());
                curWord = new StringBuilder();
            }
            lastChar = ch;
        }
        words.add(curWord.toString());
        return words;
    }

    private boolean isValidCharForEmail(char ch) {
        return (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch == '-' || ch == '_'
                || ch == '@' || ch == '.');
    }

    private boolean isEmail(String word) {
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
                }
                else {
                    isWordWithAt = true;
                }
            }
            else if (ch == '.' && lastChar == '@') {
                return false;
            }
            else if (!isWordWithAt) {
                lenOfLocal++;
            }
            else {
                lenOfDomain++;
            }
            lastChar = ch;
        }
        return lenOfLocal > 0 && lenOfDomain > 0 && isWordWithAt;
    }
}

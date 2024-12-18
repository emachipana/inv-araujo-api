package com.inversionesaraujo.api.utils;

public class Capitalize {
    public static String capitalizeEachWord(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+");
        StringBuilder capitalizedSentence = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                capitalizedSentence.append(capitalizedWord).append(" ");
            }
        }

        return capitalizedSentence.toString().trim();
    }
}

package com.example.yazlab2_1.services;

public class MergeTextService {
    public static String mergeSentences(String[] sentences) {

        StringBuilder result = new StringBuilder(sentences[0].toLowerCase());
        String lastMerged = sentences[0].toLowerCase();

        for (int i = 1; i < sentences.length; i++) {
            String current = sentences[i].toLowerCase();
            String[] lastWords = lastMerged.split(" ");
            boolean foundMatch = false;
            for (String lastWord : lastWords) {
                if (current.startsWith(lastWord)) {
                    foundMatch = true;
                    break;
                }
            }

            if (foundMatch) {
                int index = current.indexOf(lastWords[lastWords.length-1]);
                if (index >= 0) {
                    result.append(current.substring(index + lastWords[lastWords.length-1].length()));
                    lastMerged = current;
                }
            }
        }

        return result.toString();
    }
}

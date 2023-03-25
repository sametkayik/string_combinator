package com.example.yazlab2_1.services;

public class MergeTextService {

    public static String mergeSentences(String[] sentences) {

        //oldu mu
        StringBuilder result = new StringBuilder(sentences[0].toLowerCase());
        String lastMerged = sentences[0].toLowerCase();

        for (int i = 1; i < sentences.length; i++) {
            String current = sentences[i].toLowerCase();
            String[] lastWords = lastMerged.split(" ");
            String lastWord = lastWords[lastWords.length - 1];

            if (current.startsWith(lastWord)) {
                result.append(current.substring(lastWord.length()));
                lastMerged = current;
            }
            else {
                int index = current.indexOf(lastWord);
                if (index >= 0) {
                    result.append(current.substring(index + lastWord.length()));
                    lastMerged = current.substring(index);
                }
            }
        }
        return result.toString();
    }


}

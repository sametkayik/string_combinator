package com.example.yazlab2_1.services;

import com.example.yazlab2_1.models.TextEntity;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

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
    public static void permute(List<Integer> indexes, int start, List<TextEntity> textEntities, String[] texts) {
        long startTime = System.nanoTime();
        if (start == indexes.size()) {
            String[] permutedTexts = new String[texts.length];
            for (int i = 0; i < indexes.size(); i++) {
                permutedTexts[i] = texts[indexes.get(i)];
            }

            String mergedText = MergeTextService.mergeSentences(permutedTexts);
            long endTime = System.nanoTime();
            double durationTimeInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;

            System.out.println("Merged Text: " + mergedText);
            System.out.println("Merging Time : " + new DecimalFormat("#.############").format(durationTimeInSeconds) + " seconds");

            TextEntity textEntity = new TextEntity();
            textEntity.setTexts(Arrays.asList(permutedTexts));
            textEntity.setMergedText(mergedText);
            textEntity.setDurationTime(durationTimeInSeconds);
            textEntities.add(textEntity);

        } else {
            for (int i = start; i < indexes.size(); i++) {
                int temp = indexes.get(start);
                indexes.set(start, indexes.get(i));
                indexes.set(i, temp);

                permute(indexes, start + 1, textEntities, texts);

                temp = indexes.get(start);
                indexes.set(start, indexes.get(i));
                indexes.set(i, temp);
            }
        }
    }
    public static TextEntity findLongestText(List<TextEntity> textEntities) {
        TextEntity longestTextEntity = null;
        int maxLength = 0;

        for (TextEntity textEntity : textEntities) {
            String mergedText = textEntity.getMergedText();
            int length = mergedText.length();
            if (length > maxLength) {
                maxLength = length;
                longestTextEntity = textEntity;
            }
        }

        return longestTextEntity;
    }
}

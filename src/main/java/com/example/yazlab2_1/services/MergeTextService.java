package com.example.yazlab2_1.services;

import com.example.yazlab2_1.models.TextEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeTextService {
    public static String mergeSentences(String[] sentences) {
        StringBuilder result = new StringBuilder(sentences[0]);
        String lastMerged = sentences[0];

        for (int i = 1; i < sentences.length; i++) {
            String current = sentences[i];
            String[] lastWords = lastMerged.split(" ");
            String lastWord = lastWords[lastWords.length - 1].toLowerCase();
            String[] currentWords = current.split(" ");
            String currentWord = currentWords[0].toLowerCase();

            if (currentWord.equals(lastWord)) {
                result.append(current.substring(currentWords[0].length()));
                lastMerged = current;
            } else {
                int index = current.toLowerCase().indexOf(lastWord);
                if (index >= 0) {
                    result.append(current.substring(index + lastWord.length()));
                    lastMerged = current.substring(index);
                }
            }
        }
        return result.toString();
    }
    public static void permuteLinear(List<Integer> indexes, List<TextEntity> textEntities, String[] texts) {
        long startTime = System.nanoTime();
        for (int r = 2; r <= indexes.size(); r++) {
            int[] indices = new int[r];
            for (int i = 0; i < r; i++) {
                indices[i] = i;
            }
            while (true) {
                String[] permutedTexts = new String[r];
                List<Integer> mergedIndexes = new ArrayList<>();
                for (int i = 0; i < r; i++) {
                    int index = indexes.get(indices[i]);
                    permutedTexts[i] = texts[index];
                    mergedIndexes.add(index);
                }
                String mergedText = mergeSentences(permutedTexts);
                long endTime = System.nanoTime();
                double durationTimeInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;
                System.out.println("Merged Text: " + mergedText + ", Indexes: " + mergedIndexes);
                TextEntity textEntity = new TextEntity();
                textEntity.setTexts(Arrays.asList(permutedTexts));
                textEntity.setMergedText(mergedText);
                textEntity.setDurationTime(durationTimeInSeconds);
                textEntities.add(textEntity);

                int i = r - 1;
                while (i >= 0 && indices[i] == i + indexes.size() - r) {
                    i--;
                }
                if (i < 0) {
                    break;
                }
                indices[i]++;
                for (int j = i + 1; j < r; j++) {
                    indices[j] = indices[j - 1] + 1;
                }
            }
        }
    }

    public static void permuteNonlinear(List<Integer> indexes, int start, List<TextEntity> textEntities, String[] texts) {
        long startTime = System.nanoTime();
        if (start == indexes.size()) {
            String[] permutedTexts = new String[texts.length];
            List<Integer> mergedIndexes = new ArrayList<>();
            for (int i = 0; i < indexes.size(); i++) {
                int index = indexes.get(i);
                permutedTexts[i] = texts[index];
                mergedIndexes.add(index);
            }

            String mergedText = mergeSentences(permutedTexts);
            long endTime = System.nanoTime();
            double durationTimeInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;

            System.out.println("Merged Text: " + mergedText + ", Indexes: " + mergedIndexes);

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

                permuteNonlinear(indexes, start + 1, textEntities, texts);

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

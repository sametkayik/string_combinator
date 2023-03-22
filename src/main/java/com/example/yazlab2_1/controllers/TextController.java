package com.example.yazlab2_1.controllers;

import com.example.yazlab2_1.models.TextEntity;
import com.example.yazlab2_1.models.TextsRequest;
import com.example.yazlab2_1.services.TextService;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TextController {
    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }

    @PostMapping("/texts")
    public TextEntity createEntity(@RequestBody TextsRequest textsRequest) {
        List<String> texts = textsRequest.getTexts();

        //String mergedText = String.join(" ", texts);

        long startTime = System.nanoTime();
        String mergedText = mergeSentences(texts.toArray(new String[0]));
        long endTime = System.nanoTime();
        long durationTimeInNanoSec = (endTime - startTime);
        double durationTimeInSeconds = (double)durationTimeInNanoSec / 1_000_000_000.0;
        System.out.println("Merging Time in nanoseconds: " + durationTimeInNanoSec);
        System.out.println("Merging Time : " + new DecimalFormat("#.############").format(durationTimeInSeconds) + " seconds");

        TextEntity textEntity = new TextEntity();
        textEntity.setTexts(texts);
        textEntity.setMergedText(mergedText);
        textEntity.setDurationTime(durationTimeInSeconds);

        textService.saveEntity(texts, mergedText, durationTimeInSeconds);
        return textEntity;
    }
    @GetMapping("/texts")
    public List<TextEntity> getAllEntities() {
        return textService.getAllEntities();
    }
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




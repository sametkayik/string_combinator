package com.example.yazlab2_1.controllers;

import com.example.yazlab2_1.models.TextEntity;
import com.example.yazlab2_1.models.TextsRequest;
import com.example.yazlab2_1.services.TextService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String mergedText = mergeSentences(texts.toArray(new String[0]));
        TextEntity textEntity = new TextEntity();
        textEntity.setTexts(texts);
        textEntity.setMergedText(mergedText);

        textService.saveEntity(texts, mergedText);

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




package com.example.yazlab2_1.controllers;

import com.example.yazlab2_1.models.TextEntity;
import com.example.yazlab2_1.models.TextsRequest;
import com.example.yazlab2_1.services.TextService;
import com.example.yazlab2_1.services.MergeTextService;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TextController {
    private final TextService textService;
    public TextController(TextService textService) {
        this.textService = textService;
    }
    @PostMapping("/mergeText")
    public TextEntity mergeText(@RequestBody TextsRequest textsRequest) {
        long startTime = System.nanoTime();
        List<String> texts = textsRequest.getTexts();

        String mergedText = MergeTextService.mergeSentences(texts.toArray(new String[0]));
        long endTime = System.nanoTime();

        double durationTimeInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Merging Time : " + new DecimalFormat("#.############").format(durationTimeInSeconds) + " seconds");

        TextEntity textEntity = new TextEntity();
        textEntity.setTexts(texts);
        textEntity.setMergedText(mergedText);
        textEntity.setDurationTime(durationTimeInSeconds);

        return textEntity;
    }
    @PostMapping("/mergeTextNonlinear")
    public TextEntity mergeTextNonlinear(@RequestBody TextsRequest textsRequest) {

        List<String> texts = textsRequest.getTexts();
        List<TextEntity> textEntities = new ArrayList<>();

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            indexes.add(i);
        }
        long startTime = System.nanoTime();
        MergeTextService.permute(indexes, 0, textEntities, texts.toArray(new String[0]));
        long endTime = System.nanoTime();
        TextEntity longestTextEntity = MergeTextService.findLongestText(textEntities);

        double durationTimeInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Whole process time : " + new DecimalFormat("#.############").format(durationTimeInSeconds) + " seconds");

        longestTextEntity.setDurationTime(durationTimeInSeconds);
        return longestTextEntity;
    }

    @PostMapping("/saveText")
    public void saveText(@RequestBody TextsRequest textsRequest){
        textService.saveEntity(textsRequest.getTexts(), textsRequest.getMergedText(), textsRequest.getDurationTime());
    }

    @GetMapping("/texts")
    public List<TextEntity> getAllEntities() {
        return textService.getAllEntities();
    }
}
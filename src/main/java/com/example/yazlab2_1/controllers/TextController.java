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
        List<String> texts = textsRequest.getTexts();
        List<TextEntity> textEntities = new ArrayList<>();

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            indexes.add(i);
        }
        long startTime = System.nanoTime();
        MergeTextService.permuteLinear(indexes, textEntities, texts.toArray(new String[0]));
        long endTime = System.nanoTime();
        TextEntity longestTextEntity = MergeTextService.findLongestText(textEntities);

        double durationTimeInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Whole process time : " + new DecimalFormat("#.############").format(durationTimeInSeconds) + " seconds");

        longestTextEntity.setDurationTime(durationTimeInSeconds);
        return longestTextEntity;
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
        MergeTextService.permuteNonlinear(indexes, 0, textEntities, texts.toArray(new String[0]));
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
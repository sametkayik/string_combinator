package com.example.yazlab2_1.controllers;

import com.example.yazlab2_1.models.TextEntity;
import com.example.yazlab2_1.models.TextsRequest;
import com.example.yazlab2_1.services.TextService;
import com.example.yazlab2_1.services.MergeTextService;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TextController {
    private final TextService textService;
    public TextController(TextService textService) { this.textService = textService; }
    private final List<String> longestSentences = new ArrayList<>();

    @PostMapping("/mergeText")
    public TextEntity mergeText(@RequestBody TextsRequest textsRequest) {
        List<String> texts = textsRequest.getTexts();
        List<TextEntity> textEntities = new ArrayList<>();
        long startTime = System.nanoTime();
        MergeTextService.permuteLinear(getRange(texts.size()), textEntities, texts.toArray(new String[0]));
        return findLongestText(textEntities, startTime);
    }

    @PostMapping("/mergeTextNonlinear")
    public TextEntity mergeTextNonlinear(@RequestBody TextsRequest textsRequest) {
        List<String> texts = textsRequest.getTexts();
        List<TextEntity> textEntities = new ArrayList<>();
        long startTime = System.nanoTime();
        MergeTextService.permuteNonlinear(getRange(texts.size()), 0, textEntities, texts.toArray(new String[0]));
        return findLongestText(textEntities, startTime);
    }

    private TextEntity findLongestText(List<TextEntity> textEntities, long startTime) {
        List<TextEntity> longestTextEntities = MergeTextService.findLongestText(textEntities);
        Set<String> uniqueLongestSentences = longestTextEntities.stream()
                .map(TextEntity::getMergedText)
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
        longestSentences.clear();
        longestSentences.addAll(uniqueLongestSentences);
        TextEntity longestTextEntity = longestTextEntities.get(0);
        longestTextEntity.setDurationInSeconds((System.nanoTime() - startTime) / 1_000_000_000.0);
        System.out.printf("Whole process time: %.6f seconds%n", longestTextEntity.getDurationInSeconds());
        return longestTextEntity;
    }

    @PostMapping("/saveText")
    public void saveText(@RequestBody TextsRequest textsRequest) {
        textService.saveEntity(
                textsRequest.getTexts(),
                textsRequest.getMergedText(),
                textsRequest.getDurationInSeconds(),
                longestSentences
        );
    }

    private List<Integer> getRange(int size) {
        List<Integer> range = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            range.add(i);
        }
        return range;
    }

    @GetMapping("/texts")
    public List<TextEntity> getAllEntities() { return textService.getAllEntities(); }
}
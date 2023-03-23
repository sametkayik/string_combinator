package com.example.yazlab2_1.controllers;

import com.example.yazlab2_1.models.TextEntity;
import com.example.yazlab2_1.models.TextsRequest;
import com.example.yazlab2_1.services.TextService;
import com.example.yazlab2_1.services.MergeTextService;
import org.springframework.web.bind.annotation.*;
import java.text.DecimalFormat;
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

        long startTime = System.nanoTime();
        String mergedText = MergeTextService.mergeSentences(texts.toArray(new String[0]));
        long endTime = System.nanoTime();
        long durationTimeInNanoSec = (endTime - startTime);
        double durationTimeInSeconds = (double)durationTimeInNanoSec / 1_000_000_000.0;
        System.out.println("Merging Time in nanoseconds: " + durationTimeInNanoSec);
        System.out.println("Merging Time : " + new DecimalFormat("#.############").format(durationTimeInSeconds) + " seconds");

        TextEntity textEntity = new TextEntity();
        textEntity.setTexts(texts);
        textEntity.setMergedText(mergedText);
        textEntity.setDurationTime(durationTimeInSeconds);

        return textEntity;
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




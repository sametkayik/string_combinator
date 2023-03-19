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

        String mergedText = String.join(" ", texts);

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
}


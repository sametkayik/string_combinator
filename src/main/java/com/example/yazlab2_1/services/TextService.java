package com.example.yazlab2_1.services;

import com.example.yazlab2_1.models.TextEntity;
import com.example.yazlab2_1.repositories.TextRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TextService {
    private final TextRepository textRepository;

    public TextService(TextRepository myRepository) {
        this.textRepository = myRepository;
    }

    public void saveEntity(List<String> texts, String mergedText, double durationInSeconds, List<String> longestSentences) {
        TextEntity entity = new TextEntity(texts, mergedText, durationInSeconds);
        entity.setCreatedTime(LocalDateTime.now());
        entity.setLongestSentences(longestSentences);
        textRepository.save(entity);
    }
    public List<TextEntity> getAllEntities() {
        return textRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
    }

}


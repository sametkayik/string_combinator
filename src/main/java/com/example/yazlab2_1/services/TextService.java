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

    public TextEntity saveEntity(List<String> texts, String mergedText, double durationTime) {
        TextEntity entity = new TextEntity(texts, mergedText, durationTime);
        entity.setCreatedTime(LocalDateTime.now());
        return textRepository.save(entity);
    }

    public List<TextEntity> getAllEntities() {
        return textRepository.findAll(Sort.by(Sort.Direction.DESC, "createdTime"));
    }
}


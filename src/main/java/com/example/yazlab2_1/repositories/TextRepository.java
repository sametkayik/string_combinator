package com.example.yazlab2_1.repositories;

import com.example.yazlab2_1.models.TextEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TextRepository extends MongoRepository<TextEntity, String> {
}


package com.example.yazlab2_1.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "texts")
public class TextEntity {
    @Id
    private String _id;
    private List<String> texts;
    private String mergedText;
    public TextEntity() {}

    public TextEntity(List<String> texts, String mergedText) {
        this.texts = texts;
        this.mergedText = mergedText;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public String getMergedText() {
        return mergedText;
    }

    public void setMergedText(String mergedText) {
        this.mergedText = mergedText;
    }
}

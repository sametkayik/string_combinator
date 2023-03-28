package com.example.yazlab2_1.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "texts")
public class TextEntity {
    @Id
    private String _id;
    private List<String> texts;
    private List<String> longestSentences;
    private String mergedText;
    private double durationInSeconds;
    @CreatedDate
    private LocalDateTime createdTime;
    public TextEntity() {}

    public TextEntity(List<String> texts, String mergedText, double durationInSeconds) {
        this.texts = texts;
        this.mergedText = mergedText;
        this.durationInSeconds = durationInSeconds;
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
    public String getMergedText() { return mergedText; }
    public void setMergedText(String mergedText) {
        this.mergedText = mergedText;
    }
    public double getDurationInSeconds() {
        return durationInSeconds;
    }
    public void setDurationInSeconds(double durationTime) {
        this.durationInSeconds = durationTime;
    }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    public List<String> getLongestSentences() { return longestSentences;}
    public void setLongestSentences(List<String> longestSentences) { this.longestSentences = longestSentences; }
}

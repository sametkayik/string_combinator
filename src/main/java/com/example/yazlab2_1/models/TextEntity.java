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
    private String mergedText;
    private double durationTime;
    @CreatedDate
    private LocalDateTime createdTime;
    public TextEntity() {}

    public TextEntity(List<String> texts, String mergedText, double durationTime) {
        this.texts = texts;
        this.mergedText = mergedText;
        this.durationTime = durationTime;
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
    public double getDurationTime() {
        return durationTime;
    }
    public void setDurationTime(double durationTime) {
        this.durationTime = durationTime;
    }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
}

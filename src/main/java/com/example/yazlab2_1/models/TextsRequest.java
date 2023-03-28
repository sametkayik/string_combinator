package com.example.yazlab2_1.models;

import java.util.List;

public class TextsRequest {
    private List<String> texts;
    private double durationInSeconds;
    private String mergedText;

    public List<String> getTexts() {
        return texts;
    }
    public double getDurationInSeconds() { return durationInSeconds; }
    public String getMergedText() { return mergedText; }
}

